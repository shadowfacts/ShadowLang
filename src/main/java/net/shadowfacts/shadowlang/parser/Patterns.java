package net.shadowfacts.shadowlang.parser;

import java.util.regex.Pattern;

/**
 * @author shadowfacts
 */
public interface Patterns {

	Pattern CLASS = Pattern.compile("class (\\w|_|\\.)+: \\{");
	Pattern COMPONENT = Pattern.compile("component (.+)");

	Pattern METHOD = Pattern.compile("(nada|int) (\\w|_)+\\(((\\w|_)+ (\\w|_)+(, )?)+\\): \\{");

	Pattern METHOD_CALL = Pattern.compile("((\\w|_|.)+\\.)?(\\w|_)+\\(.+(, )?\\);?");

}
