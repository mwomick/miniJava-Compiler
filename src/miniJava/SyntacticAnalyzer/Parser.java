package miniJava.SyntacticAnalyzer;

public class Parser {

	Scanner scanner;
	
	Token currentToken;
	int count;
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
		currentToken = scanner.next();
	}
	
	public boolean parse() throws SyntaxError {
		boolean e = parseStatement();
		System.out.println("Terminated on: " + currentToken.kind);
		return e;
	}
	
	private boolean expect(TokenKind kind) {		
		if(kind == currentToken.kind) {
			count++;
			currentToken = scanner.next();
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean parseStatement() {
		if(expect(TokenKind.INT) || expect(TokenKind.BOOL)) {
			return expect(TokenKind.IDENTIFIER) 
					&& expect(TokenKind.EQ) 
					&& parseExpr() 
					&& expect(TokenKind.SEMICOLON);
		}
		else if(parseReference()) {
			if(expect(TokenKind.EQ)) {
				return parseExpr() && expect(TokenKind.SEMICOLON);
			}
			else if(expect(TokenKind.LSQUARE)) {
				return parseExpr() && expect(TokenKind.RSQUARE) 
						&& expect(TokenKind.EQ) 
						&& parseExpr()
						&& expect(TokenKind.SEMICOLON);
			}
			else if(expect(TokenKind.LPAREN)) {
				return parseArgs() && expect(TokenKind.SEMICOLON);
			}
		}
		else if(expect(TokenKind.RETURN)) {
			parseExpr();
			return expect(TokenKind.SEMICOLON);
		}
		else if(expect(TokenKind.IF)) {
			boolean res = expect(TokenKind.LPAREN)
							&& parseExpr()
							&& expect(TokenKind.RPAREN)
							&& parseStatement();
			if(expect(TokenKind.ELSE)) { parseStatement(); }
			return res;
		}
		else if(expect(TokenKind.WHILE)) {
			return expect(TokenKind.LPAREN) 
					&& parseExpr()
					&& expect(TokenKind.RPAREN)
					&& parseStatement();
		}
		else if(expect(TokenKind.LBRACE)) {
			int cnt = count;
			while(parseStatement()) { cnt = count; }
			return cnt == count && expect(TokenKind.RBRACE);
		}
		return false;
		
	}
	
	private boolean parseReference() {
		if(expect(TokenKind.IDENTIFIER) 
			|| expect(TokenKind.THIS)) {
			if(expect(TokenKind.DOT)) {
				return expect(TokenKind.IDENTIFIER);
			}
			return true;
		}
		return false;
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
