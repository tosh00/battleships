import java.util.Random;

public class Player {
    private Board board;
    public String playerName;
    Player(String playerName) {
        this.playerName = playerName;
    }

    public void initiateBoard(int width, int height) {
        this.board = new Board(width, height);
    }

    public Board getBoard() {
        return board;
    }

    public void placeShipAtRandom(int shipLength) throws Exception {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            boolean direction = rand.nextBoolean();
            Coordinates c = new Coordinates(rand.nextInt(this.getBoard().getWidth() - (direction ? shipLength : 0)), rand.nextInt(this.getBoard().getHeight()- (!direction ? shipLength : 0)));
            try {
                Ship s = new Ship(shipLength, direction, c);
                this.getBoard().placeShip(s);
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        throw new Exception("Couldn't place ship");
    }

}
