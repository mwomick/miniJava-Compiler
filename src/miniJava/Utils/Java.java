package miniJava.Utils;

public final class Java {
	
	public static boolean isIdentifier(String s) {
		if(ASCII.isLowercase(s.charAt(0))) {
			for(char c : s.toCharArray()) {
				if(ASCII.isDigit(c) || ASCII.isChar(c) || c == '_') {
					continue;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	public static boolean isInteger(String s) {
		for(char c : s.toCharArray()) {
			if(c < 48 && c > 57) {
				return false;
			}
		}
		return true;
	}
	
}
