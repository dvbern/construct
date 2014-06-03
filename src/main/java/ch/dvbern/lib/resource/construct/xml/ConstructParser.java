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

import java.util.*;

import javax.annotation.Nonnull;

import ch.dvbern.lib.resource.construct.*;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "construct" (<code>&lt;construct  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ConstructParser implements ElementParser {

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

        ClassObjectPair retVal;
        String className = element.getAttribute("class");
		if (className == null || className.isEmpty()) {
			throw new ElementParserException("attribute 'class' may not be null on " + element.getNodeName());
		}
        Class klass;
        try {
            klass = ClassFactory.getKlass(className);
        } catch (ClassNotFoundException ex) {
            throw new ElementParserException(
                    "classLoader could NOT load class for name=" + className,
                    ex);
        }

        List<Element> argChildren = element.getChildElements();
        List<Class<?>> argClasses = new ArrayList<Class<?>>(argChildren.size());
        List<Object> initArgs = new ArrayList<Object>(argChildren.size());
		for (Element el : argChildren) {
			//add constructor argument
			try {
				ClassObjectPair cop = factory.getParser(el.getNodeName())
								.parse(el, factory);
				argClasses.add(cop.getKlass());
				initArgs.add(cop.getObject());
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException(
								"no parser found for element name=" + el.getNodeName(),
								ex);
			}

		}

        Construct construct = new Construct(klass, argClasses
                .toArray(new Class<?>[argClasses.size()]), initArgs.toArray(new Object[initArgs.size()]));
        try {
            retVal = new ClassObjectPair(construct.getKlass(), construct
                    .getObject());
        } catch (ConstructionException ex) {
            throw new ElementParserException(ex);
        }

        return retVal;
    }
}
