package libModulate;

import libModulate.utils.ArrayUtils;

public class SymbolAssigner {
	private int BitsPerSymbol;
	private Assignment_ManyToOne[] Assignments;
	
	public SymbolAssigner() {}
	public SymbolAssigner(Assignment_ManyToOne[] assignments) {
		setAssignments(assignments);
	}
	
	public byte[] ApplyAssignments(byte[] bits) {
		int NumSymbols = (int)Math.ceil((double)bits.length / (double)getBitsPerSymbol());
		byte[] Symbols = new byte[NumSymbols];
		
		int SymbolIndex = 0;
		int BitIndex = 0;
		while (BitIndex < bits.length) {
			byte[] ComparisonBits = new byte[getBitsPerSymbol()];
			for (int i = 0; i < getBitsPerSymbol(); i++) {
				ComparisonBits[i] = bits[BitIndex++];
			}
			for (Assignment_ManyToOne assignment : Assignments) {
				if (ArrayUtils.ArrayMatches(assignment.getKey(), ComparisonBits)) {
					Symbols[SymbolIndex++] = assignment.getValue();
				}
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
