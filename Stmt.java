public class Stmt {
    
    Assign assign;
    If if_stmt;
    Loop loop;
    Out out;
    Decl decl;
    Call call;

    /**
     * Parses the <stmt> non-terminal in the Core context-free-grammar, which is defined as:
     *      <stmt> ::= <assign> | <if> | <loop> | <out> | <decl>
     */
    void parse() {

        Parser.checkCurrentTokenIs(false, Core.ID, Core.IF, Core.WHILE, Core.OUT, Core.INTEGER, Core.OBJECT, Core.BEGIN);

        // <assign>
        if (Parser.currentTokenIs(Core.ID)) {
            assign = new Assign();
            assign.parse();

        // <if>
        } else if (Parser.currentTokenIs(Core.IF)) {
            if_stmt = new If();
            if_stmt.parse();

        // <loop>
        } else if (Parser.currentTokenIs(Core.WHILE)) {
            loop = new Loop();
            loop.parse();

        // <out>
        } else if (Parser.currentTokenIs(Core.OUT)) {
            out = new Out();
            out.parse();

        // <decl>
        } else if (Parser.currentTokenIs(Core.INTEGER) || Parser.currentTokenIs(Core.OBJECT)) {
            decl = new Decl();
            decl.parse();

        // <call>
        } else if (Parser.currentTokenIs(Core.BEGIN)) {
            call = new Call();
            call.parse();
        }
    }

    // Prints a statement that's syntactically identical to the program input.
    void printer() {
        if (assign != null) {
            assign.printer();
        } else if (if_stmt != null) {
            if_stmt.printer();
        } else if (loop != null) {
            loop.printer();
        } else if (out != null) {
            out.printer();
        } else if (decl != null) {
            decl.printer();
        } else if (call != null) {
            call.printer();
        }
    }

    // Executes a statement depending on the state of the parse tree
    void execute() {
        if (assign != null) {
            assign.execute();
        } else if (if_stmt != null) {
            Executor.pushScope(Scope.IF); // Pushing if-statement scope
            if_stmt.execute();
            Executor.popScope(); // Popping if-statement scope
        } else if (loop != null) {
            Executor.pushScope(Scope.LOOP); // Pushing while-loop scope
            loop.execute();
            Executor.popScope(); // Popping while-loop scope
        } else if (out != null) {
            out.execute();
        } else if (decl != null) {
            decl.execute();
        } else if (call != null) {
            call.execute();
        }
    }
}
