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
	public InputStream inputStream;
	
	public Compiler(String[] args) {
		try {
			init(args[0]);
		} catch (FileFormatException e) {
			error("FileFormatException: File '" + args[0] 
					+ "' is not a .mjava or .java file.");
		}
	}
	
	public Compiler(String filePath) {
		try {
			init(filePath);
		} catch (FileFormatException e) {
			error("FileFormatException: File '" + filePath 
					+ "' is not a .mjava or .java file.");
		}
	}
	
	public void error(String msg) {
		System.err.println(msg);
		System.err.flush();
		System.exit(4);
	}
	
	private void init(String filePath) throws FileFormatException {
		int pos = filePath.length() - 5;
		if(filePath.substring(pos).equals(".java") 
				|| filePath.substring(pos-1).equals(".mjava")) {
			File file = new File(filePath);
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				error("FileNotFoundException: File '" 
						+ filePath 
						+ "' not found.");
			}
		}
		else {
			throw new FileFormatException();
		}
	}
}
