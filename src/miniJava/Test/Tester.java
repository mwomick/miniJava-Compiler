package miniJava.Test;

import java.io.IOException;

import miniJava.Compiler;
import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;

class Tester {
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			String fileNo = String.format("%03d", i);
			MakeTest("test/cases/Test" + fileNo + ".mjava");
		}
	}
	
	public static void MakeTest(String filePath) {
		Compiler compiler = new Compiler(filePath);
		boolean shouldPass = false;
		try {
			for(int i = 0; i < 3; i++)
				compiler.inputStream.read();
			shouldPass = compiler.inputStream.read() == 'P';
			compiler.inputStream.read();
		} catch (IOException e) { 
			System.out.println("Illegally formatted test file '" + filePath +"'."); 
		}
		Scanner scanner = new Scanner(compiler.inputStream);
		Parser parser = new Parser(scanner);
		if(parser.parse() == shouldPass) {
			System.out.println("Pass.");
		}
		else {
			System.out.println("Fail.");
		}
	}
}