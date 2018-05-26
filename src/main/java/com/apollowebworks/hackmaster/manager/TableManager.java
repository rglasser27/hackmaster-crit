package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TableManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TableManager.class);

	private final Map<AttackType, List<CritEntry>> critTables;
	private Map<String, String> effectTable;
	private final FileManager fileManager;

	@Autowired
	TableManager(FileManager fileManager) {
		this.fileManager = fileManager;
		critTables = new HashMap<>();
		critTables.put(AttackType.HACKING, readCritTable("hacking1", "hacking2"));
		critTables.put(AttackType.CRUSHING, readCritTable("crushing1", "crushing2"));
		critTables.put(AttackType.PIERCING, readCritTable("piercing1", "piercing2"));
		effectTable = readEffects();
	}

	public LookupResponse lookup(AttackType type, int locationRoll, int effectRoll) {
		List<CritEntry> critTable = critTables.get(type);
		CritEntry entry = lookupEntry(critTable, locationRoll);
		List<Effect> effects = entry.getEffects().get(effectRoll - 1)
									.stream()
									.map(this::translateEffect)
									.collect(Collectors.toList());

		LookupResponse response = new LookupResponse();
		response.setType(type);
		response.setLocation(entry.getLocation());
		response.setEffects(effects);
		return response;
	}

	private CritEntry lookupEntry(List<CritEntry> critTable, int value) {
		return critTable.stream()
						.filter(entry -> value >= entry.getLowRoll() && value <= entry.getHighRoll())
						.findAny()
						.orElse(null);
	}

	private Map<String, String> readEffects() {
		List<String[]> effects = fileManager.readFile("effects");
		return effects.stream().skip(2).collect(Collectors.toMap(vals -> vals[0], vals -> vals[1]));
	}

	private Effect translateEffect(String key) {
		Effect effect = new Effect();
		effect.setKey(key);
		String description = lookupEffect(key);
		effect.setDescription(description);
		return effect;
	}

	private String lookupEffect(String key) {
		if (effectTable.get(key) != null) {
			return (effectTable.get(key));
		}

		if (key.matches("^\\d+$")) {
			return "take " + key + " damage";
		}
		String realKey = key;
		Pattern p = Pattern.compile("^([a-zA-Z]+)([0-9]+)$");
		Matcher m = p.matcher(key);
		String numberPart = "X";

		if (m.find()) {
			String letterPart = m.group(1);
			numberPart = m.group(2);
			LOGGER.debug("I found {}, {}", letterPart, numberPart);
			realKey = letterPart + "X";
			LOGGER.debug("Replaced key with {}", realKey);
		}
		return effectTable.get(realKey) != null ?
				effectTable.get(realKey).replace("X", numberPart) : realKey;
	}

	private List<CritEntry> readCritTable(String... filenames) {
		List<String[]> data = fileManager.readFile(filenames[0]);
		List<CritEntry> entries = data.stream().skip(1).map(this::createCritTableEntry).collect(Collectors.toList());

		// Add more data from extra files
		Arrays.stream(filenames).skip(1).forEach(filename -> {
			List<String[]> data2 = fileManager.readFile(filename);
			List<List<List<String>>> moreEffects = data2.stream()
														.skip(1)
														.map(line -> readEffects(0, line))
														.collect(Collectors.toList());
			for (int i = 0; i < entries.size(); i++) {
				List<List<String>> originalEffects = entries.get(i).getEffects();
				List<List<String>> newEffects = moreEffects.get(i);
				originalEffects.addAll(newEffects);
			}
		});

		return entries;
	}

	private CritEntry createCritTableEntry(String[] cells) {
		if (cells.length < 3) {
			LOGGER.debug("Bad row detected, not enough columns (need at least 3)");
			return null;
		}

		String[] parts = cells[0].split("-");

		CritEntry result = new CritEntry();
		result.setLowRoll(Integer.parseInt(parts[0]));
		result.setHighRoll(Integer.parseInt(parts[1]));
		result.setLocation(cells[1]);
		result.setEffects(readEffects(2, cells));

		return result;
	}

	private List<List<String>> readEffects(int numToSkip, String[] cells) {
		return Arrays.stream(cells)
					 .skip(numToSkip)
					 .map(cell -> Arrays.asList(cell.split(",")))
					 .collect(Collectors.toList());
	}
}
