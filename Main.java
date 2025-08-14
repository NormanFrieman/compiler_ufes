import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.AST;
import generated.jvmLexer;
import generated.jvmParser;
import interpreter.Interpreter;
import checker.SemanticChecker;

public class Main {
    public static void main(String[] args) throws IOException {
        CharStream input = CharStreams.fromFileName(args[0]);

        jvmLexer lexer = new jvmLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        jvmParser parser = new jvmParser(tokens);
        ParseTree tree = parser.program();

        if (parser.getNumberOfSyntaxErrors() != 0)
            return;
        
        SemanticChecker checker = new SemanticChecker();
        AST astTree = checker.visit(tree);

        if (args.length == 1) {
            astTree.Print(0);
            System.out.println("PARSE SUCCESSFUL!");
        } else if ("interpreter".equals(args[1])) {
            Interpreter interpreter = new Interpreter();
            interpreter.EvalProgram(astTree);
        }    
        // checker.printTables();
    }
}