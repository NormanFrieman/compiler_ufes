package codegen;

import java.util.HashMap;
import java.util.List;

import ast.AST;
import ast.NodeKind;
import checker.utils.JvmType;
import checker.utils.Variable;

public class CodegenVisitor {
    private StringBuilder jasminCode = new StringBuilder();

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
                Variable var = scope.GetVar(ast.value);
                int index = scope.GetIndex(var.getName());

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
                AST elseNode = ast.GetChildByNodeKind(NodeKind.ELSE_DECLARATION_NODE);
                AST scopeNode = ast.GetChildByNodeKind(NodeKind.SCOPE_NODE);

                String label = elseNode != null
                    ? "ELSE"
                    : "END";

                VisitChilds(compareNode);

                String compareSymbol = compareNode.value;
                if (">".equals(compareSymbol)) {
                    jasminCode.append("    if_icmplt " + label + "\n");
                } else if ("<".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpgt " + label + "\n");
                } else if ("==".equals(compareSymbol)) {
                    jasminCode.append("    if_icmpeq " + label + "\n");
                }

                VisitChild(scopeNode);

                jasminCode.append("    END:\n");
            }
            
            default -> {
                ExitWithError("NOT IMPLEMENTED YET");
                break;
            }
        }
    }

    AST GetScope(AST ast) {
        AST root = ast.GetMaster();
        if (root.kind == NodeKind.SCOPE_NODE)
            return root;
        
        return GetScope(root);
    }

    private void VisitChilds(AST ast) {
        List<AST> childs = ast.GetChilds();

        for (AST child : childs)
            VisitChild(child);
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
