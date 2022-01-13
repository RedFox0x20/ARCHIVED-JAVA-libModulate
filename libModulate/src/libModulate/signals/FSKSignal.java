package libModulate.signals;

import libModulate.math.FourierTransform;
import libModulate.utils.BitModifiers;

public class FSKSignal extends Signal {
	// The rate at which data is modulated
	private double ModulationRate;
	private double BottomFrequency;
	private double FrequencyShift;

	// Used for MFSK modulation
	private int BitsPerSymbol;

	// Modulation symbol maps
	// Allows the mapping of different bit groups to different frequency levels
	// This allows for features such as Grey encoding to implemented and other tasks
	// such as inverting FSK bits by using:
	// Index: { 0, 1 } // Original frequency level
	// Map: { 1, 0 } // Resulting frequency level
	private byte[] ModulateSymbolMap;
	private byte[] DemodulateSymbolMap;

	// Demodulated bits - Each bit is a single byte, this isn't very memory
	// efficient however makes bit manipulation very simple. Some modes don't always
	// use 8 bits per byte
	private byte[] DemodBits;

	// FSKSignal
	// Allows for the Modulation and Demodulation of an FSKSignal 
	public FSKSignal(double sampleRate, double modulationRate, double bottomFrequency, double frequencyShift,
			double initialPhaseDeg, double amplitude) {
		setSampleRate(sampleRate);
		setAmplitude(amplitude);
		setModulationRate(modulationRate);
		setBottomFrequency(bottomFrequency);
		setFrequencyShift(frequencyShift);
		setInitialPhaseDeg(initialPhaseDeg);
		internal_setBitsPerSymbol(1);
		ModulateSymbolMap = new byte[0];
		DemodulateSymbolMap = new byte[0];
	}

	// XXX: Methods

	// ModulateRawData
	// Takes an array of RAW data bytes, using this method will modulate full 8 bit
	// bytes
	// Returns the object the method was called on to allow chain calling
	public FSKSignal ModulateRawData(byte[] Data) {
		byte[] Bits = BitModifiers.UnpackByteArray(Data);
		byte[] Symbols = ConvertUnpackedBitsToSymbols(Bits);
		ModulateSymbols(Symbols);
		return this;
	}

	// ModulateUnpackedRawBits
	// Takes an array of unpacked bits and modulates them
	// Returns the object the method was called on to allow chain calling
	public FSKSignal ModulateUnpackedRawBits(byte[] Bits) {
		byte[] Symbols = ConvertUnpackedBitsToSymbols(Bits);
		ModulateSymbols(Symbols);
		return this;
	}

	// ConvertUnpackedBitsToSymbols
	// Takes an array of unpacked bits and converts them to the appropriate symbols
	public byte[] ConvertUnpackedBitsToSymbols(byte[] Bits) {
		int NumSymbols = (int) (Bits.length / BitsPerSymbol);
		NumSymbols += Bits.length % BitsPerSymbol;
		byte[] Symbols = new byte[NumSymbols];

		for (int SymbolIndex = 0; SymbolIndex < NumSymbols; SymbolIndex++) {
			for (int BitIndex = 0; BitIndex < BitsPerSymbol; BitIndex++) {
				int DataIndex = SymbolIndex * BitsPerSymbol + BitIndex;
				Symbols[SymbolIndex] |= Bits[DataIndex] << (BitsPerSymbol - BitIndex - 1);
			}
		}

		Symbols = ApplyModulateSymbolMap(Symbols);
		return Symbols;
	}
	
