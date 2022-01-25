package miniJava.SyntacticAnalyzer;
import java.lang.Error;

public class SyntaxError extends Error {

	SyntaxError() {
		super();
	};
	
	SyntaxError(String s) {
		super(s);
	}
}