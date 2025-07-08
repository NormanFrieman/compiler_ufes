package checker.utils;

import java.util.HashMap;
import java.util.LinkedList;

import checker.FunctionDeclaration;
import checker.Variable;
import checker.VariableType;

public class Scope {
    private HashMap<String, Variable> variableMap = new HashMap<String, Variable>();
    private HashMap<String, FunctionDeclaration> functionsMap = new HashMap<String, FunctionDeclaration>();

    public Scope() {
        //#region Declaring append functino
        VariableType paramType = new VariableType(JvmType.STRING, false, -1);
        LinkedList<VariableType> paramsType = new LinkedList<VariableType>();
        paramsType.add(paramType);
        paramsType.add(paramType);
        FunctionDeclaration append = new FunctionDeclaration("append", 0, paramsType, paramType);
        //#endregion

        functionsMap.put(append.getName(), append);
    }

    public boolean VarIsDeclared(String varName) {
        Variable variable = this.GetVar(varName);

        // boolean isDeclared = variable != null;
        // if (!isDeclared)
        //     return false;
        // return true;
        return variable != null;
    }

    public boolean FunctionIsDeclared(String functionName) {
        FunctionDeclaration function = this.GetFunction(functionName);
        return function != null;
    }

    public void AddVar(Variable var) {
        variableMap.put(var.Name, var);
    }

    public Variable GetVar(String name) {
        return variableMap.get(name);
    }

    public void AddFunction(FunctionDeclaration function) {
        functionsMap.put(function.getName(), function);
    }

    public FunctionDeclaration GetFunction(String name) {
        return functionsMap.get(name);
    }
}
