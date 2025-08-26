package ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import checker.utils.Variable;
import checker.utils.VariableType;

public class AST {
    public NodeKind kind;
    
    public String value;
    public VariableType type;

    private AST master;
    private List<AST> children = new ArrayList<AST>();

    //#region Variable hashmap
    private LinkedHashMap<String, Variable> variableMap = new LinkedHashMap<String, Variable>();
    public boolean VarIsDeclared(String varName) {
        return this.GetVar(varName) != null;
    }

    public void AddVar(Variable var) {
        variableMap.put(var.Name, var);
    }

    public Variable GetVar(String name) {
        return variableMap.get(name);
    }

    public int GetIndex(String name) {
        List<String> keys = new ArrayList<String>(variableMap.keySet());
        return keys.indexOf(name);
    }
    //#endregion

    public AST(NodeKind kind, String value, VariableType type) {
        this.kind = kind;
        this.value = value;
        this.type = type;
        this.children = new ArrayList<AST>();
    }

    public String GetValue() {
        return value;
    }

    public VariableType GetType() {
        return type;
    }

    public void AddChild(AST child) {
        if (child == null)
            return;
            
        child.AddMaster(this);
        children.add(child);
    }

    public void AddChild(AST... childs) {
        if (childs == null)
            return;
        
        for (AST child : childs) {
            AddChild(child);
        }
    }

    public void AddChild(List<AST> childs) {
        if (childs == null)
            return;
        
        for (AST child : childs) {
            AddChild(child);
        }
    }

    public List<AST> GetChilds() {
        return children;
    }

    public AST GetChild(int index) {
        return children.get(index);
    }

    public AST GetChildByNodeKind(NodeKind kind) {
        for (AST child : children) {
            if (child.kind == kind)
                return child;
        }

        return null;
    }

    public List<AST> GetChildsByNodeKind(NodeKind kind) {
        List<AST> childs = new LinkedList<AST>();
        for (AST child : children) {
            if (child.kind == kind)
                childs.add(child);
        }

        return childs;
    }

    public void AddMaster(AST ast) {
        this.master = ast;
    }

    public AST GetMaster() {
        return this.master;
    }

    public void PrintVars() {
        variableMap.forEach((key, value) -> System.out.printf("Key: %s\n", key));
    }

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

        if (kind == NodeKind.VAR_UPDATE_NODE) {
            PrintDotValue("VAR_UPDATE_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.VALUE_NODE) {
            PrintDotValue("VALUE_NODE", i);
        }

        if (kind == NodeKind.VALUE_ARRAY_NODE) {
            PrintDotValue("VALUE_ARRAY_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.FUNCTION_CALL_NODE) {
            PrintDotValue("FUNCTION_CALL_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.VAR_USE_NODE) {
            PrintDotValue("VAR_USE_NODE", i);
        }

        if (kind == NodeKind.VAR_ARRAY_USE_NODE) {
            PrintDotValue("VAR_ARRAY_USE_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.FOR_DECLARATION_NODE) {
            System.out.printf("%s[FOR_DECLARATION_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.IF_DECLARATION_NODE) {
            System.out.printf("%s[IF_DECLARATION_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.ELSE_IF_DECLARATION_NODE) {
            System.out.printf("%s[ELSE_IF_DECLARATION_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.ELSE_DECLARATION_NODE) {
            System.out.printf("%s[ELSE_DECLARATION_NODE]\n", Tabs(i));
            PrintChildren(i+1);
        }

        if (kind == NodeKind.COMPARE_NODE) {
            PrintDotValue("COMPARE_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.INCREASE_NODE) {
            PrintDotValue("INCREASE_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.MATH_NODE) {
            PrintDotValue("MATH_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.NOT_NODE) {
            PrintDotValue("NOT_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.RETURN_NODE) {
            PrintDotValue("RETURN_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.CONVERSION_NODE) {
            PrintDotValue("CONVERSION_NODE", i);
            PrintChildren(i+1);
        }

        if (kind == NodeKind.CONTINUE_NODE) {
            System.out.printf("%s[CONTINUE_NODE]\n", Tabs(i));
        }

        if (kind == NodeKind.BREAK_NODE) {
            System.out.printf("%s[BREAK_NODE]\n", Tabs(i));
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
