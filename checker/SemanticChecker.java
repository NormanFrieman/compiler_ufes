package checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.Token;
import generated.jvmParser;
import generated.jvmParserBaseVisitor;
import checker.utils.JvmType;
import checker.utils.Scope;

public class SemanticChecker extends jvmParserBaseVisitor<VariableType> {
    // Scopes
    private LinkedList<Scope> scopes = new LinkedList<Scope>();
    private Scope lastScope;

    // Import list
    private HashMap<String, String> imports = new HashMap<String, String>();

    //#region Visit types
    @Override
    public VariableType visitUintType(jvmParser.UintTypeContext ctx) {
        VariableType type = new VariableType(JvmType.UINT, false, -1);
        return type;
    }

    @Override
    public VariableType visitIntType(jvmParser.IntTypeContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }

	@Override
    public VariableType visitInt8Type(jvmParser.Int8TypeContext ctx) {
        VariableType type = new VariableType(JvmType.INT8, false, -1);
        return type;
    }

	@Override
    public VariableType visitInt16Type(jvmParser.Int16TypeContext ctx) {
        VariableType type = new VariableType(JvmType.INT16, false, -1);
        return type;
    }

	@Override
    public VariableType visitInt32Type(jvmParser.Int32TypeContext ctx) {
        VariableType type = new VariableType(JvmType.INT32, false, -1);
        return type;
    }

	@Override
    public VariableType visitInt64Type(jvmParser.Int64TypeContext ctx) {
        VariableType type = new VariableType(JvmType.INT64, false, -1);
        return type;
    }

	@Override
    public VariableType visitFloat32Type(jvmParser.Float32TypeContext ctx) {
        VariableType type = new VariableType(JvmType.FLOAT32, false, -1);
        return type;
    }

	@Override
    public VariableType visitFloat64Type(jvmParser.Float64TypeContext ctx) {
        VariableType type = new VariableType(JvmType.FLOAT64, false, -1);
        return type;
    }

	@Override
    public VariableType visitStringType(jvmParser.StringTypeContext ctx) {
        VariableType type = new VariableType(JvmType.STRING, false, -1);
        return type;
    }

	@Override
    public VariableType visitBoolType(jvmParser.BoolTypeContext ctx) {
        VariableType type = new VariableType(JvmType.BOOL, false, -1);
        return type;
    }

    @Override
    public VariableType visitArrayType(jvmParser.ArrayTypeContext ctx) {
        int maxSize = ctx.size == null ? -1 : Integer.parseInt(ctx.size.getText());

        VariableType type = visit(ctx.type());
        type.setIsArray(true);
        type.setMaxSize(maxSize);
        
        return type;
    }
    //#endregion

    //#region Expression
    @Override
    public VariableType visitExprId(jvmParser.ExprIdContext ctx) {
        Token id = ctx.ID().getSymbol();
        boolean isDeclared = this.lastScope.IsDeclared(id.getText());
        if (!isDeclared) {
            System.err.println("ERROR: undefined " + id.getText() + " in line " + id.getLine());
            System.exit(1);
        }

        Variable var = this.lastScope.GetVar(id.getText());
        return var.getType();
    }
    
    @Override
    public VariableType visitExprValue(jvmParser.ExprValueContext ctx) {
        return visit(ctx.value());
    }

    @Override
    public VariableType visitExprFunctionCall(jvmParser.ExprFunctionCallContext ctx) {
        return visit(ctx.function_call());
    }

    @Override
    public VariableType visitExprNull(jvmParser.ExprNullContext ctx) {
        return null;
    }

    @Override
    public VariableType visitExprMath(jvmParser.ExprMathContext ctx) {
        VariableType type0 = visit(ctx.expr(0));
        VariableType type1 = visit(ctx.expr(1));

        boolean isCompatible = type0.compareTo(type1) == 0;
        if (!isCompatible) {
            String nameType0 = JvmType.Names.get(type0.getType().value);
            String nameType1 = JvmType.Names.get(type1.getType().value);

            System.err.println("ERROR: type " + nameType0 + " is not compatible with type " + nameType1);
            System.exit(1);
        }

        // TODO - VERIFICAR QUAL TIPO SERÁ RETORNADO
        return type0;
    }

