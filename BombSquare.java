import javax.lang.model.util.ElementScanner6;

/**
 * This class provides functionality for a MineSweeper type Game
 * The class provides logic for clicked squares made via the GameSquare superclass
 * If a square is clicked by the user a method is invoked to allow the game to play
 */

public class BombSquare extends GameSquare
{
    /** Object reference to the GameBoard this square is part of. **/
    private GameBoard board; 

    /** True if this squre contains a bomb. False otherwise. **/      
    public boolean hasBomb;    

    /** True if this squre contains a flag. False otherwise. **/  
    private boolean hasFlagged = false;    

    /** Stores no. bombs adjacent to clicked square **/
    private int adjacentCount = 0;

    /** used to create an instance of 'Gamesquare' of type 'BombSquare' to invoke the check of hasBomb using Polymorphism **/
    public GameSquare check;

    /** Stores if square has been 'discovered' via a click or recursion of zeros **/
    public boolean isRevealed = false;

    int xWidth;
    int yWidth;

    /** Constant storing probabiity of a certain square containing a mine **/
	public static final int MINE_PROBABILITY = 10;

    /**
	 * Creates a new BombSquare on the GameBoard
     * 
	 * @param x the x co-ordinate of the clicked square on the game board.
	 * @param y the y co-ordinate of the clicked square on the game board. 
     * @param board the board the BombSquare is on.
     */
    public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png");

        this.board = board;
        this.hasBomb = ((int) (Math.random() * MINE_PROBABILITY)) == 0;

    }    

    /**
	* Method invoked if the user Left Clicks on a square
	*/
    public void leftClicked() 
    {
        if (isRevealed != true) {
            isRevealed = true;
            if (this.hasBomb == true) {
                setImage("images/bomb.png");
            } else {
                squareReveal(getXLocation(),getYLocation());
            }
        }
    }

    /**
	* Method invoked if the user Right Clicks on a square
	*/
    public void rightClicked() 
    {
        if (isRevealed != true) {
            if (hasFlagged == false) {
                setImage("images/flag.png");
                hasFlagged = true;
            } else {
                setImage("images/blank.png"); 
                hasFlagged = false; 
            }
        }
    }

    /**
	* Method invoked if a Clicked square is not a bomb
	* Provides logic to display adjacent mines and recursion if there are zero adjacent squares
    * 
	* @param x the x co-ordinate of the clicked square on the game board.
	* @param y the y co-ordinate of the clicked square on the game board. 
    */
    public void squareReveal(int x, int y) 
     {  
        adjacentCount=0;
        
        while (board.getSquareAt(xWidth,0) != null) { //Gets XWidth using null return from getSquareAt
            xWidth +=1;  
        }
        while (board.getSquareAt(0, yWidth) != null) {//Gets YWidth using null return from getSquareAt
            yWidth +=1;  
        }

        for(int i=(x==0 ? x : x-1);i<=(x==xWidth-1 ? x : x+1);i++) {
            for(int j=(y==0 ? y : y-1);j<=(y==yWidth-1 ? y : y+1);j++) {      
                BombSquare check = (BombSquare) board.getSquareAt(i,j);
                if (check.hasBomb == true)
                    adjacentCount++;
            }   
        }

        (board.getSquareAt(x,y)).setImage("images/"+adjacentCount+".png");
        ((BombSquare) board.getSquareAt(x,y)).isRevealed = true;

        if (this.adjacentCount == 0) {
            if (x-1>=0)
                if ((((BombSquare) board.getSquareAt(x-1,y)).isRevealed()) == false)
                    squareReveal(x-1,y);
            if (x+1<=xWidth-1)
                if ((((BombSquare) board.getSquareAt(x+1,y)).isRevealed()) == false)
                    squareReveal(x+1,y);
            if (y-1>=0)
                if ((((BombSquare) board.getSquareAt(x,y-1)).isRevealed()) == false)
                    squareReveal(x,y-1);
            if (y+1<=yWidth-1)
                if ((((BombSquare) board.getSquareAt(x,y+1)).isRevealed()) == false)
                    squareReveal(x,y+1);
        }
    }

    /**
    * Determines the status of the clicked square on the board
    * @return Status of the cell, Revealed or not Revealed
    */
    public boolean isRevealed() 
    {
        return this.isRevealed;
    }


}
