package checker.utils;

import java.util.HashMap;

import checker.FunctionDeclaration;
import checker.Variable;
import checker.VariableType;

public class Scope {
    private HashMap<String, Variable> variableMap = new HashMap<String, Variable>();
    private HashMap<String, FunctionDeclaration> functionsMap = new HashMap<String, FunctionDeclaration>();

    public Scope() {
        // //#region Declaring functions
        // FunctionDeclaration append = new FunctionDeclaration(
        //     "append",
        //     0,
        //     null,
        //     new VariableType(JvmType.STRING, true, -1),
        //     params -> true
        // );

        // FunctionDeclaration println = new FunctionDeclaration(
        //     "Println",
        //     0,
        //     null,
        //     null,
        //     params -> true
        // );

        // FunctionDeclaration printf = new FunctionDeclaration(
        //     "Printf",
        //     0,
        //     null,
        //     null,
        //     params -> true
        // );

        // FunctionDeclaration len = new FunctionDeclaration(
        //     "len",
        //     0,
        //     null,
        //     new VariableType(JvmType.INT, false, 0),
        //     params -> true
        // );

        // FunctionDeclaration cap = new FunctionDeclaration(
        //     "cap",
        //     0,
        //     null,
        //     new VariableType(JvmType.INT, false, 0),
        //     params -> true
        // );
        // //#endregion

        // functionsMap.put(append.getName(), append);
        // functionsMap.put(println.getName(), println);
        // functionsMap.put(printf.getName(), printf);
        // functionsMap.put(len.getName(), len);
        // functionsMap.put(cap.getName(), cap);
    }

    public boolean VarIsDeclared(String varName) {
        return this.GetVar(varName) != null;
    }

    // public boolean FunctionIsDeclared(String functionName) {
    //     return this.GetFunction(functionName) != null;
    // }

    public void AddVar(Variable var) {
        variableMap.put(var.Name, var);
    }

    public Variable GetVar(String name) {
        return variableMap.get(name);
    }

    // public void AddFunction(FunctionDeclaration function) {
    //     functionsMap.put(function.getName(), function);
    // }

    // public FunctionDeclaration GetFunction(String name) {
    //     return functionsMap.get(name);
    // }
}