    @Override
    public VariableType visitExprBool(jvmParser.ExprBoolContext ctx) {
        VariableType type0 = visit(ctx.expr(0));
        VariableType type1 = visit(ctx.expr(1));

        boolean isCompatible = type0.compareTo(type1) == 0;
        if (!isCompatible) {
            String nameType0 = JvmType.Names.get(type0.getType().value);
            String nameType1 = JvmType.Names.get(type1.getType().value);

            System.err.println("ERROR: type " + nameType0 + " is not compatible with type " + nameType1);
            System.exit(1);
        }

        // TODO - VERIFICAR QUAL TIPO SERÁ RETORNADO
        return type0;
    }

    @Override
    public VariableType visitExprNotBool(jvmParser.ExprNotBoolContext ctx) {
        VariableType var = visit(ctx.expr());

        if (var.getType() != JvmType.BOOL) {
            String nameType = JvmType.Names.get(var.getType().value);

            System.err.println("ERROR: type " + nameType + "is not boolean");
            System.exit(0);
        }

        return var;
    }
    //#endregion

    //region Imports
    @Override
    public VariableType visitSimpleImport(jvmParser.SimpleImportContext ctx) {
        Token token = ctx.STRING_VALUE().getSymbol();

        String importName = token.getText().replaceAll("\"", "");
        imports.put(importName, importName);

        return null;
    }

    @Override
    public VariableType visitMultiImport(jvmParser.MultiImportContext ctx) {
        List<String> importNames = ctx.STRING_VALUE()
            .stream()
            .map(x -> x.getText().replaceAll("\"", ""))
            .collect(Collectors.toList());
        
        importNames.forEach(importName -> imports.put(importName, importName));

        return null;
    }
    //#endregion

    //#region Var assign
    @Override
    public VariableType visitVarWithoutValue(jvmParser.VarWithoutValueContext ctx) {
        Token var = ctx.ID().getSymbol();

        VariableType type = visit(ctx.type());

        lastScope.AddVar(
            new Variable(var.getText(), var.getLine(), type, ctx.CONST() != null)
        );

        return null;
    }

    @Override
    public VariableType visitVarWithValue(jvmParser.VarWithValueContext ctx) {
        Token varInicialize = ctx.varInit;

        if (ctx.CONST() != null && ctx.ASSIGN() != null) {
            System.out.println("ERROR: unexpected :=, expecting =");
            System.exit(1);
        }

        VariableType typeAssign = ctx.type() != null ? visit(ctx.type()) : null;
        VariableType typeValue = visit(ctx.expr());

        if (typeAssign != null && typeAssign.compareTo(typeValue) != 0) {
            System.err.println("ERROR: cannot use " + typeAssign.print() + " as type " + typeValue.print() + " in assignment");
            System.exit(1);
        }
        
        Variable var = new Variable(varInicialize.getText(), varInicialize.getLine(), typeAssign, ctx.CONST() != null);
        lastScope.AddVar(var);

        return null;
    }
    //#endregion

