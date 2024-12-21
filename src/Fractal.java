
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fractal {
	JFrame jframe;
	JLabel background;
	BufferedImage maze;
	Timer timer;
	Point turtle;
	boolean[][] grid;
	int tx;
	int ty;
	int txs;
	int tys;
	int fade;
	int time;
	int size;
	int[][] nodes;
	boolean full = false;
	/*for sierpinski carpet
	 * double[][] nodesnorm = new double[][] { { 0, 0 }, { 0.5, 0 }, { 1, 0 }, { 1,
	 * 0.5 }, { 1, 1 }, { 0.5, 1 }, { 0, 1 }, { 0, 0.5 } }; double a = 1.0 / 3;
	 */
	
	//for sierpinski triangle
	double[][] nodesnorm = new double[][] { { 0,0.8660 }, { 1, 0.8660 }, { 0.5, 0 }};
	double a = 1.0 / 2;
	Robot robot;

	public Fractal() {
		fade = 0;
		size = 1;
		time = 1;
		tx = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		ty = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		txs = tx / size;
		tys = ty / size;
	}

	public int mod(int a, int b) {
		return (a % b + b) % b;
	}

	public void display() {
		//BufferedImage finalmaze = maze.getSubimage(0, 0, txs, tys);
		background.setIcon(new ImageIcon(maze.getScaledInstance(tx, ty, BufferedImage.SCALE_FAST)));
		// background.setIcon(new ImageIcon(maze.getScaledInstance(tx, ty,
		// BufferedImage.SCALE_FAST)));
	}

	/*
	 * public void decay() { for (int i = 0; i < maze.getWidth(); i++) { for (int j
	 * = 0; j < maze.getHeight(); j++) { int red = new Color(maze.getRGB(i,
	 * j)).getRed(); int green = new Color(maze.getRGB(i, j)).getGreen(); int blue =
	 * new Color(maze.getRGB(i, j)).getBlue();
	 * 
	 * if (green == 0 && blue == 0) { maze.setRGB(i, j, new Color(Math.max(red - 2 *
	 * fade, 0), new Color(maze.getRGB(i, j)).getGreen(), new Color(maze.getRGB(i,
	 * j)).getBlue()).getRGB()); } else { maze.setRGB(i, j, new Color(Math.max(red -
	 * fade, 0), new Color(maze.getRGB(i, j)).getGreen(), new Color(maze.getRGB(i,
	 * j)).getBlue()).getRGB()); } if (red == 0 && blue == 0) { maze.setRGB(i, j,
	 * new Color(new Color(maze.getRGB(i, j)).getRed(), Math.max(green - 2 * fade,
	 * 0), new Color(maze.getRGB(i, j)).getBlue()).getRGB()); } else {
	 * maze.setRGB(i, j, new Color(new Color(maze.getRGB(i, j)).getRed(),
	 * Math.max(green - fade, 0), new Color(maze.getRGB(i, j)).getBlue()).getRGB());
	 * } if (green == 0 && red == 0) { maze.setRGB(i, j, new Color(new
	 * Color(maze.getRGB(i, j)).getRed(), new Color(maze.getRGB(i, j)).getGreen(),
	 * Math.max(blue - 2 * fade, 0)).getRGB()); } else { maze.setRGB(i, j, new
	 * Color(new Color(maze.getRGB(i, j)).getRed(), new Color(maze.getRGB(i,
	 * j)).getGreen(), Math.max(blue - fade, 0)).getRGB()); }
	 * 
	 * } } }
	 */

	public void gen() {
		/*
		 * if (mouse) { grid[(int) ((MouseInfo.getPointerInfo().getLocation().x) /
		 * (size))][(int) ((MouseInfo.getPointerInfo().getLocation().y) / (size))] =
		 * true; }
		 */
		for (int i = 0; i < 10000; i++) {
			int rand = (int) (Math.random() * nodes.length);
			turtle = new Point((int) ((turtle.x * a) + (nodes[rand][0] * (1 - a))),
					(int) ((turtle.y * a) + (nodes[rand][1] * (1 - a))));
			/*
			 * switch ((int) (Math.random() * 8)) { case 0: turtle = new Point((int)
			 * (turtle.x * a), (int) (turtle.y * a)); break; case 1: turtle = new
			 * Point((int) (turtle.x * a + (tx / size) * (1 - a)), (int) (turtle.y * a));
			 * break; case 2: turtle = new Point((int) (turtle.x * a), (int) (turtle.y * a +
			 * (ty / size) * (1 - a))); break; case 3: turtle = new Point((int) (turtle.x *
			 * a + (tx / size) * (1 - a)), (int) (turtle.y * a + (ty / size) * (1 - a)));
			 * break; case 4: turtle = new Point((int) (turtle.x * a + (tx / (2 * size)) *
			 * (1 - a)), (int) (turtle.y * a)); break; case 5: turtle = new Point((int)
			 * (turtle.x * a + (tx / (2 * size)) * (1 - a)), (int) (turtle.y * a + (ty /
			 * size) * (1 - a))); break; case 6: turtle = new Point((int) (turtle.x * a),
			 * (int) (turtle.y * a + (ty / (2 * size)) * (1 - a))); break; case 7: turtle =
			 * new Point((int) (turtle.x * a + (tx / size) * (1 - a)), (int) (turtle.y * a +
			 * (ty / (2 * size)) * (1 - a))); break; }
			 */
			/*
			 * maze.setRGB(((tx - ty) / (2 * size)) + turtle.x, turtle.y, new Color(((255 *
			 * turtle.x) / ty), ((255 * turtle.y) / ty), 128).getRGB());
			 */

			if (full) {
				maze.setRGB(turtle.x, turtle.y,
						Color.HSBtoRGB((float) (Math.atan2((double) turtle.y - (tys / 2), (double) turtle.x - (txs / 2))
								/ (Math.PI * 2)), 1, 1));
			} else {

				maze.setRGB(((txs - tys) / 2) + turtle.x, turtle.y,
						Color.HSBtoRGB((float) (Math.atan2((double) turtle.y - (tys / 2), (double) turtle.x - (tys / 2))
								/ (Math.PI * 2)), 1, 1));
			}

		}

	}

	public void init() {
		
		  jframe = new JFrame("Fractal"); jframe.setUndecorated(true); maze = new
		  BufferedImage(txs + 2, tys + 2, BufferedImage.TYPE_3BYTE_BGR); Graphics2D g =
		  maze.createGraphics(); g.setColor(Color.black); g.drawRect(0, 0, txs + 2, tys
		  + 2); g.dispose();
		 

		if (full) {
			turtle = new Point((int) (Math.random() * txs), (int) (Math.random() * tys));
		} else {
			turtle = new Point((int) (Math.random() * tys), (int) (Math.random() * tys));
		}
		grid = new boolean[txs][tys];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = false;
			}
		}
		nodes = new int[nodesnorm.length][2];
		for (int i = 0; i < nodesnorm.length; i++) {
			if (full) {
				nodes[i][0] = (int) (nodesnorm[i][0] * txs);
			} else {
				nodes[i][0] = (int) (nodesnorm[i][0] * tys);
			}
			nodes[i][1] = (int) (nodesnorm[i][1] * tys);
		}

		background = new JLabel();
		background.setBounds(0, 0, tx, ty);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (fade != -1) {
					// decay();
					gen();
				}
				display();

			}
		}, 0, time);
		try {
			robot = new Robot();
		} catch (AWTException e2) {
			e2.printStackTrace();
		}
		timer.schedule(new TimerTask() {
			public void run() {
				robot.keyPress(KeyEvent.VK_F23);
				robot.keyRelease(KeyEvent.VK_F23);
			}
		}, 60000, 60000);
		jframe.add(background);
		jframe.setLayout(null);
		jframe.setResizable(false);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jframe.setAlwaysOnTop(true);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		jframe.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				jframe.dispose();
				timer.cancel();
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {

			}

		});
	}

	public static void main(String[] args) {
		Fractal p = new Fractal();
		p.init();

	}
}