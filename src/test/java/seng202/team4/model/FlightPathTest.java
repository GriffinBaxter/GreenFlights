package seng202.team4.model;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Performs the tests for Airline class.
 * Setters and getters are not tested.
 */

public class FlightPathTest extends TestCase {

    /**
     * Tests whether getInsertStatement returns the correct statement
     * that adds the given airport values to the database.
     */
    @Test
    public void testInsertStatement() {
        FlightPath path = new FlightPath("FIX","ATMAP",35000,-12,118.255);
        String statement = path.getInsertStatement(10);
        assertEquals("INSERT INTO FlightPath ('TYPE', 'FLIGHTPATHID', 'ALTITUDE', 'LATITUDE', 'LONGITUDE', 'SETID') VALUES ('FIX', 'ATMAP', '35000', '-12.0', '118.26', '10');", statement);
    }

    /**
     * Test whether getValid returns a null value when given an invalid node type.
     */
    @Test
    public void testGetValidType() {
        FlightPath path = (FlightPath) FlightPath.getValid("F1X","ATMAP","35000","-12","118.255", new ArrayList<>());
        assertEquals(null, path);
    }

    /**
     * Test whether getValid returns a null value when given an invalid navaid identifier.
     */
    @Test
    public void testGetValidID() {
        FlightPath path = (FlightPath) FlightPath.getValid("FIX","ATM4P","35000","-12","118.255", new ArrayList<>());
        assertEquals(null, path);
    }

    /**
     * Test whether getValid returns a null value when given an invalid altitude.
     */
    @Test
    public void testGetValidAltitude() {
        FlightPath path = (FlightPath) FlightPath.getValid("FIX","ATMAP","3500O","-12","118.255", new ArrayList<>());
        assertEquals(null, path);
    }

    /**
     * Test whether getValid returns a null value when given an invalid latitude.
     */
    @Test
    public void testGetValidLatitude() {
        FlightPath path = (FlightPath) FlightPath.getValid("FIX","ATMAP","35000","-R","118.255", new ArrayList<>());
        assertEquals(null, path);
    }

    /**
     * Test whether getValid returns a null value when given an invalid longitude.
     */
    @Test
    public void testGetValidLongitude() {
        FlightPath path = (FlightPath) FlightPath.getValid("FIX","ATMAP","35000","-12","118.25a", new ArrayList<>());
        assertEquals(null, path);
    }

    /**
     * Test whether the getValid returns a flightplath object when given a valid input
     * and that the object has the correct attributes.
     */
    @Test
    public void testGetValid() {
        FlightPath path = new FlightPath();
        FlightPath newpath = (FlightPath) path.getValid(new String[]{"FIX","ATMAP","35000","-12","118.25"}, new ArrayList<>());
        assertTrue(newpath.equalsTest(new FlightPath("FIX","ATMAP",35000,-12,118.25)));
    }

}