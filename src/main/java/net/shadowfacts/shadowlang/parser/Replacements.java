package net.shadowfacts.shadowlang.parser;

import java.util.HashMap;

/**
 * @author shadowfacts
 */
public class Replacements {

	private static HashMap<String, String> methods = new HashMap<>();

	static {
		methods.put("System.log", "System.out.println");
	}

	public static boolean hasMethodReplacement(String s) {
		return methods.containsKey(s);
	}

	public static String getMethodReplacement(String s) {
		return methods.get(s);
	}

}
