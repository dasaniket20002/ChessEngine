package game.board;

import java.awt.Color;
import java.awt.Graphics;

import game.piece.Alliance;
import game.piece.Piece;
import utils.Coordinate;

public class Tile {

	public static final int TILE_SIZE = 64;

	private Coordinate coord;
	private Alliance color;
	private Piece piece = null;

	public Tile(Coordinate coord, Alliance color) {
		this.coord = coord;
		this.color = color;
	}
	
	public Tile(Tile t)
	{
		this.coord.setX(t.getCoord().getX());
		this.coord.setY(t.getCoord().getY());
		this.color = t.getColor();
	}

	public void setPieceOnTile(Piece piece) {
		this.piece = piece;
	}

	public void clearPieceOnTile() {
		piece = null;
	}

	public void drawTile(Graphics g) {
		if (color == Alliance.BLACK)
			g.setColor(Color.GRAY);
		else if (color == Alliance.WHITE)
			g.setColor(Color.WHITE);
		else if (color == Alliance.HIGHLIGHT_T)
			g.setColor(new Color(100, 200, 200, 100));
		else if (color == Alliance.HIGHLIGHT_S)
			g.setColor(new Color(200, 200, 100, 100));
		else if (color == Alliance.HIGHLIGHT_M)
			g.setColor(new Color(250, 250, 80, 100));
		else if (color == Alliance.HIGHLIGHT_A)
			g.setColor(new Color(200, 50, 50, 100));
		else if (color == Alliance.HIGHLIGHT_C)
			g.setColor(new Color(200, 50, 50, 150));

		g.fillRect(coord.getX() * Tile.TILE_SIZE, coord.getY() * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
	}

	public void drawPieceOnTile(Graphics g) {
		if (piece != null) {
			g.drawImage(piece.getSprite(), coord.getX() * Tile.TILE_SIZE, coord.getY() * Tile.TILE_SIZE, null);
		}
	}
	
	public Piece getPieceOnTile() {
		return piece;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public Alliance getColor() {
		return color;
	}

}
