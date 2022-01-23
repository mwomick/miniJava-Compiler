package miniJava;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenKind;
import miniJava.SyntacticAnalyzer.SyntaxError;

public class Recognizer {

	public static void main(String[] args) {
		InputStream inputStream = new ByteArrayInputStream("public class Sample { public static void main(String[] args) { int e = 42; if(e == 42){ return; }} }\u0000".getBytes());

		Scanner scanner = new Scanner(inputStream);
		try {
			Token tok = scanner.scan();
			while(tok.kind != TokenKind.EOT) {
				System.out.println("READ:" + tok.spelling + "|");
				tok = scanner.scan();
			}
		} catch(SyntaxError e) {
			System.out.println("Syntax Error");
		}
	}
	
}
