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
import com.hp.hpl.jena.vocabulary.VCARD;

@RestController
@RequestMapping("/resources/{query}")
public class SearchController {

	@Autowired
	private TvdbApiService tvdbApi;

	public final static String TVDB_NS = "http://localhost:8080/resources/";
	public final static String TVDB_ID_NS = "http://localhost:8080/resources/id/";

	@RequestMapping(value = "test/", method = RequestMethod.GET)
	public void test(@PathVariable("query") String query,
			HttpServletResponse response) throws IOException {
		// some definitions
		String personURI = "http://somewhere/JohnSmith";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;

		// create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		// and add the properties cascading style
		Resource johnSmith = model
				.createResource(personURI)
				.addProperty(VCARD.FN, fullName)
				.addProperty(
						VCARD.N,
						model.createResource()
								.addProperty(VCARD.Given, givenName)
								.addProperty(VCARD.Family, familyName));

		model.write(response.getOutputStream(), "TURTLE");

	}

	@RequestMapping(method = RequestMethod.GET)
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

		model.write(response.getOutputStream(), "N-TRIPLE");

	}
}
