import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SeaMineTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(10, 10);
    }

    // Basic SeaMine creation tests
    @Test
    public void testSeaMineCreation() {
        SeaMine mine = new SeaMine(new Coordinates(5, 5));
        assertNotNull(mine);
        assertEquals(5, mine.coordinates.x);
        assertEquals(5, mine.coordinates.y);
    }

    @Test
    public void testSeaMineDisplaySymbol() {
        SeaMine mine = new SeaMine(new Coordinates(3, 3));
        assertEquals('M', mine.getDisplaySymbol());
    }

    @Test
    public void testSeaMineGetCoordinates() {
        SeaMine mine = new SeaMine(new Coordinates(2, 4));
        Coordinates[] coords = mine.getCoordinates();
        assertEquals(1, coords.length);
        assertEquals(2, coords[0].x);
        assertEquals(4, coords[0].y);
    }

    // Mine placement tests
    @Test
    public void testPlaceMineSuccessfully() {
        SeaMine mine = new SeaMine(new Coordinates(5, 5));
        board.placeMine(mine);
        assertTrue(board.getMines().contains(mine));
    }

    @Test
    public void testPlaceMineAtEdgeOfBoard() {
        SeaMine mine = new SeaMine(new Coordinates(0, 0));
        board.placeMine(mine);
        assertTrue(board.getMines().contains(mine));
    }

    @Test
    public void testPlaceMineAtBottomRightCorner() {
        SeaMine mine = new SeaMine(new Coordinates(9, 9));
        board.placeMine(mine);
        assertTrue(board.getMines().contains(mine));
    }

    @Test
    public void testPlaceMineOutOfBounds() {
        SeaMine mine = new SeaMine(new Coordinates(10, 5));
        assertThrows(CoordinatesOutOfBoundException.class, () -> board.placeMine(mine));
    }

    @Test
    public void testPlaceMineAtNegativeCoordinates() {
        SeaMine mine = new SeaMine(new Coordinates(-1, 5));
        assertThrows(CoordinatesOutOfBoundException.class, () -> board.placeMine(mine));
    }

    @Test
    public void testPlaceMultipleMines() {
        SeaMine mine1 = new SeaMine(new Coordinates(2, 2));
        SeaMine mine2 = new SeaMine(new Coordinates(5, 5));
        SeaMine mine3 = new SeaMine(new Coordinates(8, 8));

        assertTrue(board.placeMine(mine1));
        assertTrue(board.placeMine(mine2));
        assertTrue(board.placeMine(mine3));
    }

    @Test
    public void testPlaceMinesAtSameLocation() {
        // Mines can be placed at same location (no collision check for mines)
        SeaMine mine1 = new SeaMine(new Coordinates(5, 5));
        SeaMine mine2 = new SeaMine(new Coordinates(5, 5));

        assertTrue(board.placeMine(mine1));
        assertTrue(board.placeMine(mine2));
    }

    // Ship collision with mine tests
    @Test
    public void testShipPlacedOnMineShouldBeSunk() {
        SeaMine mine = new SeaMine(new Coordinates(2, 2));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(3, true, new Coordinates(1, 2)); // Ship at (1,2), (2,2), (3,2)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Ship should be sunk when placed on mine");
    }

    @Test
    public void testShipNotTouchingMineShouldNotBeSunk() {
        SeaMine mine = new SeaMine(new Coordinates(5, 5));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(3, true, new Coordinates(0, 0)); // Ship at (0,0), (1,0), (2,0)
        board.placeShip(battleShip);

        assertFalse(battleShip.sunk, "Ship should not be sunk when not touching mine");
    }

    @Test
    public void testHorizontalShipCollidesWithMineAtStart() {
        SeaMine mine = new SeaMine(new Coordinates(0, 0));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(4, true, new Coordinates(0, 0)); // Ship at (0,0), (1,0), (2,0), (3,0)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Horizontal ship should be sunk when start coordinate hits mine");
    }

    @Test
    public void testHorizontalShipCollidesWithMineAtMiddle() {
        SeaMine mine = new SeaMine(new Coordinates(2, 3));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(4, true, new Coordinates(0, 3)); // Ship at (0,3), (1,3), (2,3), (3,3)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Horizontal ship should be sunk when middle coordinate hits mine");
    }

    @Test
    public void testHorizontalShipCollidesWithMineAtEnd() {
        SeaMine mine = new SeaMine(new Coordinates(4, 5));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(3, true, new Coordinates(2, 5)); // Ship at (2,5), (3,5), (4,5)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Horizontal ship should be sunk when end coordinate hits mine");
    }

    @Test
    public void testVerticalShipCollidesWithMineAtStart() {
        SeaMine mine = new SeaMine(new Coordinates(5, 1));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(3, false, new Coordinates(5, 1)); // Ship at (5,1), (5,2), (5,3)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Vertical ship should be sunk when start coordinate hits mine");
    }

    @Test
    public void testVerticalShipCollidesWithMineAtMiddle() {
        SeaMine mine = new SeaMine(new Coordinates(6, 5));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(5, false, new Coordinates(6, 3)); // Ship at (6,3), (6,4), (6,5), (6,6), (6,7)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Vertical ship should be sunk when middle coordinate hits mine");
    }

    @Test
    public void testVerticalShipCollidesWithMineAtEnd() {
        SeaMine mine = new SeaMine(new Coordinates(7, 9));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(4, false, new Coordinates(7, 6)); // Ship at (7,6), (7,7), (7,8), (7,9)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Vertical ship should be sunk when end coordinate hits mine");
    }

    @Test
    public void testSingleCellShipCollidesWithMine() {
        SeaMine mine = new SeaMine(new Coordinates(3, 3));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(1, true, new Coordinates(3, 3));
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Single cell ship should be sunk when placed on mine");
    }

    @Test
    public void testMultipleMinesOnlyOneHitsShip() {
        SeaMine mine1 = new SeaMine(new Coordinates(1, 1));
        SeaMine mine2 = new SeaMine(new Coordinates(5, 5));
        SeaMine mine3 = new SeaMine(new Coordinates(8, 8));

        board.placeMine(mine1);
        board.placeMine(mine2);
        board.placeMine(mine3);

        BattleShip battleShip = new BattleShip(3, true, new Coordinates(4, 5)); // Ship at (4,5), (5,5), (6,5)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk, "Ship should be sunk when hitting any mine");
    }

    @Test
    public void testMultipleShipsOnlyOneHitsMine() {
        SeaMine mine = new SeaMine(new Coordinates(5, 5));
        board.placeMine(mine);

        BattleShip battleShip1 = new BattleShip(2, true, new Coordinates(0, 0)); // Ship at (0,0), (1,0)
        BattleShip battleShip2 = new BattleShip(3, true, new Coordinates(4, 5)); // Ship at (4,5), (5,5), (6,5)
        BattleShip battleShip3 = new BattleShip(2, false, new Coordinates(0, 7)); // Ship at (0,7), (0,8)

        board.placeShip(battleShip1);
        board.placeShip(battleShip2);
        board.placeShip(battleShip3);

        assertFalse(battleShip1.sunk, "Ship1 should not be sunk");
        assertTrue(battleShip2.sunk, "Ship2 should be sunk when hitting mine");
        assertFalse(battleShip3.sunk, "Ship3 should not be sunk");
    }

    @Test
    public void testShipCannotBeePlacedOnExistingShipEvenWithMine() {
        SeaMine mine = new SeaMine(new Coordinates(2, 2));
        board.placeMine(mine);

        BattleShip battleShip1 = new BattleShip(3, true, new Coordinates(1, 2)); // Ship at (1,2), (2,2), (3,2)
        board.placeShip(battleShip1);

        BattleShip battleShip2 = new BattleShip(3, false, new Coordinates(2, 1)); // Ship at (2,1), (2,2), (2,3)
        assertThrows(CoordinatesAlreadyOcupiedException.class, () -> board.placeShip(battleShip2));
    }

    // Test mine functionality doesn't interfere with normal game mechanics
    @Test
    public void testMineDoesNotPreventShipPlacementAtDifferentLocation() {
        SeaMine mine = new SeaMine(new Coordinates(5, 5));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(3, true, new Coordinates(0, 0));
        assertDoesNotThrow(() -> board.placeShip(battleShip));
        assertFalse(battleShip.sunk);
    }

    @Test
    public void testPlaceShipThenMineAtSameLocation() {
        BattleShip battleShip = new BattleShip(3, true, new Coordinates(2, 2)); // Ship at (2,2), (3,2), (4,2)
        board.placeShip(battleShip);

        // Placing mine after ship should not affect already placed ship
        SeaMine mine = new SeaMine(new Coordinates(2, 2));
        assertTrue(board.placeMine(mine));
        assertFalse(battleShip.sunk, "Ship should not be sunk by mine placed after ship");
    }

    @Test
    public void testSunkShipFromMineIsMarkedAsShotOnBoard() {
        SeaMine mine = new SeaMine(new Coordinates(3, 3));
        board.placeMine(mine);

        BattleShip battleShip = new BattleShip(2, true, new Coordinates(3, 3)); // Ship at (3,3), (4,3)
        board.placeShip(battleShip);

        assertTrue(battleShip.sunk);
        // The board should mark all ship coordinates as shot when sunk by mine
        String display = board.displayBoard(false);
        assertTrue(display.contains("X"), "Board should show hits for ship sunk by mine");
    }

    @Test
    public void testMultipleMinesMultipleShipsComplexScenario() {
        // Place multiple mines
        SeaMine mine1 = new SeaMine(new Coordinates(2, 2));
        SeaMine mine2 = new SeaMine(new Coordinates(7, 7));
        SeaMine mine3 = new SeaMine(new Coordinates(1, 8));

        board.placeMine(mine1);
        board.placeMine(mine2);
        board.placeMine(mine3);

        // Place ships - some hit mines, some don't
        BattleShip battleShip1 = new BattleShip(2, true, new Coordinates(0, 0)); // Safe
        BattleShip battleShip2 = new BattleShip(3, true, new Coordinates(1, 2)); // Hits mine1 at (2,2)
        BattleShip battleShip3 = new BattleShip(2, false, new Coordinates(5, 5)); // Safe
        BattleShip battleShip4 = new BattleShip(4, true, new Coordinates(5, 7)); // Hits mine2 at (7,7)

        board.placeShip(battleShip1);
        board.placeShip(battleShip2);
        board.placeShip(battleShip3);
        board.placeShip(battleShip4);

        assertFalse(battleShip1.sunk, "Ship1 should be safe");
        assertTrue(battleShip2.sunk, "Ship2 should be sunk by mine1");
        assertFalse(battleShip3.sunk, "Ship3 should be safe");
        assertTrue(battleShip4.sunk, "Ship4 should be sunk by mine2");
    }

    @Test
    public void testCheckIfAnyShipLeftWithSunkShipFromMine() {
        SeaMine mine = new SeaMine(new Coordinates(0, 0));
        board.placeMine(mine);

        BattleShip battleShip1 = new BattleShip(2, true, new Coordinates(0, 0)); // Will be sunk by mine
        BattleShip battleShip2 = new BattleShip(2, true, new Coordinates(5, 5)); // Safe ship

        board.placeShip(battleShip1);
        board.placeShip(battleShip2);

        assertTrue(battleShip1.sunk);
        assertFalse(battleShip2.sunk);

        // There should still be a ship left (ship2)
        assertTrue(board.checkIfAnyShipLeft());
    }
}
