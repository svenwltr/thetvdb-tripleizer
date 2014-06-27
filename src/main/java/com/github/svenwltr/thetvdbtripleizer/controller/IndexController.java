package com.github.svenwltr.thetvdbtripleizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.svenwltr.thetvdbtripleizer.bouncer.BouncerService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private BouncerService bouncer;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("load", bouncer.getLoad());
		return "index";
		
	}

}
