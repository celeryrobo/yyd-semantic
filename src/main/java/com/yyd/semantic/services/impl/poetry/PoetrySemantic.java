package com.yyd.semantic.services.impl.poetry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.common.Number;
import com.yyd.semantic.db.bean.poetry.Author;
import com.yyd.semantic.db.bean.poetry.Poetry;
import com.yyd.semantic.db.bean.poetry.PoetrySentence;
import com.yyd.semantic.db.service.poetry.AuthorService;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;
import com.yyd.semantic.db.service.poetry.PoetryService;

@Component
public class PoetrySemantic implements Semantic<PoetryBean> {
	private final static Integer SEMANTIC_FAIL = 101;
	private final static Integer RESOURCE_NOTEXSIT = 102;

	private final static String SEMANTIC_ERR_MSG = "听不懂你说的什么";

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
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		// 查询诗词
		case PoetryIntent.QUERY_POETRY: {
			result = queryPoetry(objects, semanticContext);
			break;
		}
		// 查询标题
		case PoetryIntent.QUERY_TITLE: {
			result = queryTitle(objects, semanticContext);
			break;
		}
		// 查询作者
		case PoetryIntent.QUERY_AUTHOR: {
			result = queryAuthor(objects, semanticContext);
			break;
		}
		// 查询朝代
		case PoetryIntent.QUERY_DYNASTY: {
			result = queryDynasty(objects, semanticContext);
			break;
		}
		// 查询下一句诗
		case PoetryIntent.NEXT_SENTENCE: {
			result = nextSentence(objects, semanticContext);
			break;
		}
		// 查询上一句诗
		case PoetryIntent.PREV_SENTENCE: {
			result = prevSentence(objects, semanticContext);
			break;
		}
		// 查询当前这首诗
		case PoetryIntent.THIS_POETRY: {
			result = thisPoetry(objects, semanticContext);
			break;
		}
		// 查询指定的诗句
		case PoetryIntent.QUERY_SENTENCE: {
			result = querySentence(objects, semanticContext);
			break;
		}
		default: {
			result = new PoetryBean(SEMANTIC_FAIL, "这句话太复杂了，我还不能理解");
			break;
		}
		}
		return result;
	}

	private PoetryBean querySentence(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Poetry poetry = null;
		// 存在语义分析实体的情况
		String title = slots.get(PoetrySlot.POEM_TITLE);
		String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
		if (title != null) {
			List<Poetry> poetries = poetryService.getByTitle(title);
			if (poetries.isEmpty()) {
				result.setText("我不记得" + title + "这首诗了");
			} else {
				int idx = CommonUtils.randomInt(poetries.size());
				poetry = poetries.get(idx);
			}
		} else if (sentence != null) {
			List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
			if (poetrySentences.isEmpty()) {
				result.setText("我不记得" + sentence + "这首诗了");
			} else {
				int idx = CommonUtils.randomInt(poetrySentences.size());
				PoetrySentence poetrySentence = poetrySentences.get(idx);
				poetry = poetryService.getById(poetrySentence.getPoetryId());
			}
		}
		if (poetry == null) {
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
			}
		}
		if (poetry != null) {
			ps.clear();
			ps.setPoemId(poetry.getId());
			String numberStr = slots.get(PoetrySlot.POEM_NUMBER);
			if (numberStr != null) {
				Number number = CommonUtils.strToNumber(numberStr);
				if (number.isSuccess()) {
					List<PoetrySentence> poetrySentences = poetrySentenceService.getByPoetryId(poetry.getId());
					if (!poetrySentences.isEmpty()) {
						result.setErrCode(0);
						int index = number.getNumber().intValue();
						if (index < 1) {
							result.setText("诗句是从第一句开始的");
						} else if (index > poetrySentences.size()) {
							result.setText("这首诗我只知道" + poetrySentences.size() + "句");
						} else {
							PoetrySentence poetrySentence = poetrySentences.get(index - 1);
							ps.setPoemCurSentenceIndex(index - 1);
							result.setText(poetrySentence.getSentence());
						}
					}
				}
			}
			result.setResource(poetry);
			result.setErrCode(0);
		}
		return result;
	}

	private PoetryBean thisPoetry(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer poemId = ps.getPoemId();
		if (poemId != null) {
			Poetry poetry = poetryService.getById(poemId);
			result.setText(poetry.toString());
			result.setErrCode(0);
			result.setResource(poetry);
		}
		return result;
	}

	private PoetryBean queryPoetry(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Poetry poetry = null;
		if (slots.isEmpty()) {
			List<Integer> ids = poetryService.getIdList();
			if (!ids.isEmpty()) {
				int randomIdx = CommonUtils.randomInt(ids.size());
				poetry = poetryService.getById(ids.get(randomIdx));
			}
		} else {
			// 存在语义分析实体的情况
			String author = slots.get(PoetrySlot.POEM_AUTHOR);
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result.setText("我不记得" + title + "这首诗了");
				} else {
					int randomIdx = CommonUtils.randomInt(poetries.size());
					poetry = poetries.get(randomIdx);
					if (author != null) {
						for (Poetry poem : poetries) {
							if (author.equalsIgnoreCase(poem.getAuthorName())) {
								poetry = poem;
								break;
							}
						}
					}
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result.setText("我不记得" + sentence + "这句诗了");
				} else {
					PoetrySentence poetrySentence = poetrySentences.get(0);
					poetry = poetryService.getById(poetrySentence.getPoetryId());
					if (poetry == null) {
						result.setText("我不记得" + sentence + "这句诗了");
					}
				}
			} else if (author != null) {
				List<Poetry> poetries = poetryService.getByAuthor(author);
				if (poetries.isEmpty()) {
					result.setText("我没有读过" + author + "的诗");
				} else {
					int randomIdx = CommonUtils.randomInt(poetries.size());
					poetry = poetries.get(randomIdx);
				}
			}
		}
		if (poetry != null) {
			ps.clear();
			ps.setPoemId(poetry.getId());
			result.setText(poetry.toString());
			result.setErrCode(0);
			result.setResource(poetry);
		}
		return result;
	}

	private PoetryBean queryTitle(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Poetry poetry = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
			}
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result.setText("我不记得" + sentence + "这句诗了");
				} else {
					poetry = poetryService.getById(poetrySentences.get(0).getPoetryId());
				}
			}
			if (poetry != null) {
				ps.clear();
				ps.setPoemId(poetry.getId());
				result.setText(poetry.getTitle());
				result.setErrCode(0);
				result.setResource(poetry);
			}
		}
		return result;
	}

	private PoetryBean queryAuthor(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Poetry poetry = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
			}
		} else {
			// 存在语义分析实体的情况
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result.setText("我不记得" + title + "这首诗了");
				} else {
					poetry = poetries.get(0);
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				if (poetrySentences.isEmpty()) {
					result.setText("我不记得" + sentence + "这句诗了");
				} else {
					poetry = poetryService.getById(poetrySentences.get(0).getPoetryId());
				}
			}
			if (poetry != null) {
				ps.clear();
				ps.setPoemId(poetry.getId());
				result.setText(poetry.getAuthorName());
				result.setErrCode(0);
				result.setResource(poetry);
			}
		}
		return result;
	}

	private PoetryBean queryDynasty(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Poetry poetry = null;
		String chaoDai = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
				Author author = authorService.getAuthorById(poetry.getAuthorId());
				chaoDai = author.getChaodai();
			}
		} else {
			// 存在语义分析实体的情况
			String author = slots.get(PoetrySlot.POEM_AUTHOR);
			String title = slots.get(PoetrySlot.POEM_TITLE);
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			if (author != null) {
				List<Author> authors = authorService.getByName(author);
				if (authors.isEmpty()) {
					result.setText("我忘记了" + author + "是哪个朝代的了");
				} else {
					chaoDai = authors.get(0).getChaodai();
				}
			} else if (title != null) {
				List<Poetry> poetries = poetryService.getByTitle(title);
				if (poetries.isEmpty()) {
					result.setText("我忘记了" + title + "这首诗是哪个朝代的了");
				} else {
					poetry = poetries.get(0);
					Integer author_id = poetry.getAuthorId();
					Author authEntity = authorService.getAuthorById(author_id);
					chaoDai = authEntity.getChaodai();
				}
			} else if (sentence != null) {
				List<PoetrySentence> poetrySentences = poetrySentenceService.getBySent(sentence);
				poetry = poetryService.getById(poetrySentences.get(0).getPoetryId());
				if (poetry == null) {
					result.setText("我忘记了" + sentence + "这句诗是哪个朝代的了");
				} else {
					Author authEntity = authorService.getAuthorById(poetry.getAuthorId());
					chaoDai = authEntity.getChaodai();
				}
			}
			if (poetry != null) {
				ps.clear();
				ps.setPoemId(poetry.getId());
				result.setResource(poetry);
			}
		}
		if (chaoDai != null) {
			result.setText(chaoDai);
			result.setErrCode(0);
		}
		return result;
	}

	private PoetryBean nextSentence(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer sentenceIndex = null;
		Poetry poetry = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			sentenceIndex = ps.getPoemCurSentenceIndex();
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
			}
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			List<PoetrySentence> sentences = poetrySentenceService.getBySent(sentence);
			if (!sentences.isEmpty()) {
				poetry = poetryService.getById(sentences.get(0).getPoetryId());
				sentenceIndex = 0;
				// 根据诗的id获取所有诗句
				sentences = poetrySentenceService.getByPoetryId(poetry.getId());
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
		if (poetry != null) {
			String res = null;
			List<PoetrySentence> poetrySentences = poetrySentenceService.getByPoetryId(poetry.getId());
			Integer endIndex = poetrySentences.size() - 1;
			if (sentenceIndex == null) {
				sentenceIndex = -1;
			}
			if (sentenceIndex >= endIndex) {
				res = "这已经是最后一句了";
				if (slots.containsKey(PoetrySlot.POEM_SENTENCE)) {
					res = "这是诗人" + poetry.getAuthorName() + "所写的" + poetry.getTitle() + "的最后一句";
				}
				sentenceIndex = endIndex;
			} else {
				sentenceIndex += 1;
				PoetrySentence poetrySentence = poetrySentences.get(sentenceIndex);
				res = poetrySentence.getSentence();
			}
			ps.setPoemId(poetry.getId());
			ps.setPoemCurSentenceIndex(sentenceIndex);
			result.setText(res);
			result.setErrCode(0);
			result.setResource(poetry);
		}
		return result;
	}

	private PoetryBean prevSentence(Map<String, String> slots, SemanticContext semanticContext) {
		PoetryBean result = new PoetryBean(RESOURCE_NOTEXSIT, SEMANTIC_ERR_MSG);
		PoetrySlot ps = new PoetrySlot(semanticContext.getParams());
		Integer sentenceIndex = null;
		Poetry poetry = null;
		if (slots.isEmpty()) {
			// 不存在语义分析实体的情况
			Integer poemId = ps.getPoemId();
			if (poemId != null) {
				poetry = poetryService.getById(poemId);
			}
			sentenceIndex = ps.getPoemCurSentenceIndex();
			if (sentenceIndex == null) {
				return result;
			}
		} else {
			// 存在语义分析实体的情况
			String sentence = slots.get(PoetrySlot.POEM_SENTENCE);
			List<PoetrySentence> sentences = poetrySentenceService.getBySent(sentence);
			if (!sentences.isEmpty()) {
				// 根据诗的id获取所有诗句
				sentences = poetrySentenceService.getByPoetryId(sentences.get(0).getPoetryId());
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
		if (poetry != null) {
			String res = null;
			List<PoetrySentence> poetrySentences = poetrySentenceService.getByPoetryId(poetry.getId());
			if (sentenceIndex == null) {
				sentenceIndex = 0;
			}
			if (sentenceIndex <= 0) {
				res = "这已经是第一句了";
				sentenceIndex = 0;
			} else {
				sentenceIndex -= 1;
				PoetrySentence poetrySentence = poetrySentences.get(sentenceIndex);
				res = poetrySentence.getSentence();
			}
			ps.setPoemId(poetry.getId());
			ps.setPoemCurSentenceIndex(sentenceIndex);
			result.setText(res);
			result.setErrCode(0);
			result.setResource(poetry);
		}
		return result;
	}
}
