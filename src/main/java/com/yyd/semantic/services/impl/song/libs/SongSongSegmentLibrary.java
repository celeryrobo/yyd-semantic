package com.yyd.semantic.services.impl.song.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.song.SongService;

@Component
public class SongSongSegmentLibrary implements SegmentLibrary {
	@Autowired
	private SongService songService;

	@Override
	public List<Value> load() {
		List<String> names = songService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "song", "1"));
		}
		return values;
	}

}
