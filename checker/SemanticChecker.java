package checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Token;
import generated.jvmParser;
import generated.jvmParser.ExprValueContext;
import generated.jvmParser.TypeContext;
import generated.jvmParserBaseVisitor;
import checker.utils.JvmType;
import checker.utils.Scope;

public class SemanticChecker extends jvmParserBaseVisitor<VariableType> {
    // Scopes
    private LinkedList<Scope> scopes = new LinkedList<Scope>();
    private Scope lastScope;

    // Functions
    private HashMap<String, FunctionDeclaration> functionsMap = new HashMap<String, FunctionDeclaration>();

    // Import list
    private HashMap<String, String> imports = new HashMap<String, String>();

    public SemanticChecker() {
        //#region Declaring functions
        FunctionDeclaration append = new FunctionDeclaration(
            "append",
            0,
            null,
            new VariableType(JvmType.STRING, true, -1),
            params -> true
        );

        FunctionDeclaration println = new FunctionDeclaration(
            "Println",
            0,
            null,
            null,
            params -> true
        );

        FunctionDeclaration printf = new FunctionDeclaration(
            "Printf",
            0,
            null,
            null,
            params -> true
        );

        FunctionDeclaration len = new FunctionDeclaration(
            "len",
            0,
            null,
            new VariableType(JvmType.INT, false, 0),
            params -> true
        );

        FunctionDeclaration cap = new FunctionDeclaration(
            "cap",
            0,
            null,
            new VariableType(JvmType.INT, false, 0),
            params -> true
        );

        FunctionDeclaration toUpper = new FunctionDeclaration(
            "ToUpper",
            0,
            null,
            null,
            params -> true
        );
        //#endregion

        functionsMap.put(append.getName(), append);
        functionsMap.put(println.getName(), println);
        functionsMap.put(printf.getName(), printf);
        functionsMap.put(len.getName(), len);
        functionsMap.put(cap.getName(), cap);
        functionsMap.put(toUpper.getName(), toUpper);
    }

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

        boolean isDeclared = this.lastScope.VarIsDeclared(id.getText());
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

