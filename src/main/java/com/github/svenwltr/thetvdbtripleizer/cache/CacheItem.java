package com.github.svenwltr.thetvdbtripleizer.cache;

import org.jongo.marshall.jackson.oid.Id;

public class CacheItem {
	@Id private String uri;

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