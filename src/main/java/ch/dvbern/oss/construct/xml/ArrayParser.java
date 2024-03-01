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

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Array;


/**
 * Implementation of {@code ElementParser}. Responsible for parsing xml-tags with the element-name "array"
 * ({@code <array  >}).
 * The parser may use other {@code ElementParser} instances for parsing nested elements. <br>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ArrayParser implements ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @throws ElementParserException Thrown, if a problem occurs while parsing the xml-tag and creating the
	 *                                class/object instances.
	 */
	@Override
    @NonNull
    public ClassObjectPair parse(@NonNull Element element, @NonNull ParserFactory factory)
			throws ElementParserException {

		// get Class of array
		// check elementName
		String elementName = element.getNodeName();
		if (!elementName.equals("array")) {
			throw new ElementParserException("array-parser can not handle elements with the name=" + elementName);
		}
		String type = element.getAttribute("elementtype");
		if (type == null || type.isEmpty()) {
			throw new ElementParserException("Attribute 'elementtype' may not be null/empty on element "
					+ element.getNodeName());
		}
        Class<?> arrayClass = null;
		try {
			arrayClass = ClassFactory.getKlass(type);
		} catch (ClassNotFoundException ex) {
			throw new ElementParserException(
					"ArrayParser: ClassFactory could NOT create array-class for type=" + type,
					ex);
		}
		// get children
        var arrayChildren = element.getChildElements();
		Object array = Array.newInstance(arrayClass, arrayChildren.size());
		for (int i = 0; i < arrayChildren.size(); i++) {
            Element el = arrayChildren.get(i);
			try {
				ClassObjectPair cop = factory.getParser(el.getNodeName()).parse(el, factory);
                Array.set(array, i, cop.getObject());
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException(
						"ArrayParser: could NOT find parser for name=" + el.getNodeName(),
						ex);
			} catch (Exception ex) {
				throw new ElementParserException("ArrayParser: Could NOT set value into array", ex);
			}
		}
        var klass = array.getClass();

		return new ClassObjectPair(klass, array);

	}

}
