import java.util.List;

public class Function {

    String procedureName;
    StmtSeq stmt_seq;
    Parameters parameters;

    /**
     * Parses the <function> non-terminal in the Core context-free-grammar, which is defined as:
     *      <function> ::= procedure ID (<parameters>) is <stmt-seq> end
     */
    void parse() {

        Parser.checkCurrentTokenIs(true, Core.PROCEDURE);
        Parser.checkCurrentTokenIs(false, Core.ID);

        procedureName = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(true, Core.LPAREN);

        parameters = new Parameters();
        parameters.parse();

        List<String> paramIdentifiers = parameters.execute();
        for (int i = 0; i < paramIdentifiers.size(); i++) {
            for (int j = i + 1; j < paramIdentifiers.size(); j++) {
                if (paramIdentifiers.get(i).equals(paramIdentifiers.get(j))) {
                    System.out.println("ERROR: duplicate formal parameters defined for the function '" + procedureName + "'.");
                    System.exit(0);
                }
            }
        }

        Parser.checkCurrentTokenIs(true, Core.RPAREN);
        Parser.checkCurrentTokenIs(true, Core.IS);

        stmt_seq = new StmtSeq();
        stmt_seq.parse();

        Parser.checkCurrentTokenIs(false, Core.END);
    }

    // Prints a function definition that is syntactically identical to the input.
    void printer() {
        System.out.print("procedure " + procedureName + " (");
        parameters.printer();
        System.out.println(") is");
        stmt_seq.printer();
        System.out.println("end");
    }

    // Executes the sequence of statements inside the function body.
    void execute() {
        stmt_seq.execute();
    }
}
