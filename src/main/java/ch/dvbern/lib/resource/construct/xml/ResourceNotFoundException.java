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

/**
 * Exception indicating that a specific resource could not have been found. Used
 * by implementation of ResourceLocator
 * 
 * @see ResourceLocator
 */
public class ResourceNotFoundException extends java.lang.Exception {

	private static final long serialVersionUID = -1502396802449310508L;

	/**
     * Creates a new instance of <code>ResourceNotFoundException</code>
     * without detail message.
     */
    public ResourceNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ResourceNotFoundException</code> with
     * the specified detail message.
     * 
     * @param msg the detail message.
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
