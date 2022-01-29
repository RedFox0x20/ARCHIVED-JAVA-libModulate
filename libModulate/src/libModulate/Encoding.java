package libModulate;

public class Encoding {

	public static final byte[] BAUDOT_ENCODING_LETTERS = new byte[] { 0, 'T', '\r', 'O', ' ', 'H', 'N', 'M', '\n', 'L',
			'R', 'G', 'I', 'P', 'C', 'V', 'E', 'Z', 'D', 'B', 'S', 'Y', 'F', 'X', 'A', 'W', 'J', 1, 'U', 'Q', 'K', 2 };

	public static final byte[] BAUDOT_ENCODING_FIGURES = new byte[] { 0, '5', '\r', '9', ' ', 'H', ',', '.', '\n', ')',
			'4', '&', '8', '0', ':', ';', '3', '"', '$', '?', '\b', '6', '!', '/', '-', '2', '\'', 1, '7', '(', 2 };
}
