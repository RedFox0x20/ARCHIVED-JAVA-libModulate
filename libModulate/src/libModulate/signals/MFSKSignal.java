package libModulate.signals;

public class MFSKSignal extends FSKSignal {

	// MFSKSignal
	// A wrapper around FSKSignal that allows the user to set the BitsPerSymbol on
	// creation
	// FSKSignal contains all necessary code to modulate and demodulate MFSK signals
	// however it's not always necessary for the user to set the BitsPerSymbol and
	// separate naming will allow for more readable code
	public MFSKSignal(double sampleRate, double modulationRate, double bottomFrequency, double frequencyShift,
			double initialPhaseDeg, double amplitude, int bitsPerSymbol) {
		super(sampleRate, modulationRate, bottomFrequency, frequencyShift, initialPhaseDeg, amplitude);
		internal_setBitsPerSymbol(bitsPerSymbol);
	}

	// setBitsPerSymbol
	// Public wrapper for internal_setBitsPerSymbol to allow access to MFSK related
	// methods that are not accessible from the FSKSignal
	// Returns MFSKSignal this to allow chain calls
	public MFSKSignal setBitsPerSymbol(int bitsPerSymbol) {
		internal_setBitsPerSymbol(bitsPerSymbol);
		return this;
	}

}
