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

import java.lang.reflect.*;
import java.util.List;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "setfield" (<code>&lt;setfield  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class SetFieldParser implements ElementParser {

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

        /** * get name of field ** */
        String fieldName = element.getAttribute("name");

        /** ** get object, on which field is to set ** */
        List objectElChildren = element.getElementsByTagName("target");
        if (objectElChildren.size() != 1) {
            throw new ElementParserException(
                    "object must have exactly on child (construct or ref or cast...)");
        }
        Element objectElToParse = (Element) objectElChildren.get(0);
        ClassObjectPair cop = null;
        Object myObject = null;
        Class myClass = null;
        try {
            cop = factory.getParser(objectElToParse.getNodeName()).parse(
                    objectElToParse, factory);
            myObject = cop.getObject();
            myClass = cop.getKlass();
        } catch (ParserNotRegisteredException ex) {
            throw new ElementParserException(ex);
        }

        /** * get value to set ** */
        Element valueEl = (Element) element.getElementsByTagName("value")
                .get(0);
        Object newValue = null;
        try {
            ClassObjectPair tmp = factory.getParser(valueEl.getNodeName())
                    .parse(valueEl, factory);
            newValue = tmp.getObject();
        } catch (ParserNotRegisteredException ex) {
            throw new ElementParserException(ex);
        }

        /** * get Field and set value ** */
        try {
            Field field = myClass.getField(fieldName);
            field.set(myObject, newValue);
        } catch (NoSuchFieldException ex) {
            throw new ElementParserException(ex);
        } catch (IllegalAccessException ex) {
            throw new ElementParserException(ex);
        }

        return cop;

    }
}
