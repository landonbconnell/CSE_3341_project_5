Landon Connell

Assign.java - Has code for parsing, printing, and executing the <assign> non-terminal.
Call.java - Has code for parsing, printing, and executing the <call> non-terminal.
Cmpr.java - Has code for parsing, printing, and executing the <cmpr> non-terminal.
Cond.java - Has code for parsing, printing, and executing the <cond> non-terminal.
Core.java - A file containing enums that serve as tokens.
Decl.java - Has code for parsing, printing, and executing the <decl> non-terminal.
DeclInteger.java - Has code for parsing, printing, and executing the <decl-integer> non-terminal.
DeclObj.java - Has code for parsing, printing, and executing the <decl-obj> non-terminal.
DeclSeq.java - Has code for parsing, printing, and executing the <decl-seq> non-terminal.
Executor.java - Inititates the execution of the program, contains various methods for using and managing the 
    scope/frame stacks, and contains methods and fields for managing garbage collection.
Expr.java - Has code for parsing, printing, and executing the <expr> non-terminal.
Factor.java - Has code for parsing, printing, and executing the <factor> non-terminal.
Function.java - Has code for parsing, printing, and executing the <function> non-terminal.
If.java - Has code for parsing, printing, and executing the <if> non-terminal.
Loop.java - Has code for parsing, printing, and executing the <loop> non-terminal.
Main.java - A program that drives a compiler process which involves scanning an input file, generating a
    parse tree, and executing the code.
Out.java - Has code for parsing, printing, and executing the <out> non-terminal.
Parameters.java - Has code for parsing, printing, and executing the <parameters> nonterminal.
Parser.java - This class initiates the parsing process by invoking the 'parse()' method on a 'Procedure' object and
    provides helper methods to validate token correctness during the parsing of the Core programming language.
Procedure.java - Has code for parsing, printing, and executing the <procedure> non-terminal.
Scanner.java - A class defining a "Scanner" object, which takes source code as input, and outputs a stream of tokens.
Scope.java - A file containing enums for the different types of scopes (global, local, if, and loop)
Stmt.java - Has code for parsing, printing, and executing the <stmt> non-terminal.
StmtSeq.java - Has code for parsing, printing, and executing the <stmt-seq> non-terminal.
Term.java - Has code for parsing, printing, and executing the <term> non-terminal.
Type.java - A file containing enums symbolizing valid Core language variable types (integer and object).
Variable.java - A class defining a "Variable" object which contains 'type' and 'int/object_value' members, and
    methods for reference counting

Special Features:

This project was very straightforward, so I don't think my approach involved implementing any special features.

Garbage Collector Testing and Bugs:

To test my garbage collector, I only used the test cases that came with the project files. Since all the test cases pass, to my knowledge
there aren't any remaining bugs.
