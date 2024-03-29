public class Out {
    Expr expr;

    /**
     * Parses the <out> non-terminal in the Core context-free-grammar, which is defined as:
     *      <out> ::= out ( <expr> ); 
     */
    void parse() {
        Parser.scanner.nextToken();
        Parser.checkCurrentTokenIs(true, Core.LPAREN);

        expr = new Expr();
        expr.parse();

        Parser.checkCurrentTokenIs(true, Core.RPAREN);
        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints a call to the 'out' function that's syntactically identical to the program input.
    void printer() {
        System.out.print("out(");
        expr.printer();
        System.out.println(");");
    }

    // Prints the integer value of an expression to the console
    void execute() {
        int value = expr.execute();
        System.out.println(value);
    }
}
