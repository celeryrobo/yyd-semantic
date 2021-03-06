package com.yyd.semantic.services.impl.story;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.story.StoryCategory;
import com.yyd.semantic.db.bean.story.StoryCategoryRelationship;
import com.yyd.semantic.db.bean.story.StoryCategoryResource;
import com.yyd.semantic.db.bean.story.StoryResource;
import com.yyd.semantic.db.service.story.StoryCategoryRelationshipService;
import com.yyd.semantic.db.service.story.StoryCategoryResourceService;
import com.yyd.semantic.db.service.story.StoryCategoryService;
import com.yyd.semantic.db.service.story.StoryResourceService;

@Component
public class StorySemantic implements Semantic<StoryBean> {
	public static final Integer SEMANTIC_SUCCESS = 0;
	public static final Integer SEMANTIC_FAILURE = 201;
	public static final String ERRO_ANSWER = "不好意思，我好像没听懂。。。";
	public static final String ERRO_MSG1 = "找不到相应资源";
	public static final String ERRO_MSG2 = "语义槽为空";
	public static final String ERRO_MSG3 = "意图为空";
	@Autowired
	private StoryCategoryService categoryService;

	@Autowired
	private StoryCategoryRelationshipService categoryRelaService;

	@Autowired
	private StoryCategoryResourceService categoryResourceService;

	@Autowired
	private StoryResourceService resourceService;

	@Override
	public StoryBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		StoryBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case StoryIntent.QUERY_CATEGORY: {
			result = queryCategory(objects, semanticContext);
			break;
		}
		case StoryIntent.QUERY_RESOURCE: {
			result = queryResource(objects, semanticContext);
			break;
		}
		default:
			result = new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG3);
			break;
		}
		return result;
	}

	private String textFormat(String text) {
		return String.format("请欣赏%s。", text);
	}

	private StoryBean queryResource(Map<String, String> slots, SemanticContext semanticContext) {
		if (slots.isEmpty()) {
			// 换一个故事，没有slot
			List<Integer> ids = resourceService.getIdList();
			if (!ids.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(ids.size());
				StoryResource story = resourceService.getById(ids.get(randomIdx));
				return new StoryBean(textFormat(story.getName()), story.getPlayUrl(), story);
			} else {
				return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG1);
			}
		} else {
			// 听某个具体的故事,有slot
			String name = slots.get(StorySlot.STORY_RESOURCE);
			if (name != null) {
				StoryResource story = resourceService.getByName(name);
				if (story != null) {
					return new StoryBean(textFormat(story.getName()), story.getPlayUrl(), story);
				} else {
					return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG1);
				}
			} else {
				return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG2);
			}
		}
	}

	private StoryBean queryCategory(Map<String, String> slots, SemanticContext semanticContext) {
		// 我要听某类故事,有slot
		String categoryName = slots.get(StorySlot.STORY_CATEGORY);
		if (categoryName != null) {
			StoryCategory category = categoryService.getByName(categoryName);
			if (category != null) {
				Integer categoryId = category.getId();
				List<StoryCategoryRelationship> list = categoryRelaService.getByParentId(categoryId);
				if (list != null) {
					return getStoryResult(list, categoryId);
				} else {
					return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG1);
				}
			} else {
				return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG1);
			}
		} else {
			return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG2);
		}
	}

	private StoryBean getStoryResult(List<StoryCategoryRelationship> list, Integer categoryId) {
		if (!list.isEmpty()) {
			// 非叶子类目
			while (!list.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(list.size());
				StoryCategoryRelationship scRel = list.get(randomIdx);
				list = categoryRelaService.getByParentId(scRel.getSubId());
				if (list.isEmpty()) {
					List<StoryCategoryResource> scResList = categoryResourceService.getByCategoryId(scRel.getSubId());
					if (!scResList.isEmpty()) {
						int randomIdx1 = CommonUtils.randomInt(scResList.size());
						StoryCategoryResource scRes = scResList.get(randomIdx1);
						StoryResource story = resourceService.getById(scRes.getResourceId());
						return new StoryBean(textFormat(story.getName()), story.getPlayUrl(), story);
					}
				}
			}
		} else {
			// 第一次就取到叶子类目
			List<StoryCategoryResource> scResList = categoryResourceService.getByCategoryId(categoryId);
			if (!scResList.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(scResList.size());
				StoryCategoryResource scRes = scResList.get(randomIdx);
				StoryResource story = resourceService.getById(scRes.getResourceId());
				if (story != null) {
					return new StoryBean(textFormat(story.getName()), story.getPlayUrl(), story);
				}
			}
		}
		return new StoryBean(ERRO_ANSWER, SEMANTIC_FAILURE, ERRO_MSG1);
	}
}
