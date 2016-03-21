
public class Space extends Square {
	public Space(Location loc, Sokoban g) {
		super(loc, g);
	}

	public String getImageName() {
		return "Space";
	}

	public boolean canEnter() {
		return false;
	}
}