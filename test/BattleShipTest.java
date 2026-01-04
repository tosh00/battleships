import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleShipTest {

    @Test
    public void testShipCreation() {
        Coordinates c = new Coordinates(0, 0);
        BattleShip battleShip = new BattleShip(3, true, c);
        assertEquals(3, battleShip.length);
        assertTrue(battleShip.direction);
    }

    @Test
    public void testFindSymbolForDifferentLengths() {
        assertEquals('S', BattleShip.findSymbol(1));
        assertEquals('D', BattleShip.findSymbol(2));
        assertEquals('C', BattleShip.findSymbol(3));
        assertEquals('B', BattleShip.findSymbol(4));
        assertEquals('A', BattleShip.findSymbol(5));
        assertEquals('?', BattleShip.findSymbol(10));
    }

    @Test
    public void testShipDisplaySymbol() {
        BattleShip battleShip1 = new BattleShip(1, true, new Coordinates(0, 0));
        assertEquals('S', battleShip1.getDisplaySymbol());

        BattleShip battleShip5 = new BattleShip(5, true, new Coordinates(0, 0));
        assertEquals('A', battleShip5.getDisplaySymbol());
    }

    @Test
    public void testHorizontalShipCoordinates() {
        Coordinates start = new Coordinates(2, 3);
        BattleShip battleShip = new BattleShip(3, true, start);
        Coordinates[] coords = battleShip.getCoordinates();

        assertEquals(3, coords.length);
        assertEquals(new Coordinates(2, 3), coords[0]);
        assertEquals(new Coordinates(3, 3), coords[1]);
        assertEquals(new Coordinates(4, 3), coords[2]);
    }

    @Test
    public void testVerticalShipCoordinates() {
        Coordinates start = new Coordinates(5, 2);
        BattleShip battleShip = new BattleShip(4, false, start);
        Coordinates[] coords = battleShip.getCoordinates();

        assertEquals(4, coords.length);
        assertEquals(new Coordinates(5, 2), coords[0]);
        assertEquals(new Coordinates(5, 3), coords[1]);
        assertEquals(new Coordinates(5, 4), coords[2]);
        assertEquals(new Coordinates(5, 5), coords[3]);
    }

    @Test
    public void testSingleLengthShip() {
        Coordinates start = new Coordinates(1, 1);
        BattleShip battleShip = new BattleShip(1, true, start);
        Coordinates[] coords = battleShip.getCoordinates();

        assertEquals(1, coords.length);
        assertEquals(new Coordinates(1, 1), coords[0]);
    }
}
