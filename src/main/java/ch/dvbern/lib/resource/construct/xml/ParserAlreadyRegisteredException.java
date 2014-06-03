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
 * This exception indicates that a parser is already registerd with a given
 * name.
 *
 * @see ParserFactory
 */
public class ParserAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 4404585566412735102L;

    /**
     * Constructs an instance of <code>ParserAlreadyRegisteredException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ParserAlreadyRegisteredException(@Nonnull String msg) {
        super(msg);
    }
}
