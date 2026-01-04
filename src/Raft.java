public class Raft extends BattleShip {
    Raft(Coordinates coordinates, int length) {
        super(length,false , coordinates);
        this.setDisplaySymbol('R');
    }

    public Coordinates[] getCoordinates() {
        Coordinates[] allCoordinates = new Coordinates[this.length*this.length];
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.length; j++) {
                allCoordinates[i*this.length+j] = new Coordinates(this.coordinates.x + i, this.coordinates.y + j);
            }
        }
        return allCoordinates;
    }

}
