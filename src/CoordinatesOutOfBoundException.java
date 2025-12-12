public class CoordinatesOutOfBoundException extends RuntimeException {
    public CoordinatesOutOfBoundException(Coordinates coordinates) {
        super("Coordinates "+coordinates+" out of bounds");
    }
}
