package com.github.svenwltr.thetvdbtripleizer;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

public class TvdbOntology {
	
	protected static final String uri ="http://localhost:8080/ontology/";
	
	private static Model m = ModelFactory.createDefaultModel();

	public final static Property idProperty = m.createProperty(uri, "tvdbid");
	public final static Property matchesProperty = m.createProperty(uri, "matches");
	public final static Property labelProperty = m.createProperty(uri, "label");
	public final static Property ratingProperty = m.createProperty(uri, "rating");
	public final static Property airDateProperty = m.createProperty(uri, "airDate");

}
