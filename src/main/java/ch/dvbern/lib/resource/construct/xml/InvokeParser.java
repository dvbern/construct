/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 10:28:09 $ - $Author: meth $ - $Revision: 1.1 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.lang.reflect.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "invoke" (<code>&lt;invoke  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * Code relies on <a href="http://www.jdom.org" target="_blank">JDOM </a>
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class InvokeParser implements ElementParser {

    /**
     * Method parses the passed xml-element and creates an object based on the
     * information defined by the xml-tag.
     * 
     * @param element org.jdom.Element containing the information of the parsed
     *        xml-element
     * @param factory ParserFactory returning the parsers for parsing nested
     *        tags
     * @return ClassObjectPair: parsed xml-data, never null.
     * @exception ElementParserException Thrown, if a problem occurs while
     *            parsing the xml-tag and creating the class/object instances.
     */
    public ClassObjectPair parse(Element element, ParserFactory factory)
            throws ElementParserException {

        /** * get name of method ** */
        String methodName = element.getAttribute("methodName");

        /** ** get object, on which method is to invoke ** */
        NodeList objectElChildren = element.getElementsByTagName("target")
                .item(0).getChildNodes();
        if (objectElChildren.getLength() != 1) {
            throw new ElementParserException(
                    "object must have exactly on child (construct or ref or cast...)");
        }
        Element objectElToParse = (Element) objectElChildren.item(0);
        Object myObject = null;
        Class myClass = null;
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
        NodeList parameterElList = element.getElementsByTagName("parameters")
                .item(0).getChildNodes();
        Class[] classArray = null; //may be null
        Object[] objArray = null; // may ne null
        if (parameterElList != null) {
            classArray = new Class[parameterElList.getLength()];
            objArray = new Object[parameterElList.getLength()];
            for (int i = 0; i < parameterElList.getLength(); i++) {
                Element paramEl = (Element) parameterElList.item(i);
                try {
                    ClassObjectPair cop = factory.getParser(
                            paramEl.getNodeName()).parse(paramEl, factory);
                    classArray[i] = cop.getKlass();
                    objArray[i] = cop.getObject();
                } catch (ParserNotRegisteredException ex) {
                    throw new ElementParserException(ex);
                }
            }
        }

        /** * get Method and invoke ** */
        try {
            Method method = myClass.getMethod(methodName, classArray);
            Object obj = method.invoke(myObject, objArray);
            if (obj == null) {
                return new ClassObjectPair(null, null);
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
