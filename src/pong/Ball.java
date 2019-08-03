package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	public static final int SIZE = 16; //Size of ball as a square
	
	private int x,y; //position of the top left corner of square
	private int xVelocity, yVelocity; //Have value either 1 or -1 to have ball go up or down
	private int speed = 7; //Speed of Ball
	
	public Ball() {
	
		reset();
		
	}
	
	//Setup initial position and velocity
	private void reset() {
		
		//initial position
		
		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;
		
		//initial velocities
		xVelocity = Game.sign(Math.random() * 2.0 - 1);
		yVelocity = Game.sign(Math.random() * 2.0 - 1);
	}
	//get x
	public int getX() {
		return x;
	}
	//get y
	public int getY() {
		return y;
	}

	//change direction of ball(left/right)
	public void changeXDirection() {
		xVelocity *= -1;
	}
	//change direction of ball(up/down)
	public void changeYDirection() {
		yVelocity *= -1;
	}
	
	//Draw ball using graphics object
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, SIZE, SIZE);
	}

	//update position and collision tests
	public void update(Paddle p1, Paddle p2) {
		
		//Update position
		x += xVelocity * speed;
		y += yVelocity * speed;
		
		//collisions with ceiling and floor
		if(y + SIZE >= Game.HEIGHT || y <= 0) 
			changeYDirection();
		
		//collision with right wall
		if(x + SIZE >= Game.WIDTH) {
			p1.addPoint();
			reset();
		}
		//collision with left wall
		if(x <= 0) {
			p2.addPoint();
			reset();
		}
	}
	
	
}
