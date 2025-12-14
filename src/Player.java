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
            if(shipLength > this.getBoard().getWidth()) direction = false;
            if(shipLength > this.getBoard().getHeight()) direction = true;

            if(shipLength > this.getBoard().getWidth() && shipLength > this.getBoard().getHeight()){
                throw new Exception("Ship length exceeds board dimensions");
            }

            int x = this.getBoard().getWidth() - (direction ? (shipLength - 1) : 0);
            int y = this.getBoard().getHeight() - (!direction ? (shipLength - 1) : 0);

            Coordinates c = new Coordinates(rand.nextInt(x), rand.nextInt(y));
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
