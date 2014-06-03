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
 * Exception indicating that a variable with the given name is already stored in
 * a given scope Used by ScopeParserFactory.
 *
 * @see ScopeParserFactory
 */
public class VariableAlreadyDefinedException extends Exception {

	private static final long serialVersionUID = -3083483453012984072L;

	/**
	 * Constructs an instance of <code>VariableAlreadyDefinedException</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public VariableAlreadyDefinedException(@Nonnull String msg) {
		super(msg);
	}
}
