/*
 * Copyright (C) 2022 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.dvbern.lib.resource.construct.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ch.dvbern.lib.resource.construct.ConstructionException;
import ch.dvbern.lib.resource.construct.ObjectConstructor;
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

	@Nonnull
	private final Map<String, Object> cachedObjects;

	@Nonnull
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
	public XMLObjectConstructor(@Nonnull ParserFactory factory) {
		cachedObjects = new HashMap<>();
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
	 * @param newInstance boolean, indicating whether a new instance should be created (true) or if already created
	 * (and
	 * cached) instances should be returned.
	 * @return Object according to the definition in the xml-file with the name <code>objectId</code>. The return
	 * value is never null.
	 * @throws ConstructionException Thrown, if there is no definition for the object with the passed
	 *                               <code>objectId</code> or if the object can not be created
	 * @see ConstructParser
	 * @see ParserFactory
	 */
	@Override
	@Nonnull
	public Object construct(@Nonnull String objectId, boolean newInstance) throws ConstructionException {

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

	@Nonnull
	private Object parse(@Nonnull String objectId, @Nonnull ParserFactory parserFactory) throws ConstructionException {

		try {
			InputStream ins = parserFactory.getResourceLocator().getResourceAsStream(objectId);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(ins);
			Element root = new Element(doc.getDocumentElement());
			Object value = parserFactory.getParser(root.getNodeName()).parse(root, parserFactory).getObject();
			if (value == null) {
				throw new ConstructionException("Could not construct root, parser returned null");
			}
			return value;
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
	@Override
	public void resourceChanged(@Nonnull ResourceChangedEvent event) {

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
	@Override
	public void resourceRemoved(@Nonnull ResourceChangedEvent event) {

		// System.out.println("===================received removeEvent");
		resourceChanged(event);
	}
}
