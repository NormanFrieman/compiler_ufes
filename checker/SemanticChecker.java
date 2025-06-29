package checker;

import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import generated.jvmParser;
import generated.jvmParser.ValueContext;
import generated.jvmParserBaseVisitor;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<Variable> vt = new LinkedList<Variable>();

    // Import list
    private LinkedList<String> il = new LinkedList<String>();

    // Last declared type
    private VariableType lastType;

    @Override
    public Void visitUintType(jvmParser.UintTypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_UINT, false, -1);
        return null;
    }

    @Override
    public Void visitIntType(jvmParser.IntTypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }

	@Override
    public Void visitInt8Type(jvmParser.Int8TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT8, false, -1);
        return null;
    }

	@Override
    public Void visitInt16Type(jvmParser.Int16TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT16, false, -1);
        return null;
    }

	@Override
    public Void visitInt32Type(jvmParser.Int32TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT32, false, -1);
        return null;
    }

	@Override
    public Void visitInt64Type(jvmParser.Int64TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT64, false, -1);
        return null;
    }

	@Override
    public Void visitFloat32Type(jvmParser.Float32TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT32, false, -1);
        return null;
    }

	@Override
    public Void visitFloat64Type(jvmParser.Float64TypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT64, false, -1);
        return null;
    }

	@Override
    public Void visitStringType(jvmParser.StringTypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_STRING, false, -1);
        return null;
    }

	@Override
    public Void visitBoolType(jvmParser.BoolTypeContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_BOOL, false, -1);
        return null;
    }

    @Override
    public Void visitArrayType(jvmParser.ArrayTypeContext ctx) {
        int maxSize = ctx.size == null ? -1 : Integer.parseInt(ctx.size.getText());

        visit(ctx.type());
        lastType = new VariableType(lastType.getType(), true, maxSize);
        
        return null;
    }

    // Adiciona os imports na lista il
    @Override
    public Void visitSimpleImport(jvmParser.SimpleImportContext ctx) {
        Token token = ctx.STRING_VALUE().getSymbol();
        String importName = token.getText();
        il.add(importName.replaceAll("\"", ""));
        return null;
    }

    @Override
    public Void visitVarWithValue(jvmParser.VarWithValueContext ctx) {
        Token varInit = ctx.varInit;

        if (ctx.value() != null) {
            visit(ctx.value());
            VariableType typeAssign = lastType.newInstance();

            visit(ctx.type());
            VariableType varType = lastType.newInstance();

            if (varType.compareTo(typeAssign) != 0) {
                System.err.println("ERROR: cannot use " + typeAssign.print() + " as type " + varType.print() + " in assignment");
                System.exit(1);
            }

            Variable var = new Variable(varInit.getText(), ctx.varInit.getLine(), varType, ctx.CONST() != null);
            vt.add(var);
        }
        
        return null;
    }

    @Override
    public Void visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
        List<ValueContext> values = ctx.value();

        int maxSize = ctx.size == null ? -1 : Integer.parseInt(ctx.size.getText());
        int sizeValues = maxSize == -1 ? values.size() : values.size() - 1;

        if (sizeValues > maxSize) {
            System.err.println("ERROR: array index " + maxSize + " out of bounds");
            System.exit(1);
        }

        visit(ctx.type());
        lastType = new VariableType(lastType.getType(), true, maxSize);
        
        return null;
    }
}