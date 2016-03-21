public class Wall extends Square {
	public Wall(Location loc, Sokoban g) {
		super(loc, g);
	}

	public String getImageName() {
		return "Wall";
	}

	public boolean canEnter() {
		return false;
	}
}