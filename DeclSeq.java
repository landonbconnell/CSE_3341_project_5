public class DeclSeq {
    
    Decl decl;
    DeclSeq decl_seq;
    Function function;

    /**
     * Parses the <decl-seq> non-terminal in the Core context-free-grammar, which is defined as:
     *      <decl-seq> ::= <decl> | <decl><decl-seq> 
     */
    void parse() {

        // <function>
        if (Parser.currentTokenIs(Core.PROCEDURE)) {
            function = new Function();
            function.parse();

        // <decl>
        } else {
            decl = new Decl();
            decl.parse();
        }

        Parser.scanner.nextToken();

        // <decl><decl-seq> | <function><decl-seq>
        if (!Parser.currentTokenIs(Core.BEGIN)) {
            decl_seq = new DeclSeq();
            decl_seq.parse();
        }
    }

    // Prints a sequence of declarations that's syntactically identical to the program input.
    void printer() {
        
        if (function != null) {
            function.printer();
        } else {
            decl.printer();
        }
        
        if (decl_seq != null) {
            decl_seq.printer();
        }
    }

    // Executes a sequence of declaration statements
    void execute() {
        // <decl>
        if (decl != null) {
            decl.execute();

        // <function>
        } else {
            if (!Executor.functions.containsKey(function.procedureName))  {
                Executor.functions.put(function.procedureName, function);
            } else {
                System.out.println("ERROR: the function '" + function.procedureName + "' has already been declared.");
                System.exit(0);
            }
        }
        
        // <decl><decl-seq> | <function><decl-seq>
        if (decl_seq != null) {
            decl_seq.execute();
        }
    }
}
