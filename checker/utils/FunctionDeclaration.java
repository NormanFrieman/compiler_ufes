package checker.utils;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.IntStream;

public class FunctionDeclaration {
    public String Name;
    public int Line;
    public LinkedList<VariableType> ParamsType;
    public VariableType ReturnType;

    public Function<LinkedList<VariableType>, Boolean> VerifyParams;

    public FunctionDeclaration(
        String name,
        int line,
        LinkedList<VariableType> paramsType,
        VariableType returnType,
        Function<LinkedList<VariableType>, Boolean> verifyParams
    ) {
        Name = name;
        Line = line;
        ParamsType = paramsType;
        ReturnType = returnType;

        if (verifyParams != null)
            VerifyParams = verifyParams;
        else {
            VerifyParams = params -> {
                if (paramsType.size() != params.size()) {
                    System.err.println("ERROR: number of params is incompatible");
                    System.exit(1);
                }

                int quantParams = paramsType.size();

                IntStream.range(0, quantParams - 1).forEach(index -> {
                    VariableType typeValue = params.get(index);    
        
                    VariableType typeParam = paramsType.get(index);
        
                    if (typeParam.compareTo(typeValue) != 0) {
                        String nameTypeParam = JvmType.Names.get(typeParam.getType().value);
                        String nameTypeValue = JvmType.Names.get(typeValue.getType().value);
            
                        System.err.println("ERROR: type " + nameTypeParam + " is not compatible with type " + nameTypeValue);
                        System.exit(1);
                    }
                });

                return true;
            };
        }
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

    public void CheckParams(LinkedList<VariableType> params) {
        boolean isValid = this.VerifyParams.apply(params);
        if (!isValid) {
            System.err.println("ERROR: error type param");
            System.exit(1);
        }

        return;
    }
}
