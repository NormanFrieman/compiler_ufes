package checker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import generated.jvmParser;
import generated.jvmParser.ValueContext;
import generated.jvmParserBaseVisitor;
import checker.utils.Types;

public class SemanticChecker extends jvmParserBaseVisitor<Void> {
    private LinkedList<String> st = new LinkedList<String>();
    private LinkedList<Variable> vt = new LinkedList<Variable>();

    // Import list
    private LinkedList<String> il = new LinkedList<String>();

    // Last declared type
    private VariableType lastType;

    //#region Visit types
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
    //#endregion

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

        if (ctx.CONST() != null && ctx.ASSIGN() != null) {
            System.out.println("ERROR: unexpected :=, expecting =");
            System.exit(1);
        }
        
        if (ctx.value() != null) {
            visit(ctx.value());
            VariableType typeAssign = lastType.newInstance();

            if (ctx.type() != null) {
                visit(ctx.type());
                VariableType varType = lastType.newInstance();
    
                if (varType.compareTo(typeAssign) != 0) {
                    System.err.println("ERROR: cannot use " + typeAssign.print() + " as type " + varType.print() + " in assignment");
                    System.exit(1);
                }
            }

            Variable var = new Variable(varInit.getText(), ctx.varInit.getLine(), typeAssign, ctx.CONST() != null);
            vt.add(var);
        }
        
        return null;
    }

    //#region Visit primitive values
    @Override
    public Void visitValueIntD(jvmParser.ValueIntDContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueIntH(jvmParser.ValueIntHContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueIntO(jvmParser.ValueIntOContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueIntB(jvmParser.ValueIntBContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueIntN(jvmParser.ValueIntNContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_INT, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueFloat(jvmParser.ValueFloatContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_FLOAT64, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueFloatN(jvmParser.ValueFloatNContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_FLOAT64, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueString(jvmParser.ValueStringContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_STRING, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueChar(jvmParser.ValueCharContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_STRING, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueTrue(jvmParser.ValueTrueContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_BOOL, false, -1);
        return null;
    }
	
	@Override
    public Void visitValueFalse(jvmParser.ValueFalseContext ctx) {
        this.lastType = new VariableType(jvmParser.TYPE_BOOL, false, -1);
        return null;
    }
    //#endregion

    @Override
    public Void visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
        List<ValueContext> values = ctx.value();

        int maxSize = ctx.size == null ? -1 : Integer.parseInt(ctx.size.getText());
        if (maxSize != -1)
            values.remove(0);
        int sizeValues = values.size();
        
        if (maxSize != -1 && sizeValues > maxSize) {
            System.err.println("ERROR: array index " + maxSize + " out of bounds");
            System.exit(1);
        }

        visit(ctx.type());
        VariableType arrayType = new VariableType(lastType.getType(), true, maxSize);

        List<String> errors = new ArrayList<String>();
        values.forEach(value -> {
            visit(value);
            if (this.lastType.getType() != arrayType.getType()) {
                errors.add(
                    "ERROR: cannot use " + value.getText() + " (type "
                    + Types.Names.get(this.lastType.getType())
                    + ")" + " as type "
                    + Types.Names.get(arrayType.getType())
                    + " in array or slice literal");
            }
        });
        if (errors.size() > 0) {
            errors.forEach(err -> System.out.println(err));
            System.exit(1);
        }

        this.lastType = arrayType;
        
        return null;
    }
}