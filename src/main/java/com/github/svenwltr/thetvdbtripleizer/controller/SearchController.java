package com.github.svenwltr.thetvdbtripleizer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.svenwltr.thetvdbtripleizer.TvdbOntology;
import com.github.svenwltr.thetvdbtripleizer.thetvdb.TvShow;
import com.github.svenwltr.thetvdbtripleizer.thetvdb.TvdbApiService;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

@RestController
@RequestMapping("/resources")
public class SearchController {

	@Autowired
	private TvdbApiService tvdbApi;

	public final static String TVDB_NS = "http://localhost:8080/resources/";
	public final static String TVDB_ID_NS = "http://localhost:8080/resources/id/";

	@RequestMapping(value = "/{query}", method = RequestMethod.GET)
	public void search(@PathVariable("query") String query,
			HttpServletResponse response) throws IOException {

		Model model = ModelFactory.createDefaultModel();

		Resource queryResource = model.createResource(TVDB_NS + query);

		for (String id : tvdbApi.findIds(query)) {
			TvShow show = tvdbApi.findShow(id);

			Resource showResource = model.createResource(TVDB_ID_NS + id);

			queryResource.addProperty(TvdbOntology.matchesProperty,
					showResource);
			showResource.addProperty(TvdbOntology.labelProperty, show.name);
			showResource.addProperty(TvdbOntology.ratingProperty, show.rating);
			showResource.addProperty(TvdbOntology.airDateProperty,
					show.firstAired);

		}

		model.write(response.getOutputStream(), "TURTLE");

	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public void id(@PathVariable("id") String id, HttpServletResponse response)
			throws IOException {

		Model model = ModelFactory.createDefaultModel();

		TvShow show = tvdbApi.findShow(id);

		Resource showResource = model.createResource(TVDB_ID_NS + id);
		showResource.addProperty(TvdbOntology.labelProperty, show.name);
		showResource.addProperty(TvdbOntology.ratingProperty, show.rating);
		showResource.addProperty(TvdbOntology.airDateProperty, show.firstAired);

		model.write(response.getOutputStream(), "TURTLE");

	}

}
