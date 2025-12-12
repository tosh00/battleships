public class CoordinatesAlreadyOcupiedException extends RuntimeException {
    public CoordinatesAlreadyOcupiedException(Coordinates coordinates) {
        super("Coordinates "+coordinates+" are already occupied");
    }
}
