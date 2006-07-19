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

import java.io.*;

/**
 * This exception indicates that an <code>ElementParser</code> was not able to
 * parse a xml-element an create class/object instances.
 */
public class ElementParserException extends java.lang.Exception {

	private static final long serialVersionUID = -7590476829224567457L;

	private final Throwable nested;

    /**
     * Creates a new instance of <code>ElementParserException</code> without
     * detail message.
     */
    public ElementParserException() {
        super();
        nested = null;
    }

    /**
     * Creates a new instance of <code>ElementParserException</code> with the
     * passed nested exception.
     * 
     * @param nested Throwable, which is beeing wrapped by this exception and
     *        included in the stack trace.
     */
    public ElementParserException(Throwable nested) {
        super();
        this.nested = nested;
    }

    /**
     * Constructs an instance of <code>ElementParserException</code> with the
     * specified detail message
     * 
     * @param msg the detail message.
     */
    public ElementParserException(String msg) {
        super(msg);
        nested = null;
    }

    /**
     * Constructs an instance of <code>ElementParserException</code> with the
     * specified detail message and the passed nested exception.
     * 
     * @param msg the detail message.
     * @param nested Throwable, which is beeing wrapped by this exception and
     *        included in the stack trace.
     */
    public ElementParserException(String msg, Throwable nested) {
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
     * @param stream rintWriter to which the stack trace is beeing printed.
     * @see #printStackTrace(PrintWriter)
     */
    public void printStackTrace(PrintStream stream) {

        PrintWriter writer = new PrintWriter(stream);
        printStackTrace(writer);
        writer.close();
    }

    /**
     * Prints the stack trace of the actual exception and then the stack trace
     * of the nested exception.
     * 
     * @param writer PrintWriter to which the stack trace is beeing printed.
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
     */
    public void printStackTrace() {
        printStackTrace(System.out);
    }
}
