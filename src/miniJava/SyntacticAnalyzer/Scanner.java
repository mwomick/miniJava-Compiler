package miniJava.SyntacticAnalyzer;

import java.io.IOException;
import java.io.InputStream;

import miniJava.Utils.ASCII;

public final class Scanner {
	
	private InputStream inputStream;
	
	private char currentChar;
	private StringBuilder currentSpelling;
	
	public Scanner(InputStream inputStream) {
		this.inputStream = inputStream;
		readChar();
	}
	
	public Token scan() {
		scanSeparator();
		Token tok = scanToken();
		
		return tok;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	private void scanSeparator() {
		while(currentChar == ' '
				|| currentChar == '\n'
				|| currentChar == '\r'
				|| currentChar == '\t')
			next();
	}
	
	private Token scanToken() {
		
		switch(currentChar) {
		
		case '{':
		case '}':
		case '(':
		case ')':
		case '[':
		case ']':
		case '+':
		case '-':
		case '*':
		case '/':
			next();
			break;
			
		case '!':
		case '>':
		case '<':
		case '=':
			next();
			if(currentChar == '=') {
				next();
			}
			break;
			
		default:
			while(ASCII.isChar(currentChar) 
					|| ASCII.isDigit(currentChar)) {
				next();
			}
			
		}
		
		return new Token(currentSpelling.toString());
		
	}
	
	private void next() {
		currentSpelling.append(currentChar);
		readChar();
	}
	
	private void readChar() {
		try {
			int c = inputStream.read();
			currentChar = (char) c;			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
