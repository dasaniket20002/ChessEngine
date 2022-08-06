package game.handlers;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.board.Tile;

public class SpriteHandler {

	private static SpriteHandler instance;

	public static SpriteHandler getInstance() {
		if (instance == null)
			instance = new SpriteHandler();
		return instance;
	}

	private final String spriteSheet_file = "/Chess_Pieces_Sprite.png";

	private BufferedImage spriteSheet = null;

	private BufferedImage w_king, w_queen, w_bishop, w_knight, w_rook, w_pawn;
	private BufferedImage b_king, b_queen, b_bishop, b_knight, b_rook, b_pawn;

	public SpriteHandler() {
		try {
			spriteSheet = ImageIO.read(SpriteHandler.class.getResource(spriteSheet_file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = spriteSheet.getWidth() / 6;

		w_king = spriteSheet.getSubimage(0 * w, 0 * w, w, w);
		w_queen = spriteSheet.getSubimage(1 * w, 0 * w, w, w);
		w_bishop = spriteSheet.getSubimage(2 * w, 0 * w, w, w);
		w_knight = spriteSheet.getSubimage(3 * w, 0 * w, w, w);
		w_rook = spriteSheet.getSubimage(4 * w, 0 * w, w, w);
		w_pawn = spriteSheet.getSubimage(5 * w, 0 * w, w, w);

		b_king = spriteSheet.getSubimage(0 * w, 1 * w, w, w);
		b_queen = spriteSheet.getSubimage(1 * w, 1 * w, w, w);
		b_bishop = spriteSheet.getSubimage(2 * w, 1 * w, w, w);
		b_knight = spriteSheet.getSubimage(3 * w, 1 * w, w, w);
		b_rook = spriteSheet.getSubimage(4 * w, 1 * w, w, w);
		b_pawn = spriteSheet.getSubimage(5 * w, 1 * w, w, w);

		Image temp;
		Graphics g;

		//Resize White Pieces Images
		temp = w_king.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_king = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_king.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = w_queen.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_queen = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_queen.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = w_bishop.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_bishop = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_bishop.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = w_knight.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_knight = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_knight.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = w_rook.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_rook = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_rook.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = w_pawn.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		w_pawn = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = w_pawn.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		//Resize Black Pieces Images
		temp = b_king.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_king = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_king.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = b_queen.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_queen = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_queen.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = b_bishop.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_bishop = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_bishop.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = b_knight.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_knight = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_knight.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = b_rook.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_rook = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_rook.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		temp = b_pawn.getScaledInstance(Tile.TILE_SIZE, Tile.TILE_SIZE, Image.SCALE_SMOOTH);
		b_pawn = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = b_pawn.createGraphics();
		g.drawImage(temp, 0, 0, null);
		g.dispose();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public BufferedImage getWhite_king() {
		return w_king;
	}

	public BufferedImage getWhite_queen() {
		return w_queen;
	}

	public BufferedImage getWhite_bishop() {
		return w_bishop;
	}

	public BufferedImage getWhite_knight() {
		return w_knight;
	}

	public BufferedImage getWhite_rook() {
		return w_rook;
	}

	public BufferedImage getWhite_pawn() {
		return w_pawn;
	}

	public BufferedImage getBlack_king() {
		return b_king;
	}

	public BufferedImage getBlack_queen() {
		return b_queen;
	}

	public BufferedImage getBlack_bishop() {
		return b_bishop;
	}

	public BufferedImage getBlack_knight() {
		return b_knight;
	}

	public BufferedImage getBlack_rook() {
		return b_rook;
	}

	public BufferedImage getBlack_pawn() {
		return b_pawn;
	}

}
