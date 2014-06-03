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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

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

	private static final Set<String> ALLOWED_ELEMENT_NAMES;

	static {
		Set<String> names = new HashSet<String>();
		names.add("string") ;
		names.add("int");
		names.add("long");
		names.add("short");
		names.add("float");
		names.add("double");
		names.add("boolean");
		names.add("char");
		ALLOWED_ELEMENT_NAMES = Collections.unmodifiableSet(names);
	}

    /**
     * Method parses the passed xml-element and creates an object based on the
     * information defined by the xml-tag.
     *
     * @param element containing the information of the parsed xml-element
     * @param factory ParserFactory returning the parsers for parsing nested
     *            tags
     * @return ClassObjectPair: parsed xml-data, never null.
     * @throws ElementParserException if a problem occurs while
     *                parsing the xml-tag and creating the class/object
     *                instances.
     */
    @Nonnull
	public ClassObjectPair parse(@Nonnull Element element, @Nonnull ParserFactory factory)
            throws ElementParserException {
        String elementName = element.getNodeName();
		if (!ALLOWED_ELEMENT_NAMES.contains(elementName)) {
			throw new ElementParserException(
							"PrimParser can not handle elements with the name="
											+ elementName);
		}

        //primitives and Strings
        Class klass;
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
		Object value;
		try {
            value = PrimObjectFactory.getWrapper(elementName, strValue);
        } catch (ConstructionException ex) {
            throw new ElementParserException(ex);
        }
        return new ClassObjectPair(klass, value);

    }

}
