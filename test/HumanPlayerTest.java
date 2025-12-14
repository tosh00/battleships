import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String simulatedInput = "3,4,H\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String simulatedInput = "5,6,V\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String initialInput = "2,2,H\n";
        String simulatedInput = "2,2,H\n"; // This ship will collide with the first one.
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(initialInput.getBytes()));
        player.promptSingleShip(3); // Place the first ship.
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
        HumanPlayer player = new HumanPlayer("TestPlayer");
        player.initiateBoard(10, 10);
        String simulatedInput = "invalid,input\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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
}