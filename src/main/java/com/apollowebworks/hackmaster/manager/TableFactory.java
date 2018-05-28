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
	private final FileManager fileManager;

	@Autowired
	public TableFactory(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public Map<AttackType, List<List<List<String>>>> createCritTables() {
		Map<AttackType, List<List<List<String>>>> critTables = new HashMap<>();
		critTables.put(AttackType.HACKING, readCritTable("hacking1", "hacking2"));
		critTables.put(AttackType.CRUSHING, readCritTable("crushing1", "crushing2"));
		critTables.put(AttackType.PIERCING, readCritTable("piercing1", "piercing2"));
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
