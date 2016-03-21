abstract class Thing {
	protected Square square;
	protected Sokoban game;

	public Thing(Square sq, Sokoban g) {
		square = sq;
		game = g;
	}

	public void drawImage(Location loc) {
		game.drawAtLocation(getImageName(), loc);
	}

	public void drawSelf(Location loc) {
		drawImage(loc);
	}

	abstract public String getImageName();

	public Location getLocation() {
		return square.getLocation();
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square sq) {
		square = sq;
	}
}