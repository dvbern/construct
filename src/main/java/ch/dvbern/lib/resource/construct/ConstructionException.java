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

import java.io.*;

/**
 * This exception indicates that an <code>ObjectConstructor</code> was not
 * able to create/return an object. A detail message and/or a nested exception
 * may be passed to the constructor of this exception, which are included in the
 * stack trace of this exception.
 * 
 * @see ObjectConstructor
 */
public class ConstructionException extends java.lang.Exception {

	private static final long serialVersionUID = -5362684009744647564L;

	private final Throwable nested;

	/**
	 * Creates a new instance of <code>ConstructionException</code> without
	 * detail message.
	 */
	public ConstructionException() {
		this.nested = null;
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * passed nested exception.
	 * 
	 * @param nested
	 *            Throwable, which is beeing wrapped by this exception and
	 *            included in the stack trace.
	 */
	public ConstructionException(Throwable nested) {
		this.nested = nested;
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public ConstructionException(String msg) {
		super(msg);
		this.nested = null;
	}

	/**
	 * Constructs an instance of <code>ConstructionException</code> with the
	 * specified detail message and the passed nested exception.
	 * 
	 * @param msg
	 *            the detail message.
	 * @param nested
	 *            Throwable, which is beeing wrapped by this exception and
	 *            included in the stack trace.
	 */
	public ConstructionException(String msg, Throwable nested) {
		super(msg);
		this.nested = nested;
	}

	/**
	 * Returns the nested (wrapped) exception,
	 * 
	 * @return Throwable: nested (wrapped) exception
	 */

	public Throwable getNestedException() {
		return nested;
	}

	/**
	 * Call of printStackTrace(PrintWriter)
	 * 
	 * @param stream
	 *            PrintWriter to which the stack trace is beeing printed.
	 * @see #printStackTrace(PrintWriter)
	 */
	public void printStackTrace(PrintStream stream) {
		PrintWriter writer = new PrintWriter(stream);
		printStackTrace(writer);
		writer.flush();
		//writer.close();
	}

	/**
	 * Prints the stack trace of the actual exception and then the stack trace
	 * of the nested exception.
	 * 
	 * @param writer
	 *            PrintWriter to which the stack trace is beeing printed.
	 */
	public void printStackTrace(PrintWriter writer) {

		super.printStackTrace(writer);
		if (nested != null) {
			writer.write("\nNESTED EXCEPTION:: ");
			nested.printStackTrace(writer);
		}

	}

	/**
	 * Prints StackTrace to System.out.
	 * 
	 * @see #printStackTrace(PrintWriter writer)
	 */
	public void printStackTrace() {
		printStackTrace(System.out);
	}

}
