package miniJava.SyntacticAnalyzer;

import java.io.IOException;
import java.io.InputStream;

import miniJava.Utils.ASCII;

public final class Scanner {
	
	private InputStream inputStream;
	
	private char currentChar;
	private StringBuilder currentSpelling;
	
	private boolean eof = false;
	
	public Scanner(InputStream inputStream) {
		this.inputStream = inputStream;
		this.currentSpelling = new StringBuilder();
		readChar();
	}
	
	public Token scan() throws SyntaxError {
		scanSeparator();
		return scanToken();
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	private void scanSeparator() {
		// TODO: scan over comments like WS
		while(currentChar == ' '
				|| currentChar == '\n'
				|| currentChar == '\r'
				|| currentChar == '\t')
			readChar();
	}
	
	private Token scanToken() throws SyntaxError {
		switch(currentChar) {
		case ';':
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
			
		case '\u0000':
			close();
			break;
			
		default:
			while(ASCII.isChar(currentChar) 
					|| ASCII.isDigit(currentChar)) {
				next();
			}			
		}
		
		if(currentSpelling.isEmpty()) { throw new SyntaxError(); }
		
		Token tok = new Token(currentSpelling.toString());
		currentSpelling.setLength(0);
		return tok;
		
	}
	
	private void next() { 
		if(!eof) {
			currentSpelling.append(currentChar);
			readChar();			
		}
	}
	
	private void close() {
		currentSpelling.append(currentChar);
	}
	
	private void readChar() {
		try {
			int c = inputStream.read();
			currentChar = (char) c;
			if(currentChar == '\u0000') { eof = true; }
		} catch (IOException e) {
			eof = true;
			System.out.println("IOException");
			// TODO: handle exception
		}
	}
}
