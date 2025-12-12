public class SeaObject {
    public final Coordinates coordinates;
    private char displaySymbol;
    SeaObject(Coordinates coordinates,  char displaySymbol) {
        this.coordinates = coordinates;
        this.displaySymbol = displaySymbol;
    }
    SeaObject(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.displaySymbol = '?';
    }

    public void setDisplaySymbol(char displaySymbol) {
        this.displaySymbol = displaySymbol;
    }
    public char getDisplaySymbol() {
        return displaySymbol;
    }

    public Coordinates[] getCoordinates() {
        return new Coordinates[]{coordinates};
    }
}
