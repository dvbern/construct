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

import java.util.ArrayList;

/**
 * Implementation of {@code ElementParser}. Responsible for parsing
 * xml-tags with the element-name "construct" ({@code <construct  >}).
 * The parser may use other {@code ElementParser} instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ConstructParser implements ElementParser {

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

		ClassObjectPair retVal;
		String className = element.getAttribute("class");
		if (className == null || className.isEmpty()) {
			throw new ElementParserException("attribute 'class' may not be null on " + element.getNodeName());
		}
		Class<?> klass = null;
		try {
			klass = ClassFactory.getKlass(className);
		} catch (ClassNotFoundException ex) {
			throw new ElementParserException(
					"classLoader could NOT load class for name=" + className,
					ex);
		}

		var argChildren = element.getChildElements();
		var argClasses = new ArrayList<>(argChildren.size());
		var initArgs = new ArrayList<>(argChildren.size());
		for (Element el : argChildren) {
			//add constructor argument
			try {
				ClassObjectPair cop = factory.getParser(el.getNodeName())
						.parse(el, factory);
				argClasses.add(cop.getKlass());
				initArgs.add(cop.getObject());
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException(
						"no parser found for element name=" + el.getNodeName(),
						ex);
			}

		}

		Construct construct = new Construct(klass, argClasses
				.toArray(new Class<?>[argClasses.size()]), initArgs.toArray(new Object[initArgs.size()]));
		try {
			retVal = new ClassObjectPair(construct.getKlass(), construct
					.getObject());
		} catch (ConstructionException ex) {
			throw new ElementParserException(ex);
		}

		return retVal;
	}
}
