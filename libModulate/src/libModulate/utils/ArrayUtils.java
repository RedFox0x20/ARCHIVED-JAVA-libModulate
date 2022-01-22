package libModulate.utils;

public class ArrayUtils {

	public static Boolean ArrayMatches(byte[] a, byte[] b) {
		if (a.length != b.length) { return false; }
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}
	
}
