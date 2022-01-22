package libModulate;

public class BitAssigner {
	private int BitsPerSymbol;
	private Assignment_OneToMany[] Assignments;

	public BitAssigner() {
	}

	public BitAssigner(Assignment_OneToMany[] Assignments, int assignmentLength) {
		setAssignmentLength(assignmentLength);
		setAssignments(Assignments);
	}

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
}
