package libModulate;

public class Utils {
	// PackBits
	// Packs an array of individual bits to an array of bytes
	// [ 0, 0, 0, 0, 0, 1, 1, 0 ] would become [ 6 ]
	public static byte[] PackBits(byte[] Bits) {
		int NumBytes = (Bits.length / 8) + (Bits.length % 8);
		byte[] Data = new byte[NumBytes];

		for (int i = 0; i < NumBytes; i++) {
			byte res = 0;
			res |= (Bits[i * 8] << 7);
			res |= (Bits[i * 8 + 1] << 6);
			res |= (Bits[i * 8 + 2] << 5);
			res |= (Bits[i * 8 + 3] << 4);
			res |= (Bits[i * 8 + 4] << 3);
			res |= (Bits[i * 8 + 5] << 2);
			res |= (Bits[i * 8 + 6] << 1);
			res |= Bits[i * 8 + 7];
			Data[i] = res;
		}

		return Data;
	}

	// PackBits
	// Unpacks an array of bytes into an array of individual bits
	// [ 6 ] would become [ 0, 0, 0, 0, 0, 1, 1, 0 ]
	public static byte[] UnpackBits(byte[] Data) {
		byte[] Bits = new byte[Data.length * 8];

		for (int i = 0; i < Data.length; i++) {
			Bits[i * 8] = (byte) ((Data[i] & (1 << 7)) > 0 ? 1 : 0);
			Bits[i * 8 + 1] = (byte) ((Data[i] & (1 << 6)) > 0 ? 1 : 0);
			Bits[i * 8 + 2] = (byte) ((Data[i] & (1 << 5)) > 0 ? 1 : 0);
			Bits[i * 8 + 3] = (byte) ((Data[i] & (1 << 4)) > 0 ? 1 : 0);
			Bits[i * 8 + 4] = (byte) ((Data[i] & (1 << 3)) > 0 ? 1 : 0);
			Bits[i * 8 + 5] = (byte) ((Data[i] & (1 << 2)) > 0 ? 1 : 0);
			Bits[i * 8 + 6] = (byte) ((Data[i] & (1 << 1)) > 0 ? 1 : 0);
			Bits[i * 8 + 7] = (byte) ((Data[i] & 1) > 0 ? 1 : 0);
		}

		return Bits;
	}
	
	// InvertBits
	// Inverts an array of bytes
	// [ 0b00001111, 0b11110000] would become [ 0b11110000, 0b00001111 ]
	public static byte[] InvertBits(byte[] Data) {
		byte[] Results = Data;
		for (int i = 0; i < Results.length; i++) {
			Results[i] = (byte) (Results[i] ^ 0xFF);
		}
		return Results;
	}
}
