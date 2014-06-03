/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:11:54 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct.xml;

import java.io.InputStream;

import javax.annotation.Nonnull;

/**
 * Abstraction of resource locator. Used by XMLObjectConstructor resp. ParserFactory. Event source for
 * <code>ResourceChangedEvent</code>s
 *
 * @see ClassLoaderResourceLocator
 * @see ParserFactory
 * @see XMLObjectConstructor
 * @see ResourceChangedEvent
 */
public interface ResourceLocator {

	/**
	 * Method returns resource as InputStream or throws Exception.
	 *
	 * @param resourceName Name of resource to locate (name of xml-file)
	 * @return InputStream: resource as InputStream; never null.
	 * @throws ResourceNotFoundException if specified resource could not have been found
	 */
	@Nonnull
	InputStream getResourceAsStream(@Nonnull String resourceName) throws ResourceNotFoundException;

	/**
	 * Method registers listeners interested in changes or removals of resources.
	 *
	 * @param listener listener interested in changes or removals of resources
	 */
	void addResourceChangeListener(@Nonnull ResourceChangeListener listener);

	/**
	 * Method de-registers listeners.
	 *
	 * @param listener registered listener that has to be removed
	 */
	void removeResourceChangeListener(@Nonnull ResourceChangeListener listener);
}
