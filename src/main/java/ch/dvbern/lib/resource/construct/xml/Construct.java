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

import ch.dvbern.lib.resource.construct.*;
import java.lang.reflect.*;

import javax.annotation.Nonnull;

/**
 * This class is used for creating objects out of declarations.
 */
public class Construct {

	@Nonnull
    private Class klass;

	@Nonnull
    private Class[] argClasses;

	@Nonnull
    private Object[] argValues;

    /**
     * Constructor.
     *
     * @param klass <code>Class</code> of the object to create.
     * @param argClasses Array of <code>Class</code> instances representing
     *        the types of the parameters of the constructor for
     *        <code>klass</code>
     * @param argValues Array of <code>Object</code> instances representing
     *        the values of the parameters of the constructor for a new instance
     *        of <code>klass</code>
     */
    public Construct(@Nonnull Class<?> klass, @Nonnull Class<?>[] argClasses, @Nonnull Object[] argValues) {
        this.klass = klass;
        this.argClasses = argClasses;
        this.argValues = argValues;
    }

    /**
     * Method returns the <code>klass</code> member
     *
     * @return <code>Class</code>: The type of this <code>Construct</code>
     * @exception ConstructionException Thrown if the object cannot be created
     *            out of the member-values of this <code>Construct</code>
     */
	@Nonnull
    public Class<?> getKlass() throws ConstructionException {
        return klass;
    }

	@Nonnull
    private Class<?>[] getArgClasses() throws ClassNotFoundException {
        return argClasses;
    }

	@Nonnull
    private Object[] getInitArgs() {
        return argValues;
    }

    /**
     * This method returns the <code>Object</code> represented by this
     * <code>Construct</code>.
     *
     * @return <code>Object</code> represented by this <code>Construct</code>.
     * @exception ConstructionException Thrown if the object cannot be created
     *            out of the member-values of this <code>Construct</code>
     */
    public Object getObject() throws ConstructionException {
        try {
            Constructor<?> constructor;
			constructor = getKlass()
					.getConstructor(getArgClasses());
			return constructor.newInstance(getInitArgs());
        } catch (ClassNotFoundException ex) {
            throw new ConstructionException(ex);
        } catch (NoSuchMethodException ex) {
            throw new ConstructionException(ex);
        } catch (Exception ex) {
            throw new ConstructionException(ex);
        }
    }
}
