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

import java.util.Set;

import ch.dvbern.oss.construct.ConstructionException;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Implementation of {@code ElementParser}. Responsible for parsing
 * xml-tags with the element-name of a primitive (see description in
 * {@code PrimObjectFactory}) (for example {@code <int  >}).
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 *
 * @see PrimObjectFactory
 * @see ClassFactory
 */
public class PrimParser implements ElementParser {

	private static final Set<String> ALLOWED_ELEMENT_NAMES = Set.of(
			"string",
			"int",
			"long",
			"short",
			"float",
			"double",
			"boolean",
			"char"
	);

	/**
	 * Method parses the passed xml-element and creates an object based on the
	 * information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested
	 * tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @throws ElementParserException if a problem occurs while
	 *                                parsing the xml-tag and creating the class/object
	 *                                instances.
	 */
	@Override
	@NonNull
	public ClassObjectPair parse(@NonNull Element element, @NonNull ParserFactory factory)
			throws ElementParserException {
		String elementName = element.getNodeName();
		if (!ALLOWED_ELEMENT_NAMES.contains(elementName)) {
			throw new ElementParserException(
					"PrimParser can not handle elements with the name=" + elementName);
		}

		//primitives and Strings
		Class<?> klass = null;
		try {
			klass = ClassFactory.getKlass(elementName);
		} catch (ClassNotFoundException ex) {
			throw new ElementParserException(
					"ClassFactory could NOT load Class with type=" + elementName, ex);
		}
		String strValue = element.getAttribute("value");
		if (strValue == null) {
			throw new ElementParserException("definition of argument NOT correct (value must NOT be null)");
		}
		Object value;
		try {
			value = PrimObjectFactory.getWrapper(elementName, strValue);
		} catch (ConstructionException ex) {
			throw new ElementParserException(ex);
		}
		return new ClassObjectPair(klass, value);

	}

}
