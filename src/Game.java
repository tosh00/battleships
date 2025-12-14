public class Game {
    private final HumanPlayer player;
    private final AIPlayer opponent;

    private int boardSize = 10;
    private final int[] ships = {5, 4, 3, 2, 2, 1, 1};

    Game(String playerName) {
        player = new HumanPlayer(playerName);
        opponent = new AIPlayer();
    }

    Game(String playerName, int boardSize) {
        this.boardSize = boardSize;
        player = new HumanPlayer(playerName);
        opponent = new AIPlayer();
    }

    public void displayBoards() {
        System.out.println(player.getBoard().displayBoard(false));
        System.out.println(opponent.getBoard().displayBoard(true));
    }

    public void startGame() throws Exception {
        player.initiateBoard(this.boardSize, this.boardSize);
        opponent.initiateBoard(this.boardSize, this.boardSize);
        opponent.placeShips(ships);
        player.placeShips(ships);

        while (player.getBoard().checkIfAnyShipLeft() && opponent.getBoard().checkIfAnyShipLeft()) {
            displayBoards();
            player.promptShot(this.opponent.getBoard());
            opponent.shootAtRandom(this.player.getBoard());
        }

        if (!opponent.getBoard().checkIfAnyShipLeft()) {
            System.out.println("You win!");
        } else {
            System.out.println("You lost!");
        }

    }
}
