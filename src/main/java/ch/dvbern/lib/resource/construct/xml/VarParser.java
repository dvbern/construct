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

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "var" (<code>&lt;var  &gt;</code>). The
 * parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class VarParser implements ElementParser {

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

		// get the name of the variable
		String varName = element.getAttribute("name");
		if (varName == null || varName.isEmpty()) {
			throw new ElementParserException("attribute 'name' may not be null or empty");
		}

		//retrieve the ClassObjectPair from the scope and return it
		if (!(factory instanceof ScopeParserFactory)) {
			throw new ElementParserException(
							"VarParser needs a ScopeParserFactory");
		}

		try {
			return ((ScopeParserFactory) factory).getVariableCOP(varName);
		} catch (VariableNotDefinedException ex) {
			throw new ElementParserException(ex);
		}

	}
}
