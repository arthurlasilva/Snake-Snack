import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 600; // sets size of the window
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 60;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6; // initial body size for snake
	int snacksEaten;
	int snackX;
	int snackY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	GamePanel() {

		// setup for game panel
		random = new Random();

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(9, 121, 105));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	// starts game
	// *add game menu
	public void startGame() {
		newSnack();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	// calls paint components for graphics --
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// if statement for game over method
		if (running) {

			// gives the background a grid
			for (int i = 0; i < SCREEN_HEIGHT * UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}

			g.setColor(new Color(250, 0, 0)); // color of the snack
			g.fillOval(snackX, snackY, UNIT_SIZE, UNIT_SIZE); // size of the snack

			//body parts and color of the snake
			
			for (int i = 0; i < bodyParts; i++) {
				if (i % 3 == 0) {
					g.setColor(new Color(249, 234, 40));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

				} else if (i >= 1) {
					
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

				}
					
		}

			
			g.setColor(new Color(250, 0, 0));
			g.setFont(new Font("Fixedsys", Font.BOLD, 40)); 
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + snacksEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + snacksEaten)) / 2,
					g.getFont().getSize());

		} else {
			gameOver(g);
		}
	}

	// places snack in random location within grid
	public void newSnack() {
		snackX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		snackY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

	}

	// defines inputs for character direction
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;

		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;

		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;

		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	public void checkSnack() {
		if ((x[0] == snackX) && (y[0] == snackY)) {
			bodyParts++;
			snacksEaten++;
			newSnack();

		}
	}

	//
	//	check if body collisions could be better
	//
	public void checkCollisions() {
		// this loop checks if head collided with body
		for (int i = bodyParts; i > 2; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}

		// This section makes the borders collidable
		// In future versions I plan on making this a configuration option for wrapping
		// around the borders

		// check if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		// check if head touches right border
		if (x[0] > SCREEN_WIDTH - UNIT_SIZE) {
			running = false;
		}

		// check if head touches top border
		if (y[0] < 0) {
			running = false;
		}

		// check if head touches bottom border
		if (y[0] > SCREEN_HEIGHT - UNIT_SIZE) {
			running = false;
		}

		if (!running) {
			timer.stop();
		}

	}

	public void gameOver(Graphics g) {

		// display score on the on game over
		g.setColor(Color.red);
		g.setFont(new Font("Fixedsys", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + snacksEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + snacksEaten)) / 2,
				g.getFont().getSize());

		// game over screen setup
		g.setColor(Color.black);
		g.setFont(new Font("Fixedsys", Font.BOLD, 75)); // setting font style and size

		FontMetrics metrics2 = getFontMetrics(g.getFont()); // Gets font for positioning text on screen

		// positions text in the middle of the screen
		g.drawString("Game Over!", (SCREEN_WIDTH - metrics2.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkSnack();
			checkCollisions();
		}
		repaint();

	}
	
	

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}

			}
		}
	}
}
