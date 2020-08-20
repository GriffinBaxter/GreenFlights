package seng202.team4;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    @Override
    protected TestResult createResult() {
        return super.createResult();
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Test (added by Griffin)
     */
    public void testApp2() {
        assertFalse(false);
    }

    /**
     * Test (added by Swapnil)
     */
    public void testApp3() {assertTrue(true);}


    /**
     * Test (added by Darryl)
     */
    public void testApp4() {
        int x = 2;
        assertEquals(x, 2);
    }

    /**
     * Testing the tomatoes (by Kye)
     */
    public void testApp5(){
        String tomato = "red";
        int numTomatos = 5;

        assertTrue(tomato instanceof String);

        numTomatos --;
        assertEquals(numTomatos, 5);

    }
}
