package ui;
import javax.swing.JFrame;

public class GameOfLife {

	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false); //Setting it to true messes things up. need to rezies to fix
		frame.createBoard(100, 100, true);
		
		long lastFrame = System.currentTimeMillis();
		while(true){
			long currentFrame = System.currentTimeMillis();
			float timeFrame = (float) ((currentFrame - lastFrame) / 1000.0);
			lastFrame = currentFrame;
			
			frame.next(timeFrame);
			
			frame.repaint();
			try{
				Thread.sleep(10);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		
		


	}

}
