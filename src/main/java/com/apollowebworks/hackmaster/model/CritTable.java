package com.apollowebworks.hackmaster.model;

import java.util.ArrayList;
import java.util.List;

public class CritTable {
	private List<CritEntry> entries;

	public CritTable() {
		entries = new ArrayList<>();
	}

	public List<CritEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<CritEntry> entries) {
		this.entries = entries;
	}

	public CritEntry lookupEntry(Integer value) {
		return entries.stream()
				.filter(entry -> value >= entry.getLowRoll() && value <= entry.getHighRoll())
				.findAny()
				.orElse(null);
	}
}
