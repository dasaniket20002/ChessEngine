package game.handlers;

import java.util.List;

import game.piece.Move;
import game.piece.Piece;
import game.piece.PieceType;
import game.piece.pieces.Pawn;
import utils.Coordinate;

public class PieceMovementHandler {
	private static PieceMovementHandler instance = null;

	private Coordinate from, to;
	private Piece pieceToMove;

	private boolean isPieceMoved = false;

	public static PieceMovementHandler getInstance() {
		if (instance == null)
			instance = new PieceMovementHandler();
		return instance;
	}

	public void tryMovePiece() {
		Coordinate mouse = InputHandler.getInstance().getMouseClickedCoordinate();
		if (mouse == null)
			return;

		if (pieceToMove == null) {
			from = mouse;
			pieceToMove = GameHandler.getInstance().getBoard().getTiles()[mouse.getX()][mouse.getY()].getPieceOnTile();
		}

		if (pieceToMove != null && !pieceToMove.isAlive()
				&& pieceToMove.getColor() != GameHandler.getInstance().getBoard().getPlayerAlliance())
			return;

		if (from.equals(mouse))
			return;

		to = mouse;

		pieceToMove.generateValidMoves(GameHandler.getInstance().getBoard(), from);
		pieceToMove.handleCheckCondition(GameHandler.getInstance().getBoard(), from);
		List<Move> legalMoves = pieceToMove.getGeneratedMoves();

		// check if clicked coordinate is in legal moves, then execute Move
		for (Move m : legalMoves) {
			if (m.getDestination().equals(to)) {
				pieceToMove.getGeneratedMoves().clear();
				m.executeFinal(GameHandler.getInstance().getBoard());
				isPieceMoved = true;

				if (pieceToMove.getType() == PieceType.PAWN) {
					((Pawn) pieceToMove).setSpawned(false);
				}

				break;
			}
		}

		from = to = null;
		pieceToMove = null;
	}

	public boolean isPieceMoved() {
		boolean t = isPieceMoved;
		isPieceMoved = false;
		return t;
	}

}
