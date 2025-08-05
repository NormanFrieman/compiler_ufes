package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import checker.utils.JvmType;
import checker.utils.Variable;

public class AST {
    public NodeKind kind;
    
    public String value;
    public JvmType type;

    private List<AST> children;

    public AST(NodeKind kind, String value, JvmType type) {
        this.kind = kind;
        this.value = value;
        this.type = type;
        this.children = new ArrayList<AST>();
    }

    public void AddChild(AST child) {
        children.add(child);
    }

    public AST GetChild(int index) {
        return children.get(index);
    }

    public static AST NewSubtree(NodeKind kind, String value, JvmType type, AST... children) {
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
}
