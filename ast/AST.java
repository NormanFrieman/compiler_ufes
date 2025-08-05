package ast;

import java.nio.file.WatchEvent.Kind;
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

    public void Print(int i) {
        if (kind == NodeKind.PROGRAM_NODE) {
            System.out.printf("%s[PROGRAM_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.FUNCTION_DECLARATION_NODE) {
            PrintDotValue("FUNCTION_DECLARATION_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.SCOPE_NODE) {
            System.out.printf("%s[SCOPE_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.VAR_ASSIGN_NODE) {
            PrintDotValue("VAR_ASSIGN_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.VALUE_NODE) {
            PrintDotValue("VALUE_NODE", i);
        }

        if (kind == NodeKind.FUNCTION_CALL_NODE) {
            PrintDotValue("FUNCTION_CALL_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.VAR_USE_NODE) {
            PrintDotValue("VAR_USE_NODE", i);
        }
    }

    public void PrintDotValue(String node, int i) {
        System.out.printf("%s[%s] (%s) %s\n", Tabs(i), node, type != null ? type.print() : "void", value);
    }

    public void PrintChildren(int i) {
        for (AST child : children) {
            if (child != null)
                child.Print(i);
        }
    }

    public String Tabs(int i) {
        String tab = "";
        for (int j = 0; j < i; j++) {
            tab = tab.concat("\t");
        }
        return tab;
    }
}
