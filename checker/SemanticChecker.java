package checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Token;
import ast.AST;
import ast.NodeKind;
import generated.jvmParser;
import generated.jvmParser.CommandContext;
import generated.jvmParser.ExprValueContext;
import generated.jvmParser.TypeContext;
import generated.jvmParserBaseVisitor;
import checker.utils.ArraySize;
import checker.utils.FunctionDeclaration;
import checker.utils.JvmType;
import checker.utils.Scope;
import checker.utils.Variable;
import checker.utils.VariableType;

public class SemanticChecker extends jvmParserBaseVisitor<AST> {
    // Scopes
    private LinkedList<Scope> scopes = new LinkedList<Scope>();
    private Scope lastScope;
    AST root;

    // Functions
    private HashMap<String, FunctionDeclaration> functionsMap = new HashMap<String, FunctionDeclaration>();

    // Import list
    private HashMap<String, String> imports = new HashMap<String, String>();
    
    // Type aux
    private VariableType lastType;

    public SemanticChecker() {
        //#region Declaring functions
        FunctionDeclaration append = new FunctionDeclaration(
            "append",
            0,
            null,
            new VariableType(JvmType.STRING, true, ArraySize.NO_ARRAY.value),
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
            new VariableType(JvmType.INT, false, ArraySize.NO_ARRAY.value),
            params -> true
        );

        FunctionDeclaration cap = new FunctionDeclaration(
            "cap",
            0,
            null,
            new VariableType(JvmType.INT, false, ArraySize.NO_ARRAY.value),
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

    //#region Program
    @Override
    public AST visitProgram(jvmParser.ProgramContext ctx) {
        visit(ctx.init());
        AST stmt = visit(ctx.stmt_sect());

        this.root = AST.NewSubtree(NodeKind.PROGRAM_NODE, null, null, stmt);
        return this.root;
    }

    @Override
    public AST visitScope(jvmParser.ScopeContext ctx) {
        AST scope = AST.NewSubtree(NodeKind.SCOPE_NODE, null, null, null);

        List<CommandContext> commands = ctx.command();
        foreach (jvmParser.CommandContext command : commands) {
            scope.AddChild(visit(command));
        }

        return scope;
    }
    //#endregion

    //#region Imports
    @Override
    public AST visitSimpleImport(jvmParser.SimpleImportContext ctx) {
        Token token = ctx.STRING_VALUE().getSymbol();

        String importName = token.getText().replaceAll("\"", "");
        imports.put(importName, importName);

        return null;
    }

    @Override
    public AST visitMultiImport(jvmParser.MultiImportContext ctx) {
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
    public AST visitExprId(jvmParser.ExprIdContext ctx) {
        Token id = ctx.ID().getSymbol();

        this.lastType = this.CheckVar(id).getType();
        return null;
    }
    
    @Override
    public AST visitExprValue(jvmParser.ExprValueContext ctx) {
        return visit(ctx.value());
    }

    @Override
    public AST visitExprFunctionCall(jvmParser.ExprFunctionCallContext ctx) {
        return visit(ctx.function_call());
    }

    @Override
    public AST visitExprNull(jvmParser.ExprNullContext ctx) {
        this.lastType = null;
        return null;
    }

    @Override
    public AST visitExprMath(jvmParser.ExprMathContext ctx) {
        visit(ctx.expr(0));
        VariableType type0 = this.lastType;

        visit(ctx.expr(1));
        VariableType type1 = this.lastType;

        this.TypeCompare(type0, type1);
        this.lastType = type0;
        return null;
    }

    @Override
    public AST visitExprBool(jvmParser.ExprBoolContext ctx) {
        visit(ctx.expr(0));
        VariableType type0 = this.lastType;

        visit(ctx.expr(1));
        VariableType type1 = this.lastType;

        this.TypeCompare(type0, type1);
        this.lastType = new VariableType(JvmType.BOOL);
        return null;
    }

    @Override
    public AST visitExprNotBool(jvmParser.ExprNotBoolContext ctx) {
        visit(ctx.expr());
        VariableType var = this.lastType;

        if (var.getType() != JvmType.BOOL) {
            String nameType = JvmType.Names.get(var.getType().value);

            ExitWithError("ERROR: type " + nameType + "is not boolean");
        }

        return null;
    }
    //#endregion

    //#region Visit types
    @Override
    public AST visitUintType(jvmParser.UintTypeContext ctx) {
        this.lastType = new VariableType(JvmType.UINT);
        return null;
    }

    @Override
    public AST visitIntType(jvmParser.IntTypeContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }

	@Override
    public AST visitInt8Type(jvmParser.Int8TypeContext ctx) {
        this.lastType = new VariableType(JvmType.INT8);
        return null;
    }

	@Override
    public AST visitInt16Type(jvmParser.Int16TypeContext ctx) {
        this.lastType = new VariableType(JvmType.INT16);
        return null;
    }

	@Override
    public AST visitInt32Type(jvmParser.Int32TypeContext ctx) {
        this.lastType = new VariableType(JvmType.INT32);
        return null;
    }

	@Override
    public AST visitInt64Type(jvmParser.Int64TypeContext ctx) {
        this.lastType = new VariableType(JvmType.INT64);
        return null;
    }

	@Override
    public AST visitFloat32Type(jvmParser.Float32TypeContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT32);
        return null;
    }

	@Override
    public AST visitFloat64Type(jvmParser.Float64TypeContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT64);
        return null;
    }

	@Override
    public AST visitStringType(jvmParser.StringTypeContext ctx) {
        this.lastType = new VariableType(JvmType.STRING);
        return null;
    }

	@Override
    public AST visitBoolType(jvmParser.BoolTypeContext ctx) {
        this.lastType = new VariableType(JvmType.BOOL);
        return null;
    }

    @Override
    public AST visitArrayType(jvmParser.ArrayTypeContext ctx) {
        int maxSize = ctx.size == null ? ArraySize.NO_EXPLICIT_SIZE.value : Integer.parseInt(ctx.size.getText());

        visit(ctx.type());
        this.lastType.setIsArray(true);
        this.lastType.setMaxSize(maxSize);
        
        return null;
    }
    //#endregion

    //#region Value
    //#region Visit primitive values
    @Override
    public AST visitValueIntD(jvmParser.ValueIntDContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }
	
	@Override
    public AST visitValueIntH(jvmParser.ValueIntHContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }
	
	@Override
    public AST visitValueIntO(jvmParser.ValueIntOContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }
	
	@Override
    public AST visitValueIntB(jvmParser.ValueIntBContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }
	
	@Override
    public AST visitValueIntN(jvmParser.ValueIntNContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return null;
    }
	
	@Override
    public AST visitValueFloat(jvmParser.ValueFloatContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT32);
        return null;
    }
	
