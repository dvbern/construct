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

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "vardef" (<code>&lt;vardef  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class VardefParser implements ElementParser {

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
	@Nonnull
	public ClassObjectPair parse(@Nonnull Element element, @Nonnull ParserFactory factory)
			throws ElementParserException {
		/** * get variable name ** */
		String varName = element.getAttribute("name");
		if (varName == null || varName.isEmpty()) {
			throw new ElementParserException("attribute 'name' may not be null or empty");
		}

		/** * get class object pair for variable value ** */
		Element objectElement = element.getChildElements().get(0);
		ClassObjectPair cop;
		try {
			cop = factory.getParser(objectElement.getNodeName()).parse(
					objectElement, factory);
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException(ex);
		}

		/** * set variable into ScopeParserFactory ** */
		if (!(factory instanceof ScopeParserFactory)) {
			throw new ElementParserException(
					"passed factory must be of type 'ScopeParserFactory'");
		}
		try {
			((ScopeParserFactory) factory).setVariableCOP(varName, cop);
		} catch (VariableAlreadyDefinedException ex) {
			throw new ElementParserException(ex);
		}

		return cop;
	}
}
