package game.piece.pieces;

import game.board.Board;
import game.handlers.SpriteHandler;
import game.piece.Alliance;
import game.piece.Move;
import game.piece.Piece;
import game.piece.PieceType;
import utils.Coordinate;

public class Knight extends Piece {

	public static final int MOVEMENT_DIRECTIONS[][] = { { 2, 1 }, { 1, 2 }, { 2, -1 }, { -1, 2 }, { -2, 1 }, { 1, -2 },
			{ -2, -1 }, { -1, -2 } };

	public Knight(Alliance color) {
		super(PieceType.KNIGHT, color);
	}

	@Override
	public void setSprite() {
		if (getColor() == Alliance.WHITE)
			this.sprite = SpriteHandler.getInstance().getWhite_knight();
		else
			this.sprite = SpriteHandler.getInstance().getBlack_knight();
	}

	@Override
	public void generateValidMoves(Board board, Coordinate currentPosition) {
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
