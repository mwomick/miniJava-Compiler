package miniJava;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenKind;
import miniJava.SyntacticAnalyzer.SyntaxError;

public class Recognizer {

	public static void main(String[] args) {
		InputStream inputStream = new ByteArrayInputStream("class p { // this is a comment\n }\u0000".getBytes());

		Scanner scanner;
		try {
			scanner = new Scanner(inputStream);
			Parser parser = new Parser(scanner);
			parser.parseProgram();
			/* Token tok = scanner.scan();
			while(tok.kind != TokenKind.EOT) {
				System.out.println("TOKEN - Kind: " + tok.kind + " Spelling: |" + tok.spelling + "|");
				tok = scanner.scan();
			} */
		} catch (SyntaxError e) {
			System.out.print("Syntax Error - ");
			System.out.print(e.getMessage());
		}

	}
	
}
