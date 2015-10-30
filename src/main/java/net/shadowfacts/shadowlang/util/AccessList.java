package net.shadowfacts.shadowlang.util;

import net.shadowfacts.shadowlang.model.Access;

import java.util.ArrayList;

/**
 * @author shadowfacts
 */
public class AccessList extends ArrayList<Access> {

	public int getAccess() {
		int access = 0;
		for (Access a : this) access += a.opcode;
		return access;
	}

	public boolean isStatic() {
		return contains(Access.STATIC);
	}

}
