package com.apollowebworks.hackmaster.controller;

import com.apollowebworks.hackmaster.manager.TableManager;
import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.LookupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HackmasterRestController {


	private final TableManager tableManager;

	@Autowired
	public HackmasterRestController(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	@RequestMapping("/api/hackmaster")
	public Map<String, Object> reportEffect(LookupRequest lookupRequest) {

		HashMap<String, Object> response = new HashMap<>();
		tableManager.update(lookupRequest);
		response.put("request", lookupRequest);
		response.put("response", tableManager.lookup(lookupRequest));
		return response;
	}

	private int random(int highest) {
		return (int) Math.floor(Math.random() * highest);
	}
}
