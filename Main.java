import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import generated.jvmLexer;
import generated.jvmParser;

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
        checker.visit(tree);

        System.out.println("PARSE SUCCESSFUL!");
        // checker.printTables();
    }
}