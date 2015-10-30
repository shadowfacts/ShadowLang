package net.shadowfacts.shadowlang.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shadowfacts
 */
public class Method {

	public List<Annotation> annotations = new ArrayList<>();

	public List<Access> access = Arrays.asList(Access.PUBLIC);

	public Method(String data) {
		// TODO: Parse data
	}

}
