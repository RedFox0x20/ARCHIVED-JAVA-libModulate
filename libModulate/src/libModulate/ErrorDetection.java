package libModulate;

public class ErrorDetection {

	// ParityCheckLast
	// Performs a mod2 against each bit from offset for a count of checkBitsCount
	// and compares it to the parity bit at: offset (from 0)+checkBitsCount
	// i.e. ([0,1,1,1,0,1], 0, 5) would be tested as
	// -- bits: [01110], SignalParity: 1, RealParity: 1
	// Returns true if error detected
	public static Boolean ParityCheckLast(byte[] bits, int offset, int checkBitsCount) {
		if (offset + checkBitsCount > bits.length) {
			return true;
		}

		byte RealParity = 0;
		byte SignalParity = bits[offset + checkBitsCount];
		for (int i = offset; i < offset + checkBitsCount; i++) {
			RealParity ^= bits[i];
		}

		if (RealParity != SignalParity) {
			return true;
		} else {
			return false;
		}
	}

	// ParityCheckFirst
	// Performs a mod2 against each bit from offset+1 for a count of checkBitsCount
	// and compares it to the parity bit at: offset
	// i.e. ([0,1,1,1,0,1], 0, 5) would be tested as
	// -- bits: [11101], SignalParity: 0, RealParity: 0
	// Returns true if error detected
	public static Boolean ParityCheckFirst(byte[] bits, int offset, int checkBitsCount) {
		if (offset + checkBitsCount > bits.length) {
			return true;
		}

		byte RealParity = 0;
		byte SignalParity = bits[offset];
		for (int i = offset + 1; i <= offset + checkBitsCount; i++) {
			RealParity ^= bits[i];
		}

		if (RealParity != SignalParity) {
			return true;
		} else {
			return false;
		}
	}
}
