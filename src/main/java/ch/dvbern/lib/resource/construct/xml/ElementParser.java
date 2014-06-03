/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 11:39:58 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import javax.annotation.Nonnull;

/**
 * Abstraction of ElementParser. Its implementations are responsible for parsing
 * xml-elements and creating objects based on the declared information in the
 * xml-tags.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public interface ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the
	 * information defined by the xml-tag.
	 *
	 * @param element
	 *            containing the information of the parsed xml-element
	 * @param factory
	 *            ParserFactory returning the parsers for parsing nested tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @exception ElementParserException
	 *                Thrown, if a problem occurs while parsing the xml-tag and
	 *                creating the class/object instances.
	 */
	@Nonnull
	ClassObjectPair parse(@Nonnull Element element, @Nonnull ParserFactory factory)
					throws ElementParserException;

}
