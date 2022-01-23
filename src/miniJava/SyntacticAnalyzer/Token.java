package miniJava.SyntacticAnalyzer;

import java.util.HashMap;
import java.util.Map;

import miniJava.Utils.Java;

// TODO: should be protected, but for debug purposes, public
public class Token {
	
    static final Map<String, TokenKind> LUT; 

    static {
        LUT = new HashMap<String, TokenKind>();
        for(TokenKind kind : TokenKind.values()) {
        	LUT.put(kind.spelling, kind);
        }
    }
	
	public TokenKind kind;
	public String spelling;
	
	public Token(String spelling) throws SyntaxError {
		this.spelling = spelling;
		
		this.kind = LUT.get(spelling);
		
		if(this.kind == null) {
			if(Java.isInteger(this.spelling)) {
				this.kind = TokenKind.LITERAL;
			}
			else if(Java.isIdentifier(this.spelling)){
				this.kind = TokenKind.IDENTIFIER;
			}
			else {
				throw new SyntaxError("Illegal identifier - \"" + this.spelling + "\"");
			}
		}
	}
	
}
