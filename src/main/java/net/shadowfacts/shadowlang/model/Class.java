package net.shadowfacts.shadowlang.model;

import net.shadowfacts.shadowlang.util.AccessList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shadowfacts
 */
public class Class {

	/**
	 * The class' annotations
	 */
	public List<Annotation> annotations = new ArrayList<>();

	/**
	 * The class' fields
	 */
	public List<Field> fields = new ArrayList<>();

	/**
	 * The class' methods
	 */
	public List<Method> methods = new ArrayList<>();

	/**
	 * The fully qualified name of the class
	 */
	public String name;

	/**
	 * The fully qualified name of the super class
	 */
	public final String superName = "Ljava/lang/Object;";

	/**
	 * The constructor, leave {@code null} for the default constructor
	 */
	public Method constructor;

	/**
	 * The access the constructor has
	 */
	public AccessList constructorAccess;

	public boolean hasStaticField() {
		return fields.stream().filter(f -> f.access.isStatic()).findAny().isPresent();
	}

}
