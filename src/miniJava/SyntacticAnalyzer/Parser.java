package miniJava.SyntacticAnalyzer;

public class Parser {

	Scanner scanner;
	
	private void expect(TokenKind kind) throws SyntaxError {
		Token tok = scanner.scan();
		if(tok.kind != kind) {
			throw new SyntaxError("Expected \"" + kind.spelling + "\" but parsed \"" + tok.spelling + "\"");
		}
	}
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void parseProgram() throws SyntaxError {
		parseClassDecl();
		parseEot();
	}
	
	public void parseClassDecl() throws SyntaxError {
		parseClass();
		parseId();
		parseLbrace(); 
		parseRbrace();
	}
	
	public void parseClass() throws SyntaxError {
		expect(TokenKind.CLASS);
	}
	
	public void parseId() throws SyntaxError {
		expect(TokenKind.IDENTIFIER);
	}
	
	public void parseLbrace() throws SyntaxError {
		expect(TokenKind.LBRACE);
	}
	
	public void parseRbrace() throws SyntaxError {
		expect(TokenKind.RBRACE);
	}

	public void parseEot() throws SyntaxError {
		expect(TokenKind.EOT);
	}
}
