package libModulateTest;

import libModulate.signals.MFSKSignal;
import libModulate.utils.BitModifiers;

public class Main {
	public static void main(String[] args) {
		byte[] SampleData = new byte[] { 'H', 'E', 'L', 'L', 'O', ' ', 'W', 'O', 'R', 'L', 'D', '!' };
		byte[] SampleBits = new byte[] { 0b00000000, (byte) 0b11111111, 0b01010101, (byte) 0b10101010 };
		byte[] SampleSymbols = new byte[] { 1, 2, 3, 0, 0, 0, 0, 3, 2, 1, 2, 1, 2, 3, 0 };
		MFSKSignal Sig = new MFSKSignal(8000, 50, 50, 50, 0, 1, 2);
		byte[] DemodData;

		// XXX: TEST - Modulation of user-provided RAW BYTES
		Sig.ModulateRawData(SampleData);
		Sig.WriteSignalToPCM("RawData.8000.16b.pcm", 1.0);
		Sig.Demodulate();
		DemodData = Sig.getDemodBitsPacked();
		System.out.println("Started with:\tRawBytes " + new String(SampleData));
		System.out.println("Demod to:\tRawBytes " + new String(DemodData));
		System.out.println();

		// XXX: TEST - Modulation of user-provided RAW UNPACKED BITS
		Sig.ModulateUnpackedRawBits(BitModifiers.UnpackByteArray(SampleBits));
		Sig.WriteSignalToPCM("RawUnpackedBits.8000.16b.pcm", 1.0);
		Sig.Demodulate();
		DemodData = Sig.getDemodBits();
		for (int i = 0; i < DemodData.length; i++) {
			DemodData[i] += '0';
		}
		byte[] UnpackedSampleBits = BitModifiers.UnpackByteArray(SampleBits);
		for (int i = 0; i < UnpackedSampleBits.length; i++) {
			UnpackedSampleBits[i] += '0';
		}
		System.out.println("Started with:\tRawBits " + new String(UnpackedSampleBits));
		System.out.println("Demod to:\tRawBits " + new String(DemodData));
		System.out.println();

		// XXX: TEST - Modulation of user-provided symbols
		Sig.ModulateSymbols(SampleSymbols);
		Sig.WriteSignalToPCM("Symbols.8000.16b.pcm", 1.0);
		Sig.Demodulate();
		DemodData = Sig.ConvertUnpackedBitsToSymbols(Sig.getDemodBits());
		for (int i = 0; i < SampleSymbols.length; i++) {
			SampleSymbols[i] += '0';
		}
		for (int i = 0; i < DemodData.length; i++) {
			DemodData[i] += '0';
		}
		
		System.out.println("Started with:\tSymbols " + new String(SampleSymbols));
		System.out.println("Demod to:\tSymbols " + new String(DemodData));
	}
}
