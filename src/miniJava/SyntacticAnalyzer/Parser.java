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
		boolean e = parseProgram();
		System.out.println("Terminated on: " + currentToken.kind);
		return e && expect(TokenKind.EOT);
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
	
	private boolean parseProgram() {
		int cnt = count;
		while(parseClass()) { 
			cnt = count;
		}
		return cnt == count && expect(TokenKind.EOT);
	}
	
	private boolean parseClass() {
		if(expect(TokenKind.CLASS) 
				&& expect(TokenKind.IDENTIFIER)
				&& expect(TokenKind.LBRACE)) {
			int cnt = count;
			while(parseFieldDecl() || parseMethodDecl()) {
				cnt = count;
			}
			return cnt == count && expect(TokenKind.RBRACE);
		}
		return false;
	}
	
	private boolean parseVisibility() {
		return (expect(TokenKind.PUBLIC) 
				|| expect(TokenKind.PRIVATE));
	}
	
	private boolean parseFieldDecl() {
		parseVisibility();
		expect(TokenKind.STATIC);
		if(parseType()) {
			return expect(TokenKind.IDENTIFIER) 
					&& expect(TokenKind.SEMICOLON);
		}
		return false;
	}
	
	private boolean parseParams() {
		int cnt = count;
		while(parseType() && expect(TokenKind.IDENTIFIER)) {
			if(expect(TokenKind.COMMA)) {
				continue;
			}
			cnt = count;
			break;
		}
		return cnt == count;
	}
	
	private boolean parseMethodDecl() {
		parseVisibility();
		expect(TokenKind.STATIC);
		if(parseType() || expect(TokenKind.VOID)) {
			if(expect(TokenKind.IDENTIFIER) 
					&& expect(TokenKind.LPAREN)
					&& parseParams()
					&& expect(TokenKind.RPAREN)
					&& expect(TokenKind.LBRACE)) {
				int cnt = count;
				while(parseStatement()) {
					cnt = count;
				}
				return cnt == count && expect(TokenKind.RBRACE);
			}			
			return false;
		}
		return false;
	}
	
	private boolean parseType() {
		if(expect(TokenKind.INT) || expect(TokenKind.IDENTIFIER)) {
			if(expect(TokenKind.LSQUARE)) {
				return expect(TokenKind.RSQUARE);
			}
			return true;
		}
		else {
			return expect(TokenKind.BOOL);
		}
	}
	
	private boolean parseStatement() {
		if(parseType()) {
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
			if(res && expect(TokenKind.ELSE)) { 
				return parseStatement();  
			}
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
				|| expect(TokenKind.MULT)
				|| expect(TokenKind.AND_AND)
				|| expect(TokenKind.OR_OR)
				|| expect(TokenKind.GT)
				|| expect(TokenKind.LT)
				|| expect(TokenKind.EQ_EQ)
				|| expect(TokenKind.LT_EQ)
				|| expect(TokenKind.GT_EQ)
				|| expect(TokenKind.NOT_EQ)) {
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
