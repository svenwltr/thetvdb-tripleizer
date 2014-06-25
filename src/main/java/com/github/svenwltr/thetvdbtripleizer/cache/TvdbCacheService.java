package com.github.svenwltr.thetvdbtripleizer.cache;

import java.util.Date;

import javax.ws.rs.client.WebTarget;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TvdbCacheService {

	private MongoCollection collection;

	@Autowired
	protected TvdbCacheService(Jongo jongo) {
		collection = jongo.getCollection("cache");

	}

	public void store(WebTarget target, String xml) {
		CacheItem item = new CacheItem();
		item.setUri(target.getUri().toASCIIString());
		item.setXml(xml);
		item.setTime(new Date().getTime());

		collection.save(item);

	}

	public CacheItem load(WebTarget target) {
		return collection.findOne("{_id: #}", target.getUri().toASCIIString())
				.as(CacheItem.class);

	}

}
