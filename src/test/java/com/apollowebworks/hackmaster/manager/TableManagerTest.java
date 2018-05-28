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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TableManagerTest {

	private static final String EFFECT = "ded";
	private static final String BODY_PART = "nose";
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	private TableManager tableManager;

	@Before
	public void setUp() {
		Map<AttackType, List<List<List<String>>>> critTables = new HashMap<>();
		critTables.put(AttackType.HACKING, createCritTable());
		List<BodyPart> bodyParts = new ArrayList<>();
		bodyParts.add(createBodyPart());
		Map<String, String> effects = new HashMap<>();
		effects.put("d", EFFECT);
		tableManager = new TableManager(critTables, bodyParts, effects);
	}

	private BodyPart createBodyPart() {
		BodyPart bodyPart = new BodyPart();
		bodyPart.setLowRoll(1);
		bodyPart.setHighRoll(1);
		bodyPart.setName(BODY_PART);
		bodyPart.setId(1);
		return bodyPart;
	}

	private ArrayList<List<List<String>>> createCritTable() {
		ArrayList<List<List<String>>> table = new ArrayList<>();
		List<List<String>> row = new ArrayList<>();
		table.add(row);
		row.add(Collections.singletonList("d"));
		return table;
	}

	@Test
	public void rangeLookupTest() {
		LookupResponse response = tableManager.lookup(AttackType.HACKING, 1, 1);
		assertEquals(BODY_PART, response.getBodyPart().getName());
		assertEquals(1, response.getEffects().size());
		assertEquals(EFFECT, response.getEffects().get(0).getDescription());
	}
}