import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AIPlayerTest {

    /**
     * Test class for the AIPlayer class and its shootAtRandom method.
     * The shootAtRandom method is responsible for simulating a randomized attack
     * on the opponent's board. It repeatedly generates random valid coordinates
     * and performs a shot until it successfully records that shot.
     */

    @RepeatedTest(100)
    public void testShootAtRandomWithEmptyBoard(){
        // Arrange
        AIPlayer aiPlayer = new AIPlayer();
        Board mockBoard = mock(Board.class);
        when(mockBoard.getWidth()).thenReturn(10);
        when(mockBoard.getHeight()).thenReturn(10);

        // Act
        aiPlayer.shootAtRandom(mockBoard);

        // Assert
        verify(mockBoard, times(1)).shot(any(Coordinates.class));
    }

    @RepeatedTest(100)
    public void testShootAtRandomWithRetryAfterException(){
        // Arrange
        AIPlayer aiPlayer = new AIPlayer();
        Board mockBoard = mock(Board.class);
        when(mockBoard.getWidth()).thenReturn(5);
        when(mockBoard.getHeight()).thenReturn(5);

        // Simulate first shot failing, followed by a successful shot
        doThrow(new CoordinatesAlreadyUsedException(new Coordinates(0, 0)))
                .doNothing()
                .when(mockBoard).shot(any(Coordinates.class));

        // Act
        aiPlayer.shootAtRandom(mockBoard);

        // Assert
        verify(mockBoard, times(2)).shot(any(Coordinates.class));
    }

    @RepeatedTest(100)
    public void testShootAtRandomBoardBoundaries() {
        // Arrange
        AIPlayer aiPlayer = new AIPlayer();
        Board mockBoard = mock(Board.class);
        when(mockBoard.getWidth()).thenReturn(8);
        when(mockBoard.getHeight()).thenReturn(8);

        // Act
        aiPlayer.shootAtRandom(mockBoard);

        // Assert
        verify(mockBoard, times(1)).shot(argThat(c ->
                c.x >= 0 && c.x < 8 && c.y >= 0 && c.y < 8
        ));
    }

    @RepeatedTest(100)
    public void testShootAtRandomHandlesMultipleExceptions() {
        // Arrange
        AIPlayer aiPlayer = new AIPlayer();
        Board mockBoard = mock(Board.class);
        when(mockBoard.getWidth()).thenReturn(6);
        when(mockBoard.getHeight()).thenReturn(6);

        // Simulate multiple exceptions before success
        doThrow(new CoordinatesAlreadyUsedException(new Coordinates(2, 2)))
                .doThrow(new CoordinatesOutOfBoundException(new Coordinates(5, 5)))
                .doNothing()
                .when(mockBoard).shot(any(Coordinates.class));

        // Act
        aiPlayer.shootAtRandom(mockBoard);

        // Assert
        verify(mockBoard, times(3)).shot(any(Coordinates.class));
    }

    @Test
    public void testPlaceRafts() {
        AIPlayer aiPlayer = new AIPlayer();
        aiPlayer.initiateBoard(10, 10);
        int[] rafts = {2, 2};
        try{
            aiPlayer.placeRafts(rafts);
        } catch(Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(2, aiPlayer.getBoard().getShips().size());
    }
}