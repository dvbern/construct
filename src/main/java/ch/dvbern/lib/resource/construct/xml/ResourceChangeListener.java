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

import javax.annotation.Nonnull;

/**
 * Interface for listeners, interested in changes or removals of resources.
 * Event sources: implementations of <code>ResourceLocator</code>
 *
 * @see ResourceLocator
 */
public interface ResourceChangeListener extends java.util.EventListener {
    /**
     * Method is called, if a resource has been changed.
     *
     * @param event <code>ResourceChangedEvent</code>: object containing the
     *        information about changed resource.
     */
    void resourceChanged(@Nonnull ResourceChangedEvent event);

    /**
     * Method is called, if a resource has been removed.
     *
     * @param event <code>ResourceChangedEvent</code>: object containing the
     *        information about removed resource.
     */
    void resourceRemoved(@Nonnull ResourceChangedEvent event);
}
