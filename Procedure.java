import java.util.ArrayDeque;

public class Procedure {
    String procedureName;
    DeclSeq decl_seq;
    StmtSeq stmt_seq;

    /**
     * Parses the <procedure> non-terminal in the Core context-free-grammar, which is defined as:
     *      <procedure> ::= procedure ID is <decl-seq> begin <stmt-seq> end | procedure ID is begin <stmt-seq> end
     */
    void parse() {
        Parser.checkCurrentTokenIs(true, Core.PROCEDURE);
        Parser.checkCurrentTokenIs(false, Core.ID);

        procedureName = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.checkCurrentTokenIs(true, Core.IS);

        // procedure ID is <decl-seq> begin <stmt-seq> end
        if (!Parser.currentTokenIs(Core.BEGIN)) {
            decl_seq = new DeclSeq();
            decl_seq.parse();
        }

        Parser.checkCurrentTokenIs(true, Core.BEGIN);

        stmt_seq = new StmtSeq();
        stmt_seq.parse();

        Parser.checkCurrentTokenIs(true, Core.END);
        Parser.checkCurrentTokenIs(false, Core.EOS);
    }

    // Prints a Core program that's syntactically identical to the entire program input.
    void printer() {
        System.out.println("procedure " + procedureName + " is ");
        
        if (decl_seq != null) {
            decl_seq.printer();
        }

        System.out.println("begin");
        stmt_seq.printer();
        System.out.println("end");
    }

    void execute() {
        Executor.pushFrame(new ArrayDeque<>());

        // Pushing global scope
        Executor.pushScope(Scope.GLOBAL);

        if (decl_seq != null) {
            decl_seq.execute();
        }
        
        // Pushing main-body scope
        Executor.pushScope(Scope.LOCAL);
        
        stmt_seq.execute();

        Executor.popFrame();
    }
}