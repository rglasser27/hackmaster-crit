package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.BodyPart;
import com.apollowebworks.hackmaster.model.Effect;
import com.apollowebworks.hackmaster.model.LookupResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TableManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TableManager.class);

	private final Map<AttackType, List<List<List<String>>>> critTables;
	private Map<String, String> effectTable;
	private final List<BodyPart> bodyParts;

	@Autowired
	TableManager(Map<AttackType, List<List<List<String>>>> critTables,
				 List<BodyPart> bodyParts,
				 Map<String, String> effectTable) {
		this.bodyParts = bodyParts;
		this.critTables = critTables;
		this.effectTable = effectTable;
	}

	public LookupResponse lookup(AttackType type, int locationRoll, int effectRoll) {
		BodyPart bodyPart = getBodyPart(locationRoll);
		List<List<String>> row = critTables.get(type).get(bodyPart.getId() - 1);
		List<Effect> effects = row.get(effectRoll - 1)
								  .stream()
								  .map(this::translateEffect)
								  .collect(Collectors.toList());

		LookupResponse response = new LookupResponse();
		response.setType(type);
		response.setBodyPart(bodyPart);
		response.setEffects(effects);
		return response;
	}

	private BodyPart getBodyPart(int value) {
		return bodyParts.stream()
						.filter(entry -> value >= entry.getLowRoll() && value <= entry.getHighRoll())
						.findAny()
						.orElse(null);
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
}
