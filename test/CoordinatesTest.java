import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @Test
    public void testCoordinatesCreation() {
        Coordinates c = new Coordinates(5, 10);
        assertEquals(5, c.x);
        assertEquals(10, c.y);
    }

    @Test
    public void testCoordinatesEquality() {
        Coordinates c1 = new Coordinates(3, 4);
        Coordinates c2 = new Coordinates(3, 4);
        Coordinates c3 = new Coordinates(5, 6);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    public void testCoordinatesEqualsSameObject() {
        Coordinates c = new Coordinates(1, 2);
        assertEquals(c, c);
    }

    @Test
    public void testCoordinatesEqualsNull() {
        Coordinates c = new Coordinates(1, 2);
        assertNotEquals(null, c);
    }

    @Test
    public void testCoordinatesToString() {
        Coordinates c = new Coordinates(7, 8);
        String result = c.toString();
        assertTrue(result.contains("7"));
        assertTrue(result.contains("8"));
    }
}
