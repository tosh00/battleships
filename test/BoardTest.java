import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(10, 10);
    }

    @Test
    public void testBoardCreation() {
        assertEquals(10, board.getWidth());
        assertEquals(10, board.getHeight());
    }

    @Test
    public void testPlaceShipSuccessfully() {
        BattleShip battleShip = new BattleShip(3, true, new Coordinates(0, 0));
        assertDoesNotThrow(() -> board.placeShip(battleShip));
    }

    @Test
    public void testPlaceShipOutOfBounds() {
        BattleShip battleShip = new BattleShip(3, true, new Coordinates(9, 0));
        assertThrows(CoordinatesOutOfBoundException.class, () -> board.placeShip(battleShip));
    }

    @Test
    public void testPlaceShipAtNegativeCoordinates() {
        BattleShip battleShip = new BattleShip(2, true, new Coordinates(-1, 0));
        assertThrows(CoordinatesOutOfBoundException.class, () -> board.placeShip(battleShip));
    }

    @Test
    public void testPlaceShipCollision() {
        BattleShip battleShip1 = new BattleShip(3, true, new Coordinates(0, 0));
        BattleShip battleShip2 = new BattleShip(3, false, new Coordinates(1, 0));

        board.placeShip(battleShip1);
        assertThrows(CoordinatesAlreadyOcupiedException.class, () -> board.placeShip(battleShip2));
    }

    @Test
    public void testPlaceTwoShipsWithoutCollision() {
        BattleShip battleShip1 = new BattleShip(3, true, new Coordinates(0, 0));
        BattleShip battleShip2 = new BattleShip(3, true, new Coordinates(0, 2));

        assertDoesNotThrow(() -> {
            board.placeShip(battleShip1);
            board.placeShip(battleShip2);
        });
    }

    @Test
    public void testShotAtValidCoordinate() {
        Coordinates c = new Coordinates(5, 5);
        assertDoesNotThrow(() -> board.shot(c));
    }

    @Test
    public void testShotAtSameCoordinateTwice() {
        Coordinates c = new Coordinates(5, 5);
        assertDoesNotThrow(() -> board.shot(c));
        assertThrows(CoordinatesAlreadyUsedException.class, () -> board.shot(c));
    }

    @Test
    public void testCheckIfAnyShipLeftWithShips() {
        BattleShip battleShip = new BattleShip(3, true, new Coordinates(0, 0));
        board.placeShip(battleShip);
        assertTrue(board.checkIfAnyShipLeft());
    }

    @Test
    public void testCheckIfAnyShipLeftAfterAllSunk() throws CoordinatesAlreadyUsedException {
        BattleShip battleShip = new BattleShip(2, true, new Coordinates(0, 0));
        board.placeShip(battleShip);

        board.shot(new Coordinates(0, 0));
        board.shot(new Coordinates(1, 0));

        assertFalse(board.checkIfAnyShipLeft());
    }

    @Test
    public void testCheckIfAnyShipLeftAfterPartialDamage() throws CoordinatesAlreadyUsedException {
        BattleShip battleShip = new BattleShip(3, true, new Coordinates(0, 0));
        board.placeShip(battleShip);

        board.shot(new Coordinates(0, 0));
        board.shot(new Coordinates(1, 0));

        assertTrue(board.checkIfAnyShipLeft());
    }

    @Test
    public void testDisplayBoardWithFogOfWar() {
        BattleShip battleShip = new BattleShip(2, true, new Coordinates(0, 0));
        board.placeShip(battleShip);

        String display = board.displayBoard(true);
        assertNotNull(display);
        assertTrue(display.length() > 0);
    }

    @Test
    public void testDisplayBoardWithoutFogOfWar() {
        BattleShip battleShip = new BattleShip(2, true, new Coordinates(0, 0));
        board.placeShip(battleShip);

        String display = board.displayBoard(false);
        assertNotNull(display);
        assertTrue(display.contains("D")); // Symbol for length-2 ship
    }

    @Test
    public void testDisplayBoardShowsHits() throws CoordinatesAlreadyUsedException {
        BattleShip battleShip = new BattleShip(2, true, new Coordinates(0, 0));
        board.placeShip(battleShip);
        board.shot(new Coordinates(0, 0));

        String display = board.displayBoard(false);
        assertTrue(display.contains("X")); // Hit marker
    }

    @Test
    public void testDisplayBoardShowsMisses() throws CoordinatesAlreadyUsedException {
        board.shot(new Coordinates(5, 5));

        String display = board.displayBoard(false);
        assertTrue(display.contains("O")); // Miss marker
    }

    @Test
    public void testCheckIfAnyShipLeftWithNoShips() {
        assertFalse(board.checkIfAnyShipLeft());
    }

    @Test
    public void testMultipleShipsAllSunk() throws CoordinatesAlreadyUsedException {
        BattleShip battleShip1 = new BattleShip(2, true, new Coordinates(0, 0));
        BattleShip battleShip2 = new BattleShip(2, true, new Coordinates(0, 2));
        board.placeShip(battleShip1);
        board.placeShip(battleShip2);

        // Sink first ship
        board.shot(new Coordinates(0, 0));
        board.shot(new Coordinates(1, 0));

        // Sink second ship
        board.shot(new Coordinates(0, 2));
        board.shot(new Coordinates(1, 2));

        assertFalse(board.checkIfAnyShipLeft());
    }
}
