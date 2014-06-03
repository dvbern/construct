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

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "invoke" (<code>&lt;invoke  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class InvokeParser implements ElementParser {

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

        /** * get name of method ** */
        String methodName = element.getAttribute("methodName");

        /** ** get object, on which method is to invoke ** */
        List objectElChildren = element.getElementsByTagName("target");
        if (objectElChildren.size() != 1) {
            throw new ElementParserException(
                    "object must have exactly on child (construct or ref or cast...)");
        }
        Element objectElToParse = (Element) objectElChildren.get(0);
        Object myObject;
        Class<?> myClass;
        try {
            ClassObjectPair cop = factory.getParser(
                    objectElToParse.getNodeName()).parse(objectElToParse,
                    factory);
            myObject = cop.getObject();
            myClass = cop.getKlass();
        } catch (ParserNotRegisteredException ex) {
            throw new ElementParserException(ex);
        }

        /** * get parameters ** */
        List parameterElList = element.getElementsByTagName("parameters");
        Class<?>[] classArray = new Class[parameterElList.size()];
		Object[] objArray = new Object[parameterElList.size()];
		for (int i = 0; i < parameterElList.size(); i++) {
			Element paramEl = (Element) parameterElList.get(i);
			try {
				ClassObjectPair cop = factory.getParser(
						paramEl.getNodeName()).parse(paramEl, factory);
				classArray[i] = cop.getKlass();
				objArray[i] = cop.getObject();
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException(ex);
			}
		}

        /** * get Method and invoke ** */
        try {
            Method method = myClass.getMethod(methodName, classArray);
            Object obj = method.invoke(myObject, objArray);
            if (obj == null) {
				return new ClassObjectPair(Object.class, null);
            }
            return new ClassObjectPair(obj.getClass(), obj);
        } catch (NoSuchMethodException ex) {
            throw new ElementParserException(ex);
        } catch (IllegalAccessException ex) {
            throw new ElementParserException(ex);
        } catch (InvocationTargetException ex) {
            throw new ElementParserException(ex);
        }

    }
}
