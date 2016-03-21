public class Treasure extends Thing {
	public Treasure(Square sq, Sokoban g) {
		super(sq, g);
	}

	public String getImageName() {
		return "Treasure";
	}
}