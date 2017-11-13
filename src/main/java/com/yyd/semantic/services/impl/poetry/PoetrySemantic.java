package com.yyd.semantic.services.impl.poetry;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.poetry.Author;
import com.yyd.semantic.db.service.poetry.AuthorService;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;
import com.yyd.semantic.db.service.poetry.PoetryService;

@Service
public class PoetrySemantic implements Semantic<PoetryBean>{
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private PoetryService poetryService;
	
	@Autowired
	private PoetrySentenceService poetrySentenceService;
	
	
	@Override
	public PoetryBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String text = ybnfCompileResult.toString();
		PoetryBean rs = new PoetryBean(10, text);
		rs.setErrCode(0);
		
		PoetryBean result = null;
		Map<String,String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("action");
		Map<String,String> objects = ybnfCompileResult.getObjects();
		switch(action) {
			case PoetryIntent.QUERY_POETRY:
			{
				result = queryPoetry(objects,semanticContext);
				break;
			}
			case PoetryIntent.PREV_SENTENCE:
			{
				result = queryLastSent(objects,semanticContext);
				break;
			}
			case PoetryIntent.NEXT_SENTENCE:
			{
				result = queryNextSent(objects,semanticContext);
				break;
			}
			case PoetryIntent.QUERY_AUTHOR:
			{
				result = queryAuthor(objects,semanticContext);
				break;
			}
			case PoetryIntent.QUERY_DYNASTY:
			{
				result = queryDynasty(objects,semanticContext);
				break;
			}
			case PoetryIntent.QUERY_TITLE:
			{
				result = queryTitle(objects,semanticContext);
				break;
			}
			default:
			{
				result = new PoetryBean(10, "这句话太复杂了，我还不能理解");
				break;
			}
			
		}			
	
