package net.shadowfacts.shadowlang.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shadowfacts
 */
public class Field {

	public List<Annotation> annotations = new ArrayList<>();

	public String name;

	public String desc;

	public List<Access> access = Arrays.asList(Access.PUBLIC);

	public Object deafultValue;

	public Field(String data) {
		// TODO: Parse data
	}

}
