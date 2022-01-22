package miniJava.SyntacticAnalyzer;

import java.io.InputStream;

public class Scanner {
	
	private InputStream inputStream;
	
	private char currentChar;
	private StringBuilder currentSpelling;
	
	public Scanner(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}
