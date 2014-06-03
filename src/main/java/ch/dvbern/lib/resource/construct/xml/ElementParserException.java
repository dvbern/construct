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
 * This exception indicates that an <code>ElementParser</code> was not able to
 * parse a xml-element an create class/object instances.
 */
public class ElementParserException extends java.lang.Exception {

	private static final long serialVersionUID = -7590476829224567457L;

	/**
	 * Creates a new instance of <code>ElementParserException</code> without
	 * detail message.
	 */
	public ElementParserException() {
		super();
	}

	/**
	 * Creates a new instance of <code>ElementParserException</code> with the
	 * passed nested exception.
	 *
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 *        included in the stack trace.
	 */
	public ElementParserException(@Nonnull Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an instance of <code>ElementParserException</code> with the
	 * specified detail message
	 *
	 * @param msg the detail message.
	 */
	public ElementParserException(@Nonnull String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of <code>ElementParserException</code> with the
	 * specified detail message and the passed nested exception.
	 *
	 * @param msg the detail message.
	 * @param cause Throwable, which is beeing wrapped by this exception and
	 *        included in the stack trace.
	 */
	public ElementParserException(@Nonnull String msg, @Nonnull Throwable cause) {
		super(msg, cause);
	}

}
