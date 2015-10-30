package net.shadowfacts.shadowlang;

import net.shadowfacts.shadowlang.model.Class;
import net.shadowfacts.shadowlang.model.Component;
import net.shadowfacts.shadowlang.model.Type;
import net.shadowfacts.shadowlang.parser.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shadowfacts
 */
public class ShadowLang {

	public static void main(String[] args) throws IOException {
		if (args.length < 1 || args[0].isEmpty()) {
			throw new IllegalArgumentException("Must provide a file as the first argument");
		}

		String data = new String(Files.readAllBytes(new File(args[0]).toPath()));

		Type t = Parser.getType(data, args[0]);

		List<Class> classes = new ArrayList<>();
		List<Component> components = new ArrayList<>();

		switch (t) {
			case CLASS:
				classes.add(Parser.parseClass(data, args[0]));
				break;
			case COMPONENT:
				components.add(Parser.parseComponent(data, args[0]));
				break;
		}

		File out = new File("out/");
		if (!out.exists()) out.mkdirs();

//		TODO: Components
//		for (Component component : components) {
//			byte[] bytes = Compiler.compileComponent(component);
//		}

		for (Class clazz : classes) {
			byte[] bytes = Compiler.compileClass(clazz);

			File f = new File(out, clazz.name.replace('.', '/') + ".class");
			if (f.exists()) f.delete();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
		}

	}

}
