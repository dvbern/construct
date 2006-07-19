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

import java.lang.reflect.*;

/**
 * This class is responsible for returning <code>Class</code> instances
 * corresponing to the specified <code>type</code>
 * <p>
 * The following <code>type</code> parameters are possible:
 * <ul>
 * <li>a class-name (e.g. "java.lang.Integer")</li>
 * <li>a class-name with indication of array-dimension ("[]" for every
 * dimension) (e.g. "java.lang.String[]" or "java.lang.Integer[][][]")</li>
 * <li>a String, indicating a "primitive" type (inkl."java.lang.String") <br>
 * The following types are supported:
 * <ul>
 * <li>"string"</li>
 * <li>"int"</li>
 * <li>"long"</li>
 * <li>"short"</li>
 * <li>"float"</li>
 * <li>"double"</li>
 * <li>"boolean"</li>
 * <li>"char"</li>
 * </ul>
 * </li>
 * </ul>
 */
public class ClassFactory {

    private ClassFactory() {
    }

    /**
     * Returns a <code>Class</code> instance for the specified
     * <code>type</code>, never null. See description of class.
     * 
     * @param type String, see description of this <code>ClassFactory</code>.
     * @return Returns a <code>Class</code> instance for the specified
     *         <code>type</code>, never null
     * @exception ClassNotFoundException Thrown, if no <code>Class</code> has
     *            been found for the specified <code>type</code>.
     * @see ClassFactory
     */
    public static Class getKlass(String type) throws ClassNotFoundException {
        Class retVal = null;
        if (type.equalsIgnoreCase("string")) {
            retVal = String.class;
        } else if (type.equalsIgnoreCase("int")) {
            retVal = Integer.TYPE;
        } else if (type.equalsIgnoreCase("long")) {
            retVal = Long.TYPE;
        } else if (type.equalsIgnoreCase("short")) {
            retVal = Short.TYPE;
        } else if (type.equalsIgnoreCase("float")) {
            retVal = Float.TYPE;
        } else if (type.equalsIgnoreCase("double")) {
            retVal = Double.TYPE;
        } else if (type.equalsIgnoreCase("boolean")) {
            retVal = Boolean.TYPE;
        } else if (type.equalsIgnoreCase("char")) {
            retVal = Character.TYPE;
        } else if (type.endsWith("[]")) {
            String baseType = type.substring(0, type.length() - 2);
            Class baseClass = ClassFactory.getKlass(baseType);
            retVal = Array.newInstance(baseClass, 0).getClass();
        } else {
            retVal = Class.forName(type);
        }
        return retVal;
    }

}