        return new VariableType(JvmType.BOOL, false, -1);
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
            System.err.println("ERROR: unexpected :=, expecting =");
            System.exit(1);
        }

        if (
            ctx.ASSIGN() != null
            || (ctx.VAR() != null && ctx.ASSIGN_VAR() != null)
            || (ctx.CONST() != null && ctx.ASSIGN_VAR() != null)
        ) {
            VariableType typeAssign = ctx.type() != null ? visit(ctx.type()) : null;
            VariableType typeValue = visit(ctx.expr());
    
            if (typeAssign != null && typeAssign.compareTo(typeValue) != 0) {
                System.err.println("ERROR: cannot use " + typeValue.print() + " as type " + typeAssign.print() + " in assignment");
                System.exit(1);
            }
            
            VariableType typeVar = typeAssign != null ? typeAssign : typeValue;
            Variable var = new Variable(varInicialize.getText(), varInicialize.getLine(), typeVar, ctx.CONST() != null);
            lastScope.AddVar(var);
        } else if (ctx.ASSIGN_VAR() != null) {
            boolean isDeclared = lastScope.VarIsDeclared(varInicialize.getText());
            if (!isDeclared) {
                System.err.println("ERROR: variable " + varInicialize.getText() + " is not declared");
                System.exit(1);
            }

            Variable var = lastScope.GetVar(varInicialize.getText());
            VariableType varType = var.getType();
            
            VariableType assignType = visit(ctx.expr());
            if (varType.compareTo(assignType) != 0) {
                System.err.println("ERROR: assignment incorrect");
                System.exit(1);
            }
        }

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
    @Override
    public VariableType visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
        VariableType sizeType = (ctx.size != null) ? visit(ctx.size) : null;
        VariableType expectedSizeType = new VariableType(JvmType.INT, false, -1);

        // Check if size value is int
        if (sizeType != null && expectedSizeType.compareTo(sizeType) != 0){ 
            System.err.println("ERROR: size of array must be int value");
            System.exit(1);
        }

        // Check if values of array are compatible with type
        VariableType typeArray = visit(ctx.type());
        ctx.values.forEach(value -> {
            VariableType valueType = visit(value);
            if (typeArray.compareTo(valueType) != 0) {
                String nameTypeArray = JvmType.Names.get(typeArray.getType().value);
                String nameTypeValue = JvmType.Names.get(valueType.getType().value);
    
                System.err.println("ERROR: type " + nameTypeArray + " is not compatible with type " + nameTypeValue);
                System.exit(1);
            }
        });

        // If size is value type, set maxSize of array
        if (ctx.size instanceof ExprValueContext) {
            ExprValueContext exprValue = (ExprValueContext)ctx.size;
            typeArray.setMaxSize(Integer.parseInt(exprValue.value().getText()));
        }

        // If maxSize is defined, check if values size is compatible with maxSize
        if (typeArray.getMaxSize() != -1 && ctx.values.size() > 0 && typeArray.getMaxSize() < ctx.values.size()) {
            System.err.println("ERROR: array index " + typeArray.getMaxSize() + " out of bounds");
            System.exit(1);
        }
        
        typeArray.setIsArray(true);
        return typeArray;
    }

    @Override
    public VariableType visitFunctionWithParam(jvmParser.FunctionWithParamContext ctx) {
        var functionName = ctx.parent.getText();
        if (!this.FunctionIsDeclared(functionName)) {
            System.err.println("ERROR: function " + functionName + " is not declared");
            System.exit(1);
        }

        FunctionDeclaration function = this.GetFunction(functionName);

        LinkedList<VariableType> params = new LinkedList<VariableType>();
        ctx.expr().forEach(e -> {
            VariableType type = visit(e);
            params.add(type);
        });
        function.CheckParams(params);

        return function.getReturnType();
    }

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

    @Override
    public VariableType visitValueConversion(jvmParser.ValueConversionContext ctx) {
        visit(ctx.expr());
        return visit(ctx.type());
        // VERIFY TYPE CONVERSIONS
    }

    @Override
    public VariableType visitValueArrayGet(jvmParser.ValueArrayGetContext ctx) {
        // VERIFY type INT
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
        
        Token indexVar = vars.get(0);
        Token iterVar = vars.get(1);        
        Token lastVar = vars.get(2);
        
        boolean isDeclared = lastScope.VarIsDeclared(lastVar.getText());
        if (!isDeclared) {
            System.err.println("ERROR: undefined " + lastVar.getText() + " in line " + lastVar.getLine());
            System.exit(1);
        }

        Variable lastVarDeclaration = lastScope.GetVar(lastVar.getText());
        JvmType type = lastVarDeclaration.getType().getType();

        // Index of For Range
        lastScope.AddVar(new Variable(indexVar.getText(), indexVar.getLine(), new VariableType(JvmType.INT, false, -1), false));
        // Iter of For Range
        lastScope.AddVar(new Variable(iterVar.getText(), iterVar.getLine(), new VariableType(type, false, -1), false));

        return null;
    }
    //#endregion

    //#region Scopes
    @Override
    public VariableType visitFunction_stmt(jvmParser.Function_stmtContext ctx) {
        Scope scope = new Scope();

        scopes.add(scope);
        lastScope = scope;

        visit(ctx.function_declaration());
        return visit(ctx.scope());
    }

    @Override
    public VariableType visitFunction_declaration(jvmParser.Function_declarationContext ctx) {
        String functionName = ctx.functionName.getText();
        VariableType functionType = null;
        if (ctx.functionType != null)
            functionType = visit(ctx.functionType);
        
        List<Token> ids = ctx.ids;
        List<TypeContext> types = ctx.types;

        LinkedList<VariableType> paramsType = new LinkedList<VariableType>();
        
        for (int i = 0; i < ids.size(); i++) {
            String varName = ids.get(i).getText();
            VariableType type = visit(types.get(i));

            lastScope.AddVar(new Variable(varName, ids.get(i).getLine(), type, false));
            paramsType.add(type);
        }


        FunctionDeclaration function = new FunctionDeclaration(
            functionName,
            ctx.functionName.getLine(),
            paramsType,
            functionType,
            null);

        this.AddFunction(function);

        return null;
    }

    // @Override
    // public VariableType visitReturn_stmt(jvmParser.Return_stmtContext ctx) {
    //     if (ctx.expr() == null)
    //         return null;
        
    //     VariableType returnType = ctx.expr();

    //     if (lastScope.)
    // }
    //#endregion



    public void AddFunction(FunctionDeclaration function) {
        functionsMap.put(function.getName(), function);
    }

    public FunctionDeclaration GetFunction(String name) {
        return functionsMap.get(name);
    }
    
    public boolean FunctionIsDeclared(String functionName) {
        return this.GetFunction(functionName) != null;
    }

}