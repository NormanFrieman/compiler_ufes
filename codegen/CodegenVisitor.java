package codegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.AST;
import ast.NodeKind;
import checker.utils.JvmType;
import checker.utils.Tuple;
import checker.utils.Variable;
import checker.utils.VariableType;

public class CodegenVisitor {
    private StringBuilder jasminCode = new StringBuilder();

    //#region
    private int countIfEnd = 0;
    private int countElse = 0;
    private int countFor = 0;

    //#endregion

    public String Generate(AST rootAst) {
        jasminCode.setLength(0);
        emitHeader();
        visitProgram(rootAst);
        return jasminCode.toString();
    }

    private void emitHeader() {
        jasminCode.append(".class public Program\n");
        jasminCode.append(".super java/lang/Object\n");
    }

    private void visitProgram(AST rooAst) {
        if (rooAst.kind != NodeKind.PROGRAM_NODE)
            ExitWithError("ERROR: invalid root");

        VisitChilds(rooAst);
    }

    private void VisitChild(AST ast) {
        if (ast == null)
            return;
        switch (ast.kind) {
            case FUNCTION_DECLARATION_NODE -> {
                AST master = ast.GetMaster();
                if (master.kind != NodeKind.PROGRAM_NODE)
                    ExitWithError("ERROR: function declared in wrong place");
                
                String type = "main".equals(ast.value) ? "[Ljava/lang/String;" : JasminType(ast.type.getType(), true);
                String returnType = "main".equals(ast.value) ? "V" : JasminType(ast.type.getType(), true);
                jasminCode.append(".method public static " + ast.value + "(" + type + ")" + returnType + "\n");
                // tamanho da pilha e número de variáveis de que precisamos
                jasminCode.append("    .limit stack 10\n");
                // no número de variáveis somamos um por conta do parâmetro de main, que em Java é variável local
                // jasminCode.append("    .limit locals "+(variables.length+1)+"\n");
                jasminCode.append("    .limit locals "+(10)+"\n");
            
                VisitChilds(ast);

                if ("main".equals(ast.value))
                    jasminCode.append("    return\n");
                jasminCode.append(".end method\n");

                break;
            }

            case FUNCTION_CALL_NODE -> {
                if ("Println".equals(ast.value)) {
                    jasminCode.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");

                    List<AST> childs = ast.GetChilds();
                    for (AST child : childs) {
                        VisitChild(child);
                        JvmType type = child.type.getType();    
                        jasminCode.append(
                            "    invokevirtual java/io/PrintStream/println("
                            + JasminType(type, true)
                            + ")V\n");
                    }
                    break;
                } else if ("Printf".equals(ast.value)) {
                    jasminCode.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");

                    List<AST> childs = ast.GetChilds();
                    if (childs.size() > 1) {
                        int i = 0;

                        for (AST child : childs) {
                            if (i == 0) {
                                if (child.value.contains("%")) {
                                    String value = child.value
                                        .replaceAll("%d", "")
                                        .replaceAll("%s", "")
                                        .replaceAll("%T", "")
                                        .replaceAll("\\n", "");
                                    jasminCode.append("    new java/lang/StringBuilder\n");
                                    jasminCode.append("    dup\n");
                                    jasminCode.append("    ldc " + value + "\n");
                                    jasminCode.append("    invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V\n");
                                } else {
                                    VisitChild(child);
                                    jasminCode.append("    invokevirtual java/lang/StringBuilder/append(" + JasminType(child.type.getType(), true) + ")Ljava/lang/StringBuilder;\n");
                                    jasminCode.append("    invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;\n");
                                }
                            }
                        }

                        jasminCode.append("    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
                    }
                    break;
                }

                VisitChilds(ast);
                jasminCode.append("    invokestatic Program/"
                    + ast.value + "(I)"
                    + JasminType(ast.type.getType(), false)
                    + "\n");
                break;
            }

            case SCOPE_NODE -> {
                VisitChilds(ast);
                break;
            }

            case VAR_ASSIGN_NODE -> {
                AST child = ast.GetChild(0);
                if (ast.GetMaster().kind == NodeKind.FUNCTION_DECLARATION_NODE && child == null)
                    break;
                
                VisitChild(child);

                AST scope = GetScope(ast);
                Variable var = scope.GetVar(ast.value);
                int index = scope.GetIndex(var.getName());

                if (!child.type.IsArray)
                    jasminCode.append("    " + JasminType(ast.type.getType(), false) + "store " + index + "\n");
                break;
            }

            case VALUE_ARRAY_NODE -> {
                String varName = ast.GetMaster().value;

                Tuple<Variable, AST> tuple = GetVarFromMultipleScopes(ast, varName);
                Variable var = tuple.x;
                AST master = tuple.y;
                int index = master.GetIndex(var.getName());

                int size = ast.type.getMaxSize();
                JvmType type = ast.type.getType();

                String typeStr = null;  
                if (type == JvmType.INT)
                    typeStr = "int";
                if (type == JvmType.STRING)
                    typeStr = "String";
                
                jasminCode.append("    bipush " + size + "\n");
                jasminCode.append("    newarray " + typeStr + "\n");
                jasminCode.append("    astore " + index + "\n");
                
                List<AST> childs = ast.GetChilds();
                int i = 0;
                for (AST child : childs) {
                    jasminCode.append("    aload " + index + "\n");
                    jasminCode.append("    iconst_" + i + "\n");
                    jasminCode.append("    bipush " + child.value + "\n");
                    jasminCode.append("    iastore\n");
                    i++;
                }

                break;
            }

            case VAR_USE_NODE -> {
                AST scope = GetScope(ast);

                Tuple<Variable, AST> tuple = GetVarFromMultipleScopes(scope, ast.value);
                Variable var = tuple.x;
                AST master = tuple.y;
                
                int index = master.GetIndex(var.getName());

                jasminCode.append("    " + JasminType(ast.type.getType(), false) + "load " + index + "\n");
            }

            case VAR_ARRAY_USE_NODE -> {
                Tuple<Variable, AST> tuple = GetVarFromMultipleScopes(ast, ast.value);
                Variable var = tuple.x;
                AST master = tuple.y;

                String idxVar = ast.GetChild(0).value;
                
                int index = master.GetIndex(var.getName());

                jasminCode.append("    aload " + index + "\n");
                jasminCode.append("    iconst_" + idxVar + "\n");
                jasminCode.append("    iaload\n");
                break;
            }

            case VALUE_NODE -> {
                if (ast.type.IsNumeric()) {
                    if (
                        ast.type.getType() == JvmType.FLOAT32
                        && ast.value.contains("E+")
                    ) {
                        // String[] values = ast.value.split("E+");
                        // Double value = Double.parseDouble(values[0]);
                        // value = value + Math.pow(Integer.parseInt(values[1]), 10);

                        jasminCode.append("    ldc2_w " + ast.value + "\n");
                        // jasminCode.append("    f2d\n");
                    } else {
                        jasminCode.append("    bipush " + ast.value + "\n");
                    }
                } else {
                    jasminCode.append("    ldc " + ast.value + "\n");
                }

                break;
            }

            case IF_DECLARATION_NODE -> {
                AST compareNode = ast.GetChildByNodeKind(NodeKind.COMPARE_NODE);
                List<AST> elseIfNodes = ast.GetChildsByNodeKind(NodeKind.ELSE_IF_DECLARATION_NODE);
                AST elseNode = ast.GetChildByNodeKind(NodeKind.ELSE_DECLARATION_NODE);
                AST scopeNode = ast.GetChildByNodeKind(NodeKind.SCOPE_NODE);

                String elseLabel = "ELSE_" + countElse;
                String endLabel = "END_" + countIfEnd;
                String label = elseNode != null
                    ? elseLabel
                    : endLabel;

                VisitChilds(compareNode);

                String compareSymbol = compareNode.value;
                if (">".equals(compareSymbol) || ">=".equals(compareSymbol)) {
                    jasminCode.append("    if_icmplt " + label + "\n");
                } else if ("<".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpgt " + label + "\n");
                } else if ("==".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpne " + label + "\n");
                }

                VisitChild(scopeNode);
                jasminCode.append("    goto " + endLabel + "\n");

                for (AST elseIfNode : elseIfNodes) {
                    countElse++;
                    String elseIfLabel = "ELSE_" + countElse;
                    jasminCode.append("    " + elseLabel + ":\n");
                    
                    elseLabel = "ELSE_" + countElse;

                    AST elseIfCompareNode = elseIfNode.GetChildByNodeKind(NodeKind.COMPARE_NODE);
                    AST elseIfScope = elseIfNode.GetChildByNodeKind(NodeKind.SCOPE_NODE);

                    VisitChilds(elseIfCompareNode);

                    String elseIfCompareSymbol = elseIfCompareNode.value;
                    if (">".equals(elseIfCompareSymbol) || ">=".equals(elseIfCompareSymbol)) {
                        jasminCode.append("    if_icmplt " + elseIfLabel + "\n");
                    } else if ("<".equals(elseIfCompareSymbol)) {
                        jasminCode.append("    if_icmpgt " + elseIfLabel + "\n");
                    } else if ("==".equals(elseIfCompareSymbol)) {
                        jasminCode.append("    if_icmpne " + elseIfLabel + "\n");
                    }

                    VisitChild(elseIfScope);
                    jasminCode.append("    goto " + endLabel + "\n");

                    elseLabel = "ELSE_" + countElse;
                } 

                if (elseNode != null) {
                    jasminCode.append("    " + elseLabel + ":\n");
                    countElse++;
                    VisitChild(elseNode);
                }

                jasminCode.append("    " + endLabel + ":\n");
                countIfEnd++;
                break;
            }

            case ELSE_DECLARATION_NODE -> {
                VisitChilds(ast);
                break;
            }

            case MATH_NODE -> {
                VisitChilds(ast);
                if ("%".equals(ast.value)) {
                    jasminCode.append("    irem\n");
                } else if ("+".equals(ast.value)) {
                    jasminCode.append("    iadd\n");
                } else if ("*".equals(ast.value)) {
                    jasminCode.append("    imul\n");
                }
                break;
            }

            case FOR_DECLARATION_NODE -> {
                String forStart = "FOR_START_" + countFor;
                String forEnd = "FOR_END_" + countFor;
                countFor++;

                AST varAssignNode = ast.GetChildByNodeKind(NodeKind.VAR_ASSIGN_NODE);
                AST compareNode = ast.GetChildByNodeKind(NodeKind.COMPARE_NODE);
                AST increaseNode = ast.GetChildByNodeKind(NodeKind.INCREASE_NODE);
                AST scopeNode = ast.GetChildByNodeKind(NodeKind.SCOPE_NODE);

                VisitChild(varAssignNode);

                jasminCode.append("    " + forStart + ":\n");
                VisitChilds(compareNode);

                String compareSymbol = compareNode.value;
                if (">".equals(compareSymbol) || ">=".equals(compareSymbol)) {
                    jasminCode.append("    if_icmplt " + forEnd + "\n");
                } else if ("<".equals(compareSymbol) || "<=".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpgt " + forEnd + "\n");
                } else if ("==".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpne " + forEnd + "\n");
                }

                VisitChild(scopeNode);
                VisitChild(increaseNode);

                jasminCode.append("    goto " + forStart + "\n");
                jasminCode.append("    " + forEnd + ":\n");
                break;
            }

            case INCREASE_NODE -> {
                if ("PLUS".equals(ast.value)) {
                    jasminCode.append("    iinc 0 1\n");
                } else if ("MINUS".equals(ast.value)) {
                    jasminCode.append("    iinc 0 -1\n");
                }
                break;
            }

            case VAR_UPDATE_NODE -> {
                VisitChilds(ast);

                Tuple<Variable, AST> tuple = GetVarFromMultipleScopes(ast, ast.value);
                Variable var = tuple.x;
                AST master = tuple.y;
                
                int index = master.GetIndex(var.getName());

                jasminCode.append("    istore " + index + "\n");
                break;
            }
            
            case RETURN_NODE -> {
                VisitChilds(ast);
                jasminCode.append("    ireturn\n");
                break;
            }
            
            default -> {
                // ExitWithError("NOT IMPLEMENTED YET");
                break;
            }
        }
    }

    AST GetScope(AST ast) {
        AST root = ast.GetMaster();
        if (root.kind == NodeKind.SCOPE_NODE)
            return root;
        
        if (root.kind == NodeKind.FOR_DECLARATION_NODE)
            return root;
        
        if (root.kind == NodeKind.FUNCTION_DECLARATION_NODE)
            return root;
        
        if (root.kind == NodeKind.PROGRAM_NODE)
            return null;
        
        return GetScope(root);
    }

    Tuple<Variable, AST> GetVarFromMultipleScopes(AST ast, String varName) {
        if (
            ast.kind == NodeKind.SCOPE_NODE
            || ast.kind == NodeKind.FOR_DECLARATION_NODE
            || ast.kind == NodeKind.FUNCTION_DECLARATION_NODE
        ) {
            Variable var = ast.GetVar(varName);
            if (var != null)
                return new Tuple<Variable, AST>(var, ast);
        }

        AST scope = GetScope(ast);
        if (scope == null)
            return null;
        
        return GetVarFromMultipleScopes(scope, varName);
    }

    private void VisitChilds(AST ast) {
        List<AST> childs = ast.GetChilds();

        for (AST child : childs) {
            VisitChild(child);
        }
    }

    private String JasminType(JvmType a, boolean upperCase) {
        HashMap<JvmType, String> typeJasmin = new HashMap<>();

        typeJasmin.put(JvmType.INT, upperCase ? "i".toUpperCase() : "i");
        typeJasmin.put(JvmType.STRING, "Ljava/lang/String;");
        typeJasmin.put(JvmType.FLOAT32, upperCase ? "d".toUpperCase() : "d");
        
        return typeJasmin.get(a);
    }

    void ExitWithError(String err) {
        System.err.println(err);
        System.exit(1);
    }

    public String Tabs(int i) {
        String tab = "";
        for (int j = 0; j < i; j++) {
            tab = tab.concat("\t");
        }
        return tab;
    }
}
