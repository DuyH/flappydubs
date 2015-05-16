package flappydubs;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class FlappyDubs extends GraphicsProgram {

	// Class constants:
	private static int HEIGHT = 400; // in px
	private static int WIDTH = 400; // in px
	private static int PIPE_WIDTH = 100;
	private static int PIPE_HEIGHT = 150;
	private static int PIPE_SPACING = 150;
	private static int PIPE_GAP = 150;
	private static int WAIT = 30; // ms
	private static int MOVE = 3; // Animation movement

	// Global fields:
	Random random = new Random();
	GImage dubs; // global bc we call him in keyPressed()
	boolean gameOver = false;

	public void init() {

		setBackground(Color.CYAN);
		setSize(WIDTH, HEIGHT);
		addKeyListeners();
	}

	public void run() {

		// Create the pipes

		// Top pipe
		GRect pipeTop = new GRect(250, -PIPE_HEIGHT, PIPE_WIDTH,
				PIPE_HEIGHT * 2);
		pipeTop.setColor(Color.GREEN);
		pipeTop.setFilled(true);
		add(pipeTop);

		// Bottom pipe
		GRect bottomPipe = new GRect(pipeTop.getX(), pipeTop.getY()
				+ pipeTop.getHeight() + PIPE_GAP, PIPE_WIDTH, HEIGHT);
		bottomPipe.setColor(Color.GREEN);
		bottomPipe.setFilled(true);
		add(bottomPipe);

		// Second top pipe
		GRect pipeTop2 = new GRect(pipeTop.getX() + PIPE_WIDTH + PIPE_SPACING,
				-PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT * 2);
		pipeTop2.setColor(Color.GREEN);
		pipeTop2.setFilled(true);
		add(pipeTop2);

		// Second bottom pipe
		GRect bottomPipe2 = new GRect(pipeTop2.getX(), pipeTop2.getY()
				+ pipeTop2.getHeight() + PIPE_GAP, PIPE_WIDTH, HEIGHT);
		bottomPipe2.setColor(Color.GREEN);
		bottomPipe2.setFilled(true);
		add(bottomPipe2);

		// Add the "bird"
		dubs = new GImage("dubs.png", 50, 150); // x,y start
		dubs.scale(0.5); // Half-size
		add(dubs);

		// Wait for the user to click (start game)
		waitForClick();

		// Start the game loop
		while (true) {

			// Make pipes move left
			pipeTop.move(-MOVE, 0);
			bottomPipe.move(-MOVE, 0);
			pipeTop2.move(-MOVE, 0);
			bottomPipe2.move(-MOVE, 0);

			// Once pipe runs off screen, reset it to right edge
			// Also randomize new position

			// First pair of pipes
			if (pipeTop.getX() + PIPE_WIDTH <= 0) {
				// Randomize the new height
				int pipeHeight = random.nextInt(PIPE_GAP * 2);
				pipeHeight -= PIPE_GAP; // now make negative range too
				pipeTop.setLocation(WIDTH, -PIPE_HEIGHT);
				pipeTop.move(0, pipeHeight);
				bottomPipe.setLocation(WIDTH,
						pipeTop.getY() + pipeTop.getHeight() + PIPE_GAP);

			}

			// Second pair of pipes
			if (pipeTop2.getX() + PIPE_WIDTH <= 0) {
				// Randomize the new height
				int pipeHeight = random.nextInt(PIPE_GAP * 2);
				pipeHeight -= PIPE_GAP; // now make negative range too
				pipeTop2.setLocation(WIDTH, -PIPE_HEIGHT);
				pipeTop2.move(0, pipeHeight);
				bottomPipe2.setLocation(WIDTH,
						pipeTop2.getY() + pipeTop2.getHeight() + PIPE_GAP);
			}

			// Implement gravity
			dubs.move(0, 2);

			// Check for collisions!!!

			// Check for collisions with first pair of pipes
			if (dubs.getBounds().intersects(pipeTop.getBounds())
					|| dubs.getBounds().intersects(bottomPipe.getBounds())) {
				break;
			}

			// Collision with second pair of pipes
			if (dubs.getBounds().intersects(pipeTop2.getBounds())
					|| dubs.getBounds().intersects(bottomPipe2.getBounds())) {
				break;
			}

			// Collisions with ceiling and floor
			if (dubs.getY() < 0 || dubs.getY() + dubs.getHeight() > HEIGHT) {
				break;
			}

			pause(WAIT);
		}

		// Game over stuff
		gameOver = true; // Disable movement in keyPressed()

	}

	public void keyPressed(KeyEvent k) {

		int key = k.getKeyCode();

		if (!gameOver) {
			if (key == KeyEvent.VK_SPACE) {
				dubs.move(0, -30);
			}
		}

	}

}
