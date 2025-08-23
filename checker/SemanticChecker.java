package checker;

import java.util.ArrayList;
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
import generated.jvmParser.Function_stmtContext;
import generated.jvmParser.TypeContext;
import generated.jvmParser.Else_if_stmtContext;
import generated.jvmParserBaseVisitor;
import checker.utils.ArraySize;
import checker.utils.FunctionDeclaration;
import checker.utils.Increase;
import checker.utils.JvmType;
import checker.utils.Variable;
import checker.utils.VariableType;

public class SemanticChecker extends jvmParserBaseVisitor<AST> {
    // Scopes
    AST root;
    private AST lastAst;

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
        this.root = new AST(NodeKind.PROGRAM_NODE, null, null);
        visit(ctx.init());
        visit(ctx.stmt_sect());

        return this.root;
    }

    @Override
    public AST visitStmt_sect(jvmParser.Stmt_sectContext ctx) {
        List<Function_stmtContext> functions = ctx.function_stmt();

        for (Function_stmtContext func : functions) {
            AST child = visit(func);
            if (child != null)
                this.root.AddChild(child);
        }

        return null;
    }

    @Override
    public AST visitScope(jvmParser.ScopeContext ctx) {
        AST scope = new AST(NodeKind.SCOPE_NODE, null, null);
        scope.AddMaster(this.lastAst);

        this.lastAst = scope;

        List<CommandContext> commands = ctx.command();
        for (CommandContext command : commands) {
            if (command.CONTINUE() != null) {
                AST cont = new AST(NodeKind.CONTINUE_NODE, null, null);
                scope.AddChild(cont);
            }

            if (command.BREAK() != null) {
                AST br = new AST(NodeKind.BREAK_NODE, null, null);
                scope.AddChild(br);
            }

            AST child = visit(command);
            if (child != null)
                scope.AddChild(child);
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
        Variable var = this.CheckVar(id);
        this.lastType = var.getType();

        return new AST(NodeKind.VAR_USE_NODE, var.getName(), var.getType());
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
        return new AST(NodeKind.NULL_USE_NODE, null, null);
    }

    @Override
    public AST visitExprMath(jvmParser.ExprMathContext ctx) {
        AST expr0 = visit(ctx.expr(0));
        VariableType type0 = this.lastType;

        AST expr1 = visit(ctx.expr(1));
        VariableType type1 = this.lastType;

        this.TypeCompare(type0, type1);
        this.lastType = type0;

        AST mathNode = new AST(NodeKind.MATH_NODE, ctx.math_operations().getText(), null);
        mathNode.AddChild(expr0, expr1);

        return mathNode;
    }

    @Override
    public AST visitExprBool(jvmParser.ExprBoolContext ctx) {
        AST value1 = visit(ctx.expr(0));
        VariableType type0 = this.lastType;

        AST value2 = visit(ctx.expr(1));
        VariableType type1 = this.lastType;

        this.TypeCompare(type0, type1);
        this.lastType = new VariableType(JvmType.BOOL);

        AST compare = new AST(NodeKind.COMPARE_NODE, ctx.compare().getText(), null);
        compare.AddChild(value1, value2);

        return compare;
    }

    @Override
    public AST visitExprNotBool(jvmParser.ExprNotBoolContext ctx) {
        AST exprAst = visit(ctx.expr());
        VariableType var = this.lastType;

        if (var.getType() != JvmType.BOOL) {
            String nameType = JvmType.Names.get(var.getType().value);

            ExitWithError("ERROR: type " + nameType + "is not boolean");
        }

        AST notNode = new AST(NodeKind.NOT_NODE, null, null);
        notNode.AddChild(exprAst);

        return notNode;
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
        return new AST(NodeKind.VALUE_NODE, ctx.INT_DEC().getText(), lastType);
    }
	
	@Override
    public AST visitValueIntH(jvmParser.ValueIntHContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return new AST(NodeKind.VALUE_NODE, ctx.INT_HEX().getText(), lastType);
    }
	
	@Override
    public AST visitValueIntO(jvmParser.ValueIntOContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return new AST(NodeKind.VALUE_NODE, ctx.INT_OCT().getText(), lastType);
    }
	
	@Override
    public AST visitValueIntB(jvmParser.ValueIntBContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return new AST(NodeKind.VALUE_NODE, ctx.INT_BIN().getText(), lastType);
    }
	
	@Override
    public AST visitValueIntN(jvmParser.ValueIntNContext ctx) {
        this.lastType = new VariableType(JvmType.INT);
        return new AST(NodeKind.VALUE_NODE, ctx.NEGATIVE_INT_VALUE().getText(), lastType);
    }
	
	@Override
    public AST visitValueFloat(jvmParser.ValueFloatContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT32);
        return new AST(NodeKind.VALUE_NODE, ctx.FLOAT_LITERAL().getText(), lastType);
    }
	
	@Override
    public AST visitValueFloatN(jvmParser.ValueFloatNContext ctx) {
        this.lastType = new VariableType(JvmType.FLOAT32);
        return new AST(NodeKind.VALUE_NODE, ctx.NEGATIVE_FLOAT_LITERAL().getText(), lastType);
    }
	
	@Override
    public AST visitValueString(jvmParser.ValueStringContext ctx) {
        this.lastType = new VariableType(JvmType.STRING);
        return new AST(NodeKind.VALUE_NODE, ctx.STRING_VALUE().getText(), lastType);
    }
	
	@Override
    public AST visitValueChar(jvmParser.ValueCharContext ctx) {
        this.lastType = new VariableType(JvmType.STRING);
        return new AST(NodeKind.VALUE_NODE, ctx.CHAR_VALUE().getText(), lastType);
    }
	
	@Override
    public AST visitValueTrue(jvmParser.ValueTrueContext ctx) {
        this.lastType = new VariableType(JvmType.BOOL);
        return new AST(NodeKind.VALUE_NODE, ctx.TRUE().getText(), lastType);
    }
	
	@Override
    public AST visitValueFalse(jvmParser.ValueFalseContext ctx) {
        this.lastType = new VariableType(JvmType.BOOL);
        return new AST(NodeKind.VALUE_NODE, ctx.FALSE().getText(), lastType);
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

        List<AST> children = new ArrayList<AST>();
        ctx.values.forEach(value -> {
            AST child = visit(value);
            VariableType valueType = this.lastType;

            this.TypeCompare(typeArray, valueType);
            children.add(child);
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

        AST ast = new AST(NodeKind.VALUE_ARRAY_NODE, null, typeArray);
        ast.AddChild(children);
        return ast;
    }

    @Override
    public AST visitValueArrayGet(jvmParser.ValueArrayGetContext ctx) {
        Token varToken = ctx.ID().getSymbol();
        Variable var = this.CheckVar(varToken);
        VariableType varType = var.getType();
        this.lastType = varType;

        AST exprAst = visit(ctx.expr());
        String value = exprAst.GetValue();
        VariableType type = exprAst.GetType();
        
        if (!type.IsNumeric())
            ExitWithError("ERROR: index type must be int");
        
        JvmType jvmType = type.getType();
        if (jvmType == JvmType.FLOAT32 || jvmType == JvmType.FLOAT64)
            ExitWithError("ERROR: index type must be int");
        
        if (!varType.IsArray)
            ExitWithError("ERROR: variable is not an array");

        if (exprAst.kind == NodeKind.VALUE_NODE && (varType.getMaxSize() - 1) < Integer.parseInt(value))
            ExitWithError("ERROR: index out of bounds");
        
        AST varArrayUse = new AST(NodeKind.VAR_ARRAY_USE_NODE, var.getName(), varType);
        varArrayUse.AddChild(exprAst);

        return varArrayUse;
    }

    @Override
    public AST visitValueConversion(jvmParser.ValueConversionContext ctx) {
        AST exprAst = visit(ctx.expr());
        AST typeAst = visit(ctx.type());

        AST conversionAst = new AST(NodeKind.CONVERSION_NODE, null, this.lastType);
        conversionAst.AddChild(exprAst);

        return conversionAst;
        // TO DO
        // VERIFY TYPE CONVERSIONS
    }

    @Override
    public AST visitValueProp(jvmParser.ValuePropContext ctx) {
        // TO DO
        return null;
    }
    //#endregion
    //#endregion

    //#region Var assign
    @Override
    public AST visitVarWithoutValue(jvmParser.VarWithoutValueContext ctx) {
        Token varToken = ctx.ID().getSymbol();

        visit(ctx.type());
        VariableType type = this.lastType;
        Variable var = new Variable(varToken.getText(), varToken.getLine(), type, ctx.CONST() != null);

        lastAst.AddVar(var);
        return new AST(NodeKind.VAR_ASSIGN_NODE, var.getName(), var.getType());
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

        AST varAst = null;
        if (isVarInit) {
            if (ctx.type() != null)
                visit(ctx.type());

            VariableType typeAssign = ctx.type() != null ? this.lastType : null;
            AST value = visit(ctx.expr());
            VariableType typeValue = this.lastType;
            
            Variable var = new Variable(
                varInicialize.getText(),
                varInicialize.getLine(),
                TypeAssign(typeAssign, typeValue),
                ctx.CONST() != null
            );
            lastAst.AddVar(var);

            varAst = new AST(NodeKind.VAR_ASSIGN_NODE, var.getName(), var.getType());
            varAst.AddChild(value);
        } else if (isVarUpdate) {
            Variable var = this.CheckVar(varInicialize);
            AST varUpdate = new AST(NodeKind.VAR_UPDATE_NODE, var.getName(), var.getType());
            AST exprAst = visit(ctx.expr());
            varUpdate.AddChild(exprAst);

            VariableType assignType = this.lastType;

            this.TypeCompare(var.getType(), assignType);
            varAst = varUpdate;
        }

        if (varAst == null)
            ExitWithError("Internal Error: node not created");
        return varAst;
    }

    @Override
    public AST visitVarWithIncrease(jvmParser.VarWithIncreaseContext ctx) {
        Variable var = this.CheckVar(ctx.ID().getSymbol());
        this.CheckIncrease(var);

        Increase increaseType = !ctx.value_increase().PLUS().isEmpty() ? Increase.PLUS : Increase.MINUS;
        AST increaseAst = new AST(NodeKind.INCREASE_NODE, increaseType.toString(), null);
        AST varAst = new AST(NodeKind.VAR_USE_NODE, var.getName(), var.getType());
        increaseAst.AddChild(varAst);

        return increaseAst;
    }
    //#endregion

    //#region Functions
    @Override
    public AST visitFunction_declaration(jvmParser.Function_declarationContext ctx) {
        String functionName = ctx.functionName.getText();
        if (ctx.functionType != null)
            visit(ctx.functionType);
        
        VariableType functionType = ctx.functionType != null ? this.lastType : null;

        AST scope = new AST(NodeKind.FUNCTION_DECLARATION_NODE, functionName, functionType);
        this.lastAst = scope;

        List<Token> ids = ctx.ids;
        List<TypeContext> types = ctx.types;

        LinkedList<VariableType> paramsType = new LinkedList<VariableType>();
        
        for (int i = 0; i < ids.size(); i++) {
            String varName = ids.get(i).getText();
            visit(types.get(i));
            VariableType type = this.lastType;
            Variable paramVar = new Variable(varName, ids.get(i).getLine(), type);

            AST paramAst = new AST(NodeKind.VAR_ASSIGN_NODE, paramVar.getName(), type);
            scope.AddVar(paramVar);
            paramsType.add(type);

            scope.AddChild(paramAst);
        }

        FunctionDeclaration function = new FunctionDeclaration(
            functionName,
            ctx.functionName.getLine(),
            paramsType,
            functionType,
            null);

        this.AddFunction(function);
        this.lastType = functionType;

        return scope;
    }

    @Override
    public AST visitFunctionWithParam(jvmParser.FunctionWithParamContext ctx) {
        FunctionDeclaration function = this.CheckFunction(ctx.parent);

        AST functionAst = new AST(NodeKind.FUNCTION_CALL_NODE, function.getName(), function.getReturnType());

        LinkedList<VariableType> params = new LinkedList<VariableType>();

        ctx.expr().forEach(e -> {
            AST param = visit(e);
            VariableType type = this.lastType;

            functionAst.AddChild(param);
            params.add(type);
        });
        function.CheckParams(params);

        this.lastType = function.getReturnType();
        return functionAst;
    }

    @Override
    public AST visitFunctionRecursive(jvmParser.FunctionRecursiveContext ctx) {
        this.CheckImports(ctx.parent);
        return visit(ctx.function_call());
    }

    @Override
    public AST visitFunction_stmt(jvmParser.Function_stmtContext ctx) {
        AST funcDecl = visit(ctx.function_declaration());
        VariableType functionType = this.lastType;
        
        AST scope = visit(ctx.scope());
        VariableType scopeType = this.lastType;

        Boolean functionTypeIsNull = functionType == null;
        Boolean scopeTypeIsNull = scopeType == null;

        funcDecl.AddChild(scope);
        if (functionTypeIsNull && scopeTypeIsNull)
            return funcDecl;

        if (functionTypeIsNull)
            ExitWithError("Too many return values");

        if (scopeTypeIsNull)
            ExitWithError("Missing return");

        TypeCompare(functionType, scopeType);
        return funcDecl;
    }
    
    @Override
    public AST visitReturn_stmt(jvmParser.Return_stmtContext ctx) {
        AST returnStmt = new AST(NodeKind.RETURN_NODE, null, null);
        if (ctx.expr() == null)
            return null;
        
        AST exprAst = visit(ctx.expr());
        returnStmt.AddChild(exprAst);
        return returnStmt;
    }
    //#endregion

    //#region Loop
    @Override
    public AST visitFor_init(jvmParser.For_initContext ctx) {
        Token varToken = ctx.ID().getSymbol();
        AST valueAst = visit(ctx.value());

        VariableType type = this.lastType;
        Variable var = new Variable(varToken.getText(), varToken.getLine(), type);
        
        lastAst.AddVar(var);
        
        AST varAssignAst = new AST(NodeKind.VAR_ASSIGN_NODE, var.getName(), var.getType());
        varAssignAst.AddChild(valueAst);
        lastAst.AddChild(varAssignAst);

        return null;
    }

    @Override
    public AST visitFor_cond(jvmParser.For_condContext ctx) {
        Token token = ctx.ID(0).getSymbol();
        Variable var = this.CheckVar(token);
        VariableType type = null;

        AST varAst = new AST(NodeKind.VAR_USE_NODE, var.getName(), var.getType());
        AST valueAst = null;

        if (ctx.value() != null) {
            valueAst = visit(ctx.value());
            type = this.lastType;
        }
        if (ctx.ID(1) != null) {
            valueAst = visit(ctx.ID(1));
            type = this.lastType;
        }
        if (ctx.function_call() != null) {
            valueAst = visit(ctx.function_call());
            type = this.lastType;
        }

        this.TypeCompare(var.getType(), type);

        AST compare = new AST(NodeKind.COMPARE_NODE, ctx.compare().getText(), null);
        compare.AddChild(varAst, valueAst);

        this.lastAst.AddChild(compare);
        return null;
    }

    @Override
    public AST visitFor_end(jvmParser.For_endContext ctx) {
        Variable var = this.CheckVar(ctx.ID().getSymbol());
        this.CheckIncrease(var);

        Increase increaseType = ctx.value_increase().PLUS() != null ? Increase.PLUS : Increase.MINUS;
        AST increaseAst = new AST(NodeKind.INCREASE_NODE, increaseType.toString(), null);
        AST varAst = new AST(NodeKind.VAR_USE_NODE, var.getName(), var.getType());
        increaseAst.AddChild(varAst);
        
        this.lastAst.AddChild(increaseAst);

        return null;
    }

    @Override
    public AST visitFor_range(jvmParser.For_rangeContext ctx) {
        List<Token> vars = ctx.ID()
            .stream()
            .map(x -> x.getSymbol())
            .collect(Collectors.toList());
        
        Token indexVarToken = vars.get(0);
        Token iterVarToken = vars.get(1);        
        Token lastVarToken = vars.get(2);
        
        Variable lastVar = this.CheckVar(lastVarToken);
        JvmType type = lastVar.getType().getType();

        Variable indexVar = new Variable(indexVarToken.getText(), indexVarToken.getLine(), new VariableType(JvmType.INT));
        lastAst.AddVar(indexVar);
        Variable iterVar = new Variable(iterVarToken.getText(), iterVarToken.getLine(), new VariableType(type));
        lastAst.AddVar(iterVar);

        AST indexAst = new AST(NodeKind.VAR_ASSIGN_NODE, indexVar.getName(), indexVar.getType());
        AST iterAst = new AST(NodeKind.VAR_ASSIGN_NODE, iterVar.getName(), iterVar.getType());
        AST lastVarAst = new AST(NodeKind.VAR_USE_NODE, lastVar.getName(), lastVar.getType());

        lastAst.AddChild(indexAst, iterAst, lastVarAst);
        return null;
    }

    @Override
    public AST visitFor_stmt(jvmParser.For_stmtContext ctx) {
        // Salva a AST anterior
        AST last = this.lastAst;

        // Cria uma AST para a declaração do for
        AST forDecl = new AST(NodeKind.FOR_DECLARATION_NODE, null, null);

        // Mantem temporariamente a AST do for declaration no lastAst
        this.lastAst = forDecl;

        // Adiciona a AST salva anteriormente como master da AST do for declaration
        // Necessário para realizar a consulta da tabela de variaveis das ASTs superiores
        forDecl.AddMaster(last);
        visit(ctx.for_declaration());
        
        AST scopeFor = visit(ctx.scope());
        forDecl.AddChild(scopeFor);

        // Retorna com a AST anterior
        this.lastAst = last;
        return forDecl;
    }
    //#endregion

    //#region Conditional
    @Override
    public AST visitIfSimple(jvmParser.IfSimpleContext ctx) {
        AST exprAst = visit(ctx.expr());
        if (exprAst == null)
            return null;
        
        if (this.lastType.getType() != JvmType.BOOL)
            ExitWithError("ERROR: condition is not bool");
        
        this.lastAst.AddChild(exprAst);
        return null;
    }

    @Override
    public AST visitIf_stmt(jvmParser.If_stmtContext ctx) {
        AST last = this.lastAst;

        AST ifDecl = new AST(NodeKind.IF_DECLARATION_NODE, null, null);
        this.lastAst = ifDecl;
        ifDecl.AddMaster(last);

        visit(ctx.if_init());
        AST ifScope = visit(ctx.scope());
        ifDecl.AddChild(ifScope);

        List<Else_if_stmtContext> elseIf = ctx.else_if_stmt();
        if (elseIf != null) {
            for (Else_if_stmtContext elseIfSingle : elseIf) {
                AST elseIfStmt = visit(elseIfSingle);
                ifDecl.AddChild(elseIfStmt);
            }
        }

        if (ctx.else_stmt() != null) {
            AST elseStmt = visit(ctx.else_stmt());
            ifDecl.AddChild(elseStmt);
        }

        this.lastAst = last;
        return ifDecl;
    }

    @Override
    public AST visitElse_if_stmt(jvmParser.Else_if_stmtContext ctx) {
        AST last = this.lastAst;
                
        AST elseIfDecl = new AST(NodeKind.ELSE_IF_DECLARATION_NODE, null, null);
        this.lastAst = elseIfDecl;
        elseIfDecl.AddMaster(last);

        visit(ctx.if_init());
        AST elseIfScope = visit(ctx.scope());
        elseIfDecl.AddChild(elseIfScope);

        this.lastAst = last;
        return elseIfDecl;
    }

    @Override
    public AST visitElse_stmt(jvmParser.Else_stmtContext ctx) {
        AST last = this.lastAst;
                
        AST elseDecl = new AST(NodeKind.ELSE_DECLARATION_NODE, null, null);
        this.lastAst = elseDecl;
        elseDecl.AddMaster(last);

        AST elseScope = visit(ctx.scope());
        elseDecl.AddChild(elseScope);

        this.lastAst = last;
        return elseDecl;
    }
    //#endregion

    //#region Auxiliary methods
    //#region Variable
    Variable CheckVar(Token token) {
        String varName = token.getText();

        Variable var = CheckVar(this.lastAst, varName);

        if (var == null)
            ExitWithError("ERROR: undefined " + varName + " in line " + token.getLine());

        return var;
    }

    Variable CheckVar(AST scope, String varName) {
        Variable var = scope.GetVar(varName);
        
        if (var != null)
            return var;
        
        AST master = scope.GetMaster();
        if (master == null)
            return null;
        
        return CheckVar(master, varName);
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