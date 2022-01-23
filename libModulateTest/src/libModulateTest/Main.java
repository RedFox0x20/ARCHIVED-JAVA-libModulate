package libModulateTest;

import libModulate.BitAssigner;
import libModulate.SymbolAssigner;
import libModulate.Assignment_ManyToOne;
import libModulate.Assignment_OneToMany;
import libModulate.signals.MFSKSignal;
import libModulate.utils.BitModifiers;
import libModulate.utils.DataModifiers;

public class Main {

	public static void main(String[] args) {
		MFSKSignal Sig;
		try {
			Sig = new MFSKSignal(8000, 10, 100, 50, 0, 1, 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		SymbolAssigner FSKSymbolAssigner = new SymbolAssigner();
		FSKSymbolAssigner.setBitsPerSymbol(1);
		FSKSymbolAssigner
				.setAssignments(new Assignment_ManyToOne[] { new Assignment_ManyToOne(new byte[] { 0 }, (byte) 0),
						new Assignment_ManyToOne(new byte[] { 1 }, (byte) 1) });

		BitAssigner FSKBitAssigner = new BitAssigner();
		FSKBitAssigner.setAssignmentLength(1);
		FSKBitAssigner.setAssignments(new Assignment_OneToMany[] { new Assignment_OneToMany((byte) 0, new byte[] { 0 }),
				new Assignment_OneToMany((byte) 1, new byte[] { 1 }) });

		byte[] SampleData = new byte[] { 'H', 'E', 'L', 'L', 'O', ' ', 'W', 'O', 'R', 'L', 'D', '!' };
		byte[] SampleDataBits = BitModifiers.UnpackByteArray(SampleData);
		byte[] SampleDataSymbols = FSKSymbolAssigner.ApplyAssignments(SampleDataBits);

		// XXX: TEST - Modulation of user-provided RAW BYTES
		Sig.setSymbols(SampleDataSymbols);
		Sig.Modulate();
		Sig.WriteSignalToPCM("RawData.8000.16b.pcm", 1.0);
		Sig.Demodulate();

		byte[] DemodSymbols = Sig.getSymbols();
		byte[] DemodBits = FSKBitAssigner.ApplyAssignments(DemodSymbols);
		byte[] DemodBitsASCII = DataModifiers.ByteArrayToASCII(DemodBits);
		byte[] PackedDemodBits = BitModifiers.PackBits(DemodBits);
		System.out.println("Started with:");
		PrintTwoColumns("15", "\tRawBytes", new String(SampleData));
		PrintTwoColumns("15", "\tRawBits", new String(DataModifiers.ByteArrayToASCII(SampleDataBits)));
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(SampleDataSymbols)));
		System.out.println("\nDemod to:");
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(DemodSymbols)));
		PrintTwoColumns("15", "\tRawBits", new String(DemodBitsASCII));
		PrintTwoColumns("15", "\tRawBytes", new String(PackedDemodBits));
		System.out.println();

		// XXX: EXAMPLE :: Using a repeat frequency

		// Shift the frequency up 1
		FSKSymbolAssigner
				.setAssignments(new Assignment_ManyToOne[] { new Assignment_ManyToOne(new byte[] { 0 }, (byte) 1),
						new Assignment_ManyToOne(new byte[] { 1 }, (byte) 2) });

		// Shift the symbol down 1
		FSKBitAssigner.setAssignments(new Assignment_OneToMany[] { new Assignment_OneToMany((byte) 1, new byte[] { 0 }),
				new Assignment_OneToMany((byte) 2, new byte[] { 1 }) });

		// Apply our shift to the sample bits -- This could be done within the following
		// loop however in more complex scenarios this method may be easier to maintain
		byte[] SymbolsWithoutRepeats = FSKSymbolAssigner.ApplyAssignments(SampleDataBits.clone());

		// Look for duplicate symbols, replace our duplicate with our repeat symbol (0)
		for (int i = 1; i < SymbolsWithoutRepeats.length; i++) {
			if (SymbolsWithoutRepeats[i] == SymbolsWithoutRepeats[i - 1]) {
				SymbolsWithoutRepeats[i] = 0;
			}
		}

		Sig.setSymbols(SymbolsWithoutRepeats);
		Sig.Modulate();
		Sig.WriteSignalToPCM("RawData_RepeatOnZero.8000.16b.pcm", 1.0);
		Sig.Demodulate();
		DemodSymbols = Sig.getSymbols();

		// Detect the repeat symbol (0) and swap it for the previous symbol
		for (int i = 1; i < DemodSymbols.length; i++) {
			if (DemodSymbols[i] == 0) {
				DemodSymbols[i] = DemodSymbols[i - 1];
			}
		}

		DemodBits = FSKBitAssigner.ApplyAssignments(DemodSymbols);
		DemodBitsASCII = DataModifiers.ByteArrayToASCII(DemodBits);
		PackedDemodBits = BitModifiers.PackBits(DemodBits);
		System.out.println("Started with:");
		PrintTwoColumns("15", "\tRawBytes", new String(SampleData));
		PrintTwoColumns("15", "\tRawBits", new String(DataModifiers.ByteArrayToASCII(SampleDataBits)));
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(SampleDataSymbols)));
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(SymbolsWithoutRepeats)));
		System.out.println("\nDemod to:");
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(Sig.getSymbols())));
		PrintTwoColumns("15", "\tSYMBOLS", new String(DataModifiers.ByteArrayToASCII(DemodSymbols)));
		PrintTwoColumns("15", "\tRawBits", new String(DemodBitsASCII));
		PrintTwoColumns("15", "\tRawBytes", new String(PackedDemodBits));
		System.out.println();
	}

	private static void PrintTwoColumns(String length, String left, String right) {
		System.out.printf("%-" + length + "s %s\n", left, right);
	}
}
