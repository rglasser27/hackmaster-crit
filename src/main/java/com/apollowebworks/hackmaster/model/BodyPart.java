package com.apollowebworks.hackmaster.model;

public class BodyPart {
	private Integer id;
	private Integer lowRoll;
	private Integer highRoll;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLowRoll() {
		return lowRoll;
	}

	public void setLowRoll(Integer lowRoll) {
		this.lowRoll = lowRoll;
	}

	public Integer getHighRoll() {
		return highRoll;
	}

	public void setHighRoll(Integer highRoll) {
		this.highRoll = highRoll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BodyPart{" +
				"id=" + id +
				", lowRoll=" + lowRoll +
				", highRoll=" + highRoll +
				", name='" + name + '\'' +
				'}';
	}
}
