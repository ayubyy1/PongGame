package pong;

import javax.swing.JFrame;

public class Window {

	public Window(String title, Game game) {
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit game upon closing window
		frame.setResizable(false); //Doesn't allow resizing of window
		frame.add(game); //Game is going to be a component of the JFrame
		frame.pack();
		frame.setLocationRelativeTo(null); //Game will appear centered
		frame.setVisible(true);
		
		game.start();	
	
	
	}	
}
