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

import ch.dvbern.lib.resource.construct.ConstructionException;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name of a primitive (see description in
 * <code>PrimObjectFactory</code>) (for example <code>&lt;int  &gt;</code>).
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 * 
 * @see PrimObjectFactory
 * @see ClassFactory
 */
public class PrimParser implements ElementParser {

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
    public ClassObjectPair parse(Element element, ParserFactory factory)
            throws ElementParserException {
        String elementName = element.getNodeName();
        //check element name
        if (!(elementName.equals("string") || elementName.equals("int")
                || elementName.equals("long") || elementName.equals("short")
                || elementName.equals("float") || elementName.equals("double")
                || elementName.equals("boolean") || elementName.equals("char"))) {
            throw new ElementParserException(
                    "null-parser can not handle elements with the name="
                            + elementName);
        }
        //primitives and Strings
        Class klass = null;
        Object value = null;
        try {
            klass = ClassFactory.getKlass(elementName);
        } catch (ClassNotFoundException ex) {
            throw new ElementParserException(
                    "ClassFactory could NOT load Class with type="
                            + elementName, ex);
        }
        String strValue = element.getAttribute("value");
        if (strValue == null) {
            throw new ElementParserException(
                    "definition of argument NOT correct (value must NOT be null)");
        }
        try {
            value = PrimObjectFactory.getWrapper(elementName, strValue);
        } catch (ConstructionException ex) {
            throw new ElementParserException(ex);
        }
        return new ClassObjectPair(klass, value);

    }

}
