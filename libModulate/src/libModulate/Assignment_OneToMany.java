package libModulate;

public class Assignment_OneToMany {

	private byte Key;
	private byte[] Values;
	
	public Assignment_OneToMany(byte key, byte[] values) {
		Key = key;
		Values = values;
	}

	public byte getKey() {
		return Key;
	}

	public void getKey(byte symbol) {
		Key = symbol;
	}

	public byte[] getValues() {
		return Values;
	}

	public void setValues(byte[] bits) {
		Values = bits;
	}
}
