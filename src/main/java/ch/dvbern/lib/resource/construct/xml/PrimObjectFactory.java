/*
 * Copyright � 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * gesch�tzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzul�ssig. Dies gilt
 * insbesondere f�r Vervielf�ltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht �bergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/19 10:28:09 $ - $Author: meth $ - $Revision: 1.1 $
 */
package ch.dvbern.lib.resource.construct.xml;

import ch.dvbern.lib.resource.construct.*;

/**
 * This class is responsible for creating wrappers of the primitive types (inkl.
 * "java.lang.String"). As <code>type</code> it uses the following Strings:
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
 */
public class PrimObjectFactory {

    private PrimObjectFactory() {
    }

    /**
     * This method returns a wrapper of the specified <code>type</code> for
     * the <code>strValue</code>. As <code>type</code> the following
     * Strings are supported:
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
     * 
     * @param type <code>java.lang.String</code> indicating the type of the
     *        wrapper
     * @param strValue <code>java.lang.String</code> containing the value to
     *        wrap.
     * @return returns a wrapper of the specified <code>type</code> for the
     *         <code>strValue</code>
     * @exception ConstructionException Thrown, if the specified type is not
     *            supported or if a wrapper of type <code>type</code> cannot
     *            be created for the value <code>strValue</code>
     */

    public static Object getWrapper(String type, String strValue)
            throws ConstructionException {
        Object returnVal = null;
        try {
            if (type.equalsIgnoreCase("string")) {
                returnVal = new String(strValue);
            } else if (type.equalsIgnoreCase("int")) {
                returnVal = Integer.valueOf(strValue);
            } else if (type.equalsIgnoreCase("long")) {
                returnVal = Long.valueOf(strValue);
            } else if (type.equalsIgnoreCase("short")) {
                returnVal = Short.valueOf(strValue);
            } else if (type.equalsIgnoreCase("float")) {
                returnVal = Float.valueOf(strValue);
            } else if (type.equalsIgnoreCase("double")) {
                returnVal = Double.valueOf(strValue);
            } else if (type.equalsIgnoreCase("boolean")) {
                returnVal = Boolean.valueOf(strValue);
            } else if (type.equalsIgnoreCase("char")) {
                if (strValue.length() != 1) {
                    throw new ConstructionException(strValue
                            + " is NOT a valid Character");
                }
                char ch = strValue.charAt(0);
                returnVal = new Character(ch);
            }
        } catch (Exception ex) {
            throw new ConstructionException(
                    "error while trying to create wrapper", ex);
        }
        if (returnVal == null) {
            throw new ConstructionException(
                    "can NOT create wrapper object for type=" + type
                            + " with stringValue=" + strValue);
        }
        return returnVal;
    }

}
