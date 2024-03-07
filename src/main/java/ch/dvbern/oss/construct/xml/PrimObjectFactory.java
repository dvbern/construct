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


import ch.dvbern.oss.construct.ConstructionException;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This class is responsible for creating wrappers of the primitive types (inkl.
 * "java.lang.String"). As {@code type} it uses the following Strings:
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
 */
public final class PrimObjectFactory {

	private PrimObjectFactory() {
	}

	/**
	 * This method returns a wrapper of the specified {@code type} for
	 * the {@code strValue}. As {@code type} the following
	 * Strings are supported:
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
	 *
	 * @param type {@code java.lang.String} indicating the type of the
	 * wrapper
	 * @param strValue {@code java.lang.String} containing the value to
	 * wrap.
	 * @return returns a wrapper of the specified {@code type} for the
	 * {@code strValue}
	 * @throws ConstructionException Thrown, if the specified type is not
	 *                               supported or if a wrapper of type {@code type} cannot
	 *                               be created for the value {@code strValue}
	 */
	@NonNull
	public static Object getWrapper(@NonNull String type, @NonNull String strValue)
			throws ConstructionException {
		Object returnVal = null;
		try {
			if (type.equalsIgnoreCase("string")) {
				returnVal = strValue;
			} else if (type.equalsIgnoreCase("int")) {
				returnVal = Integer.valueOf(strValue);
			} else if (type.equalsIgnoreCase("long")) {
				returnVal = Long.valueOf(strValue);
			} else if (type.equalsIgnoreCase("short")) {
				returnVal = Short.valueOf(strValue);
			} else if (type.equalsIgnoreCase("float")) {
				returnVal = Float.valueOf(strValue);
			} else if (type.equalsIgnoreCase("double")) {
				returnVal = Double.valueOf(strValue);
			} else if (type.equalsIgnoreCase("boolean")) {
				returnVal = Boolean.valueOf(strValue);
			} else if (type.equalsIgnoreCase("char")) {
				if (strValue.length() != 1) {
					throw new ConstructionException(strValue + " is NOT a valid Character");
				}
				returnVal = strValue.charAt(0);
			}
		} catch (Exception ex) {
			throw new ConstructionException("error while trying to create wrapper", ex);
		}
		if (returnVal == null) {
			throw new ConstructionException(
					"can NOT create wrapper object for type=" + type
							+ " with stringValue=" + strValue);
		}
		return returnVal;
	}

}
