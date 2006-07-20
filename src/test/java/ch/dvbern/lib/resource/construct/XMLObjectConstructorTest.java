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

import java.util.*;
import ch.dvbern.lib.resource.construct.xml.FilePathResourceLocator;
import ch.dvbern.lib.resource.construct.xml.ParserFactory;
import ch.dvbern.lib.resource.construct.xml.ResourceChangeListener;
import ch.dvbern.lib.resource.construct.xml.ResourceChangedEvent;
import ch.dvbern.lib.resource.construct.xml.ResourceLocator;
import ch.dvbern.lib.resource.construct.xml.ResourceNotFoundException;
import ch.dvbern.lib.resource.construct.xml.XMLObjectConstructor;
import junit.framework.*;
import java.io.*;

/**
 * The main test class for construct lib.
 */
public class XMLObjectConstructorTest extends TestCase {
    
    private static String FILENAME_SIMPLETC;
    private File fileSTC;
    private static String FILENAME_REF;
    private File fileRef;
    
    private XMLObjectConstructor objConstructor;
    private FilePathResourceLocator fprl;
    
    
    /**
     * JUnit test constructor.
     * @param name test name
     */
    public XMLObjectConstructorTest(String name) {
        super(name);
    }
    
    /**
     * JUnit test suite method.
     * 
     * @return a JUnit test suite.
     */
    public static Test suite() {
        return new TestSuite(XMLObjectConstructorTest.class);
    }
    
    /**
     * Main method.
     * 
     * @param args main method arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        
        //create ref.xml
        fileRef = File.createTempFile("ref.xml", null);
        FileOutputStream fout = new FileOutputStream(fileRef);
        DataOutputStream dout = new DataOutputStream(fout);
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<script>");
        dout.writeChars("<vardef name=\"map\">");
        dout.writeChars("<construct class=\"java.util.HashMap\">");
        dout.writeChars("<int value=\"5\" />");
        dout.writeChars("</construct>");
        dout.writeChars("</vardef>");
        
        dout.writeChars("<invoke methodName=\"put\">");
        dout.writeChars("<target><var name=\"map\" /></target>");
        dout.writeChars("<parameters>");
        dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"key 2\" /></cast>");
        dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"value 2\" /></cast>");
        dout.writeChars("</parameters>");
        dout.writeChars("</invoke>");
        dout.writeChars("<invoke methodName=\"put\">");
        dout.writeChars("<target><var name=\"map\" /></target>");
        dout.writeChars("<parameters>");
        dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"key 5\" /></cast>");
        dout.writeChars("<cast class=\"java.lang.Object\"><string value=\"value 5\" /></cast>");
        dout.writeChars("</parameters>");
        dout.writeChars("</invoke>");
        dout.writeChars("<return>");
        dout.writeChars("<var name=\"map\" />");
        dout.writeChars("</return>");
        dout.writeChars("</script>");
        
        
        
        FILENAME_REF = fileRef.getName();
        
        if (fout != null)
            fout.close();
        if (dout != null)
            dout.close();
        
        
        //create xml-file simpleTestConstruct.xml
        fileSTC = File.createTempFile("simpleTestConstruct.xml", null);
        originalFile();
        FILENAME_SIMPLETC = fileSTC.getName();
        
        //create objConstructor
        fprl = new FilePathResourceLocator(fileSTC.getParent(), 500);
        objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));
        
    }
    
    private void originalFile() throws Exception {
        FileOutputStream fout = new FileOutputStream(fileSTC);
        DataOutputStream dout = new DataOutputStream(fout);
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<construct class=\"ch.dvbern.lib.resource.construct.SimpleTestConstruct\">");
        //here was initargs
        //short ashort, int aint, long along, float afloat, double adouble, char achar, boolean aboolean, String astring, Object aobject
        dout.writeChars("<short value=\"1\" />");
        dout.writeChars("<int value=\"2\" />");
        dout.writeChars("<long value=\"3\" />");
        dout.writeChars("<float value=\"4.4f\" />");
        dout.writeChars("<double value=\"5.5\" />");
        dout.writeChars("<char value=\"c\" />");
        dout.writeChars("<boolean value=\"true\" />");
        dout.writeChars("<string value=\"a string\" />");
        dout.writeChars("<cast class=\"java.lang.Object\"><ref id=\""+FILENAME_REF+"\" /></cast>");
        
        dout.writeChars("<array elementtype=\"java.lang.Integer\">");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        dout.writeChars("<string value=\"1\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        dout.writeChars("<string value=\"2\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        dout.writeChars("<string value=\"3\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</array>");
        
        dout.writeChars("<array elementtype=\"int[]\">");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"11\" />");
        dout.writeChars("<int value=\"12\" />");
        dout.writeChars("<int value=\"13\" />");
        dout.writeChars("</array>");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"21\" />");
        dout.writeChars("<int value=\"22\" />");
        dout.writeChars("<int value=\"23\" />");
        dout.writeChars("</array>");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"31\" />");
        dout.writeChars("<int value=\"32\" />");
        dout.writeChars("<int value=\"33\" />");
        dout.writeChars("</array>");
        dout.writeChars("</array>");
        
        //here was /initargs
        /*
        dout.writeChars("<access>");
        dout.writeChars("<field name=\"aStaticString\">");
        dout.writeChars("<string value=\"new definition\" />");
        dout.writeChars("</field>");
        dout.writeChars("<method name=\"setAStaticInteger\" >");
        dout.writeChars("<construct class=\"java.lang.Integer\" >");
        //here was initargs
        dout.writeChars("<string value=\"333\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</method>");
        dout.writeChars("</access>");
         */
        dout.writeChars("</construct>");
        
