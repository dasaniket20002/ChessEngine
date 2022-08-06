package game.piece;

public enum Alliance {
	HIGHLIGHT_C("C"), HIGHLIGHT_S("S"), HIGHLIGHT_T("T"), HIGHLIGHT_M("M"), HIGHLIGHT_A("A"), WHITE("W"), BLACK("B");

	private String val;

	Alliance(String s) {
		this.val = s;
	}

	public String getEncodeValue() {
		return val;
	}

	public Alliance getDecodedType(String s) {
		if (s.equals("W"))
			return WHITE;
		return BLACK;
	}

	public Alliance getEnemyAlliance() {
		if (this == WHITE) {
			return BLACK;
		}
		return WHITE;
	}
}
