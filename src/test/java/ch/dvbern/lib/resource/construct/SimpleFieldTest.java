/*
 * SimpleFieldTest.java
 *
 * Created on 27. März 2002, 14:49
 */

package ch.dvbern.lib.resource.construct;

import ch.dvbern.lib.resource.construct.xml.*;

/**
 *
 * @author  loue
 */
public class SimpleFieldTest {

    public String field1 = "field 1";
    public String field2 = "field 2";
    
    public String toString() {
        return "field1="+field1+" field2="+field2;
    }
    
    public static void main(String[] args) throws Exception {
        FilePathResourceLocator loc = new FilePathResourceLocator("/home/loue/project/dvbern-lib-forms/construct", 3000);
        XMLObjectConstructor constructor = new XMLObjectConstructor(new ParserFactory(loc));
        
        SimpleFieldTest test = (SimpleFieldTest)constructor.construct("myField.xml", false);
        System.out.println(test);
        
        loc.stopResourceChecker();
    }

}
