package net.shadowfacts.shadowlang.parser;

import net.shadowfacts.shadowlang.model.Class;
import net.shadowfacts.shadowlang.model.Component;
import net.shadowfacts.shadowlang.model.Type;

import java.util.regex.Pattern;

/**
 * @author shadowfacts
 */
public class Parser {

	private static final Pattern CLASS = Pattern.compile("class (.+)");
	private static final Pattern COMPONENT = Pattern.compile("component (.+)");

	public static Type getType(String data, String fileName) {

		String[] lines = data.split("\n");
		for (String line : lines) {
			if (CLASS.matcher(line).matches()) {
				return Type.CLASS;
			} else if (COMPONENT.matcher(line).matches()) {
				return Type.COMPONENT;
			}
		}

		throw new ParseException("No type for " + fileName);
	}

	public static Class parseClass(String data, String fileName) {

		throw new ParseException("Couldn't parse class " + fileName);
	}

	public static Component parseComponent(String data, String fileName) {

		throw new ParseException("Couldn't parse component " + fileName);
	}
}
