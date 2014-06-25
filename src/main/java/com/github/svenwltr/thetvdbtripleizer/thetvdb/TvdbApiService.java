package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.svenwltr.thetvdbtripleizer.bouncer.BouncerService;
import com.github.svenwltr.thetvdbtripleizer.cache.CacheItem;
import com.github.svenwltr.thetvdbtripleizer.cache.TvdbCacheService;

@Service
public class TvdbApiService {

	private final TvdbCacheService cache;
	private final BouncerService bouncer;

	@Autowired
	protected TvdbApiService(TvdbCacheService cache, BouncerService bouncer) {
		this.cache = cache;
		this.bouncer = bouncer;

	}

	private WebTarget dynamicInterface(String path) {
		Client client = ClientBuilder.newClient();
		return client.target("http://thetvdb.com/api/").path(path);

	}

	private WebTarget staticInterface() {
		return dynamicInterface(System.getenv("TVDB_APIKEY"));

	}

	private ApiResponse loadOrRequest(WebTarget target) {
		CacheItem item = cache.load(target);
		String xml;

		if (item == null) {
			bouncer.lineUp();
			xml = target.request().get(String.class);
			cache.store(target, xml);

		} else {
			xml = item.getXml();

		}

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ApiResponse.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xml);
			ApiResponse response = (ApiResponse) unmarshaller.unmarshal(reader);

			return response;

		} catch (JAXBException e) {
			return null;

		}

	}

	public List<String> search(String query) {
		WebTarget target = dynamicInterface("GetSeries.php").queryParam(
				"seriesname", query);
		ApiResponse response = loadOrRequest(target);

		List<String> idList = new ArrayList<>();
		if (response != null && response.series != null)
			for (TvShow show : response.series)
				idList.add(show.id);

		return idList;

	}

	public TvShow findShow(String id) {
		WebTarget target = staticInterface().path("series").path(id)
				.path("en.xml");
		ApiResponse response = loadOrRequest(target);
		return response.series.get(0);

	}

}
