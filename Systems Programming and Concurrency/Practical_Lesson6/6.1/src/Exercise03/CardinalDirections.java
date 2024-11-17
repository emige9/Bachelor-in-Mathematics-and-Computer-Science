package Exercise03;

public enum CardinalDirections {
	North, South;

	public static CardinalDirections getRandomDirection() {
		return (Runway.rnd.nextBoolean())? North : South;
	}
}
