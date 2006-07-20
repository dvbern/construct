/*
 * Copyright © 2006 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschützt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulässig. Dies gilt
 * insbesondere für Vervielfältigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht übergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 * $Date: 2006/07/20 12:12:10 $ - $Author: meth $ - $Revision: 1.2 $
 */
package ch.dvbern.lib.resource.construct;

import ch.dvbern.lib.resource.construct.xml.*;

/**
 * A field test class.
 */
public class SimpleFieldTest {

    /**
     * the first field
     */
    public String field1 = "field 1";
    
    /**
     * the second field
     */
    public String field2 = "field 2";
    
    public String toString() {
        return "field1="+field1+" field2="+field2;
    }
    
    /**
     * Main method.
     * @param args main method arguments
     * @throws Exception execution failure
     */
    public static void main(String[] args) throws Exception {
        FilePathResourceLocator loc = new FilePathResourceLocator("/home/loue/project/dvbern-lib-forms/construct", 3000);
        XMLObjectConstructor constructor = new XMLObjectConstructor(new ParserFactory(loc));
        
        SimpleFieldTest test = (SimpleFieldTest)constructor.construct("myField.xml", false);
        System.out.println(test);
        
        loc.stopResourceChecker();
    }

}
