package com.yyd.semantic.services.impl.idiom;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.idiom.Idiom;
import com.yyd.semantic.db.service.idiom.IdiomService;

@Component
public class IdiomSemantic implements Semantic<IdiomBean> {
	private final static Integer SEMANTIC_FAIL = 101;
	private final static Integer RESOURCE_NOTEXSIT = 102;
	private final static Integer GAME_RULE_ERR = 111;

	private final static String SEMANTIC_ERR_MSG = "听不懂你说的什么";

	@Autowired
	private IdiomService idiomService;

	@Override
	public IdiomBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		IdiomBean idiomBean = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String intent = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (intent) {
		case IdiomIntent.READY:
			idiomBean = ready(objects, semanticContext);
			break;
		case IdiomIntent.SURRENDER:
			idiomBean = surrender(objects, semanticContext);
			break;
		case IdiomIntent.PLAYING:
			idiomBean = playing(objects, semanticContext);
			break;
		case IdiomIntent.REREAD:
			idiomBean = reread(objects, semanticContext);
			break;
		default:
			idiomBean = new IdiomBean(SEMANTIC_FAIL, "这句话太复杂了，我还不能理解");
			break;
		}
		return idiomBean;
	}

	private IdiomBean surrender(Map<String, String> slots, SemanticContext semanticContext) {
		IdiomBean result = new IdiomBean(0, "哈哈，我赢了！");
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		if (slot.isEmpty()) {
			result.setText("咱们还没开始玩呢，你就认输了呀！");
		} else {
			slot.clear();
		}
		return result;
	}

	private IdiomBean reread(Map<String, String> slots, SemanticContext semanticContext) {
		IdiomBean result = new IdiomBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		Idiom idiom = null;
		Integer idiomId = slot.getIdiomId();
		if (idiomId != null) {
			idiom = idiomService.getById(idiomId);
			result.setText(idiom.getContent());
			result.setErrCode(0);
			result.setResource(idiom);
		}
		return result;
	}

	private IdiomBean ready(Map<String, String> slots, SemanticContext semanticContext) {
		IdiomBean result = new IdiomBean(0, "好啊，你先说一个成语，我来接吧！");
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		slot.clear();
		return result;
	}

	private IdiomBean playing(Map<String, String> slots, SemanticContext semanticContext) {
		IdiomBean result = new IdiomBean(0, "好吧，你赢了！");
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		String content = slots.get(IdiomSlot.IDIOM_CONTENT);
		// 用户说的成语
		Idiom idiom = idiomService.getByContent(content);
		if (idiom == null) {
			result.setText("你确定" + content + "是成语吗？");
			result.setErrCode(GAME_RULE_ERR);
		} else {
			Integer idiomId = slot.getIdiomId();
			// 判断成语是否符合游戏规则
			if (idiomId != null) {
				Idiom im = idiomService.getById(idiomId);
				if (im == null || !(im.getPyLast() == null ? false : im.getPyLast().equals(idiom.getPyFirst()))) {
					result.setText("你说的" + idiom.getContent() + "，这个成语不符合要求");
					result.setErrCode(GAME_RULE_ERR);
					return result;
				}
			}
			// 获取到匹配成语
			List<Idiom> idioms = idiomService.findByPyFirst(idiom.getPyLast());
			if (!idioms.isEmpty()) {
				idiom = idioms.get(CommonUtils.randomInt(idioms.size()));
				slot.setIdiomId(idiom.getId());// 保存当前成语
				result.setText(idiom.getContent());
				result.setErrCode(0);
				result.setResource(idiom);
				idiom.setRefcount(idiom.getRefcount() + 1);
				idiomService.update(idiom);
			}
		}
		return result;
	}
}
