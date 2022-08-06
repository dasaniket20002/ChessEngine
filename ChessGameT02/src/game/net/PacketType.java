package game.net;

public enum PacketType {

	INVALID("0"), LOGIN("A"), BOARD("B"), CHECKMATE("C");

	private String val;

	PacketType(String val) {
		this.val = val;
	}

	public String getValue() {
		return val;
	}

	public static PacketType decode(String enc) {
		for (PacketType p : PacketType.values()) {
			if (p.getValue().equals(enc))
				return p;
		}
		return INVALID;
	}

}
