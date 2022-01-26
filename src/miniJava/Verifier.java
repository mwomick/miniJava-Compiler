package miniJava;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.SyntacticAnalyzer.SyntaxError;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenKind;

public class Verifier {

	public static void main(String[] args) {
		InputStream inputStream = new ByteArrayInputStream(
				"class Sample { private static boolean e; private int f; private static int g; void test() { int i = 0; } } class Sample2 { int h; public static void test() { int i = 0; }}\u0000".getBytes());

		try {
			Scanner scanner = new Scanner(inputStream);
			Parser parser = new Parser(scanner);
			System.out.println("Is a statement: " + parser.parse());
		} catch (SyntaxError e) {
			System.out.print("Syntax Error - ");
			System.out.print(e.getMessage());
		}

	}
}
