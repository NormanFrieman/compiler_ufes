package checker;

import java.util.LinkedList;

public class FunctionDeclaration {
    public String Name;
    public int Line;
    public LinkedList<VariableType> ParamsType;
    public VariableType ReturnType;

    public FunctionDeclaration(String name, int line, LinkedList<VariableType> paramsType, VariableType returnType) {
        Name = name;
        Line = line;
        ParamsType = paramsType;
        ReturnType = returnType;
    }

    public String getName() {
        return Name;
    }

    public int getLine() {
        return Line;
    }

    public LinkedList<VariableType> getParamsType() {
        return ParamsType;
    }

    public VariableType getReturnType() {
        return ReturnType;
    }
}
