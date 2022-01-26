package miniJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;

public class Compiler {

	public static void main(String[] args) {
		Compiler compiler = new Compiler(args);
		Scanner scanner = new Scanner(compiler.inputStream);
		Parser parser = new Parser(scanner);
		if(parser.parse()) {
			System.out.println("Success.");
			System.exit(0);
		}
		else {
			compiler.error("Something went wrong.");
			System.exit(4);
		}
	}
	
	int rc = 4;
	InputStream inputStream;
	
	Compiler(String[] args) {
		processArgs(args);
	}
	
	private void error(String msg) {
		System.err.println(msg);
		System.err.flush();
		System.exit(4);
	}
	
	private void processArgs(String argv[]) {
		int pos = argv[0].length() - 5;
		if(argv[0].substring(pos).equals(".java") 
				|| argv[0].substring(pos-1).equals(".mjava")) {
			File file = new File(argv[0]);
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				error("FileNotFoundException File '" 
						+ argv[0] 
						+ "' not found.");
			}
		}
	}
}
