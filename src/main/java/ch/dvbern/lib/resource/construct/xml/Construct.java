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

/**
 * This class is used for creating objects out of declarations.
 */
public class Construct {

    private Class klass;

    private Class[] argClasses;

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
    public Construct(Class klass, Class[] argClasses, Object[] argValues) {
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
    public Class getKlass() throws ConstructionException {
        return klass;
    }

    private Class[] getArgClasses() throws ClassNotFoundException {
        //System.out.println("argument dump:");
        for (int i = 0; i < argClasses.length; i++) {
            //System.out.println("class: "+argClasses[i]);
        }
        return argClasses;
    }

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
            Constructor constructor = getKlass()
                    .getConstructor(getArgClasses());
            Object obj = constructor.newInstance(getInitArgs());
            return obj;
        } catch (ClassNotFoundException ex) {
            throw new ConstructionException(ex);
        } catch (NoSuchMethodException ex) {
            throw new ConstructionException(ex);
        } catch (Exception ex) {
            throw new ConstructionException(ex);
        }
    }
}
