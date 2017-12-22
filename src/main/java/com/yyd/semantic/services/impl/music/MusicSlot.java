package com.yyd.semantic.services.impl.music;

import java.util.Map;

public class MusicSlot {
	public static final String MUSIC_SINGER = "singer";
	public static final String MUSIC_SONG_NAME = "song";
	public static final String MUSIC_CATEGORY = "category";
	public static final String MUSIC_ALBUM = "album";

	private static final String SONG_ID = "songId";

	private Map<Object, Object> params;

	public MusicSlot(Map<Object, Object> params) {
		this.params = params;
	}

	public Integer getSongId() {
		String songId = (String) params.get(SONG_ID);
		if (songId == null) {
			return null;
		}
		return Integer.valueOf(songId);
	}

	public void setSongId(Integer songId) {
		params.put(SONG_ID, songId.toString());
	}
}