		return result;
	}
	
	/**
	 * 查询上一句
	 * @param slots
	 * @return
	 */
	private PoetryBean queryLastSent(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
		
		String sentence = null;
		sentence= slots.get(PoetrySlot.POEM_CUR_SENTENCE);
					
		if(null == sentence) {//查询条件没有指定诗句，因此根据上下文寻找下一句
			if(null == semanticContext || semanticContext.getParams().isEmpty()) {
				serviceResult ="缺少上下文，无法回答这个问题";
				poetryBean.setText(serviceResult);
				poetryBean.setId(10);
				return poetryBean;
			}
			
			//从上下文中获取古诗的所有内容
			String currentSentence = (String)semanticContext.getParams().get(PoetrySlot.POEM_CUR_SENTENCE);
			String entireSentence =(String)semanticContext.getParams().get(PoetrySlot.POEM_CONTEXT);
			SentenceResult lastSentence = getLastSentence(entireSentence,currentSentence);
			
			if(null != lastSentence) {
				serviceResult = lastSentence.get();
				if(lastSentence.isSentence()) {
					semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, serviceResult);
				}				
			}
			
		}
		else
		{  
			//根据问题中的一诗句查找上一句(从数据库检索),它和上下文中的诗也许是一样的，那也直接从数据库查找新诗
			String currentSentence = sentence;
			Poem poem = PoetryCommonService.getBySent(this.authorService,this.poetryService,this.poetrySentenceService,sentence);
			
			if(null != poem) {
				PoetryCommonService.buildContext(semanticContext, poem);
				String entireSentence = poem.getSentence();				
				SentenceResult lastSentence = getLastSentence(entireSentence,currentSentence);			
				serviceResult =lastSentence.get();				
				if(lastSentence.isSentence()) {
					semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, lastSentence.get());
				}
			
			}
			else
			{
				serviceResult = "没有要找的诗";	
			}
			
			
		}		
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;
	}
	
	private SentenceResult getLastSentence(String context,String currentSentence) {
		SentenceResult lastResult = null;
		
		if(null == context) {
			return lastResult;
		}
		
		String lastSentence = null;
		String[] sentences = context.split("\\.|,|，|。");
		
		//如果当前句为null,则返回第一句
		if(null == currentSentence) {
			if(sentences.length >0 ) {
				lastSentence = sentences[0];
				lastResult = new SentenceResult(lastSentence,true);
			}
			else
			{
				lastSentence = "缺省上下文，无法获取上一句";
				lastResult = new SentenceResult(lastSentence,false);
			}
			return lastResult;
		}
		
		for(int i =0;i < sentences.length;i++) {
			if(sentences[i].equalsIgnoreCase(currentSentence)){
				if((i-1) < 0) {
					lastSentence = "这已经是第一句了";
					lastResult = new SentenceResult(lastSentence,false);
				}
				else
				{
					lastSentence = sentences[i-1];
					lastResult = new SentenceResult(lastSentence,true);
				}	
				break;
			}
		}
		
		
		return lastResult;
	}
	
	/**
	 * 查询下一句
	 * @param slots
	 * @return
	 */
	private PoetryBean queryNextSent(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
		
		String sentence = slots.get(PoetrySlot.POEM_CUR_SENTENCE);
		if(null == sentence) {//根据上下文寻找下一句
			if(null == semanticContext || semanticContext.getParams().isEmpty()) {
				serviceResult ="缺少上下文，无法回答这个问题";
				poetryBean.setText(serviceResult);
				poetryBean.setId(10);
				return poetryBean;
			}
			
			//获取古诗的所有内容
			String currentSentence = (String)semanticContext.getParams().get(PoetrySlot.POEM_CUR_SENTENCE);
			String entireSentence = (String)semanticContext.getParams().get(PoetrySlot.POEM_CONTEXT);
			SentenceResult nextSentence = getNextSentence(entireSentence,currentSentence);
			
			if(null != nextSentence) {
				serviceResult = nextSentence.get();
				if(nextSentence.isSentence()) {
					semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, serviceResult);
				}	
			}
		
		}
		else
		{  
			//根据问题中的一诗句查找下一句(从数据库检索),它和上下文中的诗也许是一样的，那也直接从数据库查找新诗
			String currentSentence = sentence;
			Poem poem = PoetryCommonService.getBySent(this.authorService,this.poetryService,this.poetrySentenceService,sentence);
			
			if(null != poem) {
				PoetryCommonService.buildContext(semanticContext, poem);
				String entireSentence = poem.getSentence();				
				SentenceResult nextSentence = getNextSentence(entireSentence,currentSentence);			
				serviceResult = nextSentence.get();				
				if(nextSentence.isSentence()) {
					semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, serviceResult);
				}	
			}
			else
			{
				serviceResult = "没有要找的诗";	
			}
			
		}
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;
	}
	
	private SentenceResult getNextSentence(String context,String currentSentence) {
		SentenceResult nextResult = null;
		String nextSentence = null;
		String[] sentences = context.split("\\.|,|，|。");
		
		//如果当前句为null,则返回第一句
		if(null == currentSentence) {
			if(sentences.length >0 ) {
				nextSentence = sentences[0];
				nextResult = new SentenceResult(nextSentence,true);
			}
			else
			{
				nextSentence = "缺省上下文，无法获取下一句";
				nextResult = new SentenceResult(nextSentence,false);
			}
			return nextResult;
		}
		
		
		for(int i =0;i < sentences.length;i++) {
			if(sentences[i].equalsIgnoreCase(currentSentence)){
				if((i+1) >=sentences.length) {
					nextSentence = "这已经是最后一句了";
					nextResult = new SentenceResult(nextSentence,false);
				}
				else
				{
					nextSentence = sentences[i+1];
					nextResult = new SentenceResult(nextSentence,true);
				}	
				break;
			}
		}
		
		return nextResult;
	}
	
	/**
	 * 查询诗的作者
	 * @param slots
	 * @return
	 */
	private PoetryBean queryAuthor(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
				
		String curTitle = slots.get(PoetrySlot.POEM_TITLE);
		String curSentence = slots.get(PoetrySlot.POEM_CUR_SENTENCE);
		
		if(null != curTitle) {
			Poem poem = PoetryCommonService.getByTitle(this.authorService,this.poetryService,curTitle);
			if(null != poem) {
				serviceResult = poem.getAuthor();	
				//更新一首诗的上下文
				PoetryCommonService.buildContext(semanticContext, poem);
			}
			else
			{
				serviceResult ="没有要找的诗";
			}
			
		}
		else if(null != curSentence) 
		{
			Poem poem = PoetryCommonService.getBySent(this.authorService,this.poetryService,this.poetrySentenceService,curSentence);
			if(null != poem) {
				serviceResult = poem.getAuthor();			
				//更新一首诗的上下文
				PoetryCommonService.buildContext(semanticContext, poem);
				semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, curSentence);
			}
			else
			{
				serviceResult = "没有要找的诗";
			}
			
		}
		else
		{
			if(null == semanticContext) {
				serviceResult ="缺少上下文，无法回答这个问题";
				poetryBean.setText(serviceResult);
				poetryBean.setId(10);
				return poetryBean;
			}
			String author = (String)semanticContext.getParams().get(PoetrySlot.POEM_AUTHOR);
			serviceResult = author;
		}
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;
	}
	
	/**
	 * 查询朝代
	 * @param slots
	 * @return
	 */
	private PoetryBean queryDynasty(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
		
		String curTitle = slots.get(PoetrySlot.POEM_TITLE);
		String curSentence = slots.get(PoetrySlot.POEM_CUR_SENTENCE);
		String curAuthor = slots.get(PoetrySlot.POEM_AUTHOR);
		
		if(null != curTitle){
			Poem poem = PoetryCommonService.getByTitle(this.authorService,this.poetryService,curTitle);
			if(null != poem) {
				serviceResult = poem.getDynasty();			
				PoetryCommonService.buildContext(semanticContext, poem);
			}
			else
			{
				serviceResult = "没有要找的诗";
			}
			
		}
		else if(null != curSentence) 
		{
			Poem poem = PoetryCommonService.getBySent(this.authorService,this.poetryService,this.poetrySentenceService,curSentence);
			if(null != poem) {
				serviceResult = poem.getDynasty();			
				PoetryCommonService.buildContext(semanticContext, poem);
				semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, curSentence);
			}
			else
			{
				serviceResult = "没有要找的诗";
			}
		}
		else if(null != curAuthor)
		{
			Author author = PoetryCommonService.getAuthorByName(this.authorService,curAuthor);
			if(null != author) {
				serviceResult = author.getChaodai();			
			}
			else
			{
				serviceResult = "没有要找的诗人";
			}
		}
		else
		{
			//没有上下文无法查找这首诗的朝代
			if(null == semanticContext || semanticContext.getParams().isEmpty()) {
				serviceResult = "缺少上下文，无法回答这个问题";
				poetryBean.setText(serviceResult);
				poetryBean.setId(10);
				return poetryBean;
			}
			
			String dynasty = (String)semanticContext.getParams().get(PoetrySlot.POEM_DYNASTY);
			serviceResult = dynasty;
		}
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;
	}
	
	/**
	 * 随机查找一首诗
	 * @param slots
	 * @return
	 */
	private PoetryBean queryPoetry(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
			
		Poem poem = null;
		//1.随机查找一首诗
		int count = slots.size();  
		if(count <= 0) {
			poem = PoetryCommonService.getByRandom(this.authorService,this.poetryService);
					
			if(null !=poem) {				
				serviceResult = (poem.getName()+","+poem.getAuthor()+", "+poem.getSentence());
				PoetryCommonService.buildContext(semanticContext, poem);
			}
			else
			{				
				serviceResult = "没有要找的诗";				
			}
			poetryBean.setText(serviceResult);
			poetryBean.setId(10);
			return poetryBean;
		}
		
		//2.根据诗名查找诗
		String poemTitle = slots.get(PoetrySlot.POEM_TITLE);
		if(null != poemTitle) {
			String contextPoemTitle = null;
			String contextPoemAuthor = null;
			String contextPoemContext = null;
			if(semanticContext != null && !semanticContext.getParams().isEmpty()) {
				contextPoemTitle = (String)semanticContext.getParams().get(PoetrySlot.POEM_TITLE);
				if(null != contextPoemTitle && poemTitle.equalsIgnoreCase(contextPoemTitle)) {
					contextPoemAuthor = (String)semanticContext.getParams().get(PoetrySlot.POEM_AUTHOR);
					contextPoemContext = (String)semanticContext.getParams().get(PoetrySlot.POEM_CONTEXT);
				}				
			}
			
			if(null != contextPoemContext) {
				serviceResult = (contextPoemTitle+","+contextPoemAuthor+", "+contextPoemContext);
			}
			else
			{
				poem = PoetryCommonService.getByTitle(this.authorService,this.poetryService,poemTitle);			
				
				if(null !=poem) {
					serviceResult=(poem.getName()+","+poem.getAuthor()+", "+poem.getSentence());					
					PoetryCommonService.buildContext(semanticContext, poem);
				}
				else
				{
					serviceResult = "没有要找的诗";
				}
			}
			
			poetryBean.setText(serviceResult);
			poetryBean.setId(10);
			return poetryBean;
		}
				
		//3.根据作者查找一首诗
		String poemAuthor = slots.get(PoetrySlot.POEM_AUTHOR);
		if(null != poemAuthor) {
			poem = PoetryCommonService.getByAuthor(this.authorService,this.poetryService,poemAuthor);			
			
			if(null !=poem) {
				serviceResult = (poem.getName()+","+poem.getAuthor()+", "+poem.getSentence());
				PoetryCommonService.buildContext(semanticContext, poem);
			}
			else
			{				
				serviceResult = ("没有要找的诗");				
			}
			poetryBean.setText(serviceResult);
			poetryBean.setId(10);
			return poetryBean;
		}
				
		//4.根据朝代查找一首诗
		String poemDynasty = slots.get(PoetrySlot.POEM_DYNASTY);
		if(null != poemDynasty) {
			poem = PoetryCommonService.getByDynasty(this.authorService,this.poetryService,poemDynasty);
			
			if(null !=poem) {
				serviceResult = (poem.getName()+","+poem.getAuthor()+", "+poem.getSentence());
				PoetryCommonService.buildContext(semanticContext, poem);
			}
			else
			{				
				serviceResult = ("没有要找的诗");				
			}
			poetryBean.setText(serviceResult);
			poetryBean.setId(10);
			return poetryBean;
		}
		
		serviceResult = ("不好意思，我好像没有理解你的意思");
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;
	}
	
	/**
	 * 查询诗名
	 * @param slots
	 * @return
	 */
	private PoetryBean queryTitle(Map<String, String> slots,SemanticContext semanticContext) {
		PoetryBean poetryBean = new PoetryBean();
		String serviceResult = null;
		
		String curSentence = slots.get(PoetrySlot.POEM_CUR_SENTENCE);
		
		if(null != curSentence) {
			Poem poem = PoetryCommonService.getBySent(this.authorService,this.poetryService,this.poetrySentenceService,curSentence);
			
			if(null != poem) {
				serviceResult = (poem.getName());				
				PoetryCommonService.buildContext(semanticContext, poem);
				semanticContext.getParams().put(PoetrySlot.POEM_CUR_SENTENCE, curSentence);
			}
			else
			{
				serviceResult = ("没有要找的诗");	
			}
		}
		else
		{
			//没有上下文无法查找这首诗的朝代
			if(null == semanticContext  || semanticContext.getParams().isEmpty()) {
				serviceResult = ("缺少上下文，无法回答这个问题");
				poetryBean.setId(10);
				return poetryBean;
			}
			String title = (String)semanticContext.getParams().get(PoetrySlot.POEM_TITLE);
			serviceResult = (title);
		}
		
		poetryBean.setText(serviceResult);
		poetryBean.setId(10);
		return poetryBean;		
	}
	
	
}
