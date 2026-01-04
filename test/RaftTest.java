import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RaftTest {

    @Test
    public void testRaftCreation() {
        // Arrange & Act
        Raft raft = new Raft(new Coordinates(0, 0), 2);

        // Assert
        assertEquals(2, raft.length);
        assertEquals('R', raft.getDisplaySymbol());
        assertFalse(raft.direction, "Raft should always have direction set to false");
    }

    @Test
    public void testRaftGetCoordinates2x2() {
        // Arrange
        Raft raft = new Raft(new Coordinates(3, 4), 2);

        // Act
        Coordinates[] coordinates = raft.getCoordinates();

        // Assert
        assertEquals(4, coordinates.length, "2x2 raft should have 4 coordinates");
        assertArrayEquals(new Coordinates[]{
                new Coordinates(3, 4),
                new Coordinates(3, 5),
                new Coordinates(4, 4),
                new Coordinates(4, 5)
        }, coordinates);
    }

    @Test
    public void testRaftGetCoordinates3x3() {
        // Arrange
        Raft raft = new Raft(new Coordinates(0, 0), 3);

        // Act
        Coordinates[] coordinates = raft.getCoordinates();

        // Assert
        assertEquals(9, coordinates.length, "3x3 raft should have 9 coordinates");
        assertArrayEquals(new Coordinates[]{
                new Coordinates(0, 0), new Coordinates(0, 1), new Coordinates(0, 2),
                new Coordinates(1, 0), new Coordinates(1, 1), new Coordinates(1, 2),
                new Coordinates(2, 0), new Coordinates(2, 1), new Coordinates(2, 2)
        }, coordinates);
    }

    @Test
    public void testRaftGetCoordinates1x1() {
        // Arrange
        Raft raft = new Raft(new Coordinates(5, 5), 1);

        // Act
        Coordinates[] coordinates = raft.getCoordinates();

        // Assert
        assertEquals(1, coordinates.length, "1x1 raft should have 1 coordinate");
        assertArrayEquals(new Coordinates[]{new Coordinates(5, 5)}, coordinates);
    }

    @Test
    public void testRaftPlacementOnBoard() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft = new Raft(new Coordinates(2, 2), 2);

        // Act
        board.placeShip(raft);

        // Assert
        assertEquals(1, board.getShips().size());
        assertTrue(board.getShips().contains(raft));
        assertTrue(board.checkIfAnyShipLeft());
    }

    @Test
    public void testRaftPlacementOutOfBounds() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft = new Raft(new Coordinates(9, 9), 2); // 2x2 raft at 9,9 goes out of bounds

        // Act & Assert
        assertThrows(CoordinatesOutOfBoundException.class, () -> board.placeShip(raft));
    }

    @Test
    public void testRaftPlacementAtBoardEdge() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft = new Raft(new Coordinates(8, 8), 2); // 2x2 raft fits exactly at edge

        // Act
        board.placeShip(raft);

        // Assert
        assertEquals(1, board.getShips().size());
        assertTrue(board.checkIfAnyShipLeft());
    }

    @Test
    public void testRaftCollisionWithExistingShip() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft1 = new Raft(new Coordinates(2, 2), 2);
        Raft raft2 = new Raft(new Coordinates(3, 3), 2); // Overlaps with raft1 at (3,3)

        // Act
        board.placeShip(raft1);

        // Assert
        assertThrows(CoordinatesAlreadyOcupiedException.class, () -> board.placeShip(raft2));
    }

    @Test
    public void testRaftCollisionWithLinearShip() {
        // Arrange
        Board board = new Board(10, 10);
        BattleShip ship = new BattleShip(3, true, new Coordinates(2, 2)); // Vertical ship at (2,2), (3,2), (4,2)
        Raft raft = new Raft(new Coordinates(1, 2), 2); // 2x2 raft overlaps at (2,2)

        // Act
        board.placeShip(ship);

        // Assert
        assertThrows(CoordinatesAlreadyOcupiedException.class, () -> board.placeShip(raft));
    }

    @Test
    public void testMultipleRaftsNoCollision() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft1 = new Raft(new Coordinates(0, 0), 2);
        Raft raft2 = new Raft(new Coordinates(5, 5), 2);
        Raft raft3 = new Raft(new Coordinates(0, 5), 2);

        // Act
        board.placeShip(raft1);
        board.placeShip(raft2);
        board.placeShip(raft3);

        // Assert
        assertEquals(3, board.getShips().size());
    }

    @Test
    public void testRaftSinking() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft = new Raft(new Coordinates(2, 2), 2);
        board.placeShip(raft);

        // Act - shoot all coordinates of the raft
        board.shot(new Coordinates(2, 2));
        board.shot(new Coordinates(2, 3));
        board.shot(new Coordinates(3, 2));
        board.shot(new Coordinates(3, 3));

        // Assert
        assertTrue(raft.sunk);
        assertFalse(board.checkIfAnyShipLeft());
    }

    @Test
    public void testRaftPartiallyHit() {
        // Arrange
        Board board = new Board(10, 10);
        Raft raft = new Raft(new Coordinates(2, 2), 2);
        board.placeShip(raft);

        // Act - shoot only some coordinates
        board.shot(new Coordinates(2, 2));
        board.shot(new Coordinates(2, 3));

        // Assert
        assertFalse(raft.sunk);
        assertTrue(board.checkIfAnyShipLeft());
    }

    @Test
    public void testRaftOnMine() {
        // Arrange
        Board board = new Board(10, 10);
        SeaMine mine = new SeaMine(new Coordinates(2, 2));
        board.placeMine(mine);
        Raft raft = new Raft(new Coordinates(2, 2), 2);

        // Act
        board.placeShip(raft);

        // Assert
        assertTrue(raft.sunk, "Raft should be sunk immediately when placed on a mine");
    }
}
