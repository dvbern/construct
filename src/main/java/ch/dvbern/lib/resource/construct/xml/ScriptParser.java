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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "script" (<code>&lt;script  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * Code relies on <a href="http://www.jdom.org" target="_blank">JDOM </a>
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ScriptParser implements ElementParser {

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

        /** * process all the children ** */
        ClassObjectPair lastCOP = null;
        NodeList children = element.getChildNodes();
        if (children != null && children.getLength() > 0) {
            //create new ScopeParserFactory for storage of variables
            ScopeParserFactory scopeFactory = new ScopeParserFactory(factory);
            for (int i = 0; i < children.getLength(); i++) {
                Element child = (Element) children.item(i);
                String tagName = child.getNodeName();
                if (tagName.equals("return")) {
                    //always return the object created by a return-tag
                    Element objTag = (Element) child.getChildNodes().item(0);
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

        //if there has not been a return-tag, return the last created
        // ClassObjectPair
        return lastCOP;
    }
}
