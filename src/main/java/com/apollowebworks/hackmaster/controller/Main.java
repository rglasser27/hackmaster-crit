package com.apollowebworks.hackmaster.controller;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//rangeLookupTest();
//		readTableTest();
//		readOutcomeTest();
	}

//	public static void readOutcomeTest() {
//		EffectTable ot = HackReader.readOutcomeTable("data\\effects.csv");
//		String oc = ot.lookup("bX");
//		System.out.println(oc);
//	}


//	public static void rangeLookupTest() {
//		CritTable<String> rl = new CritTable<String>();
//		rl.addEntry("Group 1!", 50, 100);
//		rl.addEntry("Group 2!", 101, 150);
//		System.out.println(rl.lookupEntry(49));
//		System.out.println(rl.lookupEntry(50));
//		System.out.println(rl.lookupEntry(100));
//		System.out.println(rl.lookupEntry(101));
//		System.out.println(rl.lookupEntry(150));
//		System.out.println(rl.lookupEntry(151));
//	}

//	public static void readTableTest() {
//		CritTable ct = HackReader.readCritTable("data\\SevLevel.csv");
//		EffectTable ot = HackReader.readOutcomeTable("data\\effects.csv");
//		int roll1 = 0, roll2 = 0;
//		while(roll1 >= 0) {
//			System.out.print("Enter a number between 1 and 10000: ");
//			Scanner scanner = new Scanner(System.in);
//			roll1 = scanner.nextInt();
//			if (roll1 >= 0) {
//				String loc = ct.getLocation(roll1);
//				System.out.println("You got hit in the "+loc+". Ow!");
//				System.out.print("Now enter a number from 1-13 to see how bad it was: ");
//				roll2 = scanner.nextInt();
//				String outcomes = ct.getOutcomeDisplay(roll1, roll2, ot);
//				System.out.println(outcomes);
//				System.out.println("Oh, that sounds pretty nasty.");
//			}
//		}
//	}

}
