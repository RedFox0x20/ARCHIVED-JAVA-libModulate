package libModulate.signals;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Signal {
	private double[] Samples, Phases;
	private double SampleRate;
	private double Amplitude;
	private double InitialPhaseDeg;

	/*
	 * LoadSignalFromPCM(String filename, double sampleRate, double amplitude)
	 * returns Boolean, true on Success, false on Error
	 * 
	 * Loads 16 Bit Big Endian PCM data from a file
	 */
	public Boolean LoadSignalFromPCM16B(String filename, double sampleRate, double amplitude) throws Exception {
		setSampleRate(sampleRate);
		setAmplitude(amplitude);

		// Load File
		byte[] Data;
		FileInputStream InFile;
		try {
			InFile = new FileInputStream(filename);
			Data = InFile.readAllBytes();
			InFile.close();
		} catch (Exception e) {
			return false;
		}

		// Convert loaded data to samples
		int SamplesCount = Data.length / 2;
		Samples = new double[SamplesCount];

		for (int i = 0; i < SamplesCount; i++) {
			long Temp = (Data[i * 2] & 0xFF) << 8;
			Temp |= Data[i * 2 + 1] & 0xFF;
			Samples[i] = Temp / (((1 << 15) - 1) * amplitude);
		}

		return true;
	}
	
	public void SkipSamplesFromStart(int numSamples) {
		double[] newSamples = new double[Samples.length - numSamples];
		for (int i = 0; i < Samples.length - numSamples; i++) {
			newSamples[i] = Samples[i + numSamples];
		}
		Samples = newSamples;
	}

	/*
	 * WriteSignalToPCM(String filename, double amplitude) returns Boolean, true on
	 * Success, false on Error
	 * 
	 * Writes 16 Bit Big Endian PCM data from a file
	 */
	public Boolean WriteSignalToPCM(String filename) {
		if (Samples.length < 2) {
			return false;
		}

		// Convert samples to PCM16 data
		byte[] Data = new byte[Samples.length * 2];
		for (int i = 0; i < Samples.length; i++) {
			long Temp = (long) (Samples[i] * ((1 << 15) - 1) / Amplitude);
			Data[2 * i] = (byte) (Temp >> 8);
			Data[2 * i + 1] = (byte) Temp;
		}

		// Attempt to open/create the file and write the data to it
		try {
			FileOutputStream OutFile = new FileOutputStream(filename);
			OutFile.write(Data);
			OutFile.close();
			System.out.println("Generated: " + filename);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// XXX: GETTERS & SETTERS

	public double[] getSamples() {
		return Samples;
	}

	public void setSamples(double[] samples) {
		Samples = samples;
	}

	public int getSamplesCount() {
		return Samples.length;
	}

	public double getSampleRate() {
		return SampleRate;
	}

	public void setSampleRate(double sampleRate) {
		if (sampleRate < 0) {
			sampleRate = 1;
		}
		SampleRate = sampleRate;
	}

	public double[] getPhases() {
		return Phases;
	}

	public void setPhases(double[] phases) {
		Phases = phases;
	}

	public double getInitialPhaseDeg() {
		return InitialPhaseDeg;
	}

	public void setInitialPhaseDeg(double initialPhase) {
		InitialPhaseDeg = initialPhase;
	}

	public double getAmplitude() {
		return Amplitude;
	}

	public void setAmplitude(double amplitude) {
		if (amplitude <= 0) {
			amplitude = 0.1;
		} else if (amplitude > 1) {
			amplitude = 1.0;
		}

		Amplitude = amplitude;
	}
}
