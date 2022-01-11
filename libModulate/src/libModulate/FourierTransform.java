package libModulate;

public class FourierTransform {
	/*
	 * CalculateDFTMagnitude Calculates the Discreet Fourier Transform of the Signal
	 * at a given frequency and sample. Returning the magnitude of that frequency
	 * within the bounds of the start sample and the window length of the FSK.
	 */
	public static double CalculateDFTMagnitude(double[] Samples, double Frequency, int Offset, double SampleRate,
			double WindowLength) {
		double Real = 0;
		double Imaginary = 0;

		// Calculate DFT across the samples
		for (int i = 0; i < WindowLength; i++) {
			double Angle = 2 * Math.PI * Frequency * i / SampleRate;
			int Sample = i + Offset;

			Real += Math.cos(Angle) * Samples[Sample];
			Imaginary += -Math.sin(Angle) * Samples[Sample];
		}

		// Return the magnitude
		return Math.sqrt((Real * Real) + (Imaginary * Imaginary));
	}

	// RemoveHarmonicsFromMagnitudes
	// Identifies the harmonics of each frequency and removes them from the known
	// Magnitude at that frequency in theory this will provide a pure magnitude for
	// the amplitude of that frequency which improves comparison accuracy when
	// attempting to determine the symbol value.
	// How I got to this solution is shown here:
	// https://github.com/RedFox0x20/Modulators/issues/1
	public static double[] RemoveHarmonicsFromMagnitudes(double[] Magnitudes, double BottomFrequency) {
		double[] Results = new double[Magnitudes.length];
		Results[0] = Magnitudes[0]; // The bottom frequency can't have harmonics so we wont do anything with it.

		// Iterate over all of the magnitudes testing for harmonics and taking harmonics
		// out of the Sample Frequencies magnitude
		for (int SampleFrequencyIndex = 1; SampleFrequencyIndex < Magnitudes.length; SampleFrequencyIndex++) {
			double SampleFrequency = BottomFrequency + BottomFrequency * SampleFrequencyIndex;
			Results[SampleFrequencyIndex] = Magnitudes[SampleFrequencyIndex];

			for (int HarmonicIndex = 0; HarmonicIndex < SampleFrequencyIndex; HarmonicIndex++) {
				double HarmonicFrequency = BottomFrequency + BottomFrequency * HarmonicIndex;

				if (SampleFrequency % HarmonicFrequency == 0) {
					Results[SampleFrequencyIndex] -= Math.round(Magnitudes[HarmonicIndex]);
				}
			}
		}
		return Results;
	}

}
