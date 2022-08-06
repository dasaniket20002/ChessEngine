package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.board.Board;
import game.board.Tile;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8860204251354754377L;

	private Board board;

	private Tile[] guiTiles;
	private int addedTileIndex = 0;

	private Tile clickedTile;

	public GamePanel() {
		super();
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);

		this.setPreferredSize(new Dimension(8 * Tile.TILE_SIZE, 8 * Tile.TILE_SIZE));
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void update() {
		guiTiles = new Tile[board.getBoardSize() * board.getBoardSize()];
		addedTileIndex = 0;
	}

	public void addGuiTiles(Tile t) {
		guiTiles[addedTileIndex++] = t;
	}

	public void setClickedTile(Tile t) {
		clickedTile = t;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (board != null)
			board.drawBoard(g);

		for (int i = 0; i < addedTileIndex; i++) {
			if (guiTiles[i] == null)
				break;
			guiTiles[i].drawTile(g);
		}

		if (clickedTile != null)
			clickedTile.drawTile(g);

		if (board != null)
			board.drawPiecesOnBoard(g);
	}

}
