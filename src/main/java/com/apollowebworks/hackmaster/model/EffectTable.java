package com.apollowebworks.hackmaster.model;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EffectTable {
	private HashMap<String, String> map;

	public EffectTable() {
		map = new HashMap<>();
	}

	public void add(String key, String val) {
		map.put(key, val);
	}

	public String get(String key) {
		return map.get(key);
	}

	public String lookup(String key) {
		if (map.get(key) != null) {
			return (map.get(key));
		}

		String realKey = key;
		Pattern p = Pattern.compile("^([a-zA-Z]+)([0-9]+)$");
		Matcher m = p.matcher(key);
		String numberPart;
		if (m.find()) {
			String letterPart = m.group(1);
			numberPart = m.group(2);
			System.out.println("I found " + letterPart + ", " + numberPart);
			realKey = letterPart + "X";
			System.out.println("Replaced key with " + realKey);
		}
		return map.get(realKey) != null ?
				map.get(realKey) : realKey;
	}
}
