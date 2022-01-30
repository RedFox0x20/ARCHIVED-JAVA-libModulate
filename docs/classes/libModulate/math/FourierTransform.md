# Math - FourierTransform.java

## Methods

### CalculateDFTAverageMagnitude
> ```java
> public static double CalculateDFTAverageMagnitude(double[] Samples, double Frequency, int Offset, double SampleRate, double WindowLength)
> ```

**Description**

> Calculates the Average magnitude of a frequency within a range of the provided samples

**Parameters**

> - Samples
>   - An array of PCM audio samples
> - Frequncy
>   - The frequency to calculate the average DFT magnitude of
> - Offset
>   - How many samples to skip
> - SampleRate
>   - The number of samples per second the provided samples used
> - WindowLenght
>   - The number of samples that should be used to calculate the DFT average

