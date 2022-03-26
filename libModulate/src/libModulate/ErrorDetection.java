package libModulate;

public class ErrorDetection {
	
	// ParityCheck
	// Performs mod2 against each bit in bits then compares the result with signalParity
	// Returns true if the parityBit matches the parity of the bits provided.
	// Returns false if the parity check failed
	public static Boolean ParityCheck(byte[] bits, byte parityBit) {
		byte transmittedParity = 0;
		
		for (byte b : bits) {
			transmittedParity ^= b;
		}
		return transmittedParity == parityBit;
	}
}
