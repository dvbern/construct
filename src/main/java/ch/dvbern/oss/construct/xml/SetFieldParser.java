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

import java.lang.reflect.Field;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Implementation of {@code ElementParser}. Responsible for parsing
 * xml-tags with the element-name "setfield" ({@code <setfield  >}).
 * The parser may use other {@code ElementParser} instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class SetFieldParser implements ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the
	 * information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested
	 * tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @throws ElementParserException Thrown, if a problem occurs while
	 *                                parsing the xml-tag and creating the class/object
	 *                                instances.
	 */
	@Override
	public @NonNull ClassObjectPair parse(@NonNull Element element, @NonNull ParserFactory factory)
			throws ElementParserException {

		// get name of field
		String fieldName = Objects.requireNonNull(
				element.getAttribute("name"),
				"name attribute is missing"
		);

		// get object, on which field is to set
		var objectElChildren = element.getElementsByTagName("target");
		if (objectElChildren.size() != 1) {
			throw new ElementParserException(
					"object must have exactly on child (construct or ref or cast...)");
		}
		Element objectElToParse = objectElChildren.get(0);
		ClassObjectPair cop;
		Object myObject;
		Class<?> myClass = null;
		try {
			cop = factory.getParser(objectElToParse.getNodeName()).parse(
					objectElToParse, factory);
			myObject = cop.getObject();
			myClass = cop.getKlass();
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException(ex);
		}

		// get value to set
		Element valueEl = element.getElementsByTagName("value")
				.get(0);
		Object newValue;
		try {
			ClassObjectPair tmp = factory.getParser(valueEl.getNodeName())
					.parse(valueEl, factory);
			newValue = tmp.getObject();
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException(ex);
		}

		// get Field and set value
		try {
			Field field = myClass.getField(fieldName);
			field.set(myObject, newValue);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			throw new ElementParserException(ex);
		}

		return cop;

	}
}
