package game.board;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.piece.Alliance;
import game.piece.Piece;
import game.piece.PieceType;
import game.piece.pieces.Bishop;
import game.piece.pieces.King;
import game.piece.pieces.Knight;
import game.piece.pieces.Pawn;
import game.piece.pieces.Queen;
import game.piece.pieces.Rook;
import utils.Coordinate;

public class Board {

	private int board_size;
	private Tile[][] board;

	private Piece[] pieces;
	private Alliance current_player_alliance;

	private Board opponent_board = null;

	public Board(int board_size, Alliance current_player_alliance) {
		this.board_size = board_size;
		board = new Tile[board_size][board_size];

		this.pieces = new Piece[board_size * 2];
		this.current_player_alliance = current_player_alliance;

		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Alliance ta;
				if ((i + j) % 2 == 0)
					ta = Alliance.BLACK;
				else
					ta = Alliance.WHITE;
				board[i][j] = new Tile(new Coordinate(i, j), ta);
			}
		}

		initOwnPieces();
		setPiecesOnBoard();
	}

	private void initOwnPieces() {
		for (int i = 0; i < board_size; i++) {
			pieces[i] = new Pawn(current_player_alliance);
		}
		pieces[8] = new Rook(current_player_alliance);
		pieces[9] = new Knight(current_player_alliance);
		pieces[10] = new Bishop(current_player_alliance);
		pieces[11] = new King(current_player_alliance);
		pieces[12] = new Queen(current_player_alliance);
		pieces[13] = new Bishop(current_player_alliance);
		pieces[14] = new Knight(current_player_alliance);
		pieces[15] = new Rook(current_player_alliance);
	}

	private void setPiecesOnBoard() {
		for (int i = board_size - 2; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Piece p = pieces[(i - (board_size - 2)) * board_size + j];
				getTiles()[j][i].setPieceOnTile(p);
			}
		}
	}

	public void clearOpponentPositionsOnBoard() {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (board[i][j].getPieceOnTile() != null
						&& board[i][j].getPieceOnTile().getColor() == current_player_alliance.getEnemyAlliance()) {
					{
						board[i][j].setPieceOnTile(null);
					}
				}
			}
		}
	}

	public void generateOpponentPositions() {
		clearOpponentPositionsOnBoard();
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (opponent_board.getTiles()[i][j].getPieceOnTile() != null && opponent_board.getTiles()[i][j]
						.getPieceOnTile().getColor() == opponent_board.current_player_alliance) {
					if (board[board_size - 1 - i][board_size - 1 - j].getPieceOnTile() != null) {
						board[board_size - 1 - i][board_size - 1 - j].getPieceOnTile().kill();
					}
					board[board_size - 1 - i][board_size - 1 - j]
							.setPieceOnTile(opponent_board.getTiles()[i][j].getPieceOnTile());
				}
			}
		}
	}

	public String encodeBoard() {
		String encode = "";
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Tile t = board[i][j];

				if (t.getPieceOnTile() != null && t.getPieceOnTile().getColor() == this.current_player_alliance
						&& t.getPieceOnTile().isAlive()) {
					encode += t.getPieceOnTile().getType().getEncodeValue();
				} else {
					encode += PieceType.EMPTY.getEncodeValue();
				}
			}
		}
		return encode;
	}

	public void decodeBoard(String encode) {
		opponent_board = new Board(board_size, current_player_alliance.getEnemyAlliance());
		int ind = 0;

		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				PieceType decodeType = PieceType.getDecodeType(encode.charAt(ind) + "");

				switch (decodeType) {
				case EMPTY:
					opponent_board.getTiles()[i][j].setPieceOnTile(null);
					break;
				case PAWN:
					opponent_board.getTiles()[i][j].setPieceOnTile(new Pawn(opponent_board.getPlayerAlliance()));
					break;
				case ROOK:
					opponent_board.getTiles()[i][j].setPieceOnTile(new Rook(opponent_board.getPlayerAlliance()));
					break;
				case KNIGHT:
					opponent_board.getTiles()[i][j].setPieceOnTile(new Knight(opponent_board.getPlayerAlliance()));
					break;
				case BISHOP:
					opponent_board.getTiles()[i][j].setPieceOnTile(new Bishop(opponent_board.getPlayerAlliance()));
					break;
				case QUEEN:
					opponent_board.getTiles()[i][j].setPieceOnTile(new Queen(opponent_board.getPlayerAlliance()));
					break;
				case KING:
					opponent_board.getTiles()[i][j].setPieceOnTile(new King(opponent_board.getPlayerAlliance()));
					break;
				}
				ind++;
			}
		}
	}

	public void drawBoard(Graphics g) {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				board[i][j].drawTile(g);
			}
		}
	}

	public void drawPiecesOnBoard(Graphics g) {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				board[i][j].drawPieceOnTile(g);
			}
		}
	}

	public void generateEnemyTravelCoords() {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Piece currentPiece = board[i][j].getPieceOnTile();
				if (currentPiece != null && currentPiece.isAlive()
						&& currentPiece.getColor() == current_player_alliance.getEnemyAlliance()) {
					if (currentPiece.getType() == PieceType.KING)
						((King) currentPiece).generateValidMoves(this, new Coordinate(i, j), false);
					else
						currentPiece.generateValidMoves(this, new Coordinate(i, j));
				}
			}
		}
	}

	public List<Piece> getEnemyPiecesOnBoard() {
		generateEnemyTravelCoords();
		List<Piece> list = new ArrayList<Piece>();
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Piece currentPiece = board[i][j].getPieceOnTile();
				if (currentPiece != null && currentPiece.isAlive()
						&& currentPiece.getColor() == current_player_alliance.getEnemyAlliance()) {
					list.add(currentPiece);
				}
			}
		}
		return list;
	}

	public boolean canKillThisKing() {
		List<Piece> enemies = getEnemyPiecesOnBoard();
		for (Piece p : enemies)
			if (p.canKillKing())
				return true;
		return false;
	}

	public Coordinate getKingCoordinate() {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (board[i][j].getPieceOnTile() != null
						&& board[i][j].getPieceOnTile().getColor() == current_player_alliance
						&& board[i][j].getPieceOnTile().getType() == PieceType.KING) {
					return new Coordinate(i, j);
				}
			}
		}
		return null;
	}

	public boolean isCheck() {
		return canKillThisKing();
	}

	public int getAlivePieces() {
		int alive = 0;
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (board[i][j].getPieceOnTile() != null
						&& board[i][j].getPieceOnTile().getColor() == current_player_alliance
						&& board[i][j].getPieceOnTile().isAlive())
					alive++;
			}
		}
		return alive;
	}

	public int movesThatSavesKing() {
		int movesThatSavesKing = 0;
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				Piece currentPiece = board[i][j].getPieceOnTile();
				if (currentPiece != null && currentPiece.getColor() == current_player_alliance) {
					currentPiece.generateValidMoves(this, new Coordinate(i, j));
					currentPiece.handleCheckCondition(this, new Coordinate(i, j));

					movesThatSavesKing += currentPiece.getGeneratedMoves().size();
				}
			}
		}
		return movesThatSavesKing;
	}

	public boolean isCheckmate() {
		return (isCheck() && movesThatSavesKing() <= 0) || getAlivePieces() <= 1;
	}

	public Board getOpponentBoard() {
		return opponent_board;
	}

	public void setOpponentBoard(Board b) {
		opponent_board = b;
	}

	public Tile[][] getTiles() {
		return board;
	}

	public Alliance getPlayerAlliance() {
		return current_player_alliance;
	}

	public int getBoardSize() {
		return board_size;
	}

}
