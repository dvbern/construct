/*
 * Copyright Â© 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschÃ¼tzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulÃ¤ssig. Dies gilt
 * insbesondere fÃ¼r VervielfÃ¤ltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht Ã¼bergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 11:39:58 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.lang.reflect.Array;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing xml-tags with the element-name "array" (<code>&lt;array  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing nested elements. <br>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class ArrayParser implements ElementParser {

	/**
	 * Method parses the passed xml-element and creates an object based on the information defined by the xml-tag.
	 *
	 * @param element containing the information of the parsed xml-element
	 * @param factory ParserFactory returning the parsers for parsing nested tags
	 * @return ClassObjectPair: parsed xml-data, never null.
	 * @exception ElementParserException Thrown, if a problem occurs while parsing the xml-tag and creating the
	 *                class/object instances.
	 */
	@Nonnull
	public ClassObjectPair parse(@Nonnull Element element, @Nonnull ParserFactory factory) throws ElementParserException {

		// get Class of array
		// check elementName
		String elementName = element.getNodeName();
		if (!elementName.equals("array")) {
			throw new ElementParserException("array-parser can not handle elements with the name=" + elementName);
		}
		String type = element.getAttribute("elementtype");
		if (type == null || type.isEmpty()) {
			throw new ElementParserException("Attribute 'elementtype' may not be null/empty on element " + element.getNodeName());
		}
		Class arrayClass;
		try {
			arrayClass = ClassFactory.getKlass(type);
		} catch (ClassNotFoundException ex) {
			throw new ElementParserException("ArrayParser: ClassFactory could NOT create array-class for type=" + type,
							ex);
		}
		// get children
		List arrayChildren = element.getChildElements();
		Object array = Array.newInstance(arrayClass, arrayChildren.size());
		for (int i = 0; i < arrayChildren.size(); i++) {
			Element el = (Element) arrayChildren.get(i);
			try {
				ClassObjectPair cop = factory.getParser(el.getNodeName()).parse(el, factory);
				Array.set(array, i, cop.getObject());
			} catch (ParserNotRegisteredException ex) {
				throw new ElementParserException("ArrayParser: could NOT find parser for name=" + el.getNodeName(), ex);
			} catch (Exception ex) {
				throw new ElementParserException("ArrayParser: Could NOT set value into array", ex);
			}
		}
		Class klass = array.getClass();

		return new ClassObjectPair(klass, array);

	}

}
