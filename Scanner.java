import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Scanner {
    
    // Members to be used by an instance of this class, storing the input stream, token enum, and word corresponding to identifiers/constants.
    private BufferedReader in;
    private String word;
    private Core token;

    private static final int MAX_CONST = 999983; // Upper limit of valid constants in our language
    private static final int MAX_LOOK_AHEAD = 1; // Max # characters to peek ahead
    private static final int EOS = -1; // Used to detect when BufferedReader is at the end of the input

    // Initialize the scanner.
    Scanner(String filename) {
        // Initializes the input reader.
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found.");
        }

        // Populates 'token' member with first token in file.
        nextToken();
    }

    // Advance to the next token.
    public void nextToken() {
        try {
            char currentChar = (char) in.read();
            word = "";

            // Consume whitespace until a character is detected.
            while (Character.isWhitespace(currentChar)) {
                currentChar = (char) in.read();
            }

            // Start building the word
            word += currentChar;

            // If the first character following whitespace is a letter... 
            if (Character.isLetter(currentChar)) {

                // ...build the 'word' string until a non-alphanumeric character is detected.
                while (Character.isLetter(peekNextChar()) || Character.isDigit(peekNextChar())) {
                    currentChar = (char) in.read();
                    word += currentChar;
                }

                // Determine whether the alphanumeric word is a keyword or an identifier, and update the 'token' member accordingly.
                detectKeywordOrIdentifier();

            // Otherwise, if the first character following whitespace is a digit...
            } else if (Character.isDigit(currentChar)) {
                
                // ...build the 'word' string until a non-digit character is detected.
                while (Character.isDigit(peekNextChar())) {
                    currentChar = (char) in.read();
                    word += currentChar;
                }

                // Get the integer value of the string, and verify it falls within the specified range.
                int value = Integer.parseInt(word);
                if (value >= 0 && value <= MAX_CONST) {
                    // If it does, set 'token' to CONST...
                    token = Core.CONST;
                } else { 
                    // ...otherwise, set the token to ERROR and give an error message.
                    token = Core.ERROR;
                    System.out.println("ERROR: Constant '" + value + "' falls outside of range 0 - " + MAX_CONST + " (inclusive).");   
                }

            // If the character isn't a letter or digit, it must be one of the special characters...
            } else {
                // ...so figure out which one it is, and update the 'token' member accordingly.
                detectSymbol();
            }
            
        } catch (IOException e) {
            System.out.println("An error has occurred reading from the file.");
        }
    }

    // Return the current token using the 'token' member.
    public Core currentToken() {
        return token;
    }

	// Return the identifier string stored in 'word'.
    public String getId() {
        return word;
    }

	// Return the constant value by parsing the 'word' string as an integer.
    public int getConst() {
        return Integer.parseInt(word);
    }

    // Determine what token the 'word' string belongs to: a keyword or identifier.
    private void detectKeywordOrIdentifier() {
        switch (word) {
            case "procedure":
                token = Core.PROCEDURE;
                break;
            case "begin":
                token = Core.BEGIN;
                break;
            case "is":
                token = Core.IS;
                break;
            case "end":
                token = Core.END;
                break;
            case "if":
                token = Core.IF;
                break;
            case "else":
                token = Core.ELSE;
                break;
            case "in":
                token = Core.IN;
                break;
            case "integer":
                token = Core.INTEGER;
                break;
            case "return":
                token = Core.RETURN;
                break;
            case "do":
                token = Core.DO;
                break;
            case "new":
                token = Core.NEW;
                break;
            case "not":
                token = Core.NOT;
                break;
            case "and":
                token = Core.AND;
                break;
            case "or":
                token = Core.OR;
                break;
            case "out":
                token = Core.OUT;
                break;
            case "object":
                token = Core.OBJECT;
                break;
            case "then":
                token = Core.THEN;
                break;
            case "while":
                token = Core.WHILE;
                break;
            default:
                token = Core.ID;
                break;
        }
    }

    // Determine what special character the current character from the input stream belongs to
    private void detectSymbol() {
        char character = word.charAt(0);
        switch (character) {
            case '+':
            token = Core.ADD;
            break;
        case '-':
            token = Core.SUBTRACT;
            break;
        case '*':
            token = Core.MULTIPLY;
            break;
        case '/':
            token = Core.DIVIDE;
            break;
        case '=':
            // Since '=' and '==' are distinct tokens, we must peek ahead one character.
            try {
                in.mark(MAX_LOOK_AHEAD);
                char nextChar = (char) in.read();

                // Set the 'token' member to 'EQUAL' for '==', and 'ASSIGN' for '='.
                if (nextChar == '=') {
                    token = Core.EQUAL;
                } else {
                    token = Core.ASSIGN;
                    in.reset(); // Prevents premature token consumption.
                }

            } catch (IOException e) {
                System.out.println("Error reading next character from input stream.");
            }
            
            break;
        case '<':
            token = Core.LESS;
            break;
        case ':':
            token = Core.COLON;
            break;
        case ';':
            token = Core.SEMICOLON;
            break;
        case '.':
            token = Core.PERIOD;
            break;
        case ',':
            token = Core.COMMA;
            break;
        case '(':
            token = Core.LPAREN;
            break;
        case ')':
            token = Core.RPAREN;
            break;
        case '[':
            token = Core.LBRACE;
            break;
        case ']':
            token = Core.RBRACE;
            break;

        // The .read() method from BufferedReader returns a -1 when it reaches the end-of-stream.
        // Since the subject of the switch-case is a char, the integer has to be cast as a char to be detected.
        case (char) EOS:
            token = Core.EOS;

            // Close the input stream.
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("Error closing file.");
            }
            break;

        // Any symbols not recognized by the above cases are invalid, so print an error message.
        default:
            token = Core.ERROR;
            System.out.println("ERROR: Invalid character '" + character + "' detected.");
        }
    }

    // Peeks at the next character in the input stream to prevent premature token consumption.
    private char peekNextChar() throws IOException {
        in.mark(MAX_LOOK_AHEAD);
        char nextChar = (char) in.read();
        in.reset();
        
        return nextChar;
    }
}
