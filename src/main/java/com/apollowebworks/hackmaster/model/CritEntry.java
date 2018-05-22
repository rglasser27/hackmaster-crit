package com.apollowebworks.hackmaster.model;

import java.util.ArrayList;
import java.util.List;

public class CritEntry {
	private int lowRoll, highRoll;
	private String location;
	private List<List<String>> effects;
	private final int N_OUTCOMES = 13;

	public CritEntry() {
		lowRoll = 0;
		highRoll = 0;
		location = "";
		effects = new ArrayList<>();
		for (int i = 0; i < N_OUTCOMES; i++) {
			effects.add(new ArrayList<>());
		}
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

	public List<String> getOutcomes(int roll) {
//		if (roll <= 0 || roll > N_OUTCOMES) {
//			// Possibly throw an exception?
//			return null;
//		} else {
			return effects.get(roll - 1);
//		}
	}

	public void addEffect(String effect, int roll) {
		if (roll < 0 || roll >= N_OUTCOMES) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			effects.get(roll).add(effect);
		}
	}
}
