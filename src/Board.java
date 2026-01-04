import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int width;
    private final int height;
    private final List<SeaObject> seaObjects = new ArrayList<>();
    private final List<Coordinates> shots = new ArrayList<>();

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

    public List<BattleShip> getShips() {
        return seaObjects.stream().filter(s -> s instanceof BattleShip).map(s -> (BattleShip) s).toList();
    }

    public List<SeaMine> getMines() {
        return seaObjects.stream().filter(s -> s instanceof SeaMine).map(s -> (SeaMine) s).toList();
    }

    private boolean checkIfCoordinateInBounds(Coordinates c) {
        return c.x >= this.width || c.x < 0 || c.y >= this.height || c.y < 0;
    }

    private boolean checkIfCollides(Coordinates c) {
        for (SeaObject s : this.getShips()) {
            for (Coordinates seaObjectCoordinate : s.getCoordinates()) {
                if (seaObjectCoordinate.equals(c) && !(s.getClass() == SeaMine.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfCollidesWithMine(BattleShip battleShip){
        for (Coordinates c : battleShip.getCoordinates()) {
            for (SeaObject s : this.seaObjects) {
                if (s.getClass() == SeaMine.class && (Arrays.asList(s.getCoordinates()).contains(c))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void placeShip(BattleShip battleShip) {
        for (Coordinates c : battleShip.getCoordinates()) {
            if (checkIfCoordinateInBounds(c)) {
                throw new CoordinatesOutOfBoundException(c);
            }
            if (this.checkIfCollides(c)) {
                throw new CoordinatesAlreadyOcupiedException(c);
            }
        }
        this.seaObjects.add(battleShip);
        if(checkIfCollidesWithMine(battleShip)){
            battleShip.sunk = true;
            this.shots.addAll(Arrays.asList(battleShip.getCoordinates()));
        }
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
            for (SeaObject s : this.getShips()) {
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

    private boolean checkIfCoordinatesShooted(Coordinates c) {
        for (Coordinates s : shots) {
            if (s == c) {
                return true;
            }
        }
        return false;
    }

    public String displayBoard(boolean fogOfWar) {
        this.updateBoard(fogOfWar);
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (checkIfCoordinatesShooted(new Coordinates(x, y))) {
                    result.append("{").append(board[x][y]).append("}");
                    System.out.println("jest");
                } else {
                    result.append("[").append(board[x][y]).append("]");
                }
            }
            result.append("\n");
        }

        return result.toString();
    }

    public void shot(Coordinates c) throws CoordinatesAlreadyUsedException {
        for (Coordinates shot : this.shots) {
            if (shot.equals(c)) {
                throw new CoordinatesAlreadyUsedException(shot);
            }
        }
        shots.add(c);
        if(checkShipsSunk()){
            System.out.println("You sunk a ship!");
        }
    }

    private boolean checkShipsSunk() {
        for (SeaObject s : this.getShips()) {
            if (this.shots.containsAll(List.of(s.getCoordinates()))) {
                if (s instanceof BattleShip) {
                    ((BattleShip) s).sunk = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfAnyShipLeft() {
        for (SeaObject s : this.getShips()) {
            for (Coordinates c : s.getCoordinates()) {
                if (!this.shots.contains(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}
