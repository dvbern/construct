/*
 * Copyright � 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * gesch�tzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzul�ssig. Dies gilt
 * insbesondere f�r Vervielf�ltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht �bergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 10:28:09 $ - $Author: meth $ - $Revision: 1.1 $
 */
package ch.dvbern.lib.resource.construct.xml;

/**
 * Event indicating that a resource has been changed or removed at all. Event
 * object contains the name of the changed resource.
 */
public class ResourceChangedEvent extends java.util.EventObject {

	private static final long serialVersionUID = -7039199533056588697L;

	private String resource;

    /**
     * Constructor.
     * 
     * @param source: Object generating the event
     * @param resourceName: name of resource that has been changed or removed
     */
    public ResourceChangedEvent(Object source, String resourceName) {
        super(source);
        this.resource = resourceName;
    }

    /**
     * Method returns the name of the resource that has been changed or removed.
     * 
     * @return Name of changed/removed resource
     */
    public String getResourceName() {
        return resource;
    }

}
