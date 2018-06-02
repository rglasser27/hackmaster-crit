package com.apollowebworks.hackmaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HackmasterController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
