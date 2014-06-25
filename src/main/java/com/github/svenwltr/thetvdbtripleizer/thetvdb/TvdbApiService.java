package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Service;

@Service
public class TvdbApiService {

	protected TvdbApiService() {
	
	}

	private WebTarget dynamicInterface(String path) {
		Client client = ClientBuilder.newClient();
		return client.target("http://thetvdb.com/api/").path(path);

	}

	private WebTarget staticInterface() {
		return dynamicInterface(System.getenv("TVDB_APIKEY"));

	}

	public List<String> search(String query) {
		ApiResponse searchResponse = dynamicInterface("GetSeries.php")
				.queryParam("seriesname", query).request()
				.get(ApiResponse.class);
		
		List<String> idList = new ArrayList<>();
		for(TvShow show : searchResponse.series)
			idList.add(show.id);

		return idList;

	}

	public TvShow findShow(String id) {
		ApiResponse data = staticInterface().path("series").path(id)
				.path("en.xml").request().get(ApiResponse.class);
		return data.series.get(0);

	}

}
