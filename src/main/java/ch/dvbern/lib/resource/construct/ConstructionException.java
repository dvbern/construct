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
package ch.dvbern.lib.resource.construct;

import javax.annotation.Nullable;

/**
 * This exception indicates that an <code>ObjectConstructor</code> was not
 * able to create/return an object. A detail message and/or a nested exception
 * may be passed to the constructor of this exception, which are included in the
 * stack trace of this exception.
 *
 * @see ObjectConstructor
 */
public class ConstructionException extends Exception {

	private static final long serialVersionUID = -5362684009744647564L;

	/**
	 * Creates a new instance of <code>ConstructionException</code> without
	 * detail message.
	 */
	public ConstructionException() {
		super();
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * passed nested exception.
	 *
	 * @param cause
	 *            Throwable, which is beeing wrapped by this exception and
	 *            included in the stack trace.
	 */
	public ConstructionException(@Nullable Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * specified detail message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public ConstructionException(@Nullable String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * specified detail message and the passed nested exception.
	 *  @param msg
	 *            the detail message.
	 * @param cause
	 *            Throwable, which is beeing wrapped by this exception and
	 */
	public ConstructionException(@Nullable String msg, @Nullable Throwable cause) {
		super(msg, cause);
	}
}
