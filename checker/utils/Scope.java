package checker.utils;

import java.util.HashMap;

public class Scope {
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
}
