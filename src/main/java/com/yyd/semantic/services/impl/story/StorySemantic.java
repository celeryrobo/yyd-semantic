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
		String action = slots.get("action");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		System.out.println("---action---->"+action);
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
			result = new StoryBean("听不懂你说的是什么");
			break;
		}

		return result;
	}

	private StoryBean queryResource(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的是什么";
		Integer storyId = null;
		System.out.println("------->"+slots.isEmpty());
		if (slots.isEmpty()) {
			// 换一个故事，没有slot
			List<Integer> ids = resourceService.getIdList();
			if (!ids.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(ids.size());
				storyId = ids.get(randomIdx);
				StoryResource story = resourceService.getById(storyId);
				result = story.getContentUrl();
			}
		} else {
			// 听某个具体的故事,有slot
			String storyName = slots.get(StorySlot.STORY_RESOURCE);
			if (storyName != null) {
				StoryResource story = resourceService.getByName(storyName);
				if (story == null) {
					result = "没有找到你要的资源";
				} else {
					result = story.getContentUrl();
				}
			}
		}
		return new StoryBean(result);
	}

	private StoryBean queryCategory(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的是什么";
		Integer storyId = null;
		if (slots.isEmpty()) {
			// 换一个某类故事，没有slot
			List<Integer> ids = resourceService.getIdList();
			if (!ids.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(ids.size());
				storyId = ids.get(randomIdx);
				StoryResource story = resourceService.getById(storyId);
				result = story.getContentUrl();
			}
		} else {
			// 我要听某类故事,有slot
			String storyName = slots.get(StorySlot.STORY_CATEGORY);
			if (storyName != null) {
				StoryCategory category = categoryService.getByName(storyName);
				
				if (category == null) {
					result = "没有找到你要的资源";
				} else {
					Integer categoryId = category.getId();
					result = getStoryResult(categoryRelaService.getByParentId(categoryId), categoryId);
				}
			}
		}
		return new StoryBean(result);
	}

	private String getStoryResult(List<StoryCategoryRelationship> list, Integer categoryId) {
		String result = null;
		if (!list.isEmpty()) {
			while (!list.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(list.size());
				StoryCategoryRelationship scRel = list.get(randomIdx);
				list = categoryRelaService.getByParentId(scRel.getSubId());
				if (list.isEmpty()) {
					List<StoryCategoryResource> scResList = categoryResourceService.getByCategoryId(scRel.getSubId());
					int randomIdx1 = CommonUtils.randomInt(scResList.size());
					StoryCategoryResource scRes = scResList.get(randomIdx1);
					StoryResource story = resourceService.getById(scRes.getResourceId());
					result = story.getContentUrl();
				}
			}
		} else {
			List<StoryCategoryResource> scResList = categoryResourceService.getByCategoryId(categoryId);
			int randomIdx = CommonUtils.randomInt(scResList.size());
			StoryCategoryResource scRes = scResList.get(randomIdx);
			StoryResource story = resourceService.getById(scRes.getResourceId());
			result = story.getContentUrl();
		}

		return result;

	}
}
