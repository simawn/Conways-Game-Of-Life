package ui;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

import game.Board;

public class Frame extends JFrame{

	private static final long serialVersionUID = 8925367259178345608L;
	private Screen screen;
	private Board board;
	private final float REFRESH_TIME = 0.1f;
	private float timeFrameTotal;
	private class Screen extends JLabel{

		private static final long serialVersionUID = -1396143359720390744L;

		public void paintComponent(Graphics graphics){
			super.paintComponent(graphics);
			board.draw(graphics);
		}
	}
	
	public Frame(){
		setExtendedState(MAXIMIZED_BOTH); //Fill screen
	}
	
	public void repaint(){
		screen.repaint();
	}
	
	public void next(float timeFrame){
		this.timeFrameTotal += timeFrame;
		if(timeFrameTotal > this.REFRESH_TIME){
			board.next();
			this.timeFrameTotal = 0;
		}
	}
	public void createBoard(int i, int j, boolean random){
		this.board = new Board(i,j,random); //Create new board
		screen = new Screen();
		add(screen);
	}
}
