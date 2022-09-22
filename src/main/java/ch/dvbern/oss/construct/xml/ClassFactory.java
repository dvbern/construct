/*
 * Copyright (C) 2022 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.dvbern.oss.construct.xml;

import java.lang.reflect.Array;

import javax.annotation.Nonnull;

/**
 * This class is responsible for returning <code>Class</code> instances
 * corresponing to the specified <code>type</code>
 * <p>
 * The following <code>type</code> parameters are possible:
 * <ul>
 * <li>a class-name (e.g. "java.lang.Integer")</li>
 * <li>a class-name with indication of array-dimension ("[]" for every
 * dimension) (e.g. "java.lang.String[]" or "java.lang.Integer[][][]")</li>
 * <li>a String, indicating a "primitive" type (inkl."java.lang.String") <br>
 * The following types are supported:
 * <ul>
 * <li>"string"</li>
 * <li>"int"</li>
 * <li>"long"</li>
 * <li>"short"</li>
 * <li>"float"</li>
 * <li>"double"</li>
 * <li>"boolean"</li>
 * <li>"char"</li>
 * </ul>
 * </li>
 * </ul>
 */
public final class ClassFactory {

	private ClassFactory() {
	}

	/**
	 * Returns a <code>Class</code> instance for the specified
	 * <code>type</code>, never null. See description of class.
	 *
	 * @param type String, see description of this <code>ClassFactory</code>.
	 * @return Returns a <code>Class</code> instance for the specified
	 * <code>type</code>, never null
	 * @throws ClassNotFoundException Thrown, if no <code>Class</code> has
	 *                                been found for the specified <code>type</code>.
	 * @see ClassFactory
	 */
	@Nonnull
	public static Class getKlass(@Nonnull String type) throws ClassNotFoundException {
		Class retVal;
		if (type.equalsIgnoreCase("string")) {
			retVal = String.class;
		} else if (type.equalsIgnoreCase("int")) {
			retVal = Integer.TYPE;
		} else if (type.equalsIgnoreCase("long")) {
			retVal = Long.TYPE;
		} else if (type.equalsIgnoreCase("short")) {
			retVal = Short.TYPE;
		} else if (type.equalsIgnoreCase("float")) {
			retVal = Float.TYPE;
		} else if (type.equalsIgnoreCase("double")) {
			retVal = Double.TYPE;
		} else if (type.equalsIgnoreCase("boolean")) {
			retVal = Boolean.TYPE;
		} else if (type.equalsIgnoreCase("char")) {
			retVal = Character.TYPE;
		} else if (type.endsWith("[]")) {
			String baseType = type.substring(0, type.length() - 2);
			Class baseClass = ClassFactory.getKlass(baseType);
			retVal = Array.newInstance(baseClass, 0).getClass();
		} else {
			retVal = Class.forName(type);
		}
		return retVal;
	}

}
