//---------------------------------------------------------------80 columns---|

/* comp285 Square.java
 * --------------
 */

class Square {
    protected Location location;
    protected Sokoban  game;
    protected Thing    contents;
  
    public Square(Location loc, Sokoban g){
	location = loc;
	game = g;
    }
	
    public Location getLocation(){
	return location;
    }

	
    public Thing getContents(){
	return contents;
    }

    
    public void drawSelf(){
	drawImage();
	drawContents();
    }
    
    public void drawContents(){
	if(contents != null)
	    contents.drawSelf(getLocation());
    }
	
	
    public String getImageName(){
	return "Empty";
    }

	
    public void drawImage(){
	game.drawAtLocation(getImageName(), getLocation());	    
    }
	
    public boolean canEnter(){	
	return (contents == null);
    }

    public boolean canPush(int direction){
	return ( ( contents != null ) && 
		 ( game.squareAt(location.adjacentLocation(direction)) != null) &&
		 game.squareAt(location.adjacentLocation(direction)).canEnter() );
    }

    public boolean pushContents(int direction){
	if( !canPush(direction) ) return false;
	Square neighbour = game.squareAt(location.adjacentLocation(direction));
	neighbour.addContents(contents);
	return true;
    }

    
    public boolean addContents(Thing c){
	if (canEnter()){
	    c.getSquare().removeContents(); 
	    // add to this square
	    contents = c;
	    // make sure thing knows where it is
	    c.setSquare(this); 
	    drawSelf();
	    return true;
	} 
	return false;
    }

    
  
    public  void removeContents(){
	contents = null;
	drawSelf();
    }
    
}