    //#region Visit primitive values
    @Override
    public VariableType visitValueIntD(jvmParser.ValueIntDContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueIntH(jvmParser.ValueIntHContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueIntO(jvmParser.ValueIntOContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueIntB(jvmParser.ValueIntBContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueIntN(jvmParser.ValueIntNContext ctx) {
        VariableType type = new VariableType(JvmType.INT, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueFloat(jvmParser.ValueFloatContext ctx) {
        VariableType type = new VariableType(JvmType.FLOAT64, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueFloatN(jvmParser.ValueFloatNContext ctx) {
        VariableType type = new VariableType(JvmType.FLOAT64, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueString(jvmParser.ValueStringContext ctx) {
        VariableType type = new VariableType(JvmType.STRING, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueChar(jvmParser.ValueCharContext ctx) {
        VariableType type = new VariableType(JvmType.STRING, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueTrue(jvmParser.ValueTrueContext ctx) {
        VariableType type = new VariableType(JvmType.BOOL, false, -1);
        return type;
    }
	
	@Override
    public VariableType visitValueFalse(jvmParser.ValueFalseContext ctx) {
        VariableType type = new VariableType(JvmType.BOOL, false, -1);
        return type;
    }
    //#endregion

    //#region Visit Composite values
    // @Override
    // public VariableType visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
    //     List<ValueContext> values = ctx.value();

    //     int maxSize = ctx.size == null ? -1 : Integer.parseInt(ctx.size.getText());
    //     if (maxSize != -1)
    //         values.remove(0);
    //     int sizeValues = values.size();
        
    //     if (maxSize != -1 && sizeValues > maxSize) {
    //         System.err.println("ERROR: array index " + maxSize + " out of bounds");
    //         System.exit(1);
    //     }

    //     visit(ctx.type());
    //     VariableType arrayType = new VariableType(lastType.getType(), true, maxSize);

    //     List<String> errors = new ArrayList<String>();
    //     values.forEach(value -> {
    //         visit(value);
    //         if (type.getType() != arrayType.getType()) {
    //             errors.add(
    //                 "ERROR: cannot use " + value.getText() + " (type "
    //                 + Types.Names.get(type.getType())
    //                 + ")" + " as type "
    //                 + Types.Names.get(arrayType.getType())
    //                 + " in array or slice literal");
    //         }
    //     });
    //     if (errors.size() > 0) {
    //         errors.forEach(err -> System.err.println(err));
    //         System.exit(1);
    //     }

    //     VariableType type = arrayType;
        
    //     return null;
    // }

    // @Override
    // public VariableType visitFunctionWithParam(jvmParser.FunctionWithParamContext ctx) {
    //     List<Token> vars = ctx.ID()
    //         .stream()
    //         .map(x -> x.getSymbol())
    //         .collect(Collectors.toList());
        
    //     if (ctx.parent != null)
    //         vars.remove(0);

    //     List<String> errors = new ArrayList<String>();
    //     vars.forEach(var -> {
    //         boolean isDeclared = lastScope.IsDeclared(var);
    //         if (!isDeclared)
    //             errors.add("ERROR: undefined " + var.getText() + " in line " + var.getLine());
    //     });
        
    //     if (errors.size() > 0) {
    //         errors.forEach(err -> System.err.println(err));
    //         System.exit(1);
    //     }

    //     return null;
    // }

    @Override
    public VariableType visitFunctionRecursive(jvmParser.FunctionRecursiveContext ctx) {
        String parentName = ctx.parent.getText();

        boolean isDeclared = imports.get(parentName) != null;
        if (!isDeclared) {
            System.err.println("ERROR: undefined " + parentName + " in line " + ctx.parent.getLine());
            System.exit(1);
        }

        visit(ctx.function_call());

        return null;
    }
    //#endregion

    //#region For
    @Override
    public VariableType visitFor_init(jvmParser.For_initContext ctx) {
        Token var = ctx.ID().getSymbol();
        VariableType type = visit(ctx.value());

        lastScope.AddVar(new Variable(var.getText(), var.getLine(), type, false));

        return null;
    }

    @Override
    public VariableType visitFor_range(jvmParser.For_rangeContext ctx) {
        List<Token> vars = ctx.ID()
            .stream()
            .map(x -> x.getSymbol())
            .collect(Collectors.toList());
        
        Token lastVar = vars.remove(vars.size() - 1);
        boolean isDeclared = lastScope.IsDeclared(lastVar.getText());
        if (!isDeclared) {
            System.err.println("ERROR: undefined " + lastVar.getText() + " in line " + lastVar.getLine());
            System.exit(1);
        }
        Variable lastVarDeclaration = lastScope.GetVar(lastVar.getText());

        vars.forEach(var -> {
            JvmType type = lastVarDeclaration.getType().getType();
            lastScope.AddVar(new Variable(var.getText(), var.getLine(), new VariableType(type, false, -1), false));
        });

        return null;
    }
    //#endregion

    //#region Scopes
    @Override
    public VariableType visitFunction_stmt(jvmParser.Function_stmtContext ctx) {
        Scope scope = new Scope();

        scopes.add(scope);
        lastScope = scope;

        return visit(ctx.scope());
    }
    //#endregion

}