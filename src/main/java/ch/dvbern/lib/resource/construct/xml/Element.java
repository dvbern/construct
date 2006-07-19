/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 15:55:28 $ - $Author: meth $ - $Revision: 1.2 $
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getAttribute(java.lang.String)
	 */
	public String getAttribute(String name) {

		return nestedElement.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdom.Element#getAttributeValue(java.lang.String)
	 */
	public String getAttributeValue(String name) {

		return nestedElement.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Element#getElementsByTagName(java.lang.String)
	 */
	public List getElementsByTagName(String name) {

		NodeList nodeList = nestedElement.getElementsByTagName(name).item(0).getChildNodes();

		List result = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNodeName()
	 */
	public String getNodeName() {

		return nestedElement.getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdom.Node#getName()
	 */
	public String getName() {

		return nestedElement.getNodeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getChildNodes()
	 */
	public List getChildElements() {

		NodeList nodeList = nestedElement.getChildNodes();

		List result = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			result.add(new Element((org.w3c.dom.Element) nodeList.item(i)));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdom.Element#getChildren()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdom.Element#getParent()
	 */
	public Element getParent() {
	    return new Element((org.w3c.dom.Element)nestedElement.getParentNode());
	}
	
}
