package com.apollowebworks.hackmaster.model;

public class Outcome {
	private String key;
	private String description;
	private int nDice;
	private int diceType;

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getnDice() {
		return nDice;
	}

	public void setnDice(int nDice) {
		this.nDice = nDice;
	}

	public int getDiceType() {
		return diceType;
	}

	public void setDiceType(int diceType) {
		this.diceType = diceType;
	}
}
