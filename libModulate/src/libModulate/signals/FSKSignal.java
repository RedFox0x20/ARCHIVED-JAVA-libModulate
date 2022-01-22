package libModulate.signals;

import libModulate.math.FourierTransform;

public class FSKSignal extends Signal {
	// The rate at which data is modulated
	private double ModulationRate;
	private double BottomFrequency;
	private double FrequencyShift;

	// Used for MFSK modulation
	private int FrequencyLevelsCount;

	private byte[] Symbols;

	// FSKSignal
	// Allows for the Modulation and Demodulation of an FSKSignal
	public FSKSignal(double sampleRate, double modulationRate, double bottomFrequency, double frequencyShift,
			double initialPhaseDeg, double amplitude) throws Exception {

		setSampleRate(sampleRate);
		setAmplitude(amplitude);
		setModulationRate(modulationRate);
		setBottomFrequency(bottomFrequency);
		setFrequencyShift(frequencyShift);
		setInitialPhaseDeg(initialPhaseDeg);
		internal_setFrequencyLevelsCount(2);

		if (bottomFrequency + (frequencyShift * FrequencyLevelsCount) * 2 > sampleRate) {
			throw new Exception("Sample rate too low!");
		}
	}

	// XXX: Methods
	// Modulate
	// Takes an array of symbols and generates the appropriate FSK Signal
	// Returns the object the method was called on to allow chain calling
	public FSKSignal Modulate() {
		if (Symbols == null) {
			return this;
		}
		int NumberOfSamples = (int) (Symbols.length * getWindowLength());
		double[] Wave = new double[NumberOfSamples];
		double[] WavePhase = new double[NumberOfSamples];

		// Setup the initial phase and Wave value
		WavePhase[0] = getInitialPhaseDeg();
		Wave[0] = getAmplitude() * Math.sin(WavePhase[0] * Math.PI / 180);

		// Generate the resulting sine wave
		for (int i = 1; i < NumberOfSamples; i++) {
			double Frequency = Symbols[(int) (i / getWindowLength())] * FrequencyShift + BottomFrequency;
			WavePhase[i] = (Frequency * 360 / getSampleRate()) + WavePhase[i - 1];
			Wave[i] = getAmplitude() * Math.sin(WavePhase[i] * Math.PI / 180);
		}

		setSamples(Wave);
		setPhases(WavePhase);
		return this;
	}

	// DemodulateSymbols
	// Demodulates the given signal into an array of Symbols
	// Returns byte[] Symbols
	public FSKSignal Demodulate() {
		int NumSymbols = (int) (Math.ceil(getSamplesCount() / getWindowLength()));
		Symbols = new byte[NumSymbols];

		int SymbolIndex = 0;
		for (int SampleIndex = 0; SampleIndex < getSamplesCount(); SampleIndex += getWindowLength(), SymbolIndex++) {
			double HighestMagnitude = 0;
			int HighestMagnitudeID = 0;
			double Frequency = getBottomFrequency();
			double[] Magnitudes = new double[FrequencyLevelsCount];

			// Iterate over each frequency to get their initial magnitudes
			for (int i = 0; i < FrequencyLevelsCount; i++, Frequency += FrequencyShift) {
				Magnitudes[i] = FourierTransform.CalculateDFTAverageMagnitude(getSamples(), Frequency, SampleIndex,
						getSampleRate(), getWindowLength());
			}

			for (int i = 0; i < FrequencyLevelsCount; i++) {
				if (Magnitudes[i] > HighestMagnitude) {
					HighestMagnitude = Magnitudes[i];
					HighestMagnitudeID = i;
				}
			}
			Symbols[SymbolIndex] = (byte) HighestMagnitudeID;
		}
		return this;
	}

	public double getModulationRate() {
		return ModulationRate;
	}

	public void setModulationRate(double modulationRate) {
		ModulationRate = modulationRate;
	}

	public double getBottomFrequency() {
		return BottomFrequency;
	}

	public void setBottomFrequency(double bottomFrequency) {
		BottomFrequency = bottomFrequency;
	}

	public double getFrequencyShift() {
		return FrequencyShift;
	}

	public void setFrequencyShift(double frequencyShift) {
		FrequencyShift = frequencyShift;
	}

	public double getWindowLength() {
		return getSampleRate() / ModulationRate;
	}

	protected Boolean internal_setFrequencyLevelsCount(int frequencyLevelsCount) {
		if (frequencyLevelsCount < 2) {
			frequencyLevelsCount = 2;
		}
		FrequencyLevelsCount = frequencyLevelsCount;
		return true;
	}

	public int getFrequencyLevelsCount() {
		return FrequencyLevelsCount;
	}

	public byte[] getSymbols() {
		return Symbols.clone();
	}

	public void setSymbols(byte[] symbols) {
		Symbols = symbols;
	}
}
