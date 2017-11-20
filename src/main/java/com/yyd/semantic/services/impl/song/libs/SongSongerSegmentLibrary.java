package com.yyd.semantic.services.impl.song.libs;

import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.domain.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yyd.semantic.common.SegmentLibrary;
import com.yyd.semantic.db.service.song.ArtistService;

@Component
public class SongSongerSegmentLibrary implements SegmentLibrary {
	@Autowired
	private ArtistService artistService;

	@Override
	public List<Value> load() {
		List<String> names = artistService.getAllNames();
		List<Value> values = new LinkedList<>();
		for (String name : names) {
			values.add(new Value(name, "songer", "1"));
		}
		return values;
	}

}
