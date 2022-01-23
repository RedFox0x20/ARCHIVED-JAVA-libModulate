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
}
