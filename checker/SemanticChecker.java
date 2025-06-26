package checker;

import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

import generated.jvmParser;
import generated.jvmParser.Var_initContext;
import generated.jvmParserBaseVisitor;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<String> vt = new LinkedList<String>();

    @Override
    public Void visitVar_init(jvmParser.Var_initContext ctx) {
        System.out.println(ctx.ID());

        // Object id = ctx.ID().getClass();
        // String idStr = id.getText();
        // System.out.println(idStr);

        return visitChildren(ctx);
    }

    public void printTables() {
        System.out.print("\n\n");
        System.out.print(st);
        System.out.print("\n\n");
    	System.out.print(vt);
    	System.out.print("\n\n");
    }
}