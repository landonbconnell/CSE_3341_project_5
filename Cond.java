public class Cond {
    
    Cond cond;
    Cmpr cmpr;
    String modifier;

    /**
     * Parses the <cond> non-terminal in the Core context-free-grammar, which is defined as:
     *  <cond> ::= <cmpr> | not <cond> | [ <cond> ] | <cmpr> or <cond> | <cmpr> and <cond>
     */
    void parse() {

        // not <cond>
        if (Parser.currentTokenIs(Core.NOT)) {
            Parser.scanner.nextToken();

            modifier = "not";

            cond = new Cond();
            cond.parse();
        
        // [ <cond> ]
        } else if (Parser.currentTokenIs(Core.LBRACE)) {
            Parser.scanner.nextToken();

            cond = new Cond();
            cond.parse();

            Parser.checkCurrentTokenIs(true, Core.RBRACE);

        // <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            // <cmpr>
            cmpr = new Cmpr();
            cmpr.parse();

            // <cmpr> or <cond> | <cmpr> and <cond>
            if (Parser.currentTokenIs(Core.OR) || Parser.currentTokenIs(Core.AND)) {
                modifier = Parser.currentTokenIs(Core.OR) ? "or" : "and";

                Parser.scanner.nextToken();

                cond = new Cond();
                cond.parse();
            }
        }
    }

    // Prints a condition that's syntactically identical to the program input.
    void printer() {

        // [ <cond> ]
        if (cmpr == null && modifier == null) {
            System.out.print("[ ");
            cond.printer();
            System.out.print(" ]");
        
        // <cmpr> | not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            //<cmpr>
            if (cmpr != null) {
                cmpr.printer();
            }

            // not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
            if (modifier != null) {
                System.out.print((!modifier.equals("not") ? " " : "") + modifier + " ");
                cond.printer();
            }
        }
    }

    // Evaluates the condition expression and returns a boolean
    boolean execute() {

        boolean value = false;

        // [ <cond> ]
        if (cmpr == null && modifier == null) {
            value = cond.execute();
        
        // <cmpr> | not <cmpr> | <cmpr> or <cond> | <cmpr> and <cond>
        } else {
            if (modifier != null) {
                switch (modifier) {
                    case "not":
                        value = !cond.execute();
                        break;
                    case "and":
                        value = cmpr.execute() && cond.execute();
                        break;
                    case "or":
                        value = cmpr.execute() || cond.execute();
                        break;
                    default:
                        break;
                }
            } else {
                value = cmpr.execute();
            }
        }

        return value;
    }
}
