package com.yyd.semantic.services.impl.song;

import java.util.Map;

public class SongSlot {
	public static final String SONG_SONGER = "songer";
	public static final String SONG_SONG_NAME = "song";

	private static final String SONG_ID = "songId";

	private Map<Object, Object> params;

	public SongSlot(Map<Object, Object> params) {
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
