package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.BodyPart;
import com.apollowebworks.hackmaster.model.LookupResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TableManagerTest {

	private static final String EFFECT = "ded";
	private static final String EFFECT_WITH_NUM = "ded X times";
	private static final String BODY_PART_1 = "nose";
	private static final String BODY_PART_2 = "elbow";

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	private TableManager tableManager;

	@Before
	public void setUp() {
		Map<AttackType, List<List<List<String>>>> critTables = new HashMap<>();
		critTables.put(AttackType.HACKING, createCritTable());
		List<BodyPart> bodyParts = new ArrayList<>();
		bodyParts.add(createBodyPart(1, BODY_PART_1));
		bodyParts.add(createBodyPart(2, BODY_PART_2));
		Map<String, String> effects = new HashMap<>();
		effects.put("X", EFFECT);
		effects.put("d", EFFECT);
		effects.put("dX", EFFECT_WITH_NUM);
		tableManager = new TableManager(critTables, bodyParts, effects);
	}

	@Test
	public void lookup_whenNormalValue_thenTranslated() {
		LookupResponse response = tableManager.lookup(AttackType.HACKING, 1, 2);
		assertEquals(BODY_PART_1, response.getBodyPart().getName());
		assertEquals(1, response.getEffects().size());
		assertEquals(EFFECT, response.getEffects().get(0).getDescription());
	}

	@Test
	public void lookup_whenNumberOnly_thenTranslated() {
		LookupResponse response = tableManager.lookup(AttackType.HACKING, 1, 1);
		assertEquals(BODY_PART_1, response.getBodyPart().getName());
		assertEquals(1, response.getEffects().size());
		assertEquals("take 3 damage", response.getEffects().get(0).getDescription());
	}

	@Test
	public void lookup_whenNumericValue_thenTranslated() {
		LookupResponse response = tableManager.lookup(AttackType.HACKING, 2, 3);
		assertEquals(BODY_PART_2, response.getBodyPart().getName());
		assertEquals(1, response.getEffects().size());
		assertEquals("ded 9 times", response.getEffects().get(0).getDescription());
	}

	private BodyPart createBodyPart(int value, String name) {
		BodyPart bodyPart = new BodyPart();
		bodyPart.setLowRoll(value);
		bodyPart.setHighRoll(value);
		bodyPart.setName(name);
		bodyPart.setId(value-1);
		return bodyPart;
	}

	private ArrayList<List<List<String>>> createCritTable() {
		ArrayList<List<List<String>>> table = new ArrayList<>();
		table.add(createRow());
		table.add(createRow());
		return table;
	}

	private List<List<String>> createRow() {
		List<List<String>> row = new ArrayList<>();
		row.add(Collections.singletonList("3"));
		row.add(Collections.singletonList("d"));
		row.add(Collections.singletonList("d9"));
		return row;
	}
}