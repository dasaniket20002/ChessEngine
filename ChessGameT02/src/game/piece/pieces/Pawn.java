package game.piece.pieces;

import game.board.Board;
import game.handlers.SpriteHandler;
import game.piece.Alliance;
import game.piece.Move;
import game.piece.Piece;
import game.piece.PieceType;
import utils.Coordinate;

public class Pawn extends Piece {
	public static final int MOVEMENT_DIRECTIONS[][] = { { 0, -1 }, { 1, -1 }, { -1, -1 } };

	private boolean isSpawnedRecently;

	public Pawn(Alliance color) {
		super(PieceType.PAWN, color);
	}

	@Override
	public void setSprite() {
		if (getColor() == Alliance.WHITE)
			this.sprite = SpriteHandler.getInstance().getWhite_pawn();
		else
			this.sprite = SpriteHandler.getInstance().getBlack_pawn();

		this.isSpawnedRecently = true;
	}

	@Override
	public void generateValidMoves(Board board, Coordinate currentPosition) {
		getGeneratedMoves().clear();
		canKillEnemyKing = false;

		int i = currentPosition.getX(), j = currentPosition.getY();

		int count = 0;
		while (count < 2) {
			i += MOVEMENT_DIRECTIONS[0][0];
			j += MOVEMENT_DIRECTIONS[0][1];

			if (isInBounds(i) && isInBounds(j)) {
				if (board.getTiles()[i][j].getPieceOnTile() == null) {
					// generate major move
					Move m = new Move(currentPosition, new Coordinate(i, j), false);
					this.getGeneratedMoves().add(m);
				} else
					break;
			}
			count++;

			if (!isSpawnedRecently)
				break;
		}

		for (int dir = 1; dir < MOVEMENT_DIRECTIONS.length; dir++) {
			i = currentPosition.getX() + MOVEMENT_DIRECTIONS[dir][0];
			j = currentPosition.getY() + MOVEMENT_DIRECTIONS[dir][1];

			if (isInBounds(i) && isInBounds(j)) {
				if (board.getTiles()[i][j].getPieceOnTile() != null) {
					if (board.getTiles()[i][j].getPieceOnTile()
							.getColor() == board.getTiles()[currentPosition.getX()][currentPosition.getY()]
									.getPieceOnTile().getColor().getEnemyAlliance()) {
						// generate attack move
						Move m = new Move(currentPosition, new Coordinate(i, j), true);
						this.getGeneratedMoves().add(m);

						if (board.getTiles()[i][j].getPieceOnTile().getType() == PieceType.KING)
							canKillEnemyKing = true;
					}
				}
			}
		}
	}

	public void setSpawned(boolean b) {
		this.isSpawnedRecently = b;
	}

}
