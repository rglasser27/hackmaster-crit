package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.BodyPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TableFactory {
	private static final String HACKING_1 = "hacking1";
	private static final String HACKING_2 = "hacking2";
	private static final String CRUSHING_1 = "crushing1";
	private static final String PIERCING_1 = "piercing1";
	private static final String CRUSHING_2 = "crushing2";
	private static final String PIERCING_2 = "piercing2";

	private final FileManager fileManager;

	@Autowired
	public TableFactory(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public Map<AttackType, List<List<List<String>>>> createCritTables() {
		Map<AttackType, List<List<List<String>>>> critTables = new HashMap<>();
		critTables.put(AttackType.HACKING, readCritTable(HACKING_1, HACKING_2));
		critTables.put(AttackType.CRUSHING, readCritTable(CRUSHING_1, CRUSHING_2));
		critTables.put(AttackType.PIERCING, readCritTable(PIERCING_1, PIERCING_2));
		return critTables;
	}

	public List<BodyPart> createBodyParts() {
		return fileManager.readBodyPartFile();
	}

	public Map<String, String> createEffectsReference() {
		List<String[]> effects = fileManager.readStringArrayFile("effects");
		return effects.stream().skip(2).collect(Collectors.toMap(vals -> vals[0], vals -> vals[1]));
	}

	private List<List<List<String>>> readCritTable(String filename1, String filename2) {
		List<String[]> data1 = fileManager.readStringArrayFile(filename1);
		List<String[]> data2 = fileManager.readStringArrayFile(filename2);
		List<List<List<String>>> response = new ArrayList<>();
		for (int i = 0; i < data1.size(); i++) {
			List<List<String>> line = new ArrayList<>();
			line.addAll(readEffects(data1.get(i)));
			line.addAll(readEffects(data2.get(i)));
			response.add(line);
		}
		return response;
	}

	private List<List<String>> readEffects(String[] cells) {
		return Arrays.stream(cells)
					 .map(cell -> Arrays.asList(cell.split(",")))
					 .collect(Collectors.toList());
	}

}
