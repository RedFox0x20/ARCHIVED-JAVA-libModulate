package libModulate.utils;

public class ArrayUtils {

	// ArrayMatches
	// Returns true if the data stored in a is the same as the data stored in b
	public static Boolean ArrayMatches(byte[] a, byte[] b) {
		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}	
		return true;
	}
}
