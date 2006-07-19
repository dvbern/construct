package ch.dvbern.lib.resource.construct;

public class SimpleTestConstruct {
    
    private short ashort;
    
    private int aint;
    
    private long along;
    
    private float afloat;
    
    private double adouble;
    
    private char achar;
    
    private boolean aboolean;
    
    private String astring;
    
    private Object aobject;
    
    public static String aStaticString = "default static string";
    
    private static Integer aStaticInteger;
    
    private Integer[] arrayOne;
    private int[][] arrayTwo;
    
    public SimpleTestConstruct() {}
    public SimpleTestConstruct(short ashort, int aint, long along, float afloat, double adouble, char achar, boolean aboolean, String astring, Object aobject) {
        this(ashort, aint, along, afloat, adouble, achar, aboolean, astring, aobject, new Integer[]{}, new int[][]{});
    }
    public SimpleTestConstruct(short ashort, int aint, long along, float afloat, double adouble, char achar, boolean aboolean, String astring, Object aobject, Integer[] arrayOne, int[][] arrayTwo) {
        setAshort(ashort);
        setAint(aint);
        setAlong(along);
        setAfloat(afloat);
        setAdouble(adouble);
        setAchar(achar);
        setAboolean(aboolean);
        setAstring(astring);
        setAobject(aobject);
        setArrayOne(arrayOne);
        setArrayTwo(arrayTwo);
    }
    
    /** Getter for property ashort.
     * @return Value of property ashort.
     */
    public short getAshort() {
        return ashort;
    }
    
    /** Setter for property ashort.
     * @param ashort New value of property ashort.
     */
    public void setAshort(short ashort) {
        this.ashort = ashort;
    }
     
    /** Getter for property aint.
     * @return Value of property aint.
     */
    public int getAint() {
        return aint;
    }
    
    /** Setter for property aint.
     * @param aint New value of property aint.
     */
    public void setAint(int aint) {
        this.aint = aint;
    }
    
    /** Getter for property along.
     * @return Value of property along.
     */
    public long getAlong() {
        return along;
    }
    
    /** Setter for property along.
     * @param along New value of property along.
     */
    public void setAlong(long along) {
        this.along = along;
    }
    
    /** Getter for property afloat.
     * @return Value of property afloat.
     */
    public float getAfloat() {
        return afloat;
    }
    
    /** Setter for property afloat.
     * @param afloat New value of property afloat.
     */
    public void setAfloat(float afloat) {
        this.afloat = afloat;        
    }
    
    /** Getter for property adouble.
     * @return Value of property adouble.
     */
    public double getAdouble() {
        return adouble;
    }
    
    /** Setter for property adouble.
     * @param adouble New value of property adouble.
     */
    public void setAdouble(double adouble) {
        this.adouble = adouble;
    }
    
    
    /** Getter for property achar.
     * @return Value of property achar.
     */
    public char getAchar() {
        return achar;
    }
    
    /** Setter for property achar.
     * @param achar New value of property achar.
     */
    public void setAchar(char achar) {
        this.achar = achar;
    }
    
    /** Getter for property aboolean.
     * @return Value of property aboolean.
     */
    public boolean isAboolean() {
        return aboolean;
    }
    
    /** Setter for property aboolean.
     * @param aboolean New value of property aboolean.
     */
    public void setAboolean(boolean aboolean) {
        this.aboolean = aboolean;
    }
    
    /** Getter for property astring.
     * @return Value of property astring.
     */
    public String getAstring() {
        return astring;
    }
    
    /** Setter for property astring.
     * @param astring New value of property astring.
     */
    public void setAstring(String astring) {
        this.astring = astring;
    }
    
    /** Getter for property aobject.
     * @return Value of property aobject.
     */
    public Object getAobject() {
        return aobject;
    }
    
    /** Setter for property aobject.
     * @param aobject New value of property aobject.
     */
    public void setAobject(Object aobject) {
        this.aobject = aobject;
    }
    
    public static void setAStaticInteger(Integer integer) {
        aStaticInteger = integer;
    }
    
    public static Integer getAStaticInteger() {
        return aStaticInteger;
    }
    
    /** Getter for property arrayOne.
     * @return Value of property arrayOne.
     */
    public Integer[] getArrayOne() {
        return this.arrayOne;
    }
    
    /** Setter for property arrayOne.
     * @param arrayOne New value of property arrayOne.
     */
    public void setArrayOne(Integer[] arrayOne) {
        this.arrayOne = arrayOne;
    }
    
    /** Getter for property arrayTwo.
     * @return Value of property arrayTwo.
     */
    public int[][] getArrayTwo() {
        return this.arrayTwo;
    }
    
    /** Setter for property arrayTwo.
     * @param arrayTwo New value of property arrayTwo.
     */
    public void setArrayTwo(int[][] arrayTwo) {
        this.arrayTwo = arrayTwo;
    }
    
}

