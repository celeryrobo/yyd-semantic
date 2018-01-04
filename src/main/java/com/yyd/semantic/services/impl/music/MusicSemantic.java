package com.yyd.semantic.services.impl.music;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.AbstractSemanticResult.Operation;
import com.ybnf.compiler.beans.AbstractSemanticResult.ParamType;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.music.Singer;
import com.yyd.semantic.db.bean.music.Song;
import com.yyd.semantic.db.service.music.SingerService;
import com.yyd.semantic.db.service.music.SongService;

@Component
public class MusicSemantic implements Semantic<MusicBean> {
	@Autowired
	private SongService songService;
	@Autowired
	private SingerService singerService;

	@Override
	public MusicBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		MusicBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case MusicIntent.QUERY_SONG: {
			result = querySong(objects, semanticContext);
			break;
		}
		case MusicIntent.NEXT: {
			result = next(objects, semanticContext);
			break;
		}
		case MusicIntent.LAST: {
			result = last(objects, semanticContext);
			break;
		}
		case MusicIntent.REPEAT: {
			result = repeat(objects, semanticContext);
			break;
		}
		case MusicIntent.QUERY_SINGER: {
			result = querySinger(objects, semanticContext);
			break;
		}
		case MusicIntent.QUERY_SONG_NAME: {
			result = querySongName(objects, semanticContext);
			break;
		}
		default: {
			result = new MusicBean("这句话太复杂了，我还不能理解");
			break;
		}
		}
		return result;
	}

	private MusicBean querySong(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
		if (slots.isEmpty()) {
			//随机挑选一首歌
			List<Integer> songIds = songService.getIdList();
			
			if(songIds.size() > 0) {
				int idx = CommonUtils.randomInt(songIds.size());
				songEntity = songService.getById(songIds.get(idx));
			}
			
		} else {
			// 查找歌曲不能获取当前上下文中的歌曲，由于存在随机播放的问题
			String singer = slots.get(MusicSlot.MUSIC_SINGER);
			String song = slots.get(MusicSlot.MUSIC_SONG_NAME);
			String category = slots.get(MusicSlot.MUSIC_CATEGORY);
			if (song != null) {
				//1.根据歌名查找歌曲
				List<Song> songs = songService.getByName(song);
				if (songs.isEmpty()) {
					result = "我没听过歌曲" + song;
				} else {
					//验证歌手名是否一致
					if (singer != null) {
						List<Integer> singerIds = singerService.getIdsByName(singer);
						BREAK_POINT: for (int i = 0; i < songs.size(); i++) {
							for (Integer id : singerIds) {
								if (id.equals(songs.get(i).getSingerId())) {
									songEntity = songs.get(i);
									break BREAK_POINT;
								}
							}
						}
						if (songEntity == null) {
							result = "我没听过" + singer + "的" + song;
						}
					} else {
						int idx = CommonUtils.randomInt(songs.size());
						songEntity = songs.get(idx);
					}
				}
			} else if (singer != null) {
				//2.根据歌手名查找歌曲
				List<Integer> singerIds = singerService.getIdsByName(singer);
				int idx = CommonUtils.randomInt(singerIds.size());
				Integer singerId = singerIds.get(idx);
				List<Song> songs = songService.getBySingerId(singerId);
				if (songs.isEmpty()) {
					result = "我没听过" + singer + "的歌";
				} else {
					idx = CommonUtils.randomInt(songs.size());
					songEntity = songs.get(idx);
				}
			} else if (category != null) {
				//3. 根据类别查找歌曲
				List<Song> songs = songService.findByCategoryName(category);
				if(null == songs || songs.isEmpty()) {
					result = "我还没听过这个类型的歌";
				}
				else
				{						
					// 随机获取该类型下的一首歌
					int idx = CommonUtils.randomInt(songs.size());
					songEntity = songs.get(idx);
				}
				
			}
		}
		
		MusicBean resultBean = null;
		
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
						
			Integer singerId = songEntity.getSingerId();
			Singer singer = null;
			if(null != singerId) {
				singer = singerService.getById(singerId);
			}	
			
			MusicResource resource = new MusicResource();
			resource.setId(songEntity.getId());
			resource.setName(songEntity.getName());
			resource.setSongUrl(songEntity.getSongUrl());
			if(null != singer) {
				resource.setSinger(singer.getName());
			}
						
			resultBean = new MusicBean(null,songEntity.getSongUrl(),MusicBean.PLAY_NORMAL,resource);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			resultBean = new MusicBean(result);			
		}
		
		return resultBean;
	}
	
	private MusicBean next(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
	
		Integer songId = ss.getSongId();
		songEntity = songService.getById(songId);		
		if(null != songId) {
			songEntity = songService.getById(songId);		
		}		
		
		MusicBean resultBean = null;
		List<Integer> songIds = null;
		if (songEntity != null) {
			songIds = songService.getIdListExcept(songEntity.getId());	
		}
		else
		{
			//随机挑选一首歌
			songIds = songService.getIdList();			
		}
		
		if(null != songIds && songIds.size() > 0) {
			int idx = CommonUtils.randomInt(songIds.size());
			songEntity = songService.getById(songIds.get(idx));			
			ss.setSongId(songEntity.getId());
			
			Integer singerId = songEntity.getSingerId();
			Singer singer = null;
			if(null != singerId) {
				singer = singerService.getById(singerId);
			}			
			
			MusicResource resource = new MusicResource();
			resource.setId(songEntity.getId());
			resource.setName(songEntity.getName());
			resource.setSongUrl(songEntity.getSongUrl());
			if(null != singer) {
				resource.setSinger(singer.getName());
			}
			
			
			resultBean = new MusicBean(null,songEntity.getSongUrl(),MusicBean.PLAY_NORMAL,resource);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			result = "我还没有歌曲";
			resultBean = new MusicBean(result);
		}
			
		
		return resultBean;
	}
	
	
	
	private MusicBean last(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
	
		Integer songId = ss.getSongId();
		songEntity = songService.getById(songId);		
		if(null != songId) {
			songEntity = songService.getById(songId);		
		}
		
		MusicBean resultBean = null;
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			
			Integer singerId = songEntity.getSingerId();
			Singer singer = null;
			if(null != singerId) {
				singer = singerService.getById(singerId);
			}
						
			MusicResource resource = new MusicResource();
			resource.setId(songEntity.getId());
			resource.setName(songEntity.getName());
			resource.setSongUrl(songEntity.getSongUrl());
			if(null != singer) {
				resource.setSinger(singer.getName());
			}
			
			resultBean = new MusicBean(null,songEntity.getSongUrl(),MusicBean.PLAY_NORMAL,resource);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			result = "还没有上一曲呀";
			resultBean = new MusicBean(result);
		}
		return resultBean;
	}
	
	private MusicBean repeat(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
	
		Integer songId = ss.getSongId();
		songEntity = songService.getById(songId);		
		if(null != songId) {
			songEntity = songService.getById(songId);		
		}
		
		MusicBean resultBean = null;
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			
			Singer singer = singerService.getById(songEntity.getSingerId());			
			MusicResource resource = new MusicResource();
			resource.setId(songEntity.getId());
			resource.setName(songEntity.getName());
			resource.setSongUrl(songEntity.getSongUrl());
			if(null != singer) {
				resource.setSinger(singer.getName());
			}
			
			resultBean = new MusicBean(null,songEntity.getSongUrl(),MusicBean.PLAY_REPEAT_SINGLE,resource);
			resultBean.setOperation(Operation.PLAY);
			resultBean.setParamType(ParamType.U);
		}
		else
		{
			result = "我不知道你要重复放哪首歌";
			resultBean = new MusicBean(result);
		}
		return resultBean;
	}
	
	private MusicBean querySinger(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
			
		Integer songId = ss.getSongId();
		if(null != songId) {
			songEntity = songService.getById(songId);		
		}
	
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			Integer id  = songEntity.getSingerId();
			if(null != id) {
				Singer singer = singerService.getById(id);
				if(null != singer) {
					result = singer.getName();
				}
				else
				{
					result = "无歌手名";
				}
				
			}
			else
			{
				result = "无歌手名";
			}
		}
		else
		{
			result = "我不知道你是要问哪首歌的歌手";
		}
		
		return new MusicBean(result);
	}

	private MusicBean querySongName(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		MusicSlot ss = new MusicSlot(semanticContext.getParams());
		Song songEntity = null;
	
		Integer songId = ss.getSongId();
		songEntity = songService.getById(songId);		
		if(null != songId) {
			songEntity = songService.getById(songId);		
		}
		
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			result = songEntity.getName();
		}
		else
		{
			result = "我不知道你是要问哪首歌的歌名";
		}
		return new MusicBean(result);
	}
}
