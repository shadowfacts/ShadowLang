package net.shadowfacts.shadowlang;

import net.shadowfacts.shadowlang.model.Access;
import net.shadowfacts.shadowlang.model.Class;
import net.shadowfacts.shadowlang.model.Field;
import net.shadowfacts.shadowlang.model.Method;
import net.shadowfacts.shadowlang.util.CompilerException;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

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
//			FieldVisitor fv = cw.visitField(f.access.getAccess(), f.name, f.desc, null, null);
//			fv.visitEnd();
			cw.visitField(f.access.getAccess(), f.name, f.desc, null, null);
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
		MethodVisitor mv = cw.visitMethod(method.access.getAccess(), method.name, method.desc, null, null);
		for (AbstractInsnNode abstractInsn : method.instructions.toArray()) {
			switch (abstractInsn.getType()) {
				case AbstractInsnNode.METHOD_INSN:
					MethodInsnNode insn = (MethodInsnNode)abstractInsn;
					mv.visitMethodInsn(insn.getOpcode(), insn.owner, insn.name, insn.desc, insn.itf);
					break;
				default:
					throw new CompilerException(String.format("Unknown instruction type %d for %s", abstractInsn.getType(), abstractInsn));
			}
		}
	}

	private static void compileConstructor(ClassWriter cw, Class clazz) {
//		<init>
		if (clazz.constructor == null) {

			MethodVisitor mv = cw.visitMethod(clazz.constructorAccess.getAccess(), "<init>", "()V", null, null);

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
		if (clazz.hasStaticField()) {
			MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
			mv.visitCode();

			clazz.fields.stream()
					.filter(f -> f.access.isStatic())
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
