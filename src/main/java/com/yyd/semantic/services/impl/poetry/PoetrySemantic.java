package com.yyd.semantic.services.impl.poetry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.poetry.Author;
import com.yyd.semantic.db.bean.poetry.Poetry;
import com.yyd.semantic.db.bean.poetry.PoetrySentence;
import com.yyd.semantic.db.service.poetry.AuthorService;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;
import com.yyd.semantic.db.service.poetry.PoetryService;

@Component
public class PoetrySemantic implements Semantic<PoetryBean> {
	@Autowired
	private AuthorService authorService;

	@Autowired
	private PoetryService poetryService;

	@Autowired
	private PoetrySentenceService poetrySentenceService;

	@Override
	public PoetryBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("action");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case PoetryIntent.QUERY_POETRY: {
			result = queryPoetry(objects, semanticContext);
			break;
		}
		case PoetryIntent.QUERY_TITLE: {
			result = queryTitle(objects, semanticContext);
			break;
		}
		case PoetryIntent.QUERY_AUTHOR: {
			result = queryAuthor(objects, semanticContext);
			break;
		}
		case PoetryIntent.QUERY_DYNASTY: {
			result = queryDynasty(objects, semanticContext);
			break;
		}
		case PoetryIntent.NEXT_SENTENCE: {
			result = nextSentence(objects, semanticContext);
			break;
		}
		case PoetryIntent.PREV_SENTENCE: {
			result = prevSentence(objects, semanticContext);
			break;
		}
		default: {
			result = new PoetryBean(0, "这句话太复杂了，我还不能理解");
			break;
		}
		}
		return result;
	}

	private PoetryBean queryPoetry(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null;
		if (slots.isEmpty()) {
			List<Integer> ids = poetryService.getIdList();
			if (!ids.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(ids.size());
				poemId = ids.get(randomIdx);
				Poetry poetry = poetryService.getById(poemId);
				result = poetry.toString();
			}
		} else {
			// 存在语义分析实体的情况
			String author = slots.get(PoetrySlot.POEM_AUTHOR);
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result = "我不记得" + title + "这首诗了";
				} else {
					int randomIdx = CommonUtils.randomInt(poetries.size());
					Poetry poetry = poetries.get(randomIdx);
					if (author != null) {
						for (Poetry poem : poetries) {
							if (author.equalsIgnoreCase(poem.getAuthorName())) {
								poetry = poem;
								break;
							}
						}
					}
					poemId = poetry.getId();
					result = poetry.toString();
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result = "我不记得" + sentence + "这句诗了";
				} else {
					PoetrySentence poetrySentence = poetrySentences.get(0);
					Poetry poetry = poetryService.getById(poetrySentence.getPoetryId());
					if (poetry == null) {
						result = "我不记得" + sentence + "这句诗了";
					} else {
						poemId = poetry.getId();
						result = poetry.toString();
					}
				}
			} else if (author != null) {
				List<Poetry> poetries = poetryService.getByAuthor(author);
				if (poetries.isEmpty()) {
					result = "我没有读过" + author + "的诗";
				} else {
					int randomIdx = CommonUtils.randomInt(poetries.size());
					Poetry poetry = poetries.get(randomIdx);
					poemId = poetry.getId();
					result = poetry.toString();
				}
			}
		}
		if (poemId != null) {
			ps.clear();
			ps.setPoemId(poemId);
		}
		return new PoetryBean(poemId, result);
	}

	private PoetryBean queryTitle(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			poemId = ps.getPoemId();
			Poetry poetry = poetryService.getById(poemId);
			result = poetry.getTitle();
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result = "我不记得" + sentence + "这句诗了";
				} else {
					poemId = poetrySentences.get(0).getPoetryId();
					Poetry poetry = poetryService.getById(poemId);
					result = poetry.getTitle();
				}
			}
			if (poemId != null) {
				ps.clear();
				ps.setPoemId(poemId);
			}
		}
		return new PoetryBean(poemId, result);
	}

	private PoetryBean queryAuthor(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			poemId = ps.getPoemId();
			Poetry poetry = poetryService.getById(poemId);
			result = poetry.getAuthorName();
		} else {
			// 存在语义分析实体的情况
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result = "我不记得" + title + "这首诗了";
				} else {
					Poetry poetry = poetries.get(0);
					result = poetry.getAuthorName();
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result = "我不记得" + sentence + "这句诗了";
				} else {
					poemId = poetrySentences.get(0).getPoetryId();
					Poetry poetry = poetryService.getById(poemId);
					result = poetry.getAuthorName();
				}
			}
			if (poemId != null) {
				ps.clear();
				ps.setPoemId(poemId);
			}
		}
		return new PoetryBean(poemId, result);
	}

	private PoetryBean queryDynasty(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			poemId = ps.getPoemId();
			Poetry poetry = poetryService.getById(poemId);
			Author author = authorService.getAuthorById(poetry.getAuthorId());
			result = author.getChaodai();
		} else {
			// 存在语义分析实体的情况
			String author = slots.get(PoetrySlot.POEM_AUTHOR);
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (author != null) {
				List<Author> authors = authorService.getByName(author);
				if (authors.isEmpty()) {
					result = "我忘记了" + author + "是哪个朝代的了";
				} else {
					result = authors.get(0).getChaodai();
				}
			} else if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result = "我忘记了" + title + "这首诗是哪个朝代的了";
				} else {
					Poetry poetry = poetries.get(0);
					poemId = poetry.getId();
					Integer author_id = poetry.getAuthorId();
					Author authEntity = authorService.getAuthorById(author_id);
					result = authEntity.getChaodai();
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				poemId = poetrySentences.get(0).getPoetryId();
				Poetry poetry = poetryService.getById(poemId);
				if (poetry == null) {
					result = "我忘记了" + sentence + "这句诗是哪个朝代的了";
				} else {
					Author authEntity = authorService.getAuthorById(poetry.getAuthorId());
					result = authEntity.getChaodai();
				}
			}
			if (poemId != null) {
				ps.clear();
				ps.setPoemId(poemId);
			}
		}
		return new PoetryBean(poemId, result);
	}

	private PoetryBean nextSentence(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null, sentenceIndex = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			poemId = ps.getPoemId();
			sentenceIndex = ps.getPoemCurSentenceIndex();
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			List<PoetrySentence> sentences = poetrySentenceService.getBySent(sentence);
			if (!sentences.isEmpty()) {
				poemId = sentences.get(0).getPoetryId();
				sentenceIndex = 0;
				// 根据诗的id获取所有诗句
				sentences = poetrySentenceService.getByPoetryId(poemId);
				if (!sentences.isEmpty()) {
					for (int i = 0; i < sentences.size(); i++) {
						if (sentence.equals(sentences.get(i).getSentence())) {
							sentenceIndex = i;
							break;
						}
					}
				}
			}
		}
		if (poemId != null) {
			List<PoetrySentence> poetrySentences = poetrySentenceService.getByPoetryId(poemId);
			Integer endIndex = poetrySentences.size() - 1;
			if (sentenceIndex == null) {
				sentenceIndex = -1;
			}
			if (sentenceIndex >= endIndex) {
				result = "这已经是最后一句了";
				if (slots.containsKey(PoetrySlot.POEM_SENTENCE)) {
					Poetry poetry = poetryService.getById(poemId);
					if (poetry != null) {
						result = "这是诗人" + poetry.getAuthorName() + "所写的" + poetry.getTitle() + "的最后一句";
					}
				}
				sentenceIndex = endIndex;
			} else {
				sentenceIndex += 1;
				PoetrySentence poetrySentence = poetrySentences.get(sentenceIndex);
				result = poetrySentence.getSentence();
			}
			ps.setPoemId(poemId);
			ps.setPoemCurSentenceIndex(sentenceIndex);
		}
		return new PoetryBean(poemId, result);
	}

	private PoetryBean prevSentence(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "听不懂你说的什么";
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = null, sentenceIndex = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			poemId = ps.getPoemId();
			sentenceIndex = ps.getPoemCurSentenceIndex();
			if (sentenceIndex == null) {
				return new PoetryBean(poemId, result);
			}
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			List<PoetrySentence> sentences = poetrySentenceService.getBySent(sentence);
			if (!sentences.isEmpty()) {
				poemId = sentences.get(0).getPoetryId();
				// 根据诗的id获取所有诗句
				sentences = poetrySentenceService.getByPoetryId(poemId);
				if (!sentences.isEmpty()) {
					for (int i = 0; i < sentences.size(); i++) {
						if (sentence.equals(sentences.get(i).getSentence())) {
							sentenceIndex = i;
							break;
						}
					}
				}
			}
		}
		if (poemId != null) {
			List<PoetrySentence> poetrySentences = poetrySentenceService.getByPoetryId(poemId);
			if (sentenceIndex == null) {
				sentenceIndex = 0;
			}
			if (sentenceIndex <= 0) {
				result = "这已经是第一句了";
				sentenceIndex = 0;
			} else {
				sentenceIndex -= 1;
				PoetrySentence poetrySentence = poetrySentences.get(sentenceIndex);
				result = poetrySentence.getSentence();
			}
			ps.setPoemId(poemId);
			ps.setPoemCurSentenceIndex(sentenceIndex);
		}
		return new PoetryBean(poemId, result);
	}
}
