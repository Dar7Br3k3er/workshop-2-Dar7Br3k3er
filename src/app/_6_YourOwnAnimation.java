package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.ApplicationTime;

public class _6_YourOwnAnimation extends Animation {

	@Override
	protected ArrayList<JFrame> createFrames(ApplicationTime applicationTimeThread) {
		// a list of all frames (windows) that will be shown
		ArrayList<JFrame> frames = new ArrayList<>();

		// Create main frame (window)
		JFrame frame = new JFrame("Mathematik und Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new _6_YourOwnAnimationPanel(applicationTimeThread);
		frame.add(panel);
		frame.pack(); // adjusts size of the JFrame to fit the size of it's components
		frame.setVisible(true);

		frames.add(frame);
		return frames;
	}

}

class _6_YourOwnAnimationPanel extends JPanel {

	// panel has a single time tracking thread associated with it
	private final ApplicationTime t;

	private double time;
	private  double collisionsTime;

	public _6_YourOwnAnimationPanel(ApplicationTime thread) {
		this.t = thread;
	}

	// set this panel's preferred size for auto-sizing the container JFrame
	public Dimension getPreferredSize() {
		return new Dimension(_0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);
	}

	int width = _0_Constants.WINDOW_WIDTH;
	int height = _0_Constants.WINDOW_HEIGHT;
	double startX = width /2;
	double startY = 20;
	double vX = 0;
	double vY = 0;
	double currentX = startX;
	double currentY = startY;
	int diameter = 50;
	double deltaTime = 0.0;
	double lastFrameTime = 0.0;
	private double gravity = 100.00;
	private double energyLoss = 0.8;
	private boolean onGround = false;



	// drawing operations should be done in this method
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		time = t.getTimeInSeconds();


		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, _0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);


		deltaTime = time - lastFrameTime;
		lastFrameTime = time;


		if (currentY + diameter >= _0_Constants.WINDOW_HEIGHT) {
			vY = -vY * energyLoss; // reverse vertical velocity with energy loss
			currentY = _0_Constants.WINDOW_HEIGHT - diameter; // adjust position to not penetrate ground
			onGround = true;
		} else {
			vY += gravity * deltaTime; // apply gravity
			onGround = false;
		}

		if (currentX + diameter >= _0_Constants.WINDOW_WIDTH || currentX <= 0) {
			vX = -vX; // reverse horizontal velocity on collision with walls
		}

		currentX += vX * deltaTime;

		// update vertical position
		currentY += vY * deltaTime;

		g.setColor(Color.RED);
		g.fillOval((int) currentX, (int) currentY, diameter, diameter);

		//currentX = currentX + (vX * deltaTime);
		//currentY = currentY + (vY * deltaTime);


		// TODO: do something to bounce the ball








