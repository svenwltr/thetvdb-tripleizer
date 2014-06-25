package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Service;

@Service
public class TvdbApiService {

	private WebTarget dynamicInterface(String path) {
		Client client = ClientBuilder.newClient();
		return client.target("http://thetvdb.com/api/").path(path);

	}

	private WebTarget staticInterface() {
		return dynamicInterface(System.getenv("TVDB_APIKEY"));

	}

	protected TvdbApiService() {

	}

	protected ApiResponse search(String query) {
		ApiResponse searchResponse = dynamicInterface("GetSeries.php")
				.queryParam("seriesname", query).request()
				.get(ApiResponse.class);

		return searchResponse;

	}

	public List<String> findIds(String query) {
		List<String> ids = new ArrayList<>();

		for (TvShow show : search(query).series)
			ids.add(show.id);

		return ids;

	}

	public TvShow findShow(String id) {
		ApiResponse data = staticInterface().path("series").path(id)
				.path("en.xml").request().get(ApiResponse.class);
		return data.series.get(0);

	}

}
