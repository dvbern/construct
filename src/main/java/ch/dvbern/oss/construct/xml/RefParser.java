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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of {@code ElementParser}. Responsible for parsing
 * xml-tags with the element-name "ref" ({@code <ref  >}). The
 * parser may use other {@code ElementParser} instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class RefParser implements ElementParser {

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
	@NonNull
	public ClassObjectPair parse(@NonNull Element element, @NonNull ParserFactory factory)
			throws ElementParserException {
		String elementName = element.getNodeName();
		if (!elementName.equals("ref")) {
			throw new ElementParserException("RefParser can not handle elements with the name=" + elementName);
		}
		String objectId = element.getAttribute("id");
		if (objectId == null || objectId.isEmpty()) {
			throw new ElementParserException(
					"id of ref tag must be the name of a xml-file");
		}
		try {
			InputStream ins = factory.getResourceLocator().getResourceAsStream(
					objectId);
			DocumentBuilder builder = DocumentBuildFactoryWrapper.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(ins);
			Element root = new Element(doc.getDocumentElement());

			return factory.getParser(root.getNodeName()).parse(
					root, factory);
		} catch (SAXException | IOException | ParserConfigurationException ex) {
			throw new ElementParserException("parsing of file with id="
					+ objectId + " NOT successfull", ex);
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException("no parser found", ex);
		} catch (ResourceNotFoundException ex) {
			throw new ElementParserException("ref parser: resource not foung", ex);
		}
	}

}
