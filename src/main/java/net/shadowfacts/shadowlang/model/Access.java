package net.shadowfacts.shadowlang.model;

import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author shadowfacts
 */
public enum Access {

	PUBLIC(Opcodes.ACC_PUBLIC), // deafult access
	PROTECTED(Opcodes.ACC_PROTECTED),
	STATIC(Opcodes.ACC_STATIC);

	public int opcode;

	Access(int opcode) {
		this.opcode = opcode;
	}
}
