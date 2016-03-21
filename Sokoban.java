//---------------------------------------------------------------80 columns---|

/* comp285 Sokoban class
*/

import java.io.*;
import java.util.*;

public class Sokoban {
	private static int numLevels = 50;
	public static final boolean VERBOSE = false;

	public static final int maximumAllowedUndos = 1;

	public static void main(String args[])
	{
		Sokoban game = new Sokoban(new Display("Sokoban in Java!"));
		game.play();
	}

	private Search search;
	private Vector<Move> moveHistory = new Vector<Move>();
	private int totalMoves = 0;
	private Display display;
	private Man man;
	private int vacantSlots = 0;

	private boolean levelOver;

	private int level = 0;

	private Grid squares;

	public Sokoban(Display display)
	{
		this.display = display;
		man = null;
		search = new Search(this);
	}

	public void addMove(Move move)
	{
		moveHistory.add(move);
		if (moveHistory.size() > maximumAllowedUndos || maximumAllowedUndos == -1)
		{
			moveHistory.remove(0);
		}
		totalMoves++;

		if (Sokoban.VERBOSE)
		{
			System.out.println("Total moves: " + totalMoves);
		}
	}

	public void undoLastMove()
	{
		if (moveHistory.size() > 0)
		{
			man.doMove(moveHistory.remove(moveHistory.size() - 1));
		}

		totalMoves++;
	}

	public int vacantSlots()
	{
		return this.vacantSlots;
	}

	public void decrementSlots() {
		vacantSlots--;
		if (vacantSlots == 0)
		{
			levelOver = true;
			display.drawStatusMessage("Congratulations on completing " + level++);
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException ie)
			{
			}
		}
	}

	public void drawAtLocation(String name, Location loc) {
		display.drawAtLocation(name, loc);
	}

	public boolean inBounds(Location location) {
		return squares.inBounds(location);
	}

	public void incrementSlots() {
		vacantSlots++;
	}

	public void play() {
		while (level < numLevels) {
			levelOver = false;
			vacantSlots = 0;
			readLevelFileForLevel(level);
			while (!levelOver) {
				processSingleCommand(display.getCommandFromUser());
			}
		}

		quit();
	}

	private void processSingleCommand(Command cmd)
	{
		switch (cmd.getType())
		{
			case Command.Quit:
				quit();
				return;
			case Command.Next:
				levelOver = true;
				level++;
				break;
			case Command.Replay:
				levelOver = true;
				break;
			case Command.Jump:
				Vector<Move> moves = search.getMovesForLocation(man.getLocation(), cmd.getGoal());
				while (moves.size() > 0)
				{
					man.doMove(moves.remove(0));
				}
				break;
			case Command.Directional:
				man.move(cmd.getMove());
				break;
			case Command.Undo:
				undoLastMove();
				break;
			case Command.Error:
				break;
			default:
				break;
		}
	}

	public void quit()
	{
		System.exit(1);
	}


	private void readLevelFileForLevel(int level)
	{
		String levelDirectory = System.getProperty("user.dir") + java.io.File.separator + "Levels" + java.io.File.separator;
		String filename = levelDirectory + "Level" + level + ".data";

		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader(filename));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Cannot find file \"" + filename + "\".");
			return;
		}

		try
		{
			int numRows = Integer.valueOf(in.readLine().trim()).intValue();
			int numCols = Integer.valueOf(in.readLine().trim()).intValue();
			squares = new Grid(numRows, numCols);
			display.configureForSize(numRows, numCols);
			display.drawStatusMessage("Loading level " + level + "...");
			for (int row = 0; row < numRows; row++)
			{
				for (int col = 0; col < numCols; col++)
				{
					readOneSquare(new Location(row, col), (char) in.read());
				}

			
				in.readLine();
			}
			display.drawStatusMessage("Loaded level " + level + "...");
			display.setVisible(true);
			display.grabFocus();
			moveHistory.clear();
			totalMoves = 0;
		}
		catch (IOException e)
		{
			System.out.println("File improperly formatted, quitting");
			return;
		}
	}


	private void readOneSquare(Location location, char ch) {
		Square square = null;

		switch (ch) {
		case '#':
			square = new Wall(location, this);
			break;
		case ' ':
			square = new Square(location, this);
			break;
		case '$':
			square = new Square(location, this);
			square.addContents(new Treasure(square, this));
			break;
		case '*':
			square = new Goal(location, this);
			square.addContents(new Treasure(square, this));
			incrementSlots();
			break;
		case '.':
			square = new Goal(location, this);
			incrementSlots();
			break;
		case '@': {
			square = new Square(location, this);
			man = new Man(square, this);
			square.addContents(man);
			break;
		}
		case '!':
			square = new Space(location, this);
			break;
		}

		if (square == null) {
			System.out.println("problem interpreting character " + ch);
			return;
		}

		squares.setElementAt(location, square);
		square.drawSelf();
	}

	public Square squareAt(Location location) {
		return (squares.inBounds(location) ? ((Square) squares
				.elementAt(location)) : null);
	}
}