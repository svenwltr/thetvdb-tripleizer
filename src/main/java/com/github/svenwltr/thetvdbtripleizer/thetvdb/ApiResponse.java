package com.github.svenwltr.thetvdbtripleizer.thetvdb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Data")
public class ApiResponse {

	@XmlElement(name = "Series")
	public List<TvShow> series;

}
