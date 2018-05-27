package com.apollowebworks.hackmaster.model;

import java.util.ArrayList;
import java.util.List;

public class CritEntry {
	private int lowRoll;
	private int highRoll;
	private String location;
	private List<List<String>> effects;

	public CritEntry() {
		lowRoll = 0;
		highRoll = 0;
		location = "";
		effects = new ArrayList<>();
	}

	public int getLowRoll() {
		return lowRoll;
	}

	public void setLowRoll(int lowRoll) {
		this.lowRoll = lowRoll;
	}

	public int getHighRoll() {
		return highRoll;
	}

	public void setHighRoll(int highRoll) {
		this.highRoll = highRoll;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<List<String>> getEffects() {
		return effects;
	}

	public void setEffects(List<List<String>> effects) {
		this.effects = effects;
	}
}
