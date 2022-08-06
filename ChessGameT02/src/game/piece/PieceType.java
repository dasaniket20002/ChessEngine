package game.piece;

public enum PieceType {
	EMPTY("0"), PAWN("A"), ROOK("B"), KNIGHT("C"), BISHOP("D"), QUEEN("E"), KING("F");

	private String val;

	PieceType(String val) {
		this.val = val;
	}

	public String getEncodeValue() {
		return val;
	}

	public static PieceType getDecodeType(String val) {
		if (val.equals("A"))
			return PAWN;
		if (val.equals("B"))
			return ROOK;
		if (val.equals("C"))
			return KNIGHT;
		if (val.equals("D"))
			return BISHOP;
		if (val.equals("E"))
			return QUEEN;
		if (val.equals("F"))
			return KING;

		return EMPTY;
	}
}
