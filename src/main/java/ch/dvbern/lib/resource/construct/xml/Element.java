/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:11:54 $ - $Author: meth $ - $Revision: 1.4 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NodeList;

/**
 * Wrapper of <code>org.w3c.dom.Element</code> with constant interface.
 */
public class Element {

	private org.w3c.dom.Element nestedElement;

	/**
	 * Constructor.
	 * 
	 * @param nestedElement the wrapped element
	 */
	public Element(org.w3c.dom.Element nestedElement) {

		super();
		this.nestedElement = nestedElement;
	}

	/**
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or <code>null</code> if not existing
	 */
	public String getAttribute(String name) {

		if (!nestedElement.hasAttribute(name)) {
			return null;
		}
		return nestedElement.getAttribute(name);
	}

	/**
	 * @see org.jdom.Element#getAttributeValue(java.lang.String)
	 * @param name name of the attribute to be read
	 * @return value of the requested attribute or <code>null</code> if not existing
	 */
	public String getAttributeValue(String name) {

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
	public List getElementsByTagName(String name) {

		NodeList nodeList = nestedElement.getElementsByTagName(name).item(0).getChildNodes();

		List result = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
		}

		return result;
	}

	/**
	 * @see org.w3c.dom.Node#getNodeName()
	 * @return name of the node
	 */
	public String getNodeName() {

		return nestedElement.getNodeName();
	}

	/**
	 * @see org.jdom.Node#getName()
	 * @return name of the node
	 */
	public String getName() {

		return nestedElement.getNodeName();
	}

	/**
	 * @see org.w3c.dom.Node#getChildNodes()
	 * @return subelements or an empty <code>List</code> if not existing
	 */
	public List getChildElements() {

		NodeList nodeList = nestedElement.getChildNodes();

		List result = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof org.w3c.dom.Element) {
				result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
			}
		}

		return result;
	}

	/**
	 * @see org.jdom.Element#getChildren()
	 * @return subelements or an empty <code>List</code> if not existing
	 */
	public List getChildren() {

		NodeList nodeList = nestedElement.getChildNodes();

		List result = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof org.w3c.dom.Element) {
				result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
			}
		}

		return result;
	}

	/**
	 * @see org.jdom.Element#getParent()
	 * @return parent element
	 */
	public Element getParent() {

		return new Element((org.w3c.dom.Element) nestedElement.getParentNode());
	}

}
