/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 15:14:23 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "script" (<code>&lt;script  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ScriptParser implements ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the
	 * information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested
	 *            tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @exception ElementParserException Thrown, if a problem occurs while
	 *                parsing the xml-tag and creating the class/object
	 *                instances.
	 */
	@Nonnull
	public ClassObjectPair parse(@Nonnull Element element, @Nonnull ParserFactory factory)
					throws ElementParserException {

		/** * process all the children ** */
		ClassObjectPair lastCOP = null;
		List<Element> children = element.getChildElements();
		if (children.size() > 0) {
			//create new ScopeParserFactory for storage of variables
			ScopeParserFactory scopeFactory = new ScopeParserFactory(factory);
			for (Element child : children) {
				String tagName = child.getNodeName();
				if (tagName.equals("return")) {
					//always return the object created by a return-tag
					Element objTag = child.getChildElements().get(0);
					try {
						lastCOP = scopeFactory.getParser(objTag.getNodeName())
										.parse(objTag, scopeFactory);
						return lastCOP;
					} catch (ParserNotRegisteredException ex) {
						throw new ElementParserException(ex);
					}
				} else {
					/***********************************************************
					 * * let the VardefParser parse and define the variables
					 * (for tags with name 'vardef') or let the parsers do the
					 * business (for tags with name 'invoke')
					 **********************************************************/
					try {
						lastCOP = scopeFactory.getParser(child.getNodeName())
										.parse(child, scopeFactory);
					} catch (ParserNotRegisteredException ex) {
						throw new ElementParserException(ex);
					}
				}
			}
		}

		if (lastCOP == null) {
			throw new ElementParserException("no child elements defined for element " + element.getNodeName());
		}

		//if there has not been a return-tag, return the last created
		// ClassObjectPair
		return lastCOP;
	}
}
