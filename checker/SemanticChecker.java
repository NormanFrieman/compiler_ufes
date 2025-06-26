package checker;

import java.util.LinkedList;

import org.antlr.v4.runtime.Token;

import generated.jvmParser;
import generated.jvmParserBaseVisitor;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<Variable> vt = new LinkedList<Variable>();

    private int lastPrimitiveType;

    @Override
    public Void visitUintType(jvmParser.UintTypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_UINT;
        return null;
    }

    @Override
    public Void visitIntType(jvmParser.IntTypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT;
        return null;
    }

	@Override
    public Void visitInt8Type(jvmParser.Int8TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT8;
        return null;
    }

	@Override
    public Void visitInt16Type(jvmParser.Int16TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT16;
        return null;
    }

	@Override
    public Void visitInt32Type(jvmParser.Int32TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT32;
        return null;
    }

	@Override
    public Void visitInt64Type(jvmParser.Int64TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT64;
        return null;
    }

	@Override
    public Void visitFloat32Type(jvmParser.Float32TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT32;
        return null;
    }

	@Override
    public Void visitFloat64Type(jvmParser.Float64TypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_INT64;
        return null;
    }

	@Override
    public Void visitStringType(jvmParser.StringTypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_STRING;
        return null;
    }

	@Override
    public Void visitBoolType(jvmParser.BoolTypeContext ctx) {
        this.lastPrimitiveType = jvmParser.TYPE_BOOL;
        return null;
    }

    @Override
    public Void visitVarInit(jvmParser.VarInitContext ctx) {
        Token token = ctx.ID().getSymbol();
        String name = token.getText();
        int line = token.getLine();

        visit(ctx.type());

        vt.add(new Variable(name, line, new VariableType(this.lastPrimitiveType, false)));
        System.out.println(name);
        System.out.println(line);
        System.out.println(this.lastPrimitiveType);

        return null;
    };

    public void printTables() {
        System.out.print("\n\n");
        System.out.print(st);
        System.out.print("\n\n");
    	System.out.print(vt);
    	System.out.print("\n\n");
    }
}