package game.piece;

import game.board.Board;
import utils.Coordinate;
import utils.DeepCloner;

public class Move {

	private static Move lastMove;

	private Coordinate start;
	private Coordinate destination;

	private boolean isAttackMove;

	private Piece replacedPiece;

	public Move(Coordinate start, Coordinate destination, boolean isAttackMove) {
		this.start = start;
		this.destination = destination;
		this.isAttackMove = isAttackMove;
	}

	public Board executeMove(Board board) {
		Board dummy = DeepCloner.getInstance().deepClone(board);

		Piece piece = dummy.getTiles()[start.getX()][start.getY()].getPieceOnTile();
		if (isAttackMove) {
			dummy.getTiles()[destination.getX()][destination.getY()].getPieceOnTile().kill();
			replacedPiece = dummy.getTiles()[destination.getX()][destination.getY()].getPieceOnTile();
		}

		dummy.getTiles()[start.getX()][start.getY()].setPieceOnTile(null);
		dummy.getTiles()[destination.getX()][destination.getY()].setPieceOnTile(piece);

		return dummy;
	}

	public void executeFinal(Board board) {
		lastMove = this;

		Piece piece = board.getTiles()[start.getX()][start.getY()].getPieceOnTile();
		if (isAttackMove) {
			board.getTiles()[destination.getX()][destination.getY()].getPieceOnTile().kill();
			replacedPiece = board.getTiles()[destination.getX()][destination.getY()].getPieceOnTile();
		}

		board.getTiles()[start.getX()][start.getY()].setPieceOnTile(null);
		board.getTiles()[destination.getX()][destination.getY()].setPieceOnTile(piece);
	}

	public void revertMove(Board board) {
		Piece piece = board.getTiles()[destination.getX()][destination.getY()].getPieceOnTile();
		if (isAttackMove) {
			board.getTiles()[destination.getX()][destination.getY()].setPieceOnTile(replacedPiece);
			board.getTiles()[destination.getX()][destination.getY()].getPieceOnTile().setAlive(true);
		}
		board.getTiles()[start.getX()][start.getY()].setPieceOnTile(piece);
	}

	public static void revertLastMove(Board board) {
		if (lastMove != null)
			lastMove.revertMove(board);
	}

	public Coordinate getStart() {
		return start;
	}

	public Coordinate getDestination() {
		return destination;
	}

	public boolean isAttackMove() {
		return isAttackMove;
	}

}
