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
		case IdiomIntent.PLAYING:
			idiomBean = playing(objects, semanticContext);
			break;
		case IdiomIntent.REREAD:
			idiomBean = reread(objects, semanticContext);
			break;
		default:
			idiomBean = new IdiomBean("这句话太复杂了，我还不能理解", null);
			break;
		}
		return idiomBean;
	}

	private IdiomBean reread(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么！";
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		Integer idiomId = slot.getIdiomId();
		if (idiomId != null) {
			Idiom idiom = idiomService.getById(idiomId);
			result = idiom.getContent();
		}
		return new IdiomBean(result, null);
	}

	private IdiomBean ready(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "好啊，你先说一个成语，我来接吧！";
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		slot.clear();
		return new IdiomBean(result, null);
	}

	private IdiomBean playing(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "好吧，你赢了！";
		IdiomSlot slot = new IdiomSlot(semanticContext.getParams());
		String content = slots.get(IdiomSlot.IDIOM_CONTENT);
		// 用户说的成语
		Idiom idiom = idiomService.getByContent(content);
		if (idiom == null) {
			result = "你确定" + content + "是成语吗？";
		} else {
			Integer idiomId = slot.getIdiomId();
			// 判断成语是否符合游戏规则
			if (idiomId != null) {
				Idiom im = idiomService.getById(idiomId);
				if (im == null || !(im.getPyLast() == null ? false : im.getPyLast().equals(idiom.getPyFirst()))) {
					return new IdiomBean("你说的" + idiom.getContent() + "，这个成语不符合要求", null);
				}
			}
			// 获取到匹配成语
			List<Idiom> idioms = idiomService.findByPyFirst(idiom.getPyLast());
			if (!idioms.isEmpty()) {
				idiom = idioms.get(CommonUtils.randomInt(idioms.size()));
				slot.setIdiomId(idiom.getId());// 保存当前成语
				result = idiom.getContent();
				idiom.setRefcount(idiom.getRefcount() + 1);
				idiomService.update(idiom);
			}
		}
		return new IdiomBean(result, idiom);
	}
}
