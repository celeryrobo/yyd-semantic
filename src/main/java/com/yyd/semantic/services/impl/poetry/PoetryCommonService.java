package com.yyd.semantic.services.impl.poetry;

import java.util.List;
import java.util.Random;

import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.db.bean.poetry.Author;
import com.yyd.semantic.db.bean.poetry.Poetry;
import com.yyd.semantic.db.bean.poetry.PoetrySentence;
import com.yyd.semantic.db.service.poetry.AuthorService;
import com.yyd.semantic.db.service.poetry.PoetrySentenceService;
import com.yyd.semantic.db.service.poetry.PoetryService;

public class PoetryCommonService {
	public static void buildContext(SemanticContext semanticContext,Poem poem) {
		if(null == poem || null == semanticContext) {
			return;
		}	
		if(!semanticContext.getParams().isEmpty()) {			
			semanticContext.getParams().clear();
		}		
		
		semanticContext.getParams().put(PoetrySlot.POEM_AUTHOR, poem.getAuthor());
		semanticContext.getParams().put(PoetrySlot.POEM_DYNASTY, poem.getDynasty());
		semanticContext.getParams().put(PoetrySlot.POEM_TITLE, poem.getName());
		semanticContext.getParams().put(PoetrySlot.POEM_ID, poem.getId().toString());
		semanticContext.getParams().put(PoetrySlot.POEM_CONTEXT, poem.getSentence());
		
	}
	
	/**
	 * 根据一句诗查找整首诗内容
	 * @param sentence
	 * @return
	 */
	public static Poem getBySent(AuthorService authorService,
			                     PoetryService poetryService,
			                     PoetrySentenceService poetrySentenceService,
			                     String sentence) {
		if(null == sentence) {
			return null;
		}
		
		Poem poem = null;		
        
        //首先查找诗id
        int  poetryId = -1;
        List<PoetrySentence> poetrySentenceList = poetrySentenceService.getBySent(sentence);
        if(poetrySentenceList.size() > 0) {
        	poetryId = poetrySentenceList.get(0).getPoetryId();
        }
        else
        {
        	return null;
        }
        
        //根据诗id查询整首诗
        Poetry poetry = poetryService.getById(poetryId);    		
        if(null != poetry) {       	      
            poem = new Poem();                        
            Author author = getAuthorByName(authorService,poetry.getAuthorName());            
            poem.setAuthor(poetry.getAuthorName());
            poem.setDynasty(author.getChaodai());
            poem.setId(poetry.getId());
            poem.setName(poetry.getTitle());
            poem.setSentence(poetry.getContent());
            
       }
				
		return poem;
	}
	
	
	/**
	 * 随机查找一首诗
	 * @return
	 */
	public static Poem getByRandom(AuthorService authorService,
			                       PoetryService poetryService) {
		Poem poem = null;		    
              
        List<Integer> idList = poetryService.getIdList();        
               
        Random rand = new Random();
        int randNum = rand.nextInt(idList.size());
        int poetryId = (int)idList.get(randNum);        
        Poetry poetry = poetryService.getById(poetryId);
          
        if(null != poetry) {        	   
             poem = new Poem();
             Author author = getAuthorByName(authorService,poetry.getAuthorName());
             
             poem.setAuthor(poetry.getAuthorName());
             poem.setDynasty(author.getChaodai());
             poem.setId(poetry.getId());
             poem.setName(poetry.getTitle());
             poem.setSentence(poetry.getContent());
        }
		
		return poem;
	}
	
	/**
	 * 根据诗名查找一首诗
	 * @param slot
	 * @return
	 */
	public static Poem getByTitle(AuthorService authorService,
			                      PoetryService poetryService,
			                      String title) {
		if(null == title) {
			return null;
		}
		
		Poem poem = null;
		List<Poetry> poetryList = poetryService.getByTitle(title);            
        if(poetryList.size() > 0) {
        	 Random rand = new Random();
             int randNum = rand.nextInt(poetryList.size());
             
             poem = new Poem();
             Poetry poetry = poetryList.get(randNum);
             Author author = getAuthorByName(authorService,poetry.getAuthorName());
             
             poem.setAuthor(poetry.getAuthorName());
             poem.setDynasty(author.getChaodai());
             poem.setId(poetry.getId());
             poem.setName(poetry.getTitle());
             poem.setSentence(poetry.getContent());
        }
		
		return poem;
	}
	
	/**
	 * 根据作者随机查找一首诗
	 * @param slot
	 * @return
	 */
	public static Poem getByAuthor(AuthorService authorService,
			                       PoetryService poetryService,
			                       String author) {
		if(null == author) {
			return null;
		}
		
		Poem poem = null;
		
        List<Poetry> poetryList = poetryService.getByAuthor(author);    
        
        if(poetryList.size() > 0) {
        	 Random rand = new Random();
             int randNum = rand.nextInt(poetryList.size());
             
             poem = new Poem();
             Poetry poetry = poetryList.get(randNum);
             Author author2 = getAuthorByName(authorService,poetry.getAuthorName());
             
             poem.setAuthor(poetry.getAuthorName());
             poem.setDynasty(author2.getChaodai());
             poem.setId(poetry.getId());
             poem.setName(poetry.getTitle());
             poem.setSentence(poetry.getContent());
        }
       
		
		return poem;
	}
	
	/**
	 * 根据朝代随机查找一首诗
	 * @param dynasty
	 * @return
	 */
	public static Poem getByDynasty(AuthorService authorService,
			                        PoetryService poetryService,
			                        String dynasty) {
		if(null == dynasty) {
			return null;
		}
		
		Poem poem = null;		
    
        //先查询作者
        int authorId = -1;
        List<Author> authorList = authorService.getByDynasty(dynasty);
        if(authorList.size() > 0) {
        	 Random rand = new Random();
             int randNum = rand.nextInt(authorList.size());
             authorId = authorList.get(randNum).getId();
        }
        else
        {
        	return null;
        }
        
        //根据作者查询一首诗
        List<Poetry> poetryList = poetryService.getByAuthorId(authorId);     
      
        if(poetryList.size() > 0) {
        	 Random rand = new Random();
             int randNum = rand.nextInt(poetryList.size());
             
             poem = new Poem();
             Poetry poetry = poetryList.get(randNum);
             Author author = getAuthorByName(authorService,poetry.getAuthorName());
             
             poem.setAuthor(poetry.getAuthorName());
             poem.setDynasty(author.getChaodai());
             poem.setId(poetry.getId());
             poem.setName(poetry.getTitle());
             poem.setSentence(poetry.getContent());             
        }
		
		return poem;
	}
	
	public static Author getAuthorByName(AuthorService authorService,String authorName) {
		if(null == authorName) {
			return null;
		}
		
		Author author = null;
		 
        //查询作者
        List<Author> authorList = authorService.getByName(authorName);
        if(authorList.size() > 0) {
        	author = authorList.get(0); //TODO:不考虑同名情况
        }
        else
        {
        	return null;
        }    
         	
		return author;
	}

}
