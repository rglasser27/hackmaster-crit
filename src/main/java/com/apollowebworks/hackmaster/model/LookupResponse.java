package com.apollowebworks.hackmaster.model;

import java.util.List;

public class LookupResponse {
	private AttackType type;
	private String location;
	private List<Effect> effects;

	public AttackType getType() {
		return type;
	}

	public void setType(AttackType type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
}
