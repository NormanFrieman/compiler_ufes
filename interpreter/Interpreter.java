package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ast.AST;
import ast.NodeKind;
import checker.utils.Variable;
import checker.utils.VariableType;

public class Interpreter {
    private Map<Variable, String> memory = new HashMap<>();

    public void EvalProgram(AST ast) {
        if (ast.kind != NodeKind.PROGRAM_NODE)
            ExitWithError("ERROR: invalid root");

        VisitChilds(ast);
    }

    public String VisitChild(AST ast) {
        switch (ast.kind) {
            case FUNCTION_DECLARATION_NODE -> {
                AST master = ast.GetMaster();
                if (master.kind != NodeKind.PROGRAM_NODE)
                    ExitWithError("ERROR: function declared in wrong place");
                
                if (!"main".equals(ast.value))
                    return null;
                
                VisitChilds(ast);
            }

            case FUNCTION_CALL_NODE -> {
                if ("Println".equals(ast.value)) {
                    String value = VisitChild(ast.GetChild(0));
                    System.out.println(value);
                }
            }

            case SCOPE_NODE -> VisitChilds(ast);

            case VAR_ASSIGN_NODE -> {
                String name = ast.GetValue();
                VariableType type = ast.GetType();

                Variable var = new Variable(name, 0, type);
                if (type.IsArray) {

                } else {
                    String result = VisitChild(ast.GetChild(0));
                    memory.put(var, result);
                }
            }

            case IF_DECLARATION_NODE -> {
                List<AST> childs = ast.GetChilds();

                boolean compareRes = Boolean.valueOf(VisitChild(childs.get(0)));
                if (compareRes)
                    VisitChild(childs.get(1));
            }

            case COMPARE_NODE -> {
                List<String> results = VisitChilds(ast);
                
                String res1 = results.get(0);
                String res2 = results.get(1);

                if (">".equals(ast.value))
                    return String.valueOf(Integer.parseInt(res1) > Integer.parseInt(res2));
                if ("<".equals(ast.value))
                    return String.valueOf(Integer.parseInt(res1) < Integer.parseInt(res2));
                               
                return String.valueOf(res1.equals(res2));
            }

            case VAR_USE_NODE -> {
                Variable var = GetVariable(ast.value);
                return memory.get(var);
            }

            case VALUE_NODE -> {
                return ast.value;
            }
            
            default -> {
                System.out.println(ast.kind);
                System.out.println("NOT IMPLEMENTED YET");
            }
        }

        return null;
    }

    List<String> VisitChilds(AST ast) {
        List<AST> childs = ast.GetChilds();
        List<String> results = new ArrayList<String>();

        for (AST child : childs) {
            String result = VisitChild(child);
            if (result == null)
                continue;
            results.add(result);
        }
        return results;
    }

    void ExitWithError(String err) {
        System.err.println(err);
        System.exit(1);
    }

    Variable GetVariable(String name) {
        Set<Variable> vars = memory.keySet();

        for (Variable var : vars) {
            if (name.equals(var.getName()))
                return var;
        }

        ExitWithError("ERROR: variable not found");
        return null;
    }
}