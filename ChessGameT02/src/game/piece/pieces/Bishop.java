package game.piece.pieces;

import game.board.Board;
import game.handlers.SpriteHandler;
import game.piece.Alliance;
import game.piece.Move;
import game.piece.Piece;
import game.piece.PieceType;
import utils.Coordinate;

public class Bishop extends Piece {

	public static final int MOVEMENT_DIRECTIONS[][] = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };

	public Bishop(Alliance color) {
		super(PieceType.BISHOP, color);
	}

	@Override
	public void setSprite() {
		if (getColor() == Alliance.WHITE)
			this.sprite = SpriteHandler.getInstance().getWhite_bishop();
		else
			this.sprite = SpriteHandler.getInstance().getBlack_bishop();
	}

	@Override
	public void generateValidMoves(Board board, Coordinate currentPosition) {
		getGeneratedMoves().clear();
		canKillEnemyKing = false;

		for (int dir = 0; dir < MOVEMENT_DIRECTIONS.length; dir++) {
			int i = currentPosition.getX() + MOVEMENT_DIRECTIONS[dir][0],
					j = currentPosition.getY() + MOVEMENT_DIRECTIONS[dir][1];
			while (isInBounds(i) && isInBounds(j)) {
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
					break;
				} else {
					// generate major move
					Move move = new Move(currentPosition, new Coordinate(i, j), false);
					this.getGeneratedMoves().add(move);
				}

				i += MOVEMENT_DIRECTIONS[dir][0];
				j += MOVEMENT_DIRECTIONS[dir][1];
			}
		}
	}
}
