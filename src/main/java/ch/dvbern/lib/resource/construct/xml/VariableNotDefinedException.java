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
 * Exception indicating that a variable with the given name is not stored in a
 * given scope or in a scope of higher levels Used by ScopeParserFactory.
 *
 * @see ScopeParserFactory
 */
public class VariableNotDefinedException extends java.lang.Exception {

	private static final long serialVersionUID = 3523634640300494355L;

	/**
	 * Constructs an instance of <code>VariableNotDefinedException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public VariableNotDefinedException(@Nonnull String msg) {
		super(msg);
	}
}
