package com.apollowebworks.hackmaster.model;

import java.util.List;

public class LookupResponse {
	private AttackType type;
	private BodyPart bodyPart;
	private List<Effect> effects;

	public AttackType getType() {
		return type;
	}

	public void setType(AttackType type) {
		this.type = type;
	}

	public BodyPart getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(BodyPart bodyPart) {
		this.bodyPart = bodyPart;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
}
