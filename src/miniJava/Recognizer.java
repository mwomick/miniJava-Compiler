package miniJava;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenKind;
import miniJava.SyntacticAnalyzer.SyntaxError;

public class Recognizer {

	public static void main(String[] args) {
		InputStream inputStream = new ByteArrayInputStream("public class Sample { public static void main(String[] args) {  int e = 42;  if(e == 42){ \nreturn; }} }\u0000".getBytes());

		Scanner scanner;
		try {
			scanner = new Scanner(inputStream);
			Token tok = scanner.scan();
			while(tok.kind != TokenKind.EOT) {
				System.out.println("TOKEN - Kind: " + tok.kind+" Spelling: |" + tok.spelling + "|");
				tok = scanner.scan();
			}
		} catch (SyntaxError e) {
			System.out.print(e.getMessage());
		}

	}
	
}
