package net.shadowfacts.shadowlang;

import net.shadowfacts.shadowlang.model.Access;
import net.shadowfacts.shadowlang.model.Class;
import net.shadowfacts.shadowlang.model.Field;
import net.shadowfacts.shadowlang.model.Method;
import org.objectweb.asm.*;

/**
 * @author shadowfacts
 */
public class Compiler implements Opcodes {

	public static byte[] compileClass(Class clazz) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, clazz.name, null, clazz.superName, null);

		compileAnnotations(cw, clazz);

		compileFields(cw, clazz);

		compileMethods(cw, clazz);


		return cw.toByteArray();
	}

	private static void compileAnnotations(ClassWriter cw, Class clazz) {
		// TODO: Implement
	}

	private static void compileFields(ClassWriter cw, Class clazz) {
		for (Field f : clazz.fields) {
			int access = 0;
			for (Access a : f.access) access += a.opcode;

//			FieldVisitor fv = cw.visitField(access, f.name, f.desc, null, null);
//			fv.visitEnd();
			cw.visitField(access, f.name, f.desc, null, null);
		}
	}

	private static void compileMethods(ClassWriter cw, Class clazz) {
		for (Method m : clazz.methods) {
			compileMethod(cw, clazz, m);
		}

		compileConstructor(cw, clazz);

		compileClassInitializer(cw, clazz);
	}

	private static void compileMethod(ClassWriter cw, Class clazz, Method method) {
		// TODO: Convert SL method to JVM bytecode
	}

	private static void compileConstructor(ClassWriter cw, Class clazz) {
//		<init>
		if (clazz.constructor == null) {
			int access = 0;
			for (Access a : clazz.constructorAccess) access += a.opcode;

			MethodVisitor mv = cw.visitMethod(access, "<init>", "()V", null, null);

			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, clazz.superName, "<init>", "()V", false);
			mv.visitInsn(RETURN);

//			Label l1 = new Label();
//			mv.visitLabel(l1);
//			mv.visitLocalVariable("this", clazz.name, null, l0, l1, 0);
			mv.visitEnd();

		} else {
			// TODO: Convert SL method to JVM bytecode
		}
	}

	private static void compileClassInitializer(ClassWriter cw, Class clazz) {
//		<clinit>
		if (clazz.fields.stream().filter(f -> f.access.contains(Access.STATIC)).findAny().isPresent()) {
			int access = 0;
			for (Access a : clazz.constructorAccess) access += a.opcode;

			MethodVisitor mv = cw.visitMethod(access, "<clinit>", "()V", null, null);
			mv.visitCode();

			clazz.fields.stream()
					.filter(f -> f.access.contains(Access.STATIC))
					.forEach(f -> {
						Label l = new Label();
						mv.visitLabel(l);
						mv.visitLdcInsn(f.deafultValue);
						mv.visitFieldInsn(PUTSTATIC, clazz.name, f.name, f.desc);
					});

			mv.visitInsn(RETURN);
		}
	}

}
