package net.shadowfacts.shadowlang.model;

import net.shadowfacts.shadowlang.util.AccessList;

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

	public AccessList access;

	public Object deafultValue;

	public Field() {
		access.add(Access.PUBLIC);
	}

}
