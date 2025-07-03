package checker.utils;

import java.util.HashMap;

import checker.Variable;

public class Scope {
    private HashMap<String, Variable> variableMap = new HashMap<String, Variable>();

    public boolean IsDeclared(String varName) {
        Variable variable = this.GetVar(varName);

        boolean isDeclared = variable != null;
        if (!isDeclared)
            return false;
        return true;
    }

    public void AddVar(Variable var) {
        variableMap.put(var.Name, var);
    }

    public Variable GetVar(String name) {
        return variableMap.get(name);
    }
}
