package net.shadowfacts.shadowlang.parser;

import net.shadowfacts.shadowlang.model.Class;
import net.shadowfacts.shadowlang.model.Component;
import net.shadowfacts.shadowlang.model.Method;
import net.shadowfacts.shadowlang.model.Type;
import net.shadowfacts.shadowlang.util.ParseException;
import net.shadowfacts.shadowlib.util.StringUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

/**
 * @author shadowfacts
 */
public class Parser implements Patterns, Opcodes {

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

		Class clazz = new Class();

		boolean foundName = false;

		String[] lines = data.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String l = lines[i];

			if (!foundName && CLASS.matcher(l).matches()) {
				clazz.name = l.split(" ")[1];
				foundName = true;
			} else if (METHOD.matcher(l).matches()) {

				int indent = StringUtils.count(l, "\t");

				Integer end = null;

				for (int j = i; j < lines.length; j++) {
					String start = "";
					for (int h = 0; h < indent; h++) {
						start += "\t";
					}
					start += "}";
					if (lines[j].startsWith(start)) {
						end = j;
						break;
					}
				}

				if (end == null) {
					throw new ParseException("Unterminated method starting at " + fileName + ":" + i);
				}

				i = end + 1;

				String[] methodLines = new String[end - i];
//				for (int j = 0; j < methodLines.length; j++) {
//					methodLines[j] = lines[i + j];
//				}
				System.arraycopy(lines, i, methodLines, 0, methodLines.length);

				clazz.methods.add(parseMethod(methodLines, fileName, i));

			}
		}

//		throw new ParseException("Couldn't parse class " + fileName);

		return clazz;
	}

	public static Method parseMethod(String[] data, String fileName, int startingLine) {
		Method method = new Method();

		for (int i = 0; i < data.length; i++) {
			int lineNum = startingLine + i;

			String line = data[i];
			if (METHOD_CALL.matcher(line).matches()) {
				String[] bits = line.split("\\(");
				String methodName = bits[0];
				String paramStr = bits[1];
				if (paramStr.endsWith(");")) {
					paramStr = paramStr.substring(paramStr.length() - 3, paramStr.length());
				} else if (paramStr.endsWith(")")) {
					paramStr = paramStr.substring(paramStr.length() - 2, paramStr.length());
				} else {
					throw new ParseException("Unterminated method parameters at " + fileName + ":" + lineNum);
				}
				if (Replacements.hasMethodReplacement(methodName)) {
					methodName = Replacements.getMethodReplacement(methodName);
				}

				int opcode = StringUtils.count(methodName, ".") == 1 ? INVOKESTATIC : INVOKEVIRTUAL;

//				MethodInsnNode insn = new MethodInsnNode(opcode)
			}

		}

		throw new ParseException("Couldn't parse method at " + fileName + ":" + startingLine);
	}

	public static Component parseComponent(String data, String fileName) {

		throw new ParseException("Couldn't parse component " + fileName);
	}
}
