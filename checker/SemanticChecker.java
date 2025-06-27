package checker;

import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import generated.jvmParser;
import generated.jvmParserBaseVisitor;
import generated.jvmParser.Value_assignContext;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<Variable> vt = new LinkedList<Variable>();

    // Import list
    private LinkedList<String> il = new LinkedList<String>();

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
        Token var = ctx.ID().getSymbol();
        String varName = var.getText();
        int varLine = var.getLine();

        visit(ctx.type());

        vt.add(new Variable(varName, varLine, new VariableType(this.lastPrimitiveType, false)));
        return null;
    };

    @Override
    public Void visitWithoutVarInit(jvmParser.WithoutVarInitContext ctx) {
        Token var = ctx.ID().getSymbol();
        String varName = var.getText();
        int varLine = var.getLine();

        Value_assignContext valueCtx = ctx.value_assign();
        Token value = valueCtx.stop;
        int type = value.getType();
        if (type == jvmParser.INT_DEC)
            this.lastPrimitiveType = jvmParser.TYPE_INT;

        vt.add(new Variable(varName, varLine, new VariableType(this.lastPrimitiveType, false)));
        return null;
    }

    @Override
    public Void visitValue_assign(jvmParser.Value_assignContext ctx) {
        List<TerminalNode> variables = ctx.ID();
        System.out.println(vt);
        System.out.println(ctx.ID());

        variables.forEach(variable -> {
            Token varToken = variable.getSymbol();
            String varName = varToken.getText();
            if (!vt.stream().filter(x -> x.Name.equals(varName)).findAny().isPresent()) {
                System.err.printf("SEMANTIC ERROR (%d): undefined %s\n", varToken.getLine(), varName);
                System.exit(1);
            }
        });

        return null;
    }

    @Override
    public Void visitSimpleImport(jvmParser.SimpleImportContext ctx) {
        Token token = ctx.STRING_VALUE().getSymbol();
        String importName = token.getText();
        il.add(importName.replaceAll("\"", ""));
        return null;
    }

    @Override
    public Void visitFunction_call(jvmParser.Function_callContext ctx) {
        Token idToken = ctx.ID().getSymbol();
        String lib = idToken.getText();
        if (!il.contains(lib)) {
            System.err.printf("SEMANTIC ERROR (%d): undefined %s\n", idToken.getLine(), lib);
            System.exit(1);
        }

        return null;
    }

    public void printTables() {
        System.out.print("\n\n");
        System.out.print(st);
        System.out.print("\n\n");
    	System.out.print(vt);
    	System.out.print("\n\n");
    }
}