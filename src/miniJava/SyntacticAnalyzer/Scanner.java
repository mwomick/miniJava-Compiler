package miniJava.SyntacticAnalyzer;

import java.io.IOException;
import java.io.InputStream;

import miniJava.Utils.ASCII;

public final class Scanner {
	
	private InputStream inputStream;
	
	private char currentChar;
	private StringBuilder currentSpelling;
	
	private boolean eof = false;
	
	public Scanner(InputStream inputStream) throws SyntaxError {
		this.inputStream = inputStream;
		this.currentSpelling = new StringBuilder();
		peek();
	}
	
	public Token next() throws SyntaxError {
		scanSeparator();
		return scanToken();
	}
		
	private void reset() {
		currentSpelling.setLength(0);
	}
	
	private void scanSeparator() throws SyntaxError {
		while(currentChar == ' '
				|| currentChar == '\n'
				|| currentChar == '\r'
				|| currentChar == '\t')
			peek();
	}
	
	private Token scanToken() throws SyntaxError {
		switch(currentChar) {

		case ';':	case '.':	case '{':	case '}':
		case '(':	case ')':	case '[':	case ']':
		case '+':	case '-':	case '*':	case ',':
			take();
			break;
			
		case '!':	case '>':	case '<':	case '=':
			take();
			if(currentChar == '=') take();
			break;
			
		case '/':
			take();
			if(currentChar == '/') {
				while(currentChar != '\n' && !eof) {
					peek();
				}
				peek();
				reset();
				return next();
			}
			else if(currentChar == '*') {
				while(true) {
					while(currentChar != '*') {
						peek();
					}
					peek();
					if(currentChar == '/') {
						break;
					}
				}
				peek();
				reset();
				return next();
			}
			else {
				break;
			}	
			
		case 0xFFFF:
			close();
			break;
			
		default:
			while(ASCII.isChar(currentChar) 
					|| ASCII.isDigit(currentChar))
				take();	
		}
		
		if(currentSpelling.isEmpty()) {
			throw new SyntaxError("Encountered invalid symbol: " + (int)currentChar); 
		}
		
		Token tok = new Token(currentSpelling.toString());
		reset();
		return tok;
		
	}
	
	private void take() throws SyntaxError { 
		currentSpelling.append(currentChar);
		peek();			
	}
	
	private void close() {
		currentSpelling.append("EOT");
	}
	
	private void peek() throws SyntaxError {
		try {
			if(eof) { throw new SyntaxError("Unexpectedly reached end of file."); }
			int c = inputStream.read();
			currentChar = (char) c;
			if(c == -1) { eof = true; }
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("IOException");
		}
	}
}