package miniJava.SyntacticAnalyzer;

import miniJava.Utils.Java;

// should be protected, but for debug purposes, public
public final class Token {
	
	public TokenKind kind;
	public String spelling;
	
	public Token(String spelling) {
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
				// Handle
			}
		}
	}
	
}
