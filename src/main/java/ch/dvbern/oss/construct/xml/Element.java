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
import org.checkerframework.checker.nullness.qual.Nullable;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper of {@code org.w3c.dom.Element} with constant interface.
 */
public class Element {

	private final org.w3c.dom.@NonNull Element nestedElement;

	/**
	 * Constructor.
	 *
	 * @param nestedElement the wrapped element
	 */
	public Element(org.w3c.dom.@NonNull Element nestedElement) {
		this.nestedElement = nestedElement;
	}

	/**
	 * @return all element attributes as a copy
	 * @see org.w3c.dom.Element#getAttributes()
	 */
	@NonNull
	public Map<String, String> getAttributes() {
		Map<String, String> result = new HashMap<>();
		NamedNodeMap nnm = nestedElement.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node node = nnm.item(i);
			result.put(node.getNodeName(), node.getNodeValue());
		}
		return result;
	}

	/**
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or {@code null} if not existing
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	@Nullable
	public String getAttribute(@NonNull String name) {
		return getAttributeValue(name);
	}

	/**
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or {@code null} if not existing
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	@Nullable
	public String getAttributeValue(@NonNull String name) {

		if (!nestedElement.hasAttribute(name)) {
			return null;
		}
		return nestedElement.getAttribute(name);
	}

	/**
	 * @param name name of the requested subelements
	 * @return requested subelements or an empty {@code List} if not existing
	 * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
	 */
	@NonNull
	public List<Element> getElementsByTagName(@NonNull String name) {

		NodeList nodeList = nestedElement.getElementsByTagName(name).item(0).getChildNodes();

		List<Element> result = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
		}

		return result;
	}

	/**
	 * @return name of the node
	 * @see org.w3c.dom.Node#getNodeName()
	 */
	@NonNull
	public String getNodeName() {
		return nestedElement.getNodeName();
	}

	/**
	 * @return name of the node
	 * @see Node#getNodeName()
	 */
	@NonNull
	public String getName() {
		return nestedElement.getNodeName();
	}

	/**
	 * @return subelements or an empty {@code List} if not existing
	 * @see org.w3c.dom.Node#getChildNodes()
	 */
	@NonNull
	public List<Element> getChildElements() {

		NodeList nodeList = nestedElement.getChildNodes();

		List<Element> result = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof org.w3c.dom.Element elementItem) {
				result.add(new Element(elementItem));
			}
		}
		return result;
	}

	/**
	 * @return subelements or an empty {@code List} if not existing
	 * @see org.w3c.dom.Element#getChildNodes()
	 */
	@NonNull
	public List<Element> getChildren() {
		return getChildElements();
	}

	/**
	 * @return parent element
	 * @see org.w3c.dom.Element#getParentNode()
	 */
	@Nullable
	public Element getParent() {
		return new Element((org.w3c.dom.Element) nestedElement.getParentNode());
	}

}
