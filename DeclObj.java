public class DeclObj {

    String identifier;

    /**
     * Parses the <decl-obj> non-terminal in the Core context-free-grammar, which is defined as:
     *      <decl-obj> ::= object id ;
     */
    void parse() {
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.ID);

        // saves identifier for later use
        identifier = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints an object declaration that's syntactically identical to the program input.
    void printer() {
        System.out.println("\tobject " + identifier + ";");
    }

    // Executes an object declaration statement
    void execute() {
        if (!Executor.isInCurrentScope(identifier)) {
            Executor.addVariableToCurrentScope(identifier, Type.OBJECT);
        } else {
            if (Executor.currentScopeType() == Scope.LOOP) {
                Executor.getVariable(identifier).obj_value = null;
            } else {
                System.out.println("ERROR: " + identifier + " already declared in current scope.");
                System.exit(0);
            }
        }
    }
}