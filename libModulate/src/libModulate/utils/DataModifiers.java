package libModulate.utils;

public class DataModifiers {
	// ByteArrayToASCII
	// Iterates over byte[] Data and converts all values in the range of 0-9 to
	// their ASCII equivalent (n + '0')
	public static byte[] ByteArrayToASCII(byte[] Data) {
		byte[] Results = new byte[Data.length];

		for (int i = 0; i < Results.length; i++) {
			byte Original = Data[i];
			if (Original >= 0 && Original <= 9) {
				Results[i] = (byte) (Original + (byte) '0');
			}
		}
		return Results;
	}
}
