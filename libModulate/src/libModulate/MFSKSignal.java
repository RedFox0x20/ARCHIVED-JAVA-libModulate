package libModulate;

public class MFSKSignal extends FSKSignal {

	public MFSKSignal(double sampleRate, double modulationRate, double bottomFrequency, double frequencyShift,
			double initialPhaseDeg, double amplitude, int bitsPerSymbol) {
		super(sampleRate, modulationRate, bottomFrequency, frequencyShift, initialPhaseDeg, amplitude);
		setBitsPerSymbol(bitsPerSymbol);
	}
	
}
