package codegen;

import java.util.List;

import ast.AST;
import ast.NodeKind;
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

        VisitChilds(rooAst, 1);
    }

    private void VisitChild(AST ast, int tab) {
        switch (ast.kind) {
            case FUNCTION_DECLARATION_NODE -> {
                AST master = ast.GetMaster();
                if (master.kind != NodeKind.PROGRAM_NODE)
                    ExitWithError("ERROR: function declared in wrong place");
                
                if (!"main".equals(ast.value))
                    break;
                
                VisitChilds(ast, tab++);
                break;
            }

            case FUNCTION_CALL_NODE -> {
                if ("Println".equals(ast.value)) {
                    jasminCode.append(Tabs(tab) + "getstatic java/lang/System/out Ljava/io/PrintStream;\n");
                    VisitChild(ast.GetChild(0), tab++);
                    // jasminCode.append(Tabs(tab) + "iload " + 0 + "\n");
                    jasminCode.append(Tabs(tab) + "invokevirtual java/io/PrintStream/println(I)V\n");
                }
            }

            case SCOPE_NODE -> VisitChilds(ast, tab++);

            case VAR_ASSIGN_NODE -> {
                VisitChild(ast.GetChild(0), tab++);

                AST scope = GetScope(ast);
                Variable var = scope.GetVar(ast.value);
                int index = scope.GetIndex(var.getName());

                jasminCode.append(Tabs(tab) + "istore " + index + "\n");
                break;
            }

            case VAR_USE_NODE -> {
                AST scope = GetScope(ast);
                Variable var = scope.GetVar(ast.value);
                int index = scope.GetIndex(var.getName());

                jasminCode.append(Tabs(tab) + "iload " + index + "\n");
            }

            case VALUE_NODE -> {
                if (ast.type.IsNumeric()) {
                    jasminCode.append(Tabs(tab) + "ldc " + ast.value + "\n");
                }

                break;
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

    private void VisitChilds(AST ast, int tab) {
        List<AST> childs = ast.GetChilds();

        for (AST child : childs)
            VisitChild(child, tab);
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
