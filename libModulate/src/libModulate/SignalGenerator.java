package libModulate;

public class SignalGenerator {
	public static Signal GenerateSine(double sampleRate, int numSamples, double frequency, double phaseDeg, double amplitude) {
		double[] Wave = new double[numSamples]; 
		double[] WavePhase = new double[numSamples];

		// Setup the initial phase and Wave value
		Wave[0] = amplitude * Math.sin(phaseDeg * Math.PI / 180);
		WavePhase[0] = phaseDeg;

		// Generate the resulting sine wave
		for (int i = 1; i < numSamples; i++) {
			WavePhase[i] = (frequency * 360 / sampleRate) + WavePhase[i - 1];
			Wave[i] = amplitude * Math.sin(WavePhase[i] * Math.PI / 180);
		}

		// Return the generated sine wave
		Signal OutSignal = new Signal();
		OutSignal.setSampleRate(sampleRate);
		OutSignal.setSamples(Wave);
		OutSignal.setPhases(WavePhase);
		return OutSignal;
	}
	
	public static Signal GenerateCosine(double sampleRate, int numSamples, double frequency, double phaseDeg, double amplitude) {
		return GenerateSine(sampleRate, numSamples, frequency, phaseDeg + 90, amplitude);
	}
}
