package com.github.svenwltr.thetvdbtripleizer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.svenwltr.thetvdbtripleizer.Ontology;
import com.github.svenwltr.thetvdbtripleizer.thetvdb.TvShow;
import com.github.svenwltr.thetvdbtripleizer.thetvdb.TvdbApiService;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

@RestController
public class SearchController {

	@Autowired
	private TvdbApiService tvdbApi;

	public final static String TVDB_NS = "http://tvdb.wltr.eu/resources/";
	public final static String TVDB_ID_NS = "http://tvdb.wltr.eu/id/";

	@RequestMapping(value = "/resources/{query}", method = RequestMethod.GET)
	public void search(@PathVariable("query") String query,
			HttpServletResponse response) throws IOException {

		TvShow show = tvdbApi.findShow(query);

		if (show == null) {
			response.setStatus(404);
			
		} else {

			Model model = ModelFactory.createDefaultModel();

			Resource queryResource = model.createResource(TVDB_NS + query);
			Resource showResource = model.createResource(TVDB_ID_NS + show.id);

			queryResource.addProperty(Ontology.matchesProperty, showResource);
			queryResource.addProperty(Ontology.labelProperty, show.name);
			queryResource.addProperty(Ontology.ratingProperty, show.rating);
			queryResource.addProperty(Ontology.airDateProperty, show.firstAired);

			model.write(response.getOutputStream(), "TURTLE");
		}

	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public void id(@PathVariable("id") String id, HttpServletResponse response)
			throws IOException {

		Model model = ModelFactory.createDefaultModel();

		TvShow show = tvdbApi.getShow(id);

		Resource showResource = model.createResource(TVDB_ID_NS + id);
		showResource.addProperty(Ontology.labelProperty, show.name);
		showResource.addProperty(Ontology.ratingProperty, show.rating);
		showResource.addProperty(Ontology.airDateProperty, show.firstAired);

		model.write(response.getOutputStream(), "TURTLE");

	}

}
