/*
 * Copyright � 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * gesch�tzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzul�ssig. Dies gilt
 * insbesondere f�r Vervielf�ltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht �bergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 15:14:23 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "null" (<code>&lt;null  &gt;</code>).
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class NullParser implements ElementParser {

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
        //get Class of array
        String elementName = element.getNodeName();
        if (!elementName.equals("null")) {
            throw new ElementParserException(
                    "null-parser can not handle elements with the name="
                            + elementName);
        }
        String className = element.getAttribute("class");

        if (className == null) {
			throw new ElementParserException(
							"could not find attribute with name class");
		}

        try {
            Class clazz = ClassFactory.getKlass(className);
            return new ClassObjectPair(clazz, null);
        } catch (ClassNotFoundException ex) {
            throw new ElementParserException(
                    "ArrayParser: ClassFactory could NOT create array-class for type="
                            + className, ex);
        }

    }

}
