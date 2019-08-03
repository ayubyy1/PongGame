package pong;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

	
 	private static final long serialVersionUID = -7539960646630426242L;

 	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9/16; //16:9 Aspect ratio
	
	public boolean running = false; // True if the game is running
	private Thread gameThread;  // Thread where the game is updated and drawn
	
	
	private Ball ball; //Creation of ball object
	
	private Paddle paddle1; //left paddle
	private Paddle paddle2; //right paddle
	
	public Game(){
		canvasSetup();
		
		initialize();
		
		new Window("Pong", this); 
		
		this.addKeyListener(new KeyInput(paddle1, paddle2));
		this.setFocusable(true);
		
		
	}

	private void initialize() {
		//Initialize ball
		ball = new Ball();
		
		//Initialize paddles
		paddle1 = new Paddle(Color.green, true);
		paddle2 = new Paddle(Color.green, false);
	}

	private void canvasSetup() {
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));		
		
	}

	//Game Loop
	@Override
	public void run() {
		this.requestFocus();
		
		//game timer
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
			}
			if(running) draw();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void draw() {
		//initialize drawing tools
		
		BufferStrategy br = this.getBufferStrategy(); //extract buffer aka a blank canvas that we can draw on
		
		if (br == null){ //If it doesn't exists, then create it
			this.createBufferStrategy(3); //Creating a triple buffer, triple buffer improves the performance
			return;
		}
		
		Graphics g = br.getDrawGraphics(); //Extract drawing tools from the buffers
		/*
		 * Graphics class is used to draw shapes and pictures
		 * It is a tool used to draw on a buffer
		 * 
		 * */
		
		//draw background
		drawBackground(g);
		
		//draw ball
		ball.draw(g);
		
		//draw paddles and score
		paddle1.draw(g);
		paddle2.draw(g);
		
		//dispose or display on screen
		g.dispose();
		br.show();
	}

	private void drawBackground(Graphics g) {
		//black background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//dotted line
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		
		//How to make a dotted line
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {10}, 0); //Dotted line 10 pixels on and 10 off       
		g2d.setStroke(dashed);
		g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT); // Adjusts position of line
	
	}

	private void update() {
		//update ball
		ball.update(paddle1, paddle2);
		
		//update paddles
		paddle1.update(ball);
		paddle2.update(ball);
	}

	//Start the thread and the game
	public void start() {
		gameThread = new Thread(this); //Since Game implements runnable we can pass it to thread constructor
		
		gameThread.start(); //start thread
		running = true;
	}
	
	//Stop thread and the game
	public void stop() {
		try {
			gameThread.join(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int sign(double x) { //Returns the sign either 1 or -1 of the input
		if(x <= 0)
			return -1;
		return 1;
	}
	
	
	public static int ensureRange(int val, int min, int max) {
		return Math.min(Math.max(val, min), max);
	}
	
	//Start of the game
	public static void main(String[] args) {
		new Game();
	}

}
