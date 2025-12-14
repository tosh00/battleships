import java.util.Random;

public class AIPlayer extends Player {


    AIPlayer() {
        super("uBOT");
    }

    public void shootAtRandom(Board target) {
        Random rand = new Random();
        Coordinates randomPoint = new Coordinates(rand.nextInt(target.getWidth()), rand.nextInt(target.getHeight()));
        boolean result = false;
        while (!result) {
            try {
                target.shot(randomPoint);
                result = true;
            } catch (Exception e) {
                System.out.println("Bot didn't get it");
            }
        }
    }

    public void placeShips(int[] ships) throws Exception {
        for (int l : ships) {
            this.placeShipAtRandom(l);
        }
    }
}