		/*
		 * Exercises:
		 *
		 * 1) Use the same initial conditions ( startX, startY, vX, vY ) as above. Let
		 * the circle/ball "bounce off the vertical walls", i.e. provide for correct
		 * changes of velocity whenever the circle/ball "collides elastically with" the
		 * right-hand or the left-hand frame borders.
		 *
		 * Hints: (i) As soon as the condition if( currentX >= width) yields "TRUE", do
		 * the following - reverse vX: vX = -vX; - assign the value "width" to the
		 * variable "startX"; - assign the value "time" to a new variable
		 * "collisionTime", that you have to add to the code. (ii) Replace the code line
		 * "currentX = startX + time * vX;" by "currentX = startX + (time -
		 * collisionTime) * vX;"
		 *
		 * Improve the application by ensuring that the ball does not penetrate into the
		 * right-hand wall.
		 *
		 * if(currentX + diameter >= width){
			vX = -vX;
			startX = width - diameter;
			collisionsTime = time;
		} else if (currentX <= 0) {
			vX = -vX;
			currentX = 1;
		}


		g.setColor(Color.RED);
		g.fillOval((int) currentX, (int) currentY, diameter, diameter);

		currentX = startX + (time - collisionsTime) * vX;
		currentY = startY + time  * vY;
		 *
		 * 2) Choose any initial conditions (startX, startY, vX, vY ). Let the
		 * circle/ball "bounce off the walls", i.e. provide for correct changes of
		 * velocity whenever the circle/ball "collides elastically with" any of the four
		 * frame borders.
		 *
		 * (i) As an alternative to the techniques employed in Exercise 1), use now what
		 * we may call the "deltaTime paradigm":
		 *
		 * double deltaTime = 0.0; double lastFrameTime = 0.0;
		 *
		 * deltaTime = time - lastFrameTime; lastFrameTime = time; currentX = currentX +
		 * (vX * deltaTime); currentY = currentY + (vY * deltaTime);
		 *
		 * if (currentX >= _0_Constants.WINDOW_WIDTH - diameter) {
		 * System.out.println("Object has hit the right-hand wall."); vX = -vX; currentX
		 * = currentX - 1; // One pixel is subtracted from to the current x-coordinate
		 * if the ball is at // the right-hand boundary. This prevents the ball from
		 * "sticking to the border" after a collision
		 *
		 * (ii) Note the following ways of formatting numerical output:
		 *
		 * deltaTime = time - lastFrameTime;
		lastFrameTime = time;


		if (currentX >= _0_Constants.WINDOW_WIDTH - diameter) {
			System.out.println("Objekt hat die rechte Wand getroffen");
			vX = -vX;
			currentX = _0_Constants.WINDOW_WIDTH - diameter - 1;
		} else if (currentX <= 0) {
			System.out.println("Objekt hat die linke Wand getroffen");
			vX = -vX;
			currentX = 1;
		}

		if (currentY >= _0_Constants.WINDOW_HEIGHT - diameter) {
			System.out.println("Objekt hat die untere Wand getroffen");
			vY = -vY;
			currentY = _0_Constants.WINDOW_HEIGHT - diameter - 1;
		} else if (currentY <= 0) {
			System.out.println("Objekt hat die obere Wand getroffen");
			vY = -vY;
			currentY = 1;
		}



		g.setColor(Color.RED);
		g.fillOval((int) currentX, (int) currentY, diameter, diameter);

		currentX = currentX + (vX * deltaTime);
		currentY = currentY + (vY * deltaTime);

		System.out.println("vX = " + (double) Math.round(100 * vX) / 100);
		System.out.println("currentX = " + (double) Math.round(100 * currentX) /100);
		System.out.println("currentY = " + (double) Math.round(100 * currentY) / 100 + '\n');
		 *
		 * 3) Simulate the motion of the ball/circle under the influence of gravity
		 * Place the circle a some height h above the floor (bottom frame border) with
		 * initial velocity vY = 0. Let the circle/ball undergo accelerated motion
		 * toward the bottom. Once the ball hits the floor, its velocity is reversed
		 * (fully elastic collision), the ensuing upward motion is decelerated until the
		 * circle/ball comes to rest a height h, etc. * 4) As in Exercise 3) except that
		 * there shall now be a loss of kinetic energy each time the ball hits the
		 * bottom.
		 *
		 *int width = _0_Constants.WINDOW_WIDTH;
	int height = _0_Constants.WINDOW_HEIGHT;
	double startX = width /2;
	double startY = 20;
	double vX = 0;
	double vY = 0;
	double currentX = startX;
	double currentY = startY;
	int diameter = 50;
	double deltaTime = 0.0;
	double lastFrameTime = 0.0;
	private double gravity = 100.00;
	private double energyLoss = 0.8;
	private boolean onGround = false;



	// drawing operations should be done in this method
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		time = t.getTimeInSeconds();


		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, _0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);


		deltaTime = time - lastFrameTime;
		lastFrameTime = time;


		if (currentY + diameter >= _0_Constants.WINDOW_HEIGHT) {
			vY = -vY * energyLoss; // reverse vertical velocity with energy loss
			currentY = _0_Constants.WINDOW_HEIGHT - diameter; // adjust position to not penetrate ground
			onGround = true;
		} else {
			vY += gravity * deltaTime; // apply gravity
			onGround = false;
		}

		if (currentX + diameter >= _0_Constants.WINDOW_WIDTH || currentX <= 0) {
			vX = -vX; // reverse horizontal velocity on collision with walls
		}

		currentX += vX * deltaTime;

		// update vertical position
		currentY += vY * deltaTime;

		g.setColor(Color.RED);
		g.fillOval((int) currentX, (int) currentY, diameter, diameter);
		 */

	}
}