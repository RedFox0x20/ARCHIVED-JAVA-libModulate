package libModulate.utils;

public class BitModifiers {
	// PackBits
	// Packs an array of individual bits to an array of bytes
	// [ 0, 0, 0, 0, 0, 1, 1, 0 ] would become [ 6 ]
	public static byte[] PackBits(byte[] Bits) {
		int NumBytes = (Bits.length / 8) + (Bits.length % 8);
		byte[] data = new byte[NumBytes];

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
			data[i] = res;
		}

		return data;
	}

	// PackBits
	// Unpacks a byte into an array of individual bits
	// 6 would become [ 0, 0, 0, 0, 0, 1, 1, 0 ]
	public static byte[] UnpackByte(byte data) {
		byte[] Bits = new byte[8];
		Bits[0] = (byte) ((data & (1 << 7)) > 0 ? 1 : 0);
		Bits[1] = (byte) ((data & (1 << 6)) > 0 ? 1 : 0);
		Bits[2] = (byte) ((data & (1 << 5)) > 0 ? 1 : 0);
		Bits[3] = (byte) ((data & (1 << 4)) > 0 ? 1 : 0);
		Bits[4] = (byte) ((data & (1 << 3)) > 0 ? 1 : 0);
		Bits[5] = (byte) ((data & (1 << 2)) > 0 ? 1 : 0);
		Bits[6] = (byte) ((data & (1 << 1)) > 0 ? 1 : 0);
		Bits[7] = (byte) ((data & 1) > 0 ? 1 : 0);
		return Bits;
	}

	// PackBits
	// Unpacks an array of bytes into an array of individual bits
	// [ 6, 1 ] would become [ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 ]
	public static byte[] UnpackByteArray(byte[] data) {
		byte[] Bits = new byte[data.length * 8];

		for (int i = 0; i < data.length; i++) {
			Bits[i * 8] = (byte) ((data[i] & (1 << 7)) > 0 ? 1 : 0);
			Bits[i * 8 + 1] = (byte) ((data[i] & (1 << 6)) > 0 ? 1 : 0);
			Bits[i * 8 + 2] = (byte) ((data[i] & (1 << 5)) > 0 ? 1 : 0);
			Bits[i * 8 + 3] = (byte) ((data[i] & (1 << 4)) > 0 ? 1 : 0);
			Bits[i * 8 + 4] = (byte) ((data[i] & (1 << 3)) > 0 ? 1 : 0);
			Bits[i * 8 + 5] = (byte) ((data[i] & (1 << 2)) > 0 ? 1 : 0);
			Bits[i * 8 + 6] = (byte) ((data[i] & (1 << 1)) > 0 ? 1 : 0);
			Bits[i * 8 + 7] = (byte) ((data[i] & 1) > 0 ? 1 : 0);
		}

		return Bits;
	}

	// InvertBit
	// Inverts a single byte
	// 0b01010101 would become 0b10101010
	public static byte InvertByte(byte data) {
		return (byte) (data ^ 0xFF);
	}

	// InvertBits
	// Inverts an array of bytes
	// [ 0b00001111, 0b11110000] would become [ 0b11110000, 0b00001111 ]
	public static byte[] InvertByteArray(byte[] data) {
		byte[] Results = data;
		for (int i = 0; i < Results.length; i++) {
			Results[i] = (byte) (Results[i] ^ 0xFF);
		}
		return Results;
	}

	// ReverseByte
	// Reverses the bits in a byte
	// 0b01100000 would become 0b00000110
	public static byte ReverseByte(byte data) {
		byte Res = 0;
		for (int n = 0; n < 8; n++) {
			Res |= (data & n) << (7 - n);
		}
		return Res;
	}

	// ReverseByteArray
	// Reverses the bits in an array of bytes
	// [0b01100000, 0b10010110] would become [0b00000110, 0b01101001]
	public static byte[] ReverseByteArray(byte[] data) {
		byte[] Results = new byte[data.length];
		for (int i = 0; i < Results.length; i++) {
			Results[i] = ReverseByte(data[i]);
		}
		return Results;
	}
	
	// FlipByteArray
	// Reverses all bytes in an array and reverses the order
	// [ 11001100, 10101010 ] would become [ 01010101, 00110011 ]
	public static byte[] FlipByteArray(byte[] data) {
		byte[] Results = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			Results[data.length - i - 1] = ReverseByte(data[i]);
		}
		return Results;
	}

}
