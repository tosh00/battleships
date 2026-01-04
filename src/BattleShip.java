public class BattleShip extends SeaObject {
    public final int length;
    public final boolean direction;
    public boolean sunk = false;

    public static char findSymbol(int length) {
        char symbol = ' ';
        switch (length) {
            case 1:
                symbol = 'S';
                break;
            case 2:
                symbol = 'D';
                break;
            case 3:
                symbol = 'C';
                break;
            case 4:
                symbol = 'B';
                break;
            case 5:
                symbol = 'A';
                break;
            default:
                symbol = '?';
        }
        return symbol;
    }

    BattleShip(int length, boolean direction, Coordinates coordinates) {
        super(coordinates);
        this.length = length;
        this.direction = direction;
        this.setDisplaySymbol(findSymbol(length));
    }

    @Override
    public Coordinates[] getCoordinates() {
        Coordinates[] allCoordinates = new Coordinates[length];
        if (direction) {
            for (int i = 0; i < length; i++) {
                allCoordinates[i] = new Coordinates(this.coordinates.x + i, this.coordinates.y);
            }
        } else {
            for (int i = 0; i < length; i++) {
                allCoordinates[i] = new Coordinates(this.coordinates.x, this.coordinates.y + i);
            }        }
        return allCoordinates;
    }
}
