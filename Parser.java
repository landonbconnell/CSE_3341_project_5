/**
 * This class initiates the parsing process by invoking the 'parse()' method on a 'Procedure' object and 
 * provides helper methods to validate token correctness during the parsing of the Core programming language.
 */
public class Parser {

    public static Scanner scanner;
    public Procedure procedure;

    public Parser(String fileName) {
        scanner = new Scanner(fileName);
    }
    
    void run() {
        procedure = new Procedure();
        procedure.parse();
    }

    /**
     * Checks if the current token in the scanner is equal to the token passed as an argument.
     * I created this because it's more readable than "if (Parser.scanner.currentToken == Core.X)"
     * 
     * @param token the token to be compared to the current token in the scanner
     * @return true if the current token in the scanner is equal to the 'token' argument, false otherwise.
     */
    public static boolean currentTokenIs(Core token) {
        return scanner.currentToken() == token;
    }

    /**
     * If the current token doesn't match any of the expected tokens, show an error message of the format:
     * "ERROR: expected <list-of-expected-tokens>, but received <current-token>" and stop execution of the Parser. 
     * 
     * Otherwise, if advanceScanner is true, advance the scanner to the next token.
     * 
     * @param advanceScanner a boolean that, when set to true, advances the scanner to the next token.
     * @param expectedTokens an array of tokens, any of which would be deemed valid by the semantic checker in the current parse tree context.
     */
    public static void checkCurrentTokenIs(boolean advanceScanner, Core... expectedTokens) {
        assert expectedTokens.length > 0 : "checkCurrentTokenIs expects 1 or more arguments.";

        boolean expectedTokenDetected = false;
        
        // sets expectedTokenDetected flag to true if the current token is at least one of the expected tokens.
        for (Core token : expectedTokens) if (currentTokenIs(token)) expectedTokenDetected = true;

        // if none of the expected tokens are the current token, print a descriptive error message.
        if (!expectedTokenDetected) {
            String expectedTokensList = "";

            if (expectedTokens.length == 1) {
                expectedTokensList = "'" + tokenToString(expectedTokens[0]) + "'";

            } else if (expectedTokens.length == 2) {
                // generates a list of expected tokens in the format "'token_1' or 'token_2'," (no commas).
                expectedTokensList = "'" + tokenToString(expectedTokens[0]) + "' or '" + tokenToString(expectedTokens[1]) + "',";
            
            } else {
                // generates a list of expected tokens in the format "'token_1', 'token_2', ..., or 'token_n',".
                for (int i = 0; i < expectedTokens.length - 1; i++) {
                    expectedTokensList += "'" + tokenToString(expectedTokens[i]) + "', ";
                }
                expectedTokensList += "or '" + tokenToString(expectedTokens[expectedTokens.length - 1]) + "',";
            }
            
            System.out.println("ERROR: expected " + expectedTokensList + " but received '" + tokenToString(scanner.currentToken()) + "'.");
            System.exit(0);
        }
        

        if (advanceScanner) scanner.nextToken();
    }

    /**
     * If the scanner converts words/symbols into tokens, this does the opposite.
     * 
     * @param token the token that is being translated into a string.
     * @return the Core programming language string associated with the 'token' argument.
     */
    private static String tokenToString(Core token) {
        switch (token) {
            case PROCEDURE:
            case BEGIN:
            case IS:
            case END:
            case IF:
            case ELSE:
            case IN:
            case INTEGER:
            case RETURN:
            case DO:
            case NEW:
            case NOT:
            case AND:
            case OR:
            case OUT:
            case OBJECT:
            case THEN:
            case WHILE:
            case ID:
            case CONST:
            case ERROR:
                return token.name().toLowerCase();
            case ADD:
                return "+";
            case SUBTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            case DIVIDE:
                return "/";
            case ASSIGN:
                return "=";
            case EQUAL:
                return "==";
            case LESS:
                return "<";
            case COLON:
                return ":";
            case SEMICOLON:
                return ";";
            case PERIOD:
                return ".";
            case COMMA:
                return ",";
            case LPAREN:
                return "(";
            case RPAREN:
                return ")";
            case LBRACE:
                return "[";
            case RBRACE:
                return "]";
            case EOS:
                return "EOS";
            default:
                return "";
        }
    }
}
