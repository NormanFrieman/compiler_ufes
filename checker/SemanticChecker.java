package checker;

import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

import generated.jvmParser;
import generated.jvmParserBaseVisitor;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<String> vt = new LinkedList<String>();

    public void printTables() {
        System.out.print("\n\n");
        System.out.print(st);
        System.out.print("\n\n");
    	System.out.print(vt);
    	System.out.print("\n\n");
    }
}