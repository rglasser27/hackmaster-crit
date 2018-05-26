package com.apollowebworks.hackmaster.controller;

import com.apollowebworks.hackmaster.manager.TableManager;
import com.apollowebworks.hackmaster.model.AttackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HackmasterController {

	private static final int MAX_PART = 10000;
	private static final int MAX_SEVERITY = 24;

	private final TableManager tableManager;

	@Autowired
	public HackmasterController(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	@RequestMapping("/hackmaster")
	public Map<String, Object> reportEffect(@RequestParam(required = false) AttackType type,
											@RequestParam(required = false) Integer part,
											@RequestParam(required = false) Integer severity) {
		Map<String, Object> response = new HashMap<>();
		AttackType finalType = type != null ? type : AttackType.values()[random(AttackType.values().length)];
		Integer finalPart = part != null ? part : random(MAX_PART);
		Integer finalSeverity = severity != null ? severity : random(MAX_SEVERITY);

		response.put("part", finalPart);
		response.put("severity", finalSeverity);
		response.put("result", tableManager.lookup(finalType, finalPart, finalSeverity));
		return response;
	}

	private int random(int highest) {
		return (int) Math.floor(Math.random() * highest);
	}
}
