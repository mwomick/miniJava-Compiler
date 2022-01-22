package miniJava.Utils;

import java.nio.charset.Charset;

public class ASCII {
	
	public static boolean isValid(String s) {
		return Charset.forName("US-ASCII").newEncoder().canEncode(s);
	}
	
	public static boolean isDigit(char c) {
		return c > 47 && c < 58;
	}
	
	public static boolean isLowercase(char c) {
		return c > 97 && c < 123;
	}
	
	public static boolean isUppercase(char c) {
		return c > 96 && c < 122;
	}
	
	public static boolean isChar(char c) {
		return isLowercase(c) || isUppercase(c);
	}

}
