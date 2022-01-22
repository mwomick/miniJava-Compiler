package miniJava.SyntacticAnalyzer;

import miniJava.Utils.Java;

final class Token {
	
	public TokenKind kind;
	public String spelling;
	
	public Token(String spelling) throws Exception {
		this.spelling = spelling;
		
		for(TokenKind kind : TokenKind.values()) {
			if(kind.spelling.equals(this.spelling)) {
				this.kind = kind;
			}
		}
		if(this.kind == null) {
			if(Java.isInteger(this.spelling)) {
				this.kind = TokenKind.LITERAL;
			}
			else if(Java.isIdentifier(this.spelling)){
				this.kind = TokenKind.IDENTIFIER;
			}
			else {
				throw new SyntaxError("Unrecognized token");
			}
		}
	}
	
}
