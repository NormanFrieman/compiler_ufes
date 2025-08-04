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
import checker.utils.ArraySize;
import checker.utils.FunctionDeclaration;
import checker.utils.JvmType;
import checker.utils.Scope;
import checker.utils.Variable;
import checker.utils.VariableType;

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

    //#region Imports
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

    //#region Expression
    @Override
    public VariableType visitExprId(jvmParser.ExprIdContext ctx) {
        Token id = ctx.ID().getSymbol();

        Variable var = this.CheckVar(id);
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

        this.TypeCompare(type0, type1);
        return type0;
    }

    @Override
    public VariableType visitExprBool(jvmParser.ExprBoolContext ctx) {
        VariableType type0 = visit(ctx.expr(0));
        VariableType type1 = visit(ctx.expr(1));

        this.TypeCompare(type0, type1);
        return new VariableType(JvmType.BOOL);
    }

    @Override
    public VariableType visitExprNotBool(jvmParser.ExprNotBoolContext ctx) {
        VariableType var = visit(ctx.expr());

        if (var.getType() != JvmType.BOOL) {
            String nameType = JvmType.Names.get(var.getType().value);

            ExitWithError("ERROR: type " + nameType + "is not boolean");
        }

        return var;
    }
    //#endregion

    //#region Visit types
    @Override
    public VariableType visitUintType(jvmParser.UintTypeContext ctx) {
        return new VariableType(JvmType.UINT);
    }

    @Override
    public VariableType visitIntType(jvmParser.IntTypeContext ctx) {
        return new VariableType(JvmType.INT);
    }

	@Override
    public VariableType visitInt8Type(jvmParser.Int8TypeContext ctx) {
        return new VariableType(JvmType.INT8);
    }

	@Override
    public VariableType visitInt16Type(jvmParser.Int16TypeContext ctx) {
        return new VariableType(JvmType.INT16);
    }

	@Override
    public VariableType visitInt32Type(jvmParser.Int32TypeContext ctx) {
        return new VariableType(JvmType.INT32);
    }

	@Override
    public VariableType visitInt64Type(jvmParser.Int64TypeContext ctx) {
        return new VariableType(JvmType.INT64);
    }

	@Override
    public VariableType visitFloat32Type(jvmParser.Float32TypeContext ctx) {
        return new VariableType(JvmType.FLOAT32);
    }

	@Override
    public VariableType visitFloat64Type(jvmParser.Float64TypeContext ctx) {
        return new VariableType(JvmType.FLOAT64);
    }

	@Override
    public VariableType visitStringType(jvmParser.StringTypeContext ctx) {
        return new VariableType(JvmType.STRING);
    }

	@Override
    public VariableType visitBoolType(jvmParser.BoolTypeContext ctx) {
        return new VariableType(JvmType.BOOL);
    }

    @Override
    public VariableType visitArrayType(jvmParser.ArrayTypeContext ctx) {
        int maxSize = ctx.size == null ? ArraySize.NO_EXPLICIT_SIZE.value : Integer.parseInt(ctx.size.getText());

        VariableType type = visit(ctx.type());
        type.setIsArray(true);
        type.setMaxSize(maxSize);
        
        return type;
    }
    //#endregion

    //#region Value
    //#region Visit primitive values
    @Override
    public VariableType visitValueIntD(jvmParser.ValueIntDContext ctx) {
        return new VariableType(JvmType.INT);
    }
	
	@Override
    public VariableType visitValueIntH(jvmParser.ValueIntHContext ctx) {
        return new VariableType(JvmType.INT);
    }
	
	@Override
    public VariableType visitValueIntO(jvmParser.ValueIntOContext ctx) {
        return new VariableType(JvmType.INT);
    }
	
	@Override
    public VariableType visitValueIntB(jvmParser.ValueIntBContext ctx) {
        return new VariableType(JvmType.INT);
    }
	
	@Override
    public VariableType visitValueIntN(jvmParser.ValueIntNContext ctx) {
        return new VariableType(JvmType.INT);
    }
	
	@Override
    public VariableType visitValueFloat(jvmParser.ValueFloatContext ctx) {
        return new VariableType(JvmType.FLOAT32);
    }
	
	@Override
    public VariableType visitValueFloatN(jvmParser.ValueFloatNContext ctx) {
        return new VariableType(JvmType.FLOAT32);
    }
	
	@Override
    public VariableType visitValueString(jvmParser.ValueStringContext ctx) {
        return new VariableType(JvmType.STRING);
    }
	
	@Override
    public VariableType visitValueChar(jvmParser.ValueCharContext ctx) {
        return new VariableType(JvmType.STRING);
    }
	
	@Override
    public VariableType visitValueTrue(jvmParser.ValueTrueContext ctx) {
        return new VariableType(JvmType.BOOL);
    }
	
	@Override
    public VariableType visitValueFalse(jvmParser.ValueFalseContext ctx) {
        return new VariableType(JvmType.BOOL);
    }
    //#endregion

    //#region Visit Composite values
    @Override
    public VariableType visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
        VariableType sizeType = (ctx.size != null) ? visit(ctx.size) : null;
        VariableType expectedSizeType = new VariableType(JvmType.INT);

        // Check if size value is int
        if (sizeType != null && expectedSizeType.compareTo(sizeType) != 0){
            ExitWithError("ERROR: size of array must be int value");
        }

        // Check if values of array are compatible with type
        VariableType typeArray = visit(ctx.type());
        ctx.values.forEach(value -> {
            VariableType valueType = visit(value);
            this.TypeCompare(typeArray, valueType);
        });

        // If size is value type, set maxSize of array
        if (ctx.size instanceof ExprValueContext) {
            ExprValueContext exprValue = (ExprValueContext)ctx.size;
            typeArray.setMaxSize(Integer.parseInt(exprValue.value().getText()));
        }

        // If maxSize is defined, check if values size is compatible with maxSize
        if (typeArray.getMaxSize() != -1 && ctx.values.size() > 0 && typeArray.getMaxSize() < ctx.values.size())
            ExitWithError("ERROR: array index " + typeArray.getMaxSize() + " out of bounds");
        
        typeArray.setIsArray(true);
        return typeArray;
    }

    @Override
    public VariableType visitValueArrayGet(jvmParser.ValueArrayGetContext ctx) {
        return null;
    }

    @Override
    public VariableType visitValueConversion(jvmParser.ValueConversionContext ctx) {
        visit(ctx.expr());
        return visit(ctx.type());
        // VERIFY TYPE CONVERSIONS
    }

    @Override
    public VariableType visitValueProp(jvmParser.ValuePropContext ctx) {
        return null;
    }
    //#endregion
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

        if (ctx.CONST() != null && ctx.ASSIGN() != null)
            ExitWithError("ERROR: unexpected :=, expecting =");

        boolean isVarInit = ctx.ASSIGN() != null
            || (ctx.VAR() != null && ctx.ASSIGN_VAR() != null)
            || (ctx.CONST() != null && ctx.ASSIGN_VAR() != null);
        boolean isVarUpdate = ctx.ASSIGN_VAR() != null;

        if (isVarInit) {
            VariableType typeAssign = ctx.type() != null ? visit(ctx.type()) : null;
            VariableType typeValue = visit(ctx.expr());
            
            Variable var = new Variable(
                varInicialize.getText(),
                varInicialize.getLine(),
                TypeAssign(typeAssign, typeValue),
                ctx.CONST() != null
            );
            lastScope.AddVar(var);
        } else if (isVarUpdate) {
            Variable var = this.CheckVar(varInicialize);            
            VariableType assignType = visit(ctx.expr());

            this.TypeCompare(var.getType(), assignType);
        }

        return null;
    }

    @Override
    public VariableType visitVarWithIncrease(jvmParser.VarWithIncreaseContext ctx) {
        // TO DO
        return null;
    }
    //#endregion

    //#region Functions
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

            lastScope.AddVar(new Variable(varName, ids.get(i).getLine(), type));
            paramsType.add(type);
        }


        FunctionDeclaration function = new FunctionDeclaration(
            functionName,
            ctx.functionName.getLine(),
            paramsType,
            functionType,
            null);

        this.AddFunction(function);

        return functionType;
    }

    @Override
    public VariableType visitFunctionWithParam(jvmParser.FunctionWithParamContext ctx) {
        FunctionDeclaration function = this.CheckFunction(ctx.parent);

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
        this.CheckImports(ctx.parent);
        return visit(ctx.function_call());
    }

    @Override
    public VariableType visitFunction_stmt(jvmParser.Function_stmtContext ctx) {
        Scope scope = new Scope();

        scopes.add(scope);
        lastScope = scope;

        VariableType functionType = visit(ctx.function_declaration());
        VariableType scopeType = visit(ctx.scope());

        Boolean functionTypeIsNull = functionType == null;
        Boolean scopeTypeIsNull = scopeType == null;

        if (functionTypeIsNull && scopeTypeIsNull)
            return null;

        if (functionTypeIsNull)
            ExitWithError("Too many return values");

        if (scopeTypeIsNull)
            ExitWithError("Missing return");

        TypeCompare(functionType, scopeType);
        return null;
    }
    
    @Override
    public VariableType visitReturn_stmt(jvmParser.Return_stmtContext ctx) {
        if (ctx.expr() == null)
            return null;
        
        return visit(ctx.expr());
    }
    //#endregion

    //#region Loop
    @Override
    public VariableType visitFor_init(jvmParser.For_initContext ctx) {
        Token var = ctx.ID().getSymbol();
        VariableType type = visit(ctx.value());

        lastScope.AddVar(new Variable(var.getText(), var.getLine(), type));

        return null;
    }

    @Override
    public VariableType visitFor_cond(jvmParser.For_condContext ctx) {
        // TO DO
        return null;
    }

    @Override
    public VariableType visitFor_end(jvmParser.For_endContext ctx) {
        // TO DO
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
        if (!isDeclared)
            ExitWithError("ERROR: undefined " + lastVar.getText() + " in line " + lastVar.getLine());

        Variable lastVarDeclaration = lastScope.GetVar(lastVar.getText());
        JvmType type = lastVarDeclaration.getType().getType();

        // Index of For Range
        lastScope.AddVar(new Variable(indexVar.getText(), indexVar.getLine(), new VariableType(JvmType.INT)));
        // Iter of For Range
        lastScope.AddVar(new Variable(iterVar.getText(), iterVar.getLine(), new VariableType(type)));

        return null;
    }
    //#endregion

    //#region Conditional
    
    // TO DO

    //#endregion

    //#region GOTO

    // TO DO

    //#endregion

    //#region Auxiliary methods
    //#region Variable
    Variable CheckVar(Token token) {
        String varName = token.getText();

        Variable var = this.lastScope.GetVar(varName);
        if (var == null)
            ExitWithError("ERROR: undefined " + varName + " in line " + token.getLine());

        return var;
    }

    void TypeCompare(VariableType type0, VariableType type1) {
        if (type1.compareTo(type0) != 0)
            ExitWithError("ERROR: mismatched types " + type1.print() + " and " + type0.print());
    }

    VariableType TypeAssign(VariableType type0, VariableType type1) {
        if (type0 == null)
            return type1;
        
        if (type1 == null)
            return type0;
        
        if (!type0.IsAssignable(type1))
            ExitWithError("ERROR: cannot use " + type1.print() + " as type " + type0.print() + " in assignment");

        JvmType type = type0.TypeAssign(type1);
        return type == type0.getType()
            ? type0
            : type1;
    }

    //#endregion
    //#region Function
    FunctionDeclaration CheckFunction(Token token) {
        String functionName = token.getText();

        FunctionDeclaration function = this.GetFunction(functionName);
        if (function == null)
            ExitWithError("ERROR: undefined " + function + " in line " + token.getLine());

        return function;
    }

    public void AddFunction(FunctionDeclaration function) {
        functionsMap.put(function.getName(), function);
    }

    public FunctionDeclaration GetFunction(String name) {
        return functionsMap.get(name);
    }
    
    public boolean FunctionIsDeclared(String functionName) {
        return this.GetFunction(functionName) != null;
    }
    //#endregion
    //#region Imports
    public void CheckImports(Token token) {
        String parentName = token.getText();

        boolean isDeclared = imports.get(parentName) != null;
        if (!isDeclared)
            ExitWithError("ERROR: undefined " + parentName + " in line " + token.getLine());
    }
    //#endregion
    //#region Error
    void ExitWithError(String err) {
        System.err.println(err);
        System.exit(1);
    }
    //#endregion
    
    //#endregion
}