/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2007/05/14 14:29:48 $ - $Author: meth $ - $Revision: 1.5 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Wrapper of <code>org.w3c.dom.Element</code> with constant interface.
 */
public class Element {

	@Nonnull
	private org.w3c.dom.Element nestedElement;

	/**
	 * Constructor.
	 *
	 * @param nestedElement the wrapped element
	 */
	public Element(@Nonnull org.w3c.dom.Element nestedElement) {
		this.nestedElement = nestedElement;
	}

	/**
	 * @see org.w3c.dom.Element#getAttributes()
	 * @return all element attributes as a copy
	 */
	@Nonnull
	public Map<String, String> getAttributes() {
		Map<String, String> result = new HashMap<String, String>();
		NamedNodeMap nnm = nestedElement.getAttributes();
		for (int i = 0; i < nnm.getLength(); i++) {
			Node node = nnm.item(i);
			result.put(node.getNodeName(), node.getNodeValue());
		}
		return result;
	}

	/**
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or <code>null</code> if not existing
	 */
	@Nullable
	public String getAttribute(@Nonnull String name) {

		if (!nestedElement.hasAttribute(name)) {
			return null;
		}
		return nestedElement.getAttribute(name);
	}

	/**
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or <code>null</code> if not existing
	 */
	public String getAttributeValue(@Nonnull String name) {

		if (!nestedElement.hasAttribute(name)) {
			return null;
		}
		return nestedElement.getAttribute(name);
	}

	/**
	 * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
	 * @param name name of the requested subelements
	 * @return requested subelements or an empty <code>List</code> if not existing
	 */
	@Nonnull
	public List<Element> getElementsByTagName(@Nonnull String name) {

		NodeList nodeList = nestedElement.getElementsByTagName(name).item(0).getChildNodes();

		List<Element> result = new ArrayList<Element>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
		}

		return result;
	}

	/**
	 * @see org.w3c.dom.Node#getNodeName()
	 * @return name of the node
	 */
	@Nonnull
	public String getNodeName() {

		return nestedElement.getNodeName();
	}

	/**
	 * @see Node#getNodeName()
	 * @return name of the node
	 */
	@Nonnull
	public String getName() {

		return nestedElement.getNodeName();
	}

	/**
	 * @see org.w3c.dom.Node#getChildNodes()
	 * @return subelements or an empty <code>List</code> if not existing
	 */
	@Nonnull
	public List<Element> getChildElements() {

		NodeList nodeList = nestedElement.getChildNodes();

		List<Element> result = new ArrayList<Element>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof org.w3c.dom.Element) {
				result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
			}
		}

		return result;
	}

	/**
	 * @see org.w3c.dom.Element#getChildNodes()
	 * @return subelements or an empty <code>List</code> if not existing
	 */
	@Nonnull
	public List<Element> getChildren() {

		NodeList nodeList = nestedElement.getChildNodes();

		List<Element> result = new ArrayList<Element>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof org.w3c.dom.Element) {
				result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
			}
		}

		return result;
	}

	/**
	 * @see org.w3c.dom.Element#getParentNode()
	 * @return parent element
	 */
	@Nullable
	public Element getParent() {

		return new Element((org.w3c.dom.Element) nestedElement.getParentNode());
	}

}
