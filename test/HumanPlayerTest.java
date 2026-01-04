import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HumanPlayerTest {

    /**
     * Test class for the HumanPlayer class and its promptSingleShip method.
     * The promptSingleShip method allows a player to manually place a ship
     * on the game board by providing input in the format "x,y,direction",
     * where "direction" can be "V" for vertical or "H" for horizontal.
     */

    @Test
    public void testPromptSingleShipValidInputHorizontal() {
        // Arrange
        String simulatedInput = "3,4,H\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {
            // Act
            boolean result = player.promptSingleShip(3);

            // Assert
            assertTrue(result, "Expected ship to be placed successfully with valid horizontal input.");
            assertTrue(player.getBoard().checkIfAnyShipLeft());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptSingleShipValidInputVertical() {
        // Arrange
        String simulatedInput = "5,6,V\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {
            // Act
            boolean result = player.promptSingleShip(4);

            // Assert
            assertTrue(result, "Expected ship to be placed successfully with valid vertical input.");
            assertTrue(player.getBoard().checkIfAnyShipLeft());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptSingleShipInvalidCoordinates() {
        // Arrange
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String simulatedInput = "20,30,H\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            // Act
            boolean result = player.promptSingleShip(3);

            // Assert
            assertFalse(result, "Expected ship placement to fail due to out-of-bound coordinates.");
            assertFalse(player.getBoard().checkIfAnyShipLeft());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptSingleShipCollision() {
        // Arrange
        String initialInput = "2,2,H\n2,2,H\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(initialInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        player.promptSingleShip(3); // Place the first ship.

        try {
            // Act
            boolean result = player.promptSingleShip(3);

            // Assert
            assertFalse(result, "Expected ship placement to fail due to collision with another ship.");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptSingleShipInvalidFormat() {
        // Arrange
        String simulatedInput = "Invalid,input\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {
            // Act
            boolean result = player.promptSingleShip(3);

            // Assert
            assertFalse(result, "Expected ship placement to fail due to invalid input format.");
            assertFalse(player.getBoard().checkIfAnyShipLeft());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptSingleShipOutOfBoundsShipPlacement() {
        // Arrange
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String simulatedInput = "9,9,H\n"; // Ship will be placed out of bounds.
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            // Act
            boolean result = player.promptSingleShip(3);

            // Assert
            assertFalse(result, "Expected ship placement to fail due to out-of-bounds placement.");
            assertFalse(player.getBoard().checkIfAnyShipLeft());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPlaceShipsValidInputs() {
        // Arrange
        int[] ships = {3, 4, 2};
        String simulatedInput = "0,0,H\n3,3,V\n5,5,H\n"; // Valid inputs for placing all ships
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {
            // Act
            player.placeShips(ships);

            // Assert
            assertTrue(player.getBoard().checkIfAnyShipLeft(),
                    "Expected ships to be placed successfully.");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPlaceShipsWithInvalidInputRetry() {
        // Arrange
        int[] ships = {4};
        String simulatedInput = "-10,-10,V\n20,20,H\n5,5,H\n"; // Retry with valid input
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {

            // Act
            player.placeShips(ships);

            // Assert
            assertTrue(player.getBoard().checkIfAnyShipLeft(),
                    "Expected ship to be placed after retry with valid input.");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPlaceShipsPreventCollisions() {
        // Arrange
        int[] ships = {3, 3};
        String simulatedInput = "1,1,H\n1,1,H\n4,4,V\n"; // Second placement collides, retries
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        try {
            // Act
            player.placeShips(ships);

            // Assert
            assertTrue(player.getBoard().checkIfAnyShipLeft(),
                    "Expected both ships to be placed, avoiding collision.");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testPromptShot() {
        Board mockBoard = mock(Board.class);

        String simulatedInput = "0,0";

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        player.promptShot(mockBoard);

        verify(mockBoard, times(1)).shot(new Coordinates(0, 0));
    }

    @Test
    public void testPromptMines() {
        Board mockBoard = new Board(10, 10);

        String simulatedInput = "0,0\n1,1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        player.promptPlaceMines(mockBoard);
        player.promptPlaceMines(mockBoard);

        assertEquals(2, mockBoard.getMines().size());
    }

    @Test
    public void testPromptRaft() {

        String simulatedInput = "0,0\n1,1\n";
        int[] rafts = {2, 2};
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);

        player.placeRafts(rafts);

        assertEquals(1, player.getBoard().getShips().size());
    }
}