package net.shadowfacts.shadowlang.model;

import net.shadowfacts.shadowlang.util.AccessList;
import org.objectweb.asm.tree.InsnList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shadowfacts
 */
public class Method {

	public List<Annotation> annotations = new ArrayList<>();

	public AccessList access = new AccessList();

	public InsnList instructions = new InsnList();

	public String name;

	public String desc;

	public Method() {
		access.add(Access.PUBLIC);
	}

}
