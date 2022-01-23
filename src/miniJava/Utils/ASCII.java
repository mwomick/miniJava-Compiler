package miniJava.Utils;

public final class ASCII {
	
	public static boolean isDigit(char c) {
		return c > 47 && c < 58;
	}
	
	public static boolean isLowercase(char c) {
		return c > 96 && c < 123;
	}
	
	public static boolean isUppercase(char c) {
		return c > 64 && c < 91;
	}
	
	public static boolean isChar(char c) {
		return isLowercase(c) || isUppercase(c);
	}

}
