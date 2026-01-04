import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    public void testPlayerCreation() {
        assertEquals("TestPlayer", player.playerName);
    }

    @Test
    public void testInitiateBoard() {
        player.initiateBoard(10, 10);
        Board board = player.getBoard();
        assertNotNull(board);
        assertEquals(10, board.getWidth());
        assertEquals(10, board.getHeight());
    }

    @RepeatedTest(100)
    public void testPlaceShipAtRandomSmallShip() throws Exception {
        player.initiateBoard(10, 10);
        assertDoesNotThrow(() -> player.placeShipAtRandom(2));
        assertTrue(player.getBoard().checkIfAnyShipLeft());
    }

    @RepeatedTest(100)
    public void testPlaceShipAtRandomMultipleShips() throws Exception {
        player.initiateBoard(10, 10);
        player.placeShipAtRandom(2);
        player.placeShipAtRandom(3);
        player.placeShipAtRandom(1);
        assertTrue(player.getBoard().checkIfAnyShipLeft());
    }

    @RepeatedTest(100)
    public void testPlaceShipAtRandomOnSmallBoard() {
        player.initiateBoard(3, 3);
        assertDoesNotThrow(() -> player.placeShipAtRandom(2));
    }

    @RepeatedTest(100)
    public void testPlaceShipAtRandomShipTooBigForBoard() {
        player.initiateBoard(2, 2);
        // Ship length 5 on a 2x2 board should fail
        assertThrows(Exception.class, () -> player.placeShipAtRandom(5));
    }

    @RepeatedTest(100)
    public void testPlaceShipAtRandomOnFullBoard() {
        player.initiateBoard(3, 1);
        // Fill the board
        assertDoesNotThrow(() -> player.placeShipAtRandom(3));
        // Try to place another ship - should fail as board is full
        assertThrows(Exception.class, () -> player.placeShipAtRandom(2));
    }

    @Test
    public void testGetBoardBeforeInitiation() {
        assertNull(player.getBoard());
    }

    @RepeatedTest(100)
    public void testPlaceMultipleShipsOnLargeBoard() throws Exception {
        player.initiateBoard(15, 15);
        player.placeShipAtRandom(5);
        player.placeShipAtRandom(4);
        player.placeShipAtRandom(3);
        player.placeShipAtRandom(2);
        player.placeShipAtRandom(1);
        assertTrue(player.getBoard().checkIfAnyShipLeft());
    }

    @Test
    public void testPlaceMineAtRandom() {
        Board board = new Board(10, 10);
        player.initiateBoard(10, 10);
        try {
            player.placeMineAtRandom(board);
        } catch (Exception ignored) {
        }
        assertEquals(1, board.getMines().size());
    }


    @Test
    public void testPlaceRaftAtRandom() {
        player.initiateBoard(10, 10);
        try {
            player.placeRaftAtRandom(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(1, player.getBoard().getShips().size());
    }
}
