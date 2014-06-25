package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import java.util.Date;

import javax.ws.rs.client.WebTarget;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TvdbCache {

	private MongoCollection collection;

	@Autowired
	protected TvdbCache(Jongo jongo) {
		collection = jongo.getCollection("cache");

	}

	public void store(WebTarget target, String xml) {
		CacheItem item = new CacheItem();
		item.uri = target.getUri().toASCIIString();
		item.xml = xml;
		item.time = new Date().getTime();

		collection.save(item);

	}

	public CacheItem load(WebTarget target) {
		return collection.findOne("{_id: #}", target.getUri().toASCIIString())
				.as(CacheItem.class);

	}

	public static class CacheItem {
		@Id
		private String uri;

		private String xml;

		private long time;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getXml() {
			return xml;
		}

		public void setXml(String xml) {
			this.xml = xml;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

	}

}
