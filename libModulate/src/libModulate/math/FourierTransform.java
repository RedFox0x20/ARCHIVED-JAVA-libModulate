package libModulate.math;

public class FourierTransform {

	// CalculateDFTAverageMagnitude
	// Calculates the average magnitude of a given series of samples using a DFT
	// implementation.
	public static double CalculateDFTAverageMagnitude(double[] Samples, double Frequency, int Offset, double SampleRate,
			double WindowLength) {
		double Real = 0;
		double Imaginary = 0;

		// Calculate DFT across the samples
		for (int i = 0; i < WindowLength; i++) {
			int Sample = i + Offset;
			if (Sample >= Samples.length) {
				break;
			}
			double Angle = 2 * Math.PI * Frequency * i / SampleRate;

			Real += Math.cos(Angle) * Samples[Sample];
			Imaginary += -Math.sin(Angle) * Samples[Sample];
		}

		// Calculate the average over the sample window
		Real /= WindowLength;
		Imaginary /= WindowLength;

		// Return the magnitude
		return Math.sqrt((Real * Real) + (Imaginary * Imaginary));
	}
}
