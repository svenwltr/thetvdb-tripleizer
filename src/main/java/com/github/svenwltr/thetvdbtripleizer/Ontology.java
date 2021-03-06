package com.github.svenwltr.thetvdbtripleizer;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

public class Ontology {
	
	protected static final String owl ="http://www.w3.org/2002/07/owl#";
	protected static final String tvdb ="http://tvdb.wltr.eu/ontology/";
	protected static final String rdfs ="http://www.w3.org/2000/01/rdf-schema#";
	protected static final String dbpedia ="http://dbpedia.org/ontology/";
	protected static final String dbpprop ="http://dbpedia.org/property/";
	
	private static Model m = ModelFactory.createDefaultModel();
	
	public final static Property idProperty = m.createProperty(tvdb, "tvdbid");
	public final static Property matchesProperty = m.createProperty(owl, "sameAs");
	public final static Property labelProperty = m.createProperty(dbpprop, "showName");
	public final static Property ratingProperty = m.createProperty(tvdb, "rating");
	public final static Property airDateProperty = m.createProperty(dbpedia, "releaseDate");

}
