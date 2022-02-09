package libModulate;

// BitAssigner
// Provides a method of converting from an array of symbols to an array of unpacked bits
public class BitAssigner {
	private int BitsPerSymbol;
	private Assignment_OneToMany[] Assignments;

	public BitAssigner() {
	}

	public BitAssigner(Assignment_OneToMany[] Assignments, int assignmentLength) {
		setAssignmentLength(assignmentLength);
		setAssignments(Assignments);
	}

	// ApplyAssignments
	// Apply the stored symbol to bit assignments and return an array of unpacked
	// bits
	public byte[] ApplyAssignments(byte[] Symbols) {
		int NumBits = Symbols.length * BitsPerSymbol;
		byte[] Bits = new byte[NumBits];

		int BitIndex = 0;
		for (byte Symbol : Symbols) {
			for (Assignment_OneToMany Assignment : Assignments) {
				if (Assignment.getKey() == Symbol) {
					for (byte Bit : Assignment.getValues()) {
						Bits[BitIndex++] = Bit;
					}
				}
			}
		}

		return Bits;
	}

	public int getAssignmentLength() {
		return BitsPerSymbol;
	}

	public void setAssignmentLength(int assignmentLength) {
		BitsPerSymbol = assignmentLength;
	}

	public Assignment_OneToMany[] getAssignments() {
		return Assignments;
	}

	public Boolean setAssignments(Assignment_OneToMany[] assignments) {
		for (Assignment_OneToMany AssignmentVal : assignments) {
			if (AssignmentVal.getValues().length != BitsPerSymbol) {
				return false;
			}
		}
		Assignments = assignments;
		return true;
	}

	public static final Assignment_OneToMany[] DEFAULT_ASSIGNMENT_FSK_BITS = new Assignment_OneToMany[] {
			new Assignment_OneToMany((byte) 0, new byte[] { 0, 0 }),
			new Assignment_OneToMany((byte) 1, new byte[] { 0, 1 }) };

	public static final Assignment_OneToMany[] DEFAULT_ASSIGNMENT_MFSK4_BITS = new Assignment_OneToMany[] {
			new Assignment_OneToMany((byte) 0, new byte[] { 0, 0 }),
			new Assignment_OneToMany((byte) 1, new byte[] { 0, 1 }),
			new Assignment_OneToMany((byte) 2, new byte[] { 1, 0 }),
			new Assignment_OneToMany((byte) 3, new byte[] { 1, 1 }) };

	public static final Assignment_OneToMany[] DEFAULT_ASSIGNMENT_MFSK16_BITS = new Assignment_OneToMany[] {
			new Assignment_OneToMany((byte) 0, new byte[] { 0, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 1, new byte[] { 0, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 2, new byte[] { 0, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 3, new byte[] { 0, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 4, new byte[] { 0, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 5, new byte[] { 0, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 6, new byte[] { 0, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 7, new byte[] { 0, 1, 1, 1 }),
			new Assignment_OneToMany((byte) 8, new byte[] { 1, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 9, new byte[] { 1, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 10, new byte[] { 1, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 11, new byte[] { 1, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 12, new byte[] { 1, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 13, new byte[] { 1, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 14, new byte[] { 1, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 15, new byte[] { 1, 1, 1, 1 }) };

	public static final Assignment_OneToMany[] DEFAULT_ASSIGNMENT_MFSK32_BITS = new Assignment_OneToMany[] {
			new Assignment_OneToMany((byte) 0, new byte[] { 0, 0, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 1, new byte[] { 0, 0, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 2, new byte[] { 0, 0, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 3, new byte[] { 0, 0, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 4, new byte[] { 0, 0, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 5, new byte[] { 0, 0, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 6, new byte[] { 0, 0, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 7, new byte[] { 0, 0, 1, 1, 1 }),
			new Assignment_OneToMany((byte) 8, new byte[] { 0, 1, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 9, new byte[] { 0, 1, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 10, new byte[] { 0, 1, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 11, new byte[] { 0, 1, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 12, new byte[] { 0, 1, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 13, new byte[] { 0, 1, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 14, new byte[] { 0, 1, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 15, new byte[] { 0, 1, 1, 1, 1 }),
			new Assignment_OneToMany((byte) 16, new byte[] { 1, 0, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 17, new byte[] { 1, 0, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 18, new byte[] { 1, 0, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 19, new byte[] { 1, 0, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 20, new byte[] { 1, 0, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 21, new byte[] { 1, 0, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 22, new byte[] { 1, 0, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 23, new byte[] { 1, 0, 1, 1, 1 }),
			new Assignment_OneToMany((byte) 24, new byte[] { 1, 1, 0, 0, 0 }),
			new Assignment_OneToMany((byte) 25, new byte[] { 1, 1, 0, 0, 1 }),
			new Assignment_OneToMany((byte) 26, new byte[] { 1, 1, 0, 1, 0 }),
			new Assignment_OneToMany((byte) 27, new byte[] { 1, 1, 0, 1, 1 }),
			new Assignment_OneToMany((byte) 28, new byte[] { 1, 1, 1, 0, 0 }),
			new Assignment_OneToMany((byte) 29, new byte[] { 1, 1, 1, 0, 1 }),
			new Assignment_OneToMany((byte) 30, new byte[] { 1, 1, 1, 1, 0 }),
			new Assignment_OneToMany((byte) 31, new byte[] { 1, 1, 1, 1, 1 }) };
}
