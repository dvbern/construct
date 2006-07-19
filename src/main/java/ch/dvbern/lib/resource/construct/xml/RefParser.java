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

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing
 * xml-tags with the element-name "ref" (<code>&lt;ref  &gt;</code>). The
 * parser may use other <code>ElementParser</code> instances for parsing
 * nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class RefParser implements ElementParser {

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
        if (!elementName.equals("ref")) {
            throw new ElementParserException(
                    "null-parser can not handle elements with the name="
                            + elementName);
        }
        String objectId = element.getAttribute("id");
        if (objectId == null || objectId.equals("")) {
            throw new ElementParserException(
                    "id of ref tag must be the name of a xml-file");
        }
        try {
            InputStream ins = factory.getResourceLocator().getResourceAsStream(
                    objectId);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(ins);
            Element root = new Element(doc.getDocumentElement());

            ClassObjectPair cop = factory.getParser(root.getNodeName()).parse(
                    root, factory);
            return cop;
        } catch (SAXException ex) {
            throw new ElementParserException("parsing of file with id="
                    + objectId + " NOT successfull", ex);
        } catch (IOException ex) {
            throw new ElementParserException("parsing of file with id="
                    + objectId + " NOT successfull", ex);
        } catch (ParserConfigurationException ex) {
            throw new ElementParserException("parsing of file with id="
                    + objectId + " NOT successfull", ex);
        } catch (ParserNotRegisteredException ex) {
            throw new ElementParserException("no parser found", ex);
        } catch (ResourceNotFoundException ex) {
            throw new ElementParserException("ref parser: resource not foung",
                    ex);
        }
    }

}
