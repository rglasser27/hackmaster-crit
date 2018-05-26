package com.apollowebworks.hackmaster.manager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

@RunWith(MockitoJUnitRunner.class)
public class TableManagerTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Mock
	private FileManager fileManager;

	@Before
	public void setUp() {
		TableManager tableManager = new TableManager(fileManager);
	}

	@Test
	public void rangeLookupTest() {
//		CritTable rl = new CritTable();
//		rl.addEntry("Group 1!", 50, 100);
//		rl.addEntry("Group 2!", 101, 150);
//		System.out.println(rl.lookupEntry(49));
//		System.out.println(rl.lookupEntry(50));
//		System.out.println(rl.lookupEntry(100));
//		System.out.println(rl.lookupEntry(101));
//		System.out.println(rl.lookupEntry(150));
//		System.out.println(rl.lookupEntry(151));
	}
}