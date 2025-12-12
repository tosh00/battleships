public class CoordinatesAlreadyUsedException extends RuntimeException {
    public CoordinatesAlreadyUsedException(Coordinates coordinates) {
        super("Coordinates " + coordinates + " are already used");
    }
}
