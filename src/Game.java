import java.util.Scanner;

public class Game {
    private final Player player;
    private final AIPlayer opponent;

    private final int[] ships = {5, 4, 3, 2, 2, 1, 1};
//    private final int[] ships = { 1, 1};

    Game(String playerName) {
        player = new Player(playerName);
        opponent = new AIPlayer();
    }

    Player getPlayer() {
        return player;
    }

    Player getOpponent() {
        return opponent;
    }

    private void placeAIShips() throws Exception {
        for (int l : ships) {
            opponent.placeShipAtRandom(l);
        }
    }

    private boolean promptUserSingleShip(int length) {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Where to place the " + length + "-length ships? ");
            String input = scanner.nextLine();
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            boolean direction = splited[2].equals("V");
            Ship ship = new Ship(length, direction, c);
            player.getBoard().placeShip(ship);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void promptUserShips() throws Exception {
//        for (int l : ships) {
//            this.player.getBoard().displayBoard(false);
//            while(!promptUserSingleShip(l));
//        }
        for (int l : ships) {
            player.placeShipAtRandom(l);
        }
    }

    public void displayBoards(){
        System.out.println(player.getBoard().displayBoard(false));
        System.out.println(opponent.getBoard().displayBoard(true));
    }

    public void startGame() throws Exception {
        player.initiateBoard(10, 10);
        opponent.initiateBoard(10, 10);
        placeAIShips();
        promptUserShips();

        while (player.getBoard().checkIfAnyShipLeft() && opponent.getBoard().checkIfAnyShipLeft()) {
            displayBoards();
            while(!promptShot());
        }

        if(!opponent.getBoard().checkIfAnyShipLeft()){
            System.out.println("You win!");
        } else {
            System.out.println("You lost!");
        }

    }

    private boolean promptShot (){
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Where to shoot captain? ");
            String input = scanner.nextLine();
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            opponent.getBoard().shot(c);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }


}
