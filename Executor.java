import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Inititates the execution of the program created by the Parser, and
 * contains various methods for using and managing the scope stack.
 */
public class Executor {
    
    public static Scanner input;
    public static Map<String, Function> functions;
    public static Deque<Deque<Map<String, Variable>>> frames;
    public static Deque<Deque<Scope>> scopeTypes;
    
    public Executor(String inputFilePath) {
        input = new Scanner(inputFilePath);
        functions = new HashMap<>();
        frames = new ArrayDeque<>();
        scopeTypes = new ArrayDeque<>();
    }

    /**
     * Starts the execution of the program defined by the parse tree
     * @param procedure the top-level object in the parse tree
     */
    public void run(Procedure procedure) {
        procedure.execute();
    }

    /**
     * Pushes a new frame to the top of the frames stack.
     * @param frame the frame being pushed to the top of the frames stack.
     */
    public static void pushFrame(Deque<Map<String, Variable>> frame) {
        scopeTypes.addFirst(new ArrayDeque<>());
        frames.addFirst(frame);
    }

    /**
     * Pops a frame off the top of the frames stack.
     */
    public static void popFrame() {
        scopeTypes.pop();
        frames.pop();
    }

    /**
     * Pushes a new scope to the top of the scope stack.
     * @param scopeType the type of scope being pushed onto the scope stack (global, local, loop, or if-statement)
     */
    public static void pushScope(Scope scopeType) {
        scopeTypes.getFirst().addFirst(scopeType);
        frames.getFirst().addFirst(new HashMap<>());
    }

    /**
     * Pops the most recently added scope off the scope stack.
     */
    public static void popScope() {
        scopeTypes.getFirst().pop();
        frames.getFirst().pop();
    }

    /**
     * Adds a new Variable instance to the top-most scope in the scope stack, initializing it
     * with the name and type provided by the arguments.
     * 
     * @param identifier the name of the variable being added to the current scope
     * @param type the type of the variable being added to the current scope (integer/object)
     */
    public static void addVariableToCurrentScope(String identifier, Type type) {
        frames.getFirst().getFirst().put(identifier, new Variable(type));
    }

    /**
     * Iterates over the variables contained in the stack and returns the Variable instance associated with the identifier,
     * or return null to indicate it hasn't been declared yet.
     * 
     * @param identifier the name of the variable being fetched.
     * @return the Variable object instance whose identifier matches the argument; return 'null' if it hasn't been declared yet.
     */
    public static Variable getVariable(String identifier) {
        Variable variable = null;
        Iterator<Map<String, Variable>> it = frames.getFirst().iterator();
        
        while (it.hasNext()) {
            Map<String, Variable> currentScope = it.next();

            if (currentScope.containsKey(identifier)) {
                variable = currentScope.get(identifier);
                break;
            }
        }

        return variable;
    }

    /**
     * Checks if any scope in the stack contains a variable with a name matching the 'identifier' argument
     * 
     * @param identifier the name of the variable being checked for declaration
     * @return true if the variable has been declared, false otherwise.
     */
    public static boolean isInScope(String identifier) {
        return getVariable(identifier) != null;
    }

    /**
     * Checks if the top-most/current scope on the stack contains a variable with a name matching the 'identifier' argument.
     * 
     * @param identifier the name of the variable being checked for declaration in the current scope
     * @return true if the variable has been declared in the current scope, false otherwise.
     */
    public static boolean isInCurrentScope(String identifier) {
        return frames.getFirst().peekFirst().containsKey(identifier);
    }

    /**
     * @return a reference to the global scope
     */
    public static Map<String, Variable> getGlobalScope() {
        return frames.getFirst().peekLast();
    }

    /**
     * @returns a Scope enum that tells what the current scope type is (global, local, loop, or if-statement)
     */
    public static Scope currentScopeType() {
        return scopeTypes.getFirst().peekFirst();
    }
}
