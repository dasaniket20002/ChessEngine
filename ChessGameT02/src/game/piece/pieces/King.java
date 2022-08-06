package game.piece.pieces;

import game.board.Board;
import game.handlers.SpriteHandler;
import game.piece.Alliance;
import game.piece.Move;
import game.piece.Piece;
import game.piece.PieceType;
import utils.Coordinate;

public class King extends Piece {

	public static final int MOVEMENT_DIRECTIONS[][] = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 }, { 1, 0 }, { 0, 1 },
			{ -1, 0 }, { 0, -1 } };

	public King(Alliance color) {
		super(PieceType.KING, color);
	}

	@Override
	public void setSprite() {
		if (getColor() == Alliance.WHITE)
			this.sprite = SpriteHandler.getInstance().getWhite_king();
		else
			this.sprite = SpriteHandler.getInstance().getBlack_king();
	}

	@Override
	public void generateValidMoves(Board board, Coordinate currentPosition) {
		getGeneratedMoves().clear();

		for (int dir = 0; dir < MOVEMENT_DIRECTIONS.length; dir++) {
			int i = currentPosition.getX() + MOVEMENT_DIRECTIONS[dir][0],
					j = currentPosition.getY() + MOVEMENT_DIRECTIONS[dir][1];
			if (isInBounds(i) && isInBounds(j)) {
				if (board.getTiles()[i][j].getPieceOnTile() != null) {
					if (board.getTiles()[i][j].getPieceOnTile()
							.getColor() == board.getTiles()[currentPosition.getX()][currentPosition.getY()]
									.getPieceOnTile().getColor().getEnemyAlliance()) {
						// generate attack move
						Move move = new Move(currentPosition, new Coordinate(i, j), true);
						Board dummy = move.executeMove(board);

						if (!dummy.canKillThisKing()) {
							this.getGeneratedMoves().add(move);
						}
					}
				} else {
					// generate major move
					Move move = new Move(currentPosition, new Coordinate(i, j), false);
					Board dummy = move.executeMove(board);
					if (!dummy.canKillThisKing()) {
						this.getGeneratedMoves().add(move);
					}
				}
			}
		}
	}

	public void generateValidMoves(Board board, Coordinate currentPosition, boolean moveThisPiece) {
		if (moveThisPiece)
			generateValidMoves(board, currentPosition);
		else {
			getGeneratedMoves().clear();
			canKillEnemyKing = false;

			for (int dir = 0; dir < MOVEMENT_DIRECTIONS.length; dir++) {
				int i = currentPosition.getX() + MOVEMENT_DIRECTIONS[dir][0],
						j = currentPosition.getY() + MOVEMENT_DIRECTIONS[dir][1];
				if (isInBounds(i) && isInBounds(j)) {
					if (board.getTiles()[i][j].getPieceOnTile() != null) {
						if (board.getTiles()[i][j].getPieceOnTile()
								.getColor() == board.getTiles()[currentPosition.getX()][currentPosition.getY()]
										.getPieceOnTile().getColor().getEnemyAlliance()) {
							// generate attack move
							Move move = new Move(currentPosition, new Coordinate(i, j), true);
							this.getGeneratedMoves().add(move);
							
							if (board.getTiles()[i][j].getPieceOnTile().getType() == PieceType.KING)
								canKillEnemyKing = true;
						}
					} else {
						// generate major move
						Move move = new Move(currentPosition, new Coordinate(i, j), false);
						this.getGeneratedMoves().add(move);
					}
				}
			}
		}
	}

}
