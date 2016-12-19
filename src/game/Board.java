package game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board{
	private int width;
	private int height;
	private Cell [][] board;
	ArrayList<Cell> liveCells = new ArrayList<Cell>();
	
	/**
	 * Offsets for neighbors.
	 */
	private final int[][] NLAYOUT = { //Relative positions for neighbors
			{-1,-1}, {-1, 0}, {-1, 1},
			{ 0,-1},          { 0, 1},
			{ 1,-1}, { 1, 0}, { 1, 1}
	};
	
	public Board(int height, int width, boolean random){
		this.width = width;
		this.height = height;
		this.board = new Cell[this.height][this.width]; //Create new board
		
		//Initialize all cell objects
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				board[i][j] = new Cell(i,j);
				if(random){
					if(Math.random()*100 < 30){
						setAlive(i,j);
					}
				}
			}
		}
		//System.out.println("Initialized!");
	}
	public void drawBoard(){ //For console
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				System.out.print(board[i][j].getIsAlive() ? "#" : ".");
			}
			System.out.println();
		}
	}
	
	public void setAlive(int i, int j){
		if(!isOOB(i, j)){
			Cell cell = board[i][j];
			cell.giveLife();
			liveCells.add(cell);
		} else {
			System.out.println("Unable to setAlive: OutOfBound.");
		}
	}
	
	private void killCell(int i, int j){
		if(!isOOB(i, j)){
			Cell cell = board[i][j];
			cell.die();
			liveCells.remove(cell);
		} else {
			System.out.println("Unable to killCell: OutOfBound.");
		}
	}
	
	private ArrayList<Cell> getNeighbors(Cell cell){
		int i = cell.getI();
		int j = cell.getJ();
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(int[] offset : NLAYOUT){
			if(!isOOB(i + offset[0], j + offset[1])){ //If it is NOT OOB, add to array.
				neighbors.add(board[i + offset[0]][j + offset[1]]);
			}
		}
		return neighbors;
	}
	
	private boolean isOOB(int i, int j){ //Is the position out of bounds? Return true if it is out of bounds (Invalid coords)
		return (i < 0 || j < 0 || i >= height || j >= width);
	}
	
	private int surroundingLifeCount(Cell target){
		int lifeCount = 0; //Initial life count set to 0
		ArrayList<Cell> neighbors = getNeighbors(target); //Retrieves the list of neighbors or the target cell
		for(Cell neighbor : neighbors){
			if(neighbor.getIsAlive()){ //If the neighbor is alive. Increase count
				lifeCount++;
			}
		}
		return lifeCount;
	}
	/**
	 * Next iteration.
	 */
	public void next(){

		//Using Set to eliminate duplicates
		Set<Cell> potentialLife = new HashSet<Cell>();
		Set<Cell> potentialDeath = new HashSet<Cell>();

		for(Cell cell : this.liveCells){ //Looping through live cells
			int lifeCount = surroundingLifeCount(cell); //Number of live cell surrounding target live cell
			if(lifeCount <= 1 || lifeCount >= 4){ //Death by loneliness or overcrowding.
				potentialDeath.add(cell); //Add to potential death list.
			}
			
			//Checking for neighbors if it is possible to have a birth
			
			ArrayList<Cell> neighborsOfTarget = getNeighbors(cell); //Retrieve neighbors of target cell. It is possible to encounter dead cells
			for(Cell neighbors : neighborsOfTarget){
				int nLifeCount = surroundingLifeCount(neighbors);
				if(!neighbors.getIsAlive() && (nLifeCount == 3)){ //We are only interested in the dead cells
					potentialLife.add(neighbors);
				}
			}
		}
		//System.out.println("Scheduled to birth: " + Arrays.toString(potentialLife.toArray()));
		//System.out.println("Scheduled to death: " + Arrays.toString(potentialDeath.toArray()));
		
		
		//Kill and give birth
		for(Cell life : potentialLife){
			setAlive(life.getI(), life.getJ());
		}
		for(Cell death : potentialDeath){
			killCell(death.getI(), death.getJ());
		}

	}
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void getCellStatus(int i, int j){
		System.out.println(board[i][j].toString());
	}
	
	public void draw(Graphics graphics){ //Draw cells for board
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				board[i][j].draw(graphics);
			}
		}
	}
}
