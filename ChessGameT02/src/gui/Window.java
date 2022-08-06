package gui;

import javax.swing.JFrame;

import game.handlers.InputHandler;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1071974856901069018L;

	private static Window instance;
	private int width, height;

	private GamePanel panel;

	private String title = "Chess";

	public static Window getInstance() {
		if (instance == null)
			instance = new Window();
		return instance;
	}

	protected Window() {
		this(600, 600);
	}

	protected Window(int width, int height) {
		setDimensions(width, height);
		init();
	}

	private void init() {
		panel = new GamePanel();
		this.add(panel);
		
		this.setTitle(title);
		this.setSize(width, height);
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(InputHandler.getInstance());
		this.addKeyListener(InputHandler.getInstance());
		
		this.pack();
	}

	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setTopTitle(String title) {
		this.title = title;
	}

	public String getTopTitle() {
		return title;
	}

	public GamePanel getPanel() {
		return panel;
	}

}
