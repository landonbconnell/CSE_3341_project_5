public class Factor {

    String identifier;
    Integer constant;
    Expr expr;

    /**
     * Parses the <factor> non-terminal in the Core context-free-grammar, which is defined as:
     *      <factor> ::= id | id [ <expr> ] | const | ( <expr> ) | in ( ) ;
     */
    void parse() {

        Parser.checkCurrentTokenIs(false, Core.ID, Core.CONST, Core.LPAREN, Core.IN);

        // id | id [ <expr> ]
        if (Parser.currentTokenIs(Core.ID)) {
            // save identifier for later use
            identifier = Parser.scanner.getId();
            Parser.scanner.nextToken();

            // id [ <expr> ]
            if (Parser.currentTokenIs(Core.LBRACE)) {
                Parser.scanner.nextToken();

                expr = new Expr();
                expr.parse();

                Parser.checkCurrentTokenIs(true, Core.RBRACE);
            }

        // const
        } else if (Parser.currentTokenIs(Core.CONST)) {
            constant = Parser.scanner.getConst();
            Parser.scanner.nextToken();

        // ( <expr> )
        } else if (Parser.currentTokenIs(Core.LPAREN)) {
            Parser.scanner.nextToken();

            expr = new Expr();
            expr.parse();

            Parser.checkCurrentTokenIs(true, Core.RPAREN);

        // in ( )
        } else if (Parser.currentTokenIs(Core.IN)) {
            Parser.scanner.nextToken();

            Parser.checkCurrentTokenIs(true, Core.LPAREN);
            Parser.checkCurrentTokenIs(true, Core.RPAREN);
        }
    }

    // Prints a factor that's syntactically identical to the program input.
    void printer() {

        // id
        if (identifier != null) {
            System.out.print(identifier);

            // id [ <expr> ]
            if (expr != null) {
                System.out.print(" [");
                expr.printer();
                System.out.print(" ]");
            }

        // const
        } else if (constant != null) {
            System.out.print(constant);

        // ( <expr> )
        } else if (expr != null) {
            System.out.print("( ");
            expr.printer();
            System.out.print(" )");

        // in ();
        } else {
            System.out.print("in()");
        }
    }

    // Evaluates a factor expression and returns its integer value
    int execute() {
        int value = 0;
        
        // id | id [ <expr> ]
        if (identifier != null) {
            Variable variable = Executor.getVariable(identifier);

            if (variable == null) {
                System.out.println("ERROR: '" + identifier + "' has not been declared.");
                System.exit(0);
            }

            // id
            if (expr == null) {
                if (variable.type == Type.INTEGER) {
                    value = variable.int_value;
                } else {
                    value = variable.obj_value[0];
                }
            
            // id [ <expr> ]
            } else {

                if (variable.type != Type.OBJECT) {
                    System.out.print("ERROR: the statement '" + identifier + "[");
                    expr.printer();
                    System.out.print("]' cannot be used on a variable with type 'integer'.");

                    System.exit(0);
                }

                value = variable.obj_value[expr.execute()];
            }
        
        // const
        } else if (constant != null) {
            value = constant;

        // ( <expr> )
        } else if (expr != null) {
            value = expr.execute();

        // in()
        } else {
            if (Executor.input.currentToken() == Core.EOS) {
                System.out.println("ERROR: attempted to read past end-of-file.");
                System.exit(0);
            }
            value = Executor.input.getConst();
            Executor.input.nextToken();
        }

        return value;
    }
}
