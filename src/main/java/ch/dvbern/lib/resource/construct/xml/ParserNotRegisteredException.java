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

import javax.annotation.Nullable;

/**
 * This exception indicates that no parser is registered for a given name.
 *
 * @see ParserFactory
 */
public class ParserNotRegisteredException extends Exception {

	private static final long serialVersionUID = -8685141376596179827L;

	/**
     * Creates a new instance of <code>ParserNotRegisteredException</code>
     * without detail message.
     */
    public ParserNotRegisteredException() {
    }

    /**
     * Constructs an instance of <code>ParserNotRegisteredException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ParserNotRegisteredException(@Nullable String msg) {
        super(msg);
    }
}
