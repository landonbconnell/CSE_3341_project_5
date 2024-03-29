import java.util.List;
import java.util.ArrayList;

public class Parameters {
    String identifier;
    Parameters parameters;

    /**
     * Parses the <parameters> non-terminal in the Core context-free-grammar, which is defined as:
     *      <parameters> ::= ID | ID, <parameters>
     */
    void parse() {
        Parser.checkCurrentTokenIs(false, Core.ID);
        identifier = Parser.scanner.getId();
        Parser.scanner.nextToken();

        if (Parser.currentTokenIs(Core.COMMA)) {
            Parser.scanner.nextToken();
            parameters = new Parameters();
            parameters.parse();
        }
    }

    // Prints a list of parameters that is syntactically identical to the input.
    void printer() {
        System.out.print(identifier);
        if (parameters != null) {
            System.out.print(", ");
            parameters.printer();
        }
    }

    // Returns a list of parameter identifiers.
    List<String> execute() {
        List<String> paramList = new ArrayList<>();
        paramList.add(identifier);

        if (parameters != null) {
            paramList.addAll(parameters.execute());
        }

        return paramList;
    }
}
