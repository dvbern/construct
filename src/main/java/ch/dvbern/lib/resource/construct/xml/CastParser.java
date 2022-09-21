/*
 * Copyright Â© 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschÃ¼tzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulÃ¤ssig. Dies gilt
 * insbesondere fÃ¼r VervielfÃ¤ltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht Ã¼bergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 15:14:23 $ - $Author: meth $ - $Revision: 1.3 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Implementation of <code>ElementParser</code>. Responsible for parsing xml-tags with the element-name "cast" (<code>&lt;cast  &gt;</code>).
 * The parser may use other <code>ElementParser</code> instances for parsing nested elements.
 * <p>
 * For a detailed description of the xml-tags see the special documentation.
 */
public class CastParser implements ElementParser {

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

		String elementName = element.getNodeName();
		if (!elementName.equals("cast")) {
			throw new ElementParserException("cast-parser can not handle elements with the name=" + elementName);
		}
		ClassObjectPair retVal;
		String className = element.getAttribute("class");
		if (className == null || className.isEmpty()) {
			throw new ElementParserException("attribute 'class' may not be null or empty on element " + element.getNodeName());
		}
		List children = element.getChildElements();
		if (children.size() != 1) {
			throw new ElementParserException("a cast-tag must have exactly ONE child");
		}
		Element child = (Element) children.get(0);
		try {
			ClassObjectPair cop = factory.getParser(child.getNodeName()).parse(child, factory);
			Class klass = ClassFactory.getKlass(className);
			retVal = new ClassObjectPair(klass, cop.getObject());
		} catch (ClassNotFoundException ex) {
			throw new ElementParserException("could not find class for name=" + className, ex);
		} catch (ParserNotRegisteredException ex) {
			throw new ElementParserException("could not find a parser for cast-child", ex);
		}
		return retVal;
	}

}
