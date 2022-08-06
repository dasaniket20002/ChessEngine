package game.piece;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import game.board.Board;
import game.handlers.GameHandler;
import utils.Coordinate;
import utils.DeepCloner;

public abstract class Piece {
	private PieceType type;
	private Alliance color;

	protected BufferedImage sprite;
	private boolean isAlive;

	private List<Move> generatedMoves;
	protected boolean canKillEnemyKing;

	public Piece(PieceType type, Alliance color) {
		this.type = type;
		this.color = color;
		this.isAlive = true;

		this.generatedMoves = new ArrayList<Move>();
		this.canKillEnemyKing = false;

		setSprite();
	}

	public abstract void setSprite();

	public abstract void generateValidMoves(Board board, Coordinate currentPosition);

	public void handleCheckCondition(Board board, Coordinate currentPosition) {
		if (!board.isCheck())
			return;

		List<Move> allMoves = DeepCloner.getInstance().deepClone(getGeneratedMoves());
		List<Move> selectedMoves = new ArrayList<Move>();

		for (Move m : allMoves) {
			Board dummy = m.executeMove(board);
			if (!dummy.isCheck())
				selectedMoves.add(m);
		}
		generatedMoves = selectedMoves;
	}

	public static boolean isInBounds(int l) {
		return l >= 0 && l < GameHandler.getInstance().getBoard().getBoardSize();
	}

	public PieceType getType() {
		return type;
	}

	public Alliance getColor() {
		return color;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void kill() {
		setAlive(false);
	}

	public void setAlive(boolean b) {
		this.isAlive = b;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public synchronized List<Move> getGeneratedMoves() {
		return generatedMoves;
	}

	public boolean canKillKing() {
		return canKillEnemyKing;
	}

}
