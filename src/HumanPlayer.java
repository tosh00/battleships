import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    public boolean promptSingleShip(int length) {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Where to place the " + length + "-length ships? ");
            String input = scanner.nextLine();
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            System.out.println(c);
            boolean direction = splited[2].equals("V");
            Ship ship = new Ship(length, direction, c);
            this.getBoard().placeShip(ship);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void placeShips(int[] ships) throws Exception {
        for (int l : ships) {
            this.getBoard().displayBoard(false);
            while (!this.promptSingleShip(l));
//            this.placeShipAtRandom(l);
        }
    }

    public void promptShot(Board target) {
        boolean result = false;
        while (!result) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Where to shoot captain? ");
                String input = scanner.nextLine();
                String[] splited = input.split(",");
                Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
                target.shot(c);
                result = true;
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.out.println(e.getMessage());
            }
        }
    }
}
