package com.yyd.semantic.services.impl.song;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.semantic.common.CommonUtils;
import com.yyd.semantic.db.bean.song.Artist;
import com.yyd.semantic.db.bean.song.Category;
import com.yyd.semantic.db.bean.song.Song;
import com.yyd.semantic.db.service.song.ArtistService;
import com.yyd.semantic.db.service.song.CategoryService;
import com.yyd.semantic.db.service.song.SongService;

@Component
public class SongSemantic implements Semantic<SongBean> {
	@Autowired
	private SongService songService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public SongBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		SongBean result = null;
		Map<String, String> slots = ybnfCompileResult.getSlots();
		String action = slots.get("intent");
		Map<String, String> objects = ybnfCompileResult.getObjects();
		switch (action) {
		case SongIntent.QUERY_SONG: {
			result = querySong(objects, semanticContext);
			break;
		}
		case SongIntent.QUERY_CURR_SONG: {
			result = queryCurrSong(objects, semanticContext);
			break;
		}
		case SongIntent.QUERY_SONGER: {
			result = querySonger(objects, semanticContext);
			break;
		}
		case SongIntent.QUERY_SONG_NAME: {
			result = querySongName(objects, semanticContext);
			break;
		}
		default: {
			result = new SongBean("这句话太复杂了，我还不能理解");
			break;
		}
		}
		return result;
	}

	private SongBean querySong(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		SongSlot ss = new SongSlot(semanticContext.getParams());
		Song songEntity = null;
		if (slots.isEmpty()) {
			List<Integer> songIds = songService.getIdList();
			int idx = CommonUtils.randomInt(songIds.size());
			songEntity = songService.getById(songIds.get(idx));
		} else {
			// 查找歌曲不能获取当前上下文中的歌曲，由于存在随机播放的问题
			String songer = slots.get(SongSlot.SONG_SONGER);
			String song = slots.get(SongSlot.SONG_SONG_NAME);
			String category = slots.get(SongSlot.SONG_CATEGORY);
			if (song != null) {
				List<Song> songs = songService.getByName(song);
				if (songs.isEmpty()) {
					result = "我没听过歌曲" + song;
				} else {
					if (songer != null) {
						List<Integer> artistIds = artistService.getIdsByName(songer);
						BREAK_POINT: for (int i = 0; i < songs.size(); i++) {
							for (Integer id : artistIds) {
								if (id.equals(songs.get(i).getArtistId())) {
									songEntity = songs.get(i);
									break BREAK_POINT;
								}
							}
						}
						if (songEntity == null) {
							result = "我没听过" + songer + "的" + song;
						}
					} else {
						int idx = CommonUtils.randomInt(songs.size());
						songEntity = songs.get(idx);
					}
				}
			} else if (songer != null) {
				List<Integer> artistIds = artistService.getIdsByName(songer);
				int idx = CommonUtils.randomInt(artistIds.size());
				Integer artistId = artistIds.get(idx);
				List<Song> songs = songService.getByArtistId(artistId);
				if (songs.isEmpty()) {
					result = "我没听过" + songer + "的歌";
				} else {
					idx = CommonUtils.randomInt(songs.size());
					songEntity = songs.get(idx);
				}
			} else if (category != null) {
				// 根据类型名称获取到类型对象
				List<Category> categories = categoryService.getByName(category);
				if (categories.isEmpty()) {
					result = "我还没听过这个类型的歌";
				} else {
					// 随机获取一个同名类型
					int idx = CommonUtils.randomInt(categories.size());
					Category categoryEntity = categories.get(idx);
					// 根据该类型获取到其下所有子类型
					categories = categoryService.getByParentId(categoryEntity.getId());
					List<Song> songs = null;
					int loopCount = -1;
					do {
						// 从所有子类型与本类型中抽取出一个类型
						if (loopCount >= categories.size()) {
							result = "我还没听过这个类型的歌";
							break;
						} else {
							if (loopCount == -1) {
								idx = CommonUtils.randomInt(categories.size());
							} else {
								idx = loopCount;
							}
							loopCount += 1;
						}
						categoryEntity = categories.get(idx);
						// 根据类型获取该类型下的歌曲
						songs = songService.findByCategoryId(categoryEntity.getId());
					} while (songs == null || songs.isEmpty());
					// 随机获取该类型下的一首歌
					idx = CommonUtils.randomInt(songs.size());
					songEntity = songs.get(idx);
				}
			}
		}
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			result = songEntity.getId() + "," + songEntity.getName() + " : " + songEntity.getSongUrl();
		}
		return new SongBean(result);
	}

	private SongBean queryCurrSong(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		SongSlot ss = new SongSlot(semanticContext.getParams());
		Song songEntity = null;
		Integer songId = ss.getSongId();
		if (songId != null) {
			songEntity = songService.getById(songId);
		}
		if (songEntity != null) {
			result = songEntity.getId() + "," + songEntity.getName() + " : " + songEntity.getSongUrl();
		}
		return new SongBean(result);
	}

	private SongBean querySonger(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		SongSlot ss = new SongSlot(semanticContext.getParams());
		Song songEntity = null;
		if (slots.isEmpty()) {
			Integer songId = ss.getSongId();
			if (songId != null) {
				songEntity = songService.getById(songId);
			}
		} else {
			String song = slots.get(SongSlot.SONG_SONG_NAME);
			if (song != null) {
				List<Song> songs = songService.getByName(song);
				if (songs.isEmpty()) {
					result = "我没听过" + song + "这首歌";
				} else {
					int idx = CommonUtils.randomInt(songs.size());
					songEntity = songs.get(idx);
				}
			}
		}
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			Artist artist = artistService.getById(songEntity.getArtistId());
			result = artist.getName();
		}
		return new SongBean(result);
	}

	private SongBean querySongName(Map<String, String> slots, SemanticContext semanticContext) {
		String result = "我听不懂你在说什么";
		SongSlot ss = new SongSlot(semanticContext.getParams());
		Song songEntity = null;
		if (slots.isEmpty()) {
			Integer songId = ss.getSongId();
			songEntity = songService.getById(songId);
		}
		if (songEntity != null) {
			ss.setSongId(songEntity.getId());
			result = songEntity.getName();
		}
		return new SongBean(result);
	}
}