	@Override
    public AST visitValueFloatN(jvmParser.ValueFloatNContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT32);
        return null;
    }
	
	@Override
    public AST visitValueString(jvmParser.ValueStringContext ctx) {
        this.lastType = new VariableType(JvmType.STRING);
        return null;
    }
	
	@Override
    public AST visitValueChar(jvmParser.ValueCharContext ctx) {
        this.lastType = new VariableType(JvmType.STRING);
        return null;
    }
	
	@Override
    public AST visitValueTrue(jvmParser.ValueTrueContext ctx) {
        this.lastType = new VariableType(JvmType.BOOL);
        return null;
    }
	
	@Override
    public AST visitValueFalse(jvmParser.ValueFalseContext ctx) {
        this.lastType = new VariableType(JvmType.BOOL);
        return null;
    }
    //#endregion

    //#region Visit Composite values
    @Override
    public AST visitValueArrayInit(jvmParser.ValueArrayInitContext ctx) {
        if (ctx.size != null)
            visit(ctx.size);

        VariableType sizeType = (ctx.size != null) ? this.lastType : null;
        VariableType expectedSizeType = new VariableType(JvmType.INT);

        // Check if size value is int
        if (sizeType != null && expectedSizeType.compareTo(sizeType) != 0){
            ExitWithError("ERROR: size of array must be int value");
        }

        // Check if values of array are compatible with type
        visit(ctx.type());
        VariableType typeArray = this.lastType;

        ctx.values.forEach(value -> {
            visit(value);
            VariableType valueType = this.lastType;

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
        this.lastType = typeArray;
        return null;
    }

    @Override
    public AST visitValueArrayGet(jvmParser.ValueArrayGetContext ctx) {
        return null;
    }

    @Override
    public AST visitValueConversion(jvmParser.ValueConversionContext ctx) {
        visit(ctx.expr());
        visit(ctx.type());
        return null;
        // TO DO
        // VERIFY TYPE CONVERSIONS
    }

    @Override
    public AST visitValueProp(jvmParser.ValuePropContext ctx) {
        return null;
    }
    //#endregion
    //#endregion

    //#region Var assign
    @Override
    public AST visitVarWithoutValue(jvmParser.VarWithoutValueContext ctx) {
        Token var = ctx.ID().getSymbol();

        visit(ctx.type());
        VariableType type = this.lastType;

        lastScope.AddVar(
            new Variable(var.getText(), var.getLine(), type, ctx.CONST() != null)
        );

        return null;
    }

    @Override
    public AST visitVarWithValue(jvmParser.VarWithValueContext ctx) {
        Token varInicialize = ctx.varInit;
        if (ctx.CONST() != null && ctx.ASSIGN() != null)
            ExitWithError("ERROR: unexpected :=, expecting =");

        boolean isVarInit = ctx.ASSIGN() != null
            || (ctx.VAR() != null && ctx.ASSIGN_VAR() != null)
            || (ctx.CONST() != null && ctx.ASSIGN_VAR() != null);
        boolean isVarUpdate = ctx.ASSIGN_VAR() != null;

        if (isVarInit) {
            if (ctx.type() != null)
                visit(ctx.type());

            VariableType typeAssign = ctx.type() != null ? this.lastType : null;
            visit(ctx.expr());
            VariableType typeValue = this.lastType;
            
            Variable var = new Variable(
                varInicialize.getText(),
                varInicialize.getLine(),
                TypeAssign(typeAssign, typeValue),
                ctx.CONST() != null
            );
            lastScope.AddVar(var);
        } else if (isVarUpdate) {
            Variable var = this.CheckVar(varInicialize); 
            visit(ctx.expr());
            VariableType assignType = this.lastType;

            this.TypeCompare(var.getType(), assignType);
        }

        return null;
    }

    @Override
    public AST visitVarWithIncrease(jvmParser.VarWithIncreaseContext ctx) {
        Variable var = this.CheckVar(ctx.ID().getSymbol());
        this.CheckIncrease(var);

        return null;
    }
    //#endregion

    //#region Functions
    @Override
    public AST visitFunction_declaration(jvmParser.Function_declarationContext ctx) {
        String functionName = ctx.functionName.getText();
        if (ctx.functionType != null)
            visit(ctx.functionType);
        
        VariableType functionType = ctx.functionType != null ? this.lastType : null;

        List<Token> ids = ctx.ids;
        List<TypeContext> types = ctx.types;

        LinkedList<VariableType> paramsType = new LinkedList<VariableType>();
        
        for (int i = 0; i < ids.size(); i++) {
            String varName = ids.get(i).getText();
            visit(types.get(i));
            VariableType type = this.lastType;

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
        this.lastType = functionType;

        return AST.NewSubtree(NodeKind.FUNCTION_DECLARATION_NODE, functionName, functionType, null);
    }

    @Override
    public AST visitFunctionWithParam(jvmParser.FunctionWithParamContext ctx) {
        FunctionDeclaration function = this.CheckFunction(ctx.parent);

        LinkedList<VariableType> params = new LinkedList<VariableType>();
        ctx.expr().forEach(e -> {
            visit(e);
            VariableType type = this.lastType;
            params.add(type);
        });
        function.CheckParams(params);

        this.lastType = function.getReturnType();
        return null;
    }

    @Override
    public AST visitFunctionRecursive(jvmParser.FunctionRecursiveContext ctx) {
        this.CheckImports(ctx.parent);
        visit(ctx.function_call());

        return null;
    }

    @Override
    public AST visitFunction_stmt(jvmParser.Function_stmtContext ctx) {
        // Scope scope = new Scope();
        
        // scopes.add(scope);
        // lastScope = scope;
        
        AST funcDecl = visit(ctx.function_declaration());
        VariableType functionType = this.lastType;
        
        AST scope = visit(ctx.scope());
        VariableType scopeType = this.lastType;

        Boolean functionTypeIsNull = functionType == null;
        Boolean scopeTypeIsNull = scopeType == null;

        if (functionTypeIsNull && scopeTypeIsNull)
            return null;

        if (functionTypeIsNull)
            ExitWithError("Too many return values");

        if (scopeTypeIsNull)
            ExitWithError("Missing return");

        TypeCompare(functionType, scopeType);

        funcDecl.AddChild(scope);
        return funcDecl;
    }
    
    @Override
    public AST visitReturn_stmt(jvmParser.Return_stmtContext ctx) {
        if (ctx.expr() == null)
            return null;
        
        visit(ctx.expr());
        return null;
    }
    //#endregion

    //#region Loop
    @Override
    public AST visitFor_init(jvmParser.For_initContext ctx) {
        Token var = ctx.ID().getSymbol();
        visit(ctx.value());
        VariableType type = this.lastType;

        lastScope.AddVar(new Variable(var.getText(), var.getLine(), type));

        return null;
    }

    @Override
    public AST visitFor_cond(jvmParser.For_condContext ctx) {
        Token token = ctx.ID(0).getSymbol();
        Variable var = this.CheckVar(token);
        VariableType type = null;

        if (ctx.value() != null) {
            visit(ctx.value());
            type = this.lastType;
        }
        if (ctx.ID(1) != null) {
            visit(ctx.ID(1));
            type = this.lastType;
        }
        if (ctx.function_call() != null) {
            visit(ctx.function_call());
            type = this.lastType;
        }

        this.TypeCompare(var.getType(), type);
        return null;
    }

    @Override
    public AST visitFor_end(jvmParser.For_endContext ctx) {
        Variable var = this.CheckVar(ctx.ID().getSymbol());
        this.CheckIncrease(var);
        
        return null;
    }

    @Override
    public AST visitFor_range(jvmParser.For_rangeContext ctx) {
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

    void CheckIncrease(Variable var) {
        VariableType varType = var.getType();

        if (!varType.IsNumeric())
            ExitWithError("ERROR: invalid operation in non-numeric type");
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