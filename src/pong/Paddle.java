package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddle {
	
	private int x, y;
	private int velocity = 0;
	private int speed = 10;
	private int width = 22, height = 85;
	private int score = 0;
	private Color color;
	private boolean left;
	
	//Initial properties for the paddle
	public Paddle(Color c, boolean left) { //boolean tells us whether it is the left paddle or not

		color = c;
		this.left = left;
		
		if(left)
			x = 0;//left paddle x value
		else
			x = Game.WIDTH - width;//right paddle x value
		
		y = Game.HEIGHT / 2 - height / 2;
	}
	

	public void addPoint() {
		score++; 
	}

	//draw paddle and score
	public void draw(Graphics g) {

		//draw paddle
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		//draw score
		int stringX; // x position of the string
		String scoreText = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);
		
		int stringWidth = g.getFontMetrics(font).stringWidth(scoreText) + 1;		
		int padding = 25; // distance btwn center line and each score
	
		if(left)
			stringX = Game.WIDTH / 2 - padding - stringWidth;
		else
			stringX = Game.WIDTH / 2 + padding;
		g.setFont(font);
		g.drawString(scoreText, stringX, 50);
	}

	//update position and collision tests
	public void update(Ball ball) {
		
		//update position
		y = Game.ensureRange(y += velocity, 0, Game.HEIGHT - height); // ensure that paddle does not leave screen
		
		int ballX = ball.getX();
		int ballY = ball.getY();
		
		//Collisions btwn paddles and ball
		
		if(left) {
			
			if(ballX <= width && ballY + ball.SIZE >= y && ballY <= y + height)
				ball.changeXDirection();
		}else {
			
			if(ballX + ball.SIZE >= Game.WIDTH - width && ballY + ball.SIZE >= y && ballY <= y + height)
				ball.changeXDirection();
		}
	}

	public void switchDirection(int direction) {
	
		velocity = speed * direction;
		
	}
	
	//stop moving the paddle
	public void stop() {
		velocity = 0;
	}
	
	
}
