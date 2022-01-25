package miniJava.SyntacticAnalyzer;

public class Parser {

	Scanner scanner;
	
	Token currentToken;
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
		currentToken = scanner.next();
	}
	
	public boolean parse() throws SyntaxError {
		boolean e = parseExpr();
		System.out.println("Terminated on: " + currentToken.kind);
		return e;
	}
	
	private void accept() {
		currentToken = scanner.next();
	}
	
	private boolean expect(TokenKind kind) {
		if(kind == currentToken.kind) {
			currentToken = scanner.next();
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean parseReference() {
		switch(currentToken.kind) {
		case IDENTIFIER:
		case THIS:
			accept();
			break;
		default:
			return false;
		}
		
		if(expect(TokenKind.DOT)) {
			if(!expect(TokenKind.IDENTIFIER)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean parseArgs() {
		while(parseExpr()) {
			if(expect(TokenKind.COMMA)) {
				continue;
			}
			else if(expect(TokenKind.RPAREN)) {
				return true;
			}
			else {
				break;
			}
		}
		return false;
	}
	
	private boolean parseUnop() {
		if(expect(TokenKind.NOT) 
			|| expect(TokenKind.MINUS)) {
			return true;
		}
		return false;
	}
	
	private boolean parseExpr() {
		boolean res = false;
		while(parseExpression()) {
			res = true;
			if(expect(TokenKind.PLUS)
				|| expect(TokenKind.MINUS)
				|| expect(TokenKind.DIV)
				|| expect(TokenKind.MULT)) {
				res = false;
				continue;
			}
			else {
				break;
			}
		}
		return res;
	}
	
	private boolean parseExpression() {
		if(parseReference()) {
			if(expect(TokenKind.LSQUARE)) {
				return parseExpr() && expect(TokenKind.RSQUARE);
			}
			else if(expect(TokenKind.LPAREN)) {
				return parseArgs();
			}
			return true;
		}
		else if(parseUnop()) {
			return parseExpr();
		}
		else if(expect(TokenKind.LPAREN)) {
			return parseExpr() && expect(TokenKind.RPAREN);
		}
		else if(expect(TokenKind.LITERAL) 
				|| expect(TokenKind.TRUE)
				|| expect(TokenKind.FALSE)) {
			return true;
		}
		else if(expect(TokenKind.NEW)) {
			if(expect(TokenKind.IDENTIFIER)) {
				if(expect(TokenKind.LPAREN)) {
					return parseExpr() && expect(TokenKind.RPAREN);
				}
				else if(expect(TokenKind.LSQUARE)) {
					return parseExpr() && expect(TokenKind.RSQUARE);						
				}
			}
			else if(expect(TokenKind.INT)) {
				return expect(TokenKind.LSQUARE) 
						&& parseExpr() 
						&& expect(TokenKind.RSQUARE);
			}
		}
		return false;
	}
}
