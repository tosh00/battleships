import java.util.Random;

public class Player {
    private Board board;
    public String playerName;

    private final Random rand = new Random();


    Player(String playerName) {
        this.playerName = playerName;
    }

    public void initiateBoard(int width, int height) {
        System.out.println("Creating board of size " + width + "x" + height);
        this.board = new Board(width, height);
    }

    public Board getBoard() {
        return board;
    }

    public void placeShipAtRandom(int shipLength) throws Exception {
        for (int i = 0; i < 100; i++) {
            boolean direction = rand.nextBoolean();
            if (shipLength > this.getBoard().getWidth()) direction = false;
            if (shipLength > this.getBoard().getHeight()) direction = true;

            if (shipLength > this.getBoard().getWidth() && shipLength > this.getBoard().getHeight()) {
                throw new Exception("Ship length exceeds board dimensions");
            }

            int x = this.getBoard().getWidth() - (direction ? (shipLength - 1) : 0);
            int y = this.getBoard().getHeight() - (!direction ? (shipLength - 1) : 0);

            Coordinates c = new Coordinates(rand.nextInt(x), rand.nextInt(y));
            try {
                BattleShip s = new BattleShip(shipLength, direction, c);
                this.getBoard().placeShip(s);
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        throw new Exception("Couldn't place ship");
    }

    public void placeMineAtRandom(Board board) throws Exception{
        for(int i = 0; i < 100; i++){
            int x = rand.nextInt(this.getBoard().getWidth());
            int y = rand.nextInt(this.getBoard().getHeight());
            try{
                board.placeMine(new SeaMine(new Coordinates(x, y)));
                return;
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void placeRaftAtRandom(int shipLength) throws Exception {
        for (int i = 0; i < 100; i++) {
            if (shipLength > this.getBoard().getWidth() || shipLength > this.getBoard().getHeight()){
                throw new Exception("Ship length exceeds board dimensions");
            };



            int x = this.getBoard().getWidth() - ((shipLength - 1));
            int y = this.getBoard().getHeight() - ((shipLength - 1));

            Coordinates c = new Coordinates(rand.nextInt(x), rand.nextInt(y));
            try {
                Raft r = new Raft(c, shipLength);
                this.getBoard().placeShip(r);
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        throw new Exception("Couldn't place ship");
    }
}
