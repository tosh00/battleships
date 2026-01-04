import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name) {
        super(name);
    }

    public boolean promptSingleShip(int length) {
        try {
            System.out.println("Where to place the " + length + "-length ships? ");
            String input = scanner.nextLine();
            System.out.println("Input: " + input);
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            System.out.println(c);
            boolean direction = splited[2].equals("V");
            BattleShip battleShip = new BattleShip(length, direction, c);
            this.getBoard().placeShip(battleShip);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void placeShips(int[] ships) {
        for (int l : ships) {
            this.getBoard().displayBoard(false);
            boolean result = true;
            int maxRetryCount = 100;
            while (result && maxRetryCount-- > 0) {
                result = !this.promptSingleShip(l);
            }
//            try {
//                this.placeShipAtRandom(l);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
        }
    }

    public boolean promptPlaceMine(Board target) {
        try {
            System.out.print("Where to place the mine? ");
            String input = scanner.nextLine();
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            target.placeMine(new SeaMine(c));
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void promptPlaceMines(Board target) {
        boolean result = true;
        while (result) {
            result = !this.promptPlaceMine(target);
        }
    }

    public boolean promptPlaceRaft(int length) {
        try {
            System.out.print("Where to place the "+length+"x"+length+" raft? ");
            String input = scanner.nextLine();
            String[] splited = input.split(",");
            Coordinates c = new Coordinates(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            this.getBoard().placeShip(new Raft(c, length));
            return true;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void placeRafts(int[] rafts) {
        boolean result = true;
        for (int l : rafts) {

            while (result) {
                result = !this.promptPlaceRaft(l);
            }
        }
    }

    public void promptShot(Board target) {
        boolean result = false;
        while (!result) {
            try {
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
