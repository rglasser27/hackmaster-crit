package com.apollowebworks.hackmaster.model;

public class Effect {
	private String key;
	private String description;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Effect{" +
				"key='" + key + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