        if (fout != null)
            fout.close();
        if (dout != null)
            dout.close();
    }
    
    private void changeFile() throws Exception {
        FileOutputStream fout = new FileOutputStream(fileSTC.getAbsolutePath());
        DataOutputStream dout = new DataOutputStream(fout);
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<construct class=\"ch.dvbern.lib.resource.construct.SimpleTestConstruct\">");
        //here was initargs
        
        dout.writeChars("<short value=\"100\" />");
        dout.writeChars("<int value=\"2\" />");
        dout.writeChars("<long value=\"3\" />");
        dout.writeChars("<float value=\"4.4f\" />");
        dout.writeChars("<double value=\"5.5\" />");
        dout.writeChars("<char value=\"c\" />");
        dout.writeChars("<boolean value=\"true\" />");
        dout.writeChars("<string value=\"a string\" />");
        dout.writeChars("<cast class=\"java.lang.Object\"><ref id=\""+FILENAME_REF+"\" /></cast>");
        
        dout.writeChars("<array elementtype=\"java.lang.Integer\">");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        
        dout.writeChars("<string value=\"100\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        dout.writeChars("<string value=\"200\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("<construct class=\"java.lang.Integer\">");
        //here was initargs
        dout.writeChars("<string value=\"300\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</array>");
        
        dout.writeChars("<array elementtype=\"int[]\">");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"11\" />");
        dout.writeChars("<int value=\"12\" />");
        dout.writeChars("<int value=\"13\" />");
        dout.writeChars("</array>");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"21\" />");
        dout.writeChars("<int value=\"22\" />");
        dout.writeChars("<int value=\"23\" />");
        dout.writeChars("</array>");
        dout.writeChars("<array elementtype=\"int\">");
        dout.writeChars("<int value=\"31\" />");
        dout.writeChars("<int value=\"32\" />");
        dout.writeChars("<int value=\"33\" />");
        dout.writeChars("</array>");
        dout.writeChars("</array>");
        
        //here was /initargs
        /*
        dout.writeChars("<access>");
        dout.writeChars("<field name=\"aStaticString\">");
        dout.writeChars("<string value=\"new definition\" />");
        dout.writeChars("</field>");
        dout.writeChars("<method name=\"setAStaticInteger\" >");
        dout.writeChars("<construct class=\"java.lang.Integer\" >");
        //here was initargs
         
        dout.writeChars("<string value=\"999\" />");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</method>");
        dout.writeChars("</access>");
         */
        dout.writeChars("</construct>");
        
        if (fout != null)
            fout.close();
        if (dout != null)
            dout.close();
        
        
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        fprl.stopResourceChecker();
        fileSTC.delete();
        fileRef.delete();
    }
    
    
    private File constructMyClass() throws Exception {
        File file = File.createTempFile("myClass", ".xml");
        FileOutputStream fout = new FileOutputStream(file);
        DataOutputStream dout = new DataOutputStream(fout);
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<class name=\"java.lang.String\" />");
        
        dout.close();
        fout.close();
        return file;
    }
    
    /**
     * test initialization
     * 
     * @throws Exception error while initialization
     */
    public void testInitValues() throws Exception {
        SimpleTestConstruct construct = (SimpleTestConstruct)objConstructor.construct(FILENAME_SIMPLETC, false);
        assertEquals(construct.getAshort(), 1);
        assertEquals(construct.getAint(), 2);
        assertEquals(construct.getAlong(), 3);
        assertTrue(construct.getAfloat() == 4.4f);
        assertTrue(construct.getAdouble() == 5.5);
        assertEquals(construct.getAchar(), 'c');
        assertEquals(construct.isAboolean(), true);
        assertEquals(construct.getAstring(), "a string");
        //assertEquals(construct.aStaticString, "new definition");
        //assertEquals(construct.getAStaticInteger(), new Integer("333"));
    }
    
    /**
     * ref parser test
     * 
     * @throws Exception test failure
     */
    public void testRefParser() throws Exception {
        SimpleTestConstruct construct = (SimpleTestConstruct)objConstructor.construct(FILENAME_SIMPLETC, false);
        Map map = (Map)construct.getAobject();
        assertEquals(map.get("key 2"), "value 2");
    }
    
    /**
     * array parser test
     * 
     * @throws Exception test failure
     */
    public void testArrayParser() throws Exception {
        SimpleTestConstruct construct = (SimpleTestConstruct)objConstructor.construct(FILENAME_SIMPLETC, false);
        Integer[] arrayOne = construct.getArrayOne();
        assertTrue(arrayOne[1].equals(new Integer("2")));
        int[][] arrayTwo = construct.getArrayTwo();
        assertTrue(arrayTwo[0][2] == 13);
        assertTrue(arrayTwo[1][1] == 22);
        assertTrue(arrayTwo[2][0] == 31);
    }
    
    
    /**
     * remove event test
     * 
     * @throws Exception test failure
     */
    public void testRemoveEvent() throws Exception {
        try {
            objConstructor.construct(FILENAME_SIMPLETC, false);
        } catch (ConstructionException ex) {
            fail("exception thrown");
        }
        fileSTC.delete();
        Thread.sleep(1000); //see set period in setUp
        try {
            objConstructor.construct(FILENAME_SIMPLETC, false);
            fail("no exception thrown");
        } catch (ConstructionException ex) {}
    }
    
    /**
     * change event test
     * 
     * @throws Exception test failure
     */
    public void testChangeEvent() throws Exception {
        XMLObjectConstructorTest.TestFilePathResourceLocator locator = new XMLObjectConstructorTest.TestFilePathResourceLocator(fileSTC.getParent());
        XMLObjectConstructor constructor = new XMLObjectConstructor(new ParserFactory(locator));
        
        SimpleTestConstruct construct = (SimpleTestConstruct)constructor.construct(FILENAME_SIMPLETC, false);
        assertEquals(construct.getAshort(), 1);
        Integer[] arrayOne = construct.getArrayOne();
        assertTrue(arrayOne[1].equals(new Integer("2")));
        //assertEquals(construct.getAStaticInteger(), new Integer("333"));
        
        changeFile();
        locator.notifyResourceChange(FILENAME_SIMPLETC);
        
        construct = (SimpleTestConstruct)constructor.construct(FILENAME_SIMPLETC, false);
        assertEquals(construct.getAshort(), 100);
        arrayOne = construct.getArrayOne();
        assertTrue(arrayOne[1].equals(new Integer("200")));
        //assertEquals(construct.getAStaticInteger(), new Integer("999"));
        
    }
    
    /**
     * class test
     * 
     * @throws Exception test failure
     */
    public void testClass() throws Exception {
        File file = constructMyClass();
        FilePathResourceLocator fprl = new FilePathResourceLocator(file.getParent(), 500);
        XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));
        Class klass = (Class)objConstructor.construct(file.getName(), false);
        assertTrue(klass.getName().equals("".getClass().getName()));
        
        fprl.stopResourceChecker();
        file.delete();
    }
    
    /**
     * script test
     * 
     * @throws Exception test failure
     */
    public void testScript() throws Exception {
        File file = File.createTempFile("script", ".xml");
        FileOutputStream fout = new FileOutputStream(file);
        DataOutputStream dout = new DataOutputStream(fout);
        
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<script>");
        dout.writeChars("<vardef name=\"a\">");
        dout.writeChars("<construct class=\"java.lang.String\">");
        //here was initargs
        dout.writeChars("<string value=\"a string\"/>");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</vardef>");
        dout.writeChars("<vardef name=\"b\">");
        dout.writeChars("<construct class=\"java.lang.String\">");
        //here was initargs
        dout.writeChars("<string value=\" and another string\"/>");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</vardef>");
        dout.writeChars("<invoke methodName=\"concat\">");
        dout.writeChars("<target><var name=\"a\"/></target>");
        dout.writeChars("<parameters>");
        dout.writeChars("<var name=\"b\" />");
        dout.writeChars("</parameters>");
        dout.writeChars("</invoke>");
        dout.writeChars("</script>");
        
        
        
        FilePathResourceLocator fprl = new FilePathResourceLocator(file.getParent(), 500);
        XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));
        
        String obj = (String)objConstructor.construct(file.getName(), false);
        assertEquals(obj, "a string and another string");
        
        file.delete();
        dout.close();
        fout.close();
        
        file = File.createTempFile("script2", ".xml");
        fout = new FileOutputStream(file);
        dout = new DataOutputStream(fout);
        
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<script>");
        dout.writeChars("<vardef name=\"a\">");
        dout.writeChars("<construct class=\"java.lang.String\">");
        //here was initargs
        dout.writeChars("<string value=\"a string\"/>");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</vardef>");
        dout.writeChars("<vardef name=\"b\">");
        dout.writeChars("<construct class=\"java.lang.String\">");
        //here was initargs
        dout.writeChars("<string value=\" and another string\"/>");
        //here was /initargs
        dout.writeChars("</construct>");
        dout.writeChars("</vardef>");
        dout.writeChars("<vardef name=\"c\">");
        dout.writeChars("<script>");
        dout.writeChars("<vardef name=\"b\">");
        dout.writeChars("<string value=\" and a modified string\" />");
        dout.writeChars("</vardef>");
        dout.writeChars("<invoke methodName=\"concat\" >");
        dout.writeChars("<target><var name=\"a\" /></target>");
        dout.writeChars("<parameters>");
        dout.writeChars("<var name=\"b\" />");
        dout.writeChars("</parameters>");
        dout.writeChars("</invoke>");
        dout.writeChars("</script>");
        dout.writeChars("</vardef>");
        dout.writeChars("<return>");
        dout.writeChars("<invoke methodName=\"concat\">");
        dout.writeChars("<target><var name=\"c\" /></target>");
        dout.writeChars("<parameters><string value=\" are concatenated\" /></parameters>");
        dout.writeChars("</invoke>");
        dout.writeChars("</return>");
        dout.writeChars("</script>");
        
        obj = (String)objConstructor.construct(file.getName(), false);
        assertEquals(obj, "a string and a modified string are concatenated");
        
        
        file.delete();
        dout.close();
        fout.close();
        
        fprl.stopResourceChecker();
        
    }
    
    /**
     * field access test
     * 
     * @throws Exception test failure
     */
    public void testFieldAccess() throws Exception {
        File file = File.createTempFile("field", ".xml");
        FileOutputStream fout = new FileOutputStream(file);
        DataOutputStream dout = new DataOutputStream(fout);
        
        dout.writeChars("<?xml version=\"1.0\"?>");
        dout.writeChars("<script>");
        dout.writeChars("<vardef name=\"myObject\">");
        dout.writeChars("<construct class=\"ch.dvbern.lib.resource.construct.SimpleFieldTest\" />");
        dout.writeChars("</vardef>");
        dout.writeChars("<vardef name=\"field1\" >");
        dout.writeChars("<getfield name=\"field1\" >");
        dout.writeChars("<target><var name=\"myObject\" /></target>");
        dout.writeChars("</getfield>");
        dout.writeChars("</vardef>");
        dout.writeChars("<setfield name=\"field2\">");
        dout.writeChars("<target><var name=\"myObject\" /></target>");
        dout.writeChars("<value><var name=\"field1\" /></value>");
        dout.writeChars("</setfield>");
        dout.writeChars("<return><var name=\"myObject\" /></return>");
        dout.writeChars("</script>");
        
        FilePathResourceLocator fprl = new FilePathResourceLocator(file.getParent(), 500);
        XMLObjectConstructor objConstructor = new XMLObjectConstructor(new ParserFactory(fprl));
        
        SimpleFieldTest test = (SimpleFieldTest)objConstructor.construct(file.getName(), false);
        assertTrue(test.field2.equals("field 1"));
        
        file.delete();
        dout.close();
        fout.close();
        
        fprl.stopResourceChecker();
    }
    
    /*
    public void testTmpChangeEvent() throws Exception {
     
        //test
        fprl.stopResourceChecker();
     
        FilePathResourceLocator loc = new FilePathResourceLocator("/home/loue/project/dvbern-lib-forms/construct", 3000);
        XMLObjectConstructor constructor = new XMLObjectConstructor(new ParserFactory(loc));
        Map myMap = (Map)constructor.construct("myMap.xml", false);
        System.out.println(">>>tmp:"+myMap);
        System.out.println(">>>going to sleep");
        Thread.sleep(10000);
        System.out.println(">>>end of sleep");
        myMap = (Map)constructor.construct("myMap.xml", false);
        System.out.println(">>>tmp:"+myMap);
     
        loc.stopResourceChecker();
     
     
    }
     */
    
    private class TestFilePathResourceLocator implements ResourceLocator {
        private String path;
        private HashSet listeners;
        private HashSet files;
        
        /**
         * Test resource locator.
         * 
         * @param path file path
         */
        public TestFilePathResourceLocator(String path) {
            this.path = path;
            listeners = new HashSet();
            files = new HashSet();
        }
        /**
         * Method de-registers listeners.
         *
         * @param listener: registered listener that has to be removed
         */
        public void removeResourceChangeListener(ResourceChangeListener listener) {
            synchronized(listeners) {
                if (listeners.contains(listener)) {
                    listeners.remove(listener);
                }
            }
        }
        
        /**
         * Method specified resource as InputStream or throws Exception.
         *
         * @param resourceName Name of resource to locate (name of xml-file)
         * @return InputStream: resource as InputStream; never null.
         * @exception ResourceNotFoundException: Thrown if specified resource could not have been found
         */
        public InputStream getResourceAsStream(String resourceName) throws ResourceNotFoundException {
            try {
                
                File file = new File(path, resourceName);
                synchronized(files) {
                    if (!files.contains(file)) {
                        files.add(file);
                    }
                }
                return new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                throw new ResourceNotFoundException("resource="+resourceName+" not found");
            }
        }
        
        /**
         * Method registers listeners interested in changes or removals of resources.
         *
         * @param listener: listener interested in changes or removals of resources
         */
        public void addResourceChangeListener(ResourceChangeListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            synchronized(listeners) {
                if (!listeners.contains(listener)) {
                    listeners.add(listener);
                }
            }
        }
        
        /**
         * resource change notification
         * 
         * @param resource identifier of the resource
         */
        public void notifyResourceChange(String resource) {
            ResourceChangedEvent event = new ResourceChangedEvent(this, resource);
            Set clone = null;
            synchronized(listeners) {
                clone = (Set)listeners.clone();
            }
            for (Iterator i = clone.iterator(); i.hasNext();) {
                ResourceChangeListener listener = (ResourceChangeListener)i.next();
                try {
                    listener.resourceChanged(event);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
}