	// ModulateSymbols
	// Takes an array of symbols and generates the appropriate FSK Signal
	// Returns the object the method was called on to allow chain calling
	public FSKSignal ModulateSymbols(byte[] Symbols) {
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
	private byte[] DemodulateSymbols() {
		int NumSymbols = (int) (getSamplesCount() / getWindowLength());
		byte[] Symbols = new byte[NumSymbols];

		int SymbolIndex = 0;
		for (int SampleIndex = 0; SampleIndex < getSamplesCount(); SampleIndex += getWindowLength(), SymbolIndex++) {
			double HighestMagnitude = 0;
			int HighestMagnitudeID = 0;

			double Frequency = getBottomFrequency();
			double[] Magnitudes = new double[getFrequencyLevelsCount()];

			// Iterate over each frequency to get their initial magnitudes
			for (int i = 0; i < getFrequencyLevelsCount(); i++, Frequency += getFrequencyShift()) {
				Magnitudes[i] = FourierTransform.CalculateDFTMagnitude(getSamples(), Frequency, SampleIndex,
						getSampleRate(), getWindowLength());
			}

			// Remove harmonic magnitudes from each frequency to improve accuracy then pick
			// out the highest value
			double[] TrueMagnitudes = FourierTransform.RemoveHarmonicsFromMagnitudes(Magnitudes, getBottomFrequency());

			for (int i = 0; i < getFrequencyLevelsCount(); i++) {
				if (TrueMagnitudes[i] > HighestMagnitude) {
					HighestMagnitude = TrueMagnitudes[i];
					HighestMagnitudeID = i;
				}
			}
			Symbols[SymbolIndex] = (byte) HighestMagnitudeID;
		}
		return Symbols;
	}
	
	// Demodulate
	// Demodulates the given signal into data bits
	// Returns the object the method was called on to allow chain calling
	public FSKSignal Demodulate() {
		byte[] Symbols = ApplyDemodulateSymbolMap(DemodulateSymbols());
		byte[] Bits = new byte[Symbols.length * BitsPerSymbol];

		for (int SymbolIndex = 0; SymbolIndex < Symbols.length; SymbolIndex++) {
			for (int BitIndex = 0; BitIndex < BitsPerSymbol; BitIndex++) {
				byte Bit = (byte) (Symbols[SymbolIndex] & (1 << (BitsPerSymbol - BitIndex - 1)));
				Bits[SymbolIndex * BitsPerSymbol + BitIndex] = (byte) (Bit == 0 ? 0 : 1);
			}
		}

		DemodBits = Bits;
		return this;
	}

	// ApplyModulateSymbolMap
	// Applies the set ModulateSymbolMap to the provided symbols
	// Returns byte[] Mapped symbols
	public byte[] ApplyModulateSymbolMap(byte[] Symbols) {
		if (ModulateSymbolMap.length == 0) {
			return Symbols;
		}

		byte[] Results = Symbols.clone();
		for (int i = 0; i < Results.length; i++) {
			Results[i] = ModulateSymbolMap[Results[i]];
		}
		return Results;
	}

	// ApplyDemodulateSymbolMap
	// Applies the set DemodulateSymbolMap to the provided symbols
	// Returns byte[] Mapped symbols
	public byte[] ApplyDemodulateSymbolMap(byte[] Symbols) {
		if (DemodulateSymbolMap.length == 0) {
			return Symbols;
		}

		byte[] Results = Symbols.clone();
		for (int i = 0; i < Results.length; i++) {
			Results[i] = DemodulateSymbolMap[Results[i]];
		}
		return Results;
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

	public int getBitsPerSymbol() {
		return BitsPerSymbol;
	}

	public int getFrequencyLevelsCount() {
		if (BitsPerSymbol == 1) {
			return 2;
		}
		return BitsPerSymbol * BitsPerSymbol;
	}

	protected void internal_setBitsPerSymbol(double bitsPerSymbol) {
		if (bitsPerSymbol <= 0) {
			bitsPerSymbol = 1;
		} else if (bitsPerSymbol > 8) {
			bitsPerSymbol = 8;
		}
		BitsPerSymbol = (int) bitsPerSymbol;
	}

	public byte[] getModulateSymbolMap() {
		return ModulateSymbolMap;
	}

	public byte[] getDemodulateSymbolMap() {
		return DemodulateSymbolMap;
	}

	public Boolean setModulateSymbolMap(byte[] symbolMap) {
		ModulateSymbolMap = new byte[symbolMap.length];
		DemodulateSymbolMap = new byte[symbolMap.length];

		if (symbolMap.length != 0 && symbolMap.length < getFrequencyLevelsCount()) {
			return false;
		}

		for (int i = 0; i < symbolMap.length; i++) {
			if (symbolMap[i] < 0 || symbolMap[i] >= getFrequencyLevelsCount()) {
				return false;
			} else {
				ModulateSymbolMap[i] = symbolMap[i];
				DemodulateSymbolMap[ModulateSymbolMap[i]] = (byte) (i & 0xFF);
			}
		}
		return true;
	}

	public byte[] getDemodBits() {
		return DemodBits;
	}

	public byte[] getDemodBitsPacked() {
		return BitModifiers.PackBits(DemodBits);
	}
}
