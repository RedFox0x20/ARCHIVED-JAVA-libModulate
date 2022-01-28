package libModulate;

import libModulate.utils.ArrayUtils;

// SymbolAssigner
// A class that provides translation from an array of unpacked bits to symbols that can be used in modulation
public class SymbolAssigner {
	private int BitsPerSymbol;
	private Assignment_ManyToOne[] Assignments;

	public SymbolAssigner() {
	}

	public SymbolAssigner(Assignment_ManyToOne[] assignments) {
		setAssignments(assignments);
	}

	// ApplyAssignments
	// Applies the stored assignments to the bits
	// Looks at BitsPerSymbol bits and resolves a symbol from the Assignments list.
	public byte[] ApplyAssignments(byte[] bits) {
		int NumSymbols = (int) Math.ceil((double) bits.length / (double) getBitsPerSymbol());
		byte[] Symbols = new byte[NumSymbols];

		int SymbolIndex = 0;
		int BitIndex = 0;
		while (BitIndex < bits.length) {
			byte[] ComparisonBits = new byte[getBitsPerSymbol()];
			for (int i = 0; i < getBitsPerSymbol(); i++) {
				if (BitIndex >= bits.length) { break; }
				ComparisonBits[i] = bits[BitIndex++];
			}
			Boolean Found = false;
			for (Assignment_ManyToOne assignment : Assignments) {
				if (ArrayUtils.ArrayMatches(assignment.getKey(), ComparisonBits)) {
					Symbols[SymbolIndex++] = assignment.getValue();
					Found = true;
					break;
				}
			}
			if (!Found) {
				// This could also be an exception which may be the better way to handle this in
				// Java?
				return null;
			}
		}

		return Symbols;
	}

	public Assignment_ManyToOne[] getAssignments() {
		return Assignments;
	}

	public Boolean setAssignments(Assignment_ManyToOne[] assignments) {
		for (Assignment_ManyToOne AssignmentVal : assignments) {
			if (AssignmentVal.getKey().length != BitsPerSymbol) {
				return false;
			}
		}
		Assignments = assignments;
		return true;
	}

	public int getBitsPerSymbol() {
		return BitsPerSymbol;
	}

	public void setBitsPerSymbol(int bitsPerSymbol) {
		BitsPerSymbol = bitsPerSymbol;
	}

	public static final Assignment_ManyToOne[] DEFAULT_ASSIGNMENT_FSK_SYMBOLS = new Assignment_ManyToOne[] {
			new Assignment_ManyToOne(new byte[] { 0 }, (byte) 0),
			new Assignment_ManyToOne(new byte[] { 1 }, (byte) 1) };

	public static final Assignment_ManyToOne[] DEFAULT_ASSIGNMENT_MFSK4_SYMBOLS = new Assignment_ManyToOne[] {
			new Assignment_ManyToOne(new byte[] { 0, 0 }, (byte) 0),
			new Assignment_ManyToOne(new byte[] { 0, 1 }, (byte) 1),
			new Assignment_ManyToOne(new byte[] { 1, 0 }, (byte) 2),
			new Assignment_ManyToOne(new byte[] { 1, 1 }, (byte) 3) };

	public static final Assignment_ManyToOne[] DEFAULT_ASSIGNMENT_MFSK16_SYMBOLS = new Assignment_ManyToOne[] {
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 0 }, (byte) 0),
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 1 }, (byte) 1),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 0 }, (byte) 2),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 1 }, (byte) 3),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 0 }, (byte) 4),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 1 }, (byte) 5),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 0 }, (byte) 6),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 1 }, (byte) 7),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 0 }, (byte) 8),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 1 }, (byte) 9),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 0 }, (byte) 10),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 1 }, (byte) 11),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 0 }, (byte) 12),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 1 }, (byte) 13),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 0 }, (byte) 14),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 1 }, (byte) 15) };

	public static final Assignment_ManyToOne[] DEFAULT_ASSIGNMENT_MFSK32_SYMBOLS = new Assignment_ManyToOne[] {
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 0, 0 }, (byte) 0),
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 0, 1 }, (byte) 1),
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 1, 0 }, (byte) 2),
			new Assignment_ManyToOne(new byte[] { 0, 0, 0, 1, 1 }, (byte) 3),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 0, 0 }, (byte) 4),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 0, 1 }, (byte) 5),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 1, 0 }, (byte) 6),
			new Assignment_ManyToOne(new byte[] { 0, 0, 1, 1, 1 }, (byte) 7),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 0, 0 }, (byte) 8),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 0, 1 }, (byte) 9),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 1, 0 }, (byte) 10),
			new Assignment_ManyToOne(new byte[] { 0, 1, 0, 1, 1 }, (byte) 11),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 0, 0 }, (byte) 12),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 0, 1 }, (byte) 13),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 1, 0 }, (byte) 14),
			new Assignment_ManyToOne(new byte[] { 0, 1, 1, 1, 1 }, (byte) 15),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 0, 0 }, (byte) 16),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 0, 1 }, (byte) 17),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 1, 0 }, (byte) 18),
			new Assignment_ManyToOne(new byte[] { 1, 0, 0, 1, 1 }, (byte) 19),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 0, 0 }, (byte) 20),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 0, 1 }, (byte) 21),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 1, 0 }, (byte) 22),
			new Assignment_ManyToOne(new byte[] { 1, 0, 1, 1, 1 }, (byte) 23),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 0, 0 }, (byte) 24),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 0, 1 }, (byte) 25),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 1, 0 }, (byte) 26),
			new Assignment_ManyToOne(new byte[] { 1, 1, 0, 1, 1 }, (byte) 27),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 0, 0 }, (byte) 28),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 0, 1 }, (byte) 29),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 1, 0 }, (byte) 30),
			new Assignment_ManyToOne(new byte[] { 1, 1, 1, 1, 1 }, (byte) 31) };
}
