package game;
import java.awt.Color;
import java.awt.Graphics;

public class Cell{
	private int i;
	private int j;
	private boolean isAlive;
	private final int SIZE = 10; //Size of a cell to display
	
	public Cell(int i, int j){
		this.i = i;
		this.j = j;
		this.isAlive = false;
		//System.out.println("(" + i + ", " + j +") is created");
	}
	public boolean getIsAlive(){
		return isAlive;
	}
	
	void giveLife(){
		isAlive = true;
		//System.out.println("(" + i + ", " + j +") is given life");
	}
	
	void die(){
		isAlive = false;
		//System.out.println("(" + i + ", " + j +") is killed");
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ(){
		return this.j;
	}
	
	public String toString(){
		return "Cell (" + i + ", " + j + "). Alive? " + isAlive;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		return (1 * prime + (isAlive? 1231 : 1237) + i*10 + j*2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (i != other.i)
			return false;
		if (isAlive != other.isAlive)
			return false;
		if (j != other.j)
			return false;
		return true;
	}
	
	public void draw(Graphics graphics){ //Draw single cell
		if(isAlive){
			graphics.setColor(Color.BLACK);
			graphics.fillRect(i * this.SIZE, j * this.SIZE, this.SIZE, this.SIZE);
		} else {
			graphics.setColor(Color.WHITE);
			graphics.fillRect(i * this.SIZE, j * this.SIZE, this.SIZE - 1 , this.SIZE - 1);
		}
	}
}
