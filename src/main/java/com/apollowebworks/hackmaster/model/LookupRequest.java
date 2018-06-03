package com.apollowebworks.hackmaster.model;

import org.springframework.web.bind.annotation.RequestParam;

public class LookupRequest {
	private AttackType type;
	private Integer part;
	private Integer severity;

	public AttackType getType() {
		return type;
	}

	public void setType(AttackType type) {
		this.type = type;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "LookupRequest{" +
				"type=" + type +
				", part=" + part +
				", severity=" + severity +
				'}';
	}
}
