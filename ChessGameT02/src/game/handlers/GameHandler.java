package game.handlers;

import game.board.Board;
import game.board.Tile;
import game.net.ClientServer;
import game.net.PacketType;
import game.piece.Alliance;
import game.piece.Move;
import game.piece.Piece;
import gui.Window;
import utils.Coordinate;
import utils.FPSCounter;
import utils.FrameLimiter;

public class GameHandler extends Thread {

	private static GameHandler instance = null;

	public static GameHandler getInstance() {
		if (instance == null)
			instance = new GameHandler();
		return instance;
	}

	private Board board;

	private boolean turn;
	private boolean recievedBoardData = false;
	private boolean isTerminated = false;

	public void init(boolean isWhite) {
		board = new Board(8, isWhite ? Alliance.WHITE : Alliance.BLACK);
		turn = isWhite;

		NetworkHandler.getInstance().getNetwork().sendBoard(board);
		NetworkHandler.getInstance().getNetwork().recieveBoard(board);

		board.generateOpponentPositions();
	}

	@Override
	public void run() {
		Window.getInstance().setVisible(true);

		while (true) {
			FrameLimiter.startLimiter(System.currentTimeMillis());

			Window.getInstance().getPanel().setBoard(board);
			Window.getInstance().getPanel().update();

			listenToMouseHover();

			if (!isTerminated) {
				if (turn) {
					PieceMovementHandler.getInstance().tryMovePiece();
					if (PieceMovementHandler.getInstance().isPieceMoved()) {
						turn = false;
						recievedBoardData = false;

						NetworkHandler.getInstance().getNetwork().sendBoard(board);
					}
				} else {
					new Thread() {
						public void run() {
							NetworkHandler.getInstance().getNetwork().recieveBoard(board);
							recievedBoardData = true;

							Thread.currentThread().interrupt();
							return;
						}
					}.start();

					if (recievedBoardData) {
						board.generateOpponentPositions();
						turn = true;
					}
				}

				if (board.isCheckmate()) {
					NetworkHandler.getInstance().getNetwork().sendGameOver();
					terminate();
				}

				String recentRecieve = NetworkHandler.getInstance().getNetwork().getRecievedString();
				String[] data = recentRecieve.split(ClientServer.REGEX);
				if (PacketType.decode(data[0]) == PacketType.CHECKMATE) {
					terminate();
				}
			}

			Window.getInstance().getPanel().repaint();

			FrameLimiter.limitFPS();

			if (FPSCounter.getInstance().count())
				Window.getInstance().setTitle(Window.getInstance().getTopTitle() + FPSCounter.getInstance().getFPS());
		}
	}

	private void listenToMouseHover() {
		Coordinate mouse = InputHandler.getInstance().getTileCoordinateUnderMouse();
		Window.getInstance().getPanel().addGuiTiles(new Tile(mouse, Alliance.HIGHLIGHT_T));

		if (InputHandler.getInstance().getMouseClickedCoordinate() == null)
			return;

		Piece pieceOnTile = board.getTiles()[InputHandler.getInstance().getMouseClickedCoordinate().getX()][InputHandler
				.getInstance().getMouseClickedCoordinate().getY()].getPieceOnTile();
		if (turn && pieceOnTile != null && pieceOnTile.isAlive()
				&& pieceOnTile.getColor() == board.getPlayerAlliance()) {
			for (Move m : pieceOnTile.getGeneratedMoves()) {
				Tile gui = new Tile(m.getDestination(), m.isAttackMove() ? Alliance.HIGHLIGHT_A : Alliance.HIGHLIGHT_M);
				Window.getInstance().getPanel().addGuiTiles(gui);
			}
		}

		if (board.isCheck()) {
			Coordinate king = board.getKingCoordinate();
			Tile gui = new Tile(king, Alliance.HIGHLIGHT_C);
			Window.getInstance().getPanel().addGuiTiles(gui);
		}

//		Board opponentDummy = DeepCloner.getInstance().deepClone(board.getOpponentBoard());
//		opponentDummy.setOpponentBoard(board);
//		opponentDummy.generateOpponentPositions();
//		if (opponentDummy.isCheck()) {
//			Coordinate king = opponentDummy.getKingCoordinate();
//			Tile gui = new Tile(king, Alliance.HIGHLIGHT_C);
//			Window.getInstance().getPanel().addGuiTiles(gui);
//		}
	}

	public void listenToMouseClick(Coordinate mouse) {

		Window.getInstance().getPanel().setClickedTile(new Tile(mouse, Alliance.HIGHLIGHT_S));
		Piece pieceOnTile = board.getTiles()[mouse.getX()][mouse.getY()].getPieceOnTile();

		if (turn && pieceOnTile != null && pieceOnTile.isAlive()
				&& pieceOnTile.getColor() == board.getPlayerAlliance()) {
			pieceOnTile.generateValidMoves(board, mouse);
			pieceOnTile.handleCheckCondition(board, mouse);
		}
	}

	public void terminate() {
		if (isTerminated)
			return;

		isTerminated = true;
		System.out.println("GAME OVER : CHECKMATE");
	}

	public Board getBoard() {
		return board;
	}
}
