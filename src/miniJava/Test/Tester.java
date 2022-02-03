package miniJava.Test;

import java.io.IOException;

import miniJava.Compiler;
import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.SyntacticAnalyzer.SyntaxError;

class Tester {

	public static void main(String[] args) {
		for(int i = 0; i < 26; i++) {
			String fileNo = String.format("%03d", i);
			RunTest("test/cases/Test" + fileNo + ".mjava");
		}
	}
	
	public static void RunTest(String filePath) {
		Compiler compiler = new Compiler(filePath);
		boolean shouldPass = false;
		try {
			for(int i = 0; i < 3; i++)
				compiler.inputStream.read();
			char e = (char) compiler.inputStream.read();
			shouldPass = e == 'P';
			compiler.inputStream.read();
		} catch (IOException e) { 
			System.out.println("Illegally formatted test file '" + filePath + "'."); 
		}
		Scanner scanner = new Scanner(compiler.inputStream);
		Parser parser = new Parser(scanner);

		try {
			parser.parse();
			if(shouldPass) {
				System.out.println(filePath + ": Pass");
			}
			else {
				System.err.println(filePath + ": Fail");
			}
		} catch(SyntaxError e) {
			if(!shouldPass) {
				System.out.println(filePath + ": Pass");
			}
			else {
				System.err.println(filePath + ": Fail");
				e.printStackTrace();
			}
		}
	}
}