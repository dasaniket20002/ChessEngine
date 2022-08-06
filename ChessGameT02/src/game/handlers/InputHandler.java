package game.handlers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import game.board.Tile;
import game.piece.Move;
import gui.Window;
import utils.Coordinate;

public class InputHandler implements MouseListener, KeyListener {

	private static InputHandler instance = null;

	public static InputHandler getInstance() {
		if (instance == null)
			instance = new InputHandler();
		return instance;
	}

	private Coordinate mouseClickedCoordinate;

	public Coordinate getTileCoordinateUnderMouse() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p, Window.getInstance());

		return new Coordinate((int) p.getX() / Tile.TILE_SIZE, (int) (p.getY() - 30) / Tile.TILE_SIZE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Coordinate c = new Coordinate((int) e.getX() / Tile.TILE_SIZE, (int) (e.getY() - 30) / Tile.TILE_SIZE);
		mouseClickedCoordinate = c;
		
		GameHandler.getInstance().listenToMouseClick(c);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Coordinate getMouseClickedCoordinate() {
		return mouseClickedCoordinate;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			Move.revertLastMove(GameHandler.getInstance().getBoard());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
