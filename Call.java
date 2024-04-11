
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Parses the <call> non-terminal in the Core context-free-grammar, which is defined as:
 *      <call> ::= begin ID (<parameters>);
 */
public class Call {
    String identifier;
    Parameters parameters;

    void parse() {
        Parser.scanner.nextToken();
        Parser.checkCurrentTokenIs(false, Core.ID);

        identifier = Parser.scanner.getId();

        Parser.scanner.nextToken();
        Parser.checkCurrentTokenIs(true, Core.LPAREN);

        parameters = new Parameters();
        parameters.parse();

        Parser.checkCurrentTokenIs(true, Core.RPAREN);
        Parser.checkCurrentTokenIs(false, Core.SEMICOLON);
    }

    // Prints a function call that's syntactically identical to the program input.
    void printer() {
        System.out.print("begin " + identifier + " (");
        parameters.printer();
        System.out.println(");");
    }

    // Executes the function call.
    void execute() {
        if (!Executor.functions.containsKey(identifier)) {
            System.out.println("ERROR: the function '" + identifier + "' has not been declared.");
            System.exit(0);
        }

        Function function = Executor.functions.get(identifier);

        List<String> informals = parameters.execute();
        List<String> formals = function.parameters.execute();

        Deque<Map<String, Variable>> frame = new ArrayDeque<>();
        frame.add(new HashMap<>());

        for (int i = 0; i < informals.size(); i++) {
            Variable variable = Executor.getVariable(informals.get(i));
            frame.getFirst().put(formals.get(i), variable.getCopy());
            variable.incrementReferenceCount();
        }

        Executor.pushFrame(frame);
        Executor.pushScope(Scope.LOCAL);

        function.execute();

        Map<String, Variable> variables = Executor.frames.peek().peekLast(); // params
        variables.putAll(Executor.frames.peek().peekFirst()); // local variables

        Iterator<Entry<String, Variable>> it = variables.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().decrementReferenceCount();
        }

        Executor.popFrame();
    }
}
