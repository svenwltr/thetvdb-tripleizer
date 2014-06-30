package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Series")
public class TvShow {

	@XmlElement(name = "id")
	public String id;

	@XmlElement(name = "SeriesName")
	public String name;

	@XmlElement(name = "FirstAired")
	public String firstAired;

	@XmlElement(name = "Rating")
	public double rating;

}