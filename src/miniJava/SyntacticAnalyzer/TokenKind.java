package miniJava.SyntacticAnalyzer;

public enum TokenKind {
	CLASS("class"), NEW("new"), PRIVATE("private"),			// keywords 
	PUBLIC("public"), STATIC("static"), VOID("void"),
	BOOL("boolean"), INT("int"),							// type declarations 
	LBRACE("{"), RBRACE("}"), LPAREN("("), RPAREN(")"),		// grouping symbols 
	LSQUARE("["), RSQUARE("]"),
	GT(">"), LT("<"), EQ_EQ("=="), 							// relational operators
	LT_EQ("<="), GT_EQ(">="), NOT_EQ("!="),					
	AND_AND("&&"), OR_OR("||"), NOT("!"), 					// logical symbols
	TRUE("true"), FALSE("false"),
	PLUS("+"), MINUS("-"), MULT("*"), DIV("/"),				// arithmetic operators
	IF("if"), ELSE("else"), 								// control symbols
	WHILE("while"), RETURN("return"),
	SEMICOLON(";"), DOT("."), EQ("="), COMMA(","),
	THIS("this"),
	EOT("\u0000"),											// end of text
	LITERAL("<literal>"), IDENTIFIER("<identifier>");       // operands
	
	public final String spelling;
	
    private TokenKind(String spelling) {
        this.spelling = spelling;
    }
}