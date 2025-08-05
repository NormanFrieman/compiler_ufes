package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import checker.utils.Variable;
import checker.utils.VariableType;

public class AST {
    public NodeKind kind;
    
    public String value;
    public VariableType type;

    private List<AST> children = new ArrayList<AST>();

    public AST(NodeKind kind, String value, VariableType type) {
        this.kind = kind;
        this.value = value;
        this.type = type;
        this.children = new ArrayList<AST>();
    }

    public String GetValue() {
        return value;
    }

    public void AddChild(AST child) {
        children.add(child);
    }

    public AST GetChild(int index) {
        return children.get(index);
    }

    public static AST NewSubtree(NodeKind kind, String value, VariableType type, AST... children) {
        AST node = new AST(kind, value, type);
        for (AST child : children) {
            node.AddChild(child);
        }

        return node;
    }

    //#region Variable hashmap
    private HashMap<String, Variable> variableMap = new HashMap<String, Variable>();
    public boolean VarIsDeclared(String varName) {
        return this.GetVar(varName) != null;
    }

    public void AddVar(Variable var) {
        variableMap.put(var.Name, var);
    }

    public Variable GetVar(String name) {
        return variableMap.get(name);
    }
    //#endregion

    public void Print() {
        if (kind == NodeKind.PROGRAM_NODE) {
            System.out.println("Program");
            PrintChildren();
        }

        if (kind == NodeKind.FUNCTION_DECLARATION_NODE) {
            System.out.printf("(%s) %s ", type != null ? type.print() : "void", value);
            PrintChildren();
        }

        if (kind == NodeKind.SCOPE_NODE) {
            System.out.println("Scope");
            PrintChildren();
        }
    }

    public void PrintChildren() {
        for (AST child : children) {
            System.out.println(child.kind);
            if (child != null)
                child.Print();
        }
    }
}
