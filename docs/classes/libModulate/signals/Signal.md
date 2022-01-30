# Math - Signal.java

## Properties
---
### Samples
```java
private double[] Samples;
```
**Description**

A container to store the loaded/generated signal samples

Getter: `getSamples`, `getSamplesCount`

Setter: `setSamples`

---
### Samples
```java
private double[] Phases;
```
**Description**

A container to store the loaded/generated signal phases

Getter: `getPhases`

Setter: `setPhases`

---
### Samples
```java
private double SampleRate;
```
**Description**

A container to store the sample rate of the loaded/generated signal

Getter: `getSampleRate`

Setter: `setSampleRate`

---
### Amplitude
```java
private double Amplitude;
```
**Description**

A container to store the loaded/generated signals amplitude multiplier

Getter: `getAmplitude`

Setter: `setAmplitude`

---
### Samples
```java
private double InitialPhaseDeg;
```
**Description**

A container to store the initial phase of a signal for generation/demodulation in degrees

Getter: `getInitialPhase`

Setter: `setInitialPhase`

---
## Methods
---

### LoadSignalFromPCM16B
> ```java
> public Boolean LoadSignalFromPCM16B(String filename, double sampleRate, double amplitude)
> ```

**Description**

> Loads the file `filename` as a 16 bit, big endian PCM file

**Parameters**

> - filename
>   - The path of the file to load
> - sampleRate
>   - The sample rate that the file is stored in
> - amplitude
>   - A multiplier that allows the amplitude of the signal to be changed when loaded

---

### SkipSamplesFromStart
> ```java
> public void SkipSamplesFromStart(int numSamples)
> ```

**Description**

> Removes `numSamples` from the front of `this.Samples`

**Parameters**

> - numSamples
>   - The number of samples to remove

---

### WriteSignalToPCM
> ```java
> public Boolean WriteSignalToPCM(String filename)
> ```

**Description**

> Saves `this.Samples` as to `filename` using 16 bit Big Endian PCM format

**Parameters**

> - filename
>   - The path of the file to write to
