package ch.dvbern.oss.construct.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SafeDocumentBuilderFactory {

	public static DocumentBuilderFactory newInstance() throws ParserConfigurationException {

		var dbf = DocumentBuilderFactory.newInstance();

		// Disable DTDs (Doctype Definition) entirely for XML parsing
		dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

		// Disable external entities
		dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
		dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		// Disable external DTDs and schemas
		dbf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
		dbf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "");

		return dbf;
	}

}