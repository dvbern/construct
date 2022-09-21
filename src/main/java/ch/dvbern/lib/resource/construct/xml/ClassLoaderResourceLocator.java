/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:14:23 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.io.InputStream;

import javax.annotation.Nonnull;

/**
 * Implementation of ResourceLocator. Uses ClassLoader for locating the
 * resources. The resources (xml-files) must therefore be in the classpath of
 * the classloader. <br>
 * As <code>ResourceLocator</code> this class acts as event source for
 * <code>ResourceChangedEvent</code> s and implements the methods
 * <code>addResourceChangeListener</code> and
 * <code>removeResourceChangeListener</code> (empy implementations).
 *
 * @see ResourceChangedEvent
 */
public class ClassLoaderResourceLocator implements ResourceLocator {

	@Nonnull
	private ClassLoader cl;

	/**
	 * Default constructor. Uses class loader of this class for locating the
	 * resources
	 */
	public ClassLoaderResourceLocator() {
		cl = ClassLoaderResourceLocator.class.getClassLoader();
	}

	/**
	 * Alternate constructor. Uses specified ClassLoader for locating the
	 * resources.
	 *
	 * @param cl ClassLoader used for locating the resources; never null.
	 */
	public ClassLoaderResourceLocator(@Nonnull ClassLoader cl) {
		this.cl = cl;
	}

	/**
	 * Method returns specified resource as inputStream.
	 *
	 * @param resource name of resource, e.g. name of xml-file
	 * @return InputStream of specified resource, never null.
	 * @throws ResourceNotFoundException if resource could not have been located
	 */
	@Nonnull
	public InputStream getResourceAsStream(@Nonnull String resource)
					throws ResourceNotFoundException {
		InputStream ins = cl.getResourceAsStream(resource);
		if (ins == null) {
			throw new ResourceNotFoundException("resource with objectId="
							+ resource + " not found");
		} else {
			return ins;
		}
	}

	/**
	 * Empty implementation of ResourceLocator-method. (Method should register
	 * listeners interested in changes or removals of resources.)
	 *
	 * @param listener listener interested in changes or removals of resources
	 */
	public void addResourceChangeListener(@Nonnull ResourceChangeListener listener) {
	}

	/**
	 * Empty implementation of ResourceLocator-method. (Method should
	 * de-register listeners.)
	 *
	 * @param listener registered listener that has to be removed
	 */
	public void removeResourceChangeListener(@Nonnull ResourceChangeListener listener) {
	}

}
