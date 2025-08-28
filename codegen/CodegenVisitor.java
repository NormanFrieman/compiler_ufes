package codegen;

import java.util.HashMap;
import java.util.List;
import ast.AST;
import ast.NodeKind;
import checker.utils.JvmType;
import checker.utils.Tuple;
import checker.utils.Variable;

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
        emitFooter();
        return jasminCode.toString();
    }

    private void emitHeader() {
        jasminCode.append(".class public Program\n");
        jasminCode.append(".super java/lang/Object\n");
        jasminCode.append(".method public static main([Ljava/lang/String;)V\n");
        // tamanho da pilha e número de variáveis de que precisamos
        jasminCode.append("    .limit stack 10\n");
        // no número de variáveis somamos um por conta do parâmetro de main, que em Java é variável local
        // jasminCode.append("    .limit locals "+(variables.length+1)+"\n");
        jasminCode.append("    .limit locals "+(10)+"\n");
    }

    private void emitFooter() {
        // terminamos o método main
        jasminCode.append("    return\n");
        jasminCode.append(".end method\n");
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
                
                if (!"main".equals(ast.value))
                    break;
                
                VisitChilds(ast);
                break;
            }

            case FUNCTION_CALL_NODE -> {
                if ("Println".equals(ast.value)) {
                    jasminCode.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");
                    AST valueNode = ast.GetChild(0);

                    VisitChild(valueNode);
                    JvmType type = valueNode.type.getType();

                    HashMap<JvmType, String> typeJasmin = new HashMap<>();
                    typeJasmin.put(JvmType.INT, "I");
                    typeJasmin.put(JvmType.STRING, "Ljava/lang/String;");

                    jasminCode.append(
                        "    invokevirtual java/io/PrintStream/println("
                        + typeJasmin.get(type)
                        + ")V\n"
                    );
                }
            }

            case SCOPE_NODE -> VisitChilds(ast);

            case VAR_ASSIGN_NODE -> {
                VisitChild(ast.GetChild(0));

                AST scope = GetScope(ast);
                Variable var = scope.GetVar(ast.value);
                int index = scope.GetIndex(var.getName());

                jasminCode.append("    istore " + index + "\n");
                break;
            }

            case VAR_USE_NODE -> {
                AST scope = GetScope(ast);

                Tuple<Variable, AST> tuple = GetVarFromMultipleScopes(scope, ast.value);
                Variable var = tuple.x;
                AST master = tuple.y;
                
                int index = master.GetIndex(var.getName());

                jasminCode.append("    iload " + index + "\n");
            }

            case VALUE_NODE -> {
                if (ast.type.IsNumeric()) {
                    jasminCode.append("    bipush " + ast.value + "\n");
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
                jasminCode.append("    return\n");
            }

            case INCREASE_NODE -> {
                if ("PLUS".equals(ast.value)) {
                    jasminCode.append("    iinc 0 1\n");
                }
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
        
        if (root.kind == NodeKind.PROGRAM_NODE)
            return null;
        
        return GetScope(root);
    }

    Tuple<Variable, AST> GetVarFromMultipleScopes(AST ast, String varName) {
        if (ast.kind == NodeKind.SCOPE_NODE || ast.kind == NodeKind.FOR_DECLARATION_NODE) {
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
