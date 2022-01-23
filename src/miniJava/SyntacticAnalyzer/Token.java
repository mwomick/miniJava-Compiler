package miniJava.SyntacticAnalyzer;

import miniJava.Utils.Java;

// TODO: should be protected, but for debug purposes, public
public class Token {
	
	public TokenKind kind;
	public String spelling;
	
	public Token(String spelling) throws SyntaxError {
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
				throw new SyntaxError("Illegal identifier \"" + this.spelling + "\"");
			}
		}
	}
	
}
