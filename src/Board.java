import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int width;
    private final int height;
    private final List<SeaObject> seaObjects = new ArrayList<SeaObject>();
    private  final List<Coordinates> shots = new ArrayList<Coordinates>();

    private final char[][] board;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean checkIfCoordinateInBounds(Coordinates c) {
        return c.x >= this.width || c.x < 0 || c.y >= this.height || c.y < 0;
    }

    private boolean checkIfCollides(Coordinates c) {
        for (SeaObject s : this.seaObjects) {
            for (Coordinates seaObjectCoordinate : s.getCoordinates()) {
                if (seaObjectCoordinate.equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void placeShip(Ship ship) {
        for (Coordinates c : ship.getCoordinates()) {
            if (checkIfCoordinateInBounds(c)) {
                throw new CoordinatesOutOfBoundException(c);
            }
            if (this.checkIfCollides(c)) {
                throw new CoordinatesAlreadyOcupiedException(c);
            }
        }
        this.seaObjects.add(ship);
    }

    public boolean placeMine(SeaMine seaMine) {
        if (checkIfCoordinateInBounds(seaMine.coordinates)) {
            throw new CoordinatesOutOfBoundException(seaMine.coordinates);
        } else {
            this.seaObjects.add(seaMine);
            return true;
        }
    }

    private void updateBoard(boolean fogOfWar) {
        // clear board
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                board[x][y] = ' ';
            }
        }
        if (!fogOfWar) {
            for (SeaObject s : this.seaObjects) {
                for (Coordinates c : s.getCoordinates()) {
                    board[c.x][c.y] = s.getDisplaySymbol();
                }
            }
        }
        for (Coordinates c : this.shots) {
            if (this.checkIfCollides(c)) {
                board[c.x][c.y] = 'X';
            } else {
                board[c.x][c.y] = 'O';
            }
        }
    }

    public String displayBoard(boolean fogOfWar) {
        this.updateBoard(fogOfWar);
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                result.append("[").append(board[x][y]).append("]");
            }
            result.append("\n");
        }

        return result.toString();
    }

    public void shot(Coordinates c) {
        for(Coordinates shot: this.shots) {
            if (shot.equals(c)) {
                throw new CoordinatesAlreadyUsedException(shot);
            }
        }
        shots.add(c);
    }

    public boolean checkIfAnyShipLeft(){
        for(SeaObject s: this.seaObjects){
            for(Coordinates c : s.getCoordinates()){
                if(!this.shots.contains(c)){
                    return true;
                }
            }
        }
        return false;
    }
}
