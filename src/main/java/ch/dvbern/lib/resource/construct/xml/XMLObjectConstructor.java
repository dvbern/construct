/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:11:54 $ - $Author: meth $ - $Revision: 1.4 $
 */
package ch.dvbern.lib.resource.construct.xml;

import ch.dvbern.lib.resource.construct.*;
import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * This implementation of <code>ObjectConstructor</code> uses xml-files as definitions for the objects (resources). It
 * caches created objects (resources), see <code>construct()</code>.<br>
 * The <code>XMLObjectConstructor</code> registers itself (in the constructor) as <code>ResourceChangeListener</code>
 * at the <code>ResourceLocator</code> of the factory passed to the constructor. (see methods
 * <code>resourceChanged</code> and <code>resourceRemoved</code>)
 */
public class XMLObjectConstructor implements ObjectConstructor, ResourceChangeListener {

	private Map cachedObjects = null;

	private final ParserFactory factory;

	/**
	 * Standard constructor. <br>
	 * Instantiates <code>ParserFactory</code> with <code>ClassLoaderResourceLocator</code>
	 * 
	 * @see ParserFactory
	 * @see ClassLoaderResourceLocator
	 */
	public XMLObjectConstructor() {

		this(new ParserFactory(new ClassLoaderResourceLocator()));
	}

	/**
	 * 22 * Constructor.
	 * 
	 * @param factory ParserFactory returning the tag-parsers; must not be null
	 * @see ParserFactory
	 * @see ResourceLocator
	 * @see FilePathResourceLocator
	 */
	public XMLObjectConstructor(ParserFactory factory) {

		if (factory == null) {
			throw new IllegalArgumentException("factory must not be null");
		}
		cachedObjects = new HashMap();
		this.factory = factory;

		factory.getResourceLocator().addResourceChangeListener(this);
	}

	/**
	 * This method returns an object for the <code>objectId</code>. <code>objectId</code> equals the name of a
	 * xml-file, where the object is defined. The root-element of the xml-file must have the element-name "construct"
	 * (see ConstructParser) or "script" (see ScriptParser). A cached instance may be returned, if
	 * <code>newInstance</code> is false. If there is no definition for the object with the passed
	 * <code>objectId</code> or if the object can not be created, a <code>ConstructionException</code> is thrown.
	 * <p>
	 * The parsing of the xml-tags is delegated to various <code>ElementParser</code> (see <code>ParserFactory</code>)
	 * <p>
	 * If the <code>XMLObjectConstructor</code> uses a <code>ParserFactory</code> with a
	 * <code>ClassLoaderResourceLocator</code>, the xml-files must be located in the CLASSPATH, else according to the
	 * requirements of the used <code>ResourceLocator</code> (see descriptions of the constructors).
	 * 
	 * @param objectId The name of the xml-file, in which the object is declared.
	 * @param newInstance boolean, indicating whether a new instance should be created (true) or if already created (and
	 *            cached) instances should be returned.
	 * @return Object according to the definition in the xml-file with the name <code>objectId</code>. The return
	 *         value is never null.
	 * @exception ConstructionException Thrown, if there is no definition for the object with the passed
	 *                <code>objectId</code> or if the object can not be created
	 * @see ConstructParser
	 * @see ParserFactory
	 */
	public Object construct(String objectId, boolean newInstance) throws ConstructionException {

		// if NOT newInstance: try to get from cache
		Object obj = null;
		if (!newInstance) {
			synchronized (cachedObjects) {
				obj = cachedObjects.get(objectId);
			}
		}
		// else, or if not found in cache: parse file and construct Object
		if (obj == null) {
			obj = parse(objectId, factory);
			synchronized (cachedObjects) {
				// put into cache; maybe replace old entry
				cachedObjects.put(objectId, obj);
				// System.out.println("=======XMLObjectConstructor: added to
				// cachedObjects:"+objectId);
			}
		}
		return obj;
	}

	private Object parse(String objectId, ParserFactory parserFactory) throws ConstructionException {

		try {
			InputStream ins = parserFactory.getResourceLocator().getResourceAsStream(objectId);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(ins);
			Element root = new Element(doc.getDocumentElement());
			return parserFactory.getParser(root.getNodeName()).parse(root, parserFactory).getObject();
		} catch (SAXException ex) {
			throw new ConstructionException("parsing of file with id=" + objectId + " NOT successfull", ex);
		} catch (IOException ex) {
			throw new ConstructionException("parsing of file with id=" + objectId + " NOT successfull", ex);
		} catch (ParserConfigurationException ex) {
			throw new ConstructionException("parsing of file with id=" + objectId + " NOT successfull", ex);
		} catch (ParserNotRegisteredException ex) {
			throw new ConstructionException("no parser found for root-element", ex);
		} catch (ElementParserException ex) {
			throw new ConstructionException("root-element could NOT have been parsed", ex);
		} catch (ResourceNotFoundException ex) {
			throw new ConstructionException("resource with id=" + objectId + " not found", ex);
		}
	}

	/**
	 * Implementation of <code>ResourceChangeListener</code>. Method is called, when a resource has been changed. It
	 * simply removes the name of the changed resource from the cache.
	 * 
	 * @param event <code>ResourceChangedEvent</code>: object containing the information about changed resource.
	 */
	public void resourceChanged(ResourceChangedEvent event) {

		// System.out.println("=================XMLObjectConstructor: received
		// event");
		String resource = event.getResourceName();
		// System.out.println("=================XMLObjectConstructor: resource:"
		// +event.getResourceName());
		synchronized (cachedObjects) {
			if (cachedObjects.get(resource) != null) {
				cachedObjects.remove(resource);
				// System.out.println("XMLObjectConstructor: resource removed
				// from cache");
			}
		}
	}

	/**
	 * Implementation of <code>ResourceChangeListener</code>. Method is called, when a resource has been removed. It
	 * simply removes the name of the removed resource from the cache.
	 * 
	 * @param event <code>ResourceChangedEvent</code>: object containing the information about removed resource.
	 */
	public void resourceRemoved(ResourceChangedEvent event) {

		// System.out.println("===================received removeEvent");
		resourceChanged(event);
	}
}
