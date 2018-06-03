package com.apollowebworks.hackmaster.controller;

import com.apollowebworks.hackmaster.manager.TableManager;
import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.LookupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HackmasterController {

	private final TableManager tableManager;

	@Autowired
	public HackmasterController(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	@ModelAttribute("response")
	public Map<String, Object> reportEffect(@ModelAttribute LookupRequest lookupRequest) {
		HashMap<String, Object> response = new HashMap<>();
		tableManager.update(lookupRequest);
		response.put("request", lookupRequest);
		response.put("response", tableManager.lookup(lookupRequest));
		return response;
	}

	@RequestMapping(value = "/")
	public String index(Map<String, Object> model,
						@RequestParam(required = false) AttackType type,
						@RequestParam(required = false) Integer part,
						@RequestParam(required = false) Integer severity) {
		model.put("x", "asdf");
		return "index";
	}
}
