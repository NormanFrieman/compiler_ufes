package checker.utils;

import java.util.List;

public class VariableType implements Comparable<VariableType> {
    public final JvmType Type;
    public boolean IsArray;
    public int MaxSize;

    public VariableType(JvmType type) {
        Type = type;
        IsArray = false;
        MaxSize = ArraySize.NO_ARRAY.value;
    }

    public VariableType(JvmType type, boolean isArray) {
        Type = type;
        IsArray = isArray;
        MaxSize = ArraySize.NO_ARRAY.value;
    }

    public VariableType(JvmType type, boolean isArray, int maxSize) {
        Type = type;
        IsArray = isArray;
        MaxSize = maxSize;
    }

    public VariableType newInstance() {
        return new VariableType(Type, IsArray, MaxSize);
    }

    public JvmType getType() {
        return Type;
    }

    public void setIsArray(boolean isArray) {
        IsArray = isArray;
    }

    public boolean getIsArray() {
        return IsArray;
    }

    public void setMaxSize(int maxSize) {
        MaxSize = maxSize;
    }
    
    public int getMaxSize() {
        return MaxSize;
    }

    @Override
    public int compareTo(VariableType arg) {
        return
            Integer.compare(this.getType().value, arg.getType().value)
            + CompareArrayMaxSize(arg);
    }

    public boolean IsAssignable(VariableType arg) {
        if (CompareArrayMaxSize(arg) != 0)
            return false;
        
        return TypeAssign(arg) != JvmType.ERROR;
    }

    public JvmType TypeAssign(VariableType arg) {
        return JvmType.TypeAssign[arg.getType().ordinal()][this.getType().ordinal()];
    }

    public String print() {
        return !IsArray
            ? JvmType.Names.get(getType().value)
            : "[" + getMaxSize() + "]" + JvmType.Names.get(getType().value);
    }

    public boolean IsNumeric() {
        List<JvmType> numericTypes = List.of(
            JvmType.UINT,
            JvmType.INT,
            JvmType.INT8,
            JvmType.INT16,
            JvmType.INT32,
            JvmType.INT64,
            JvmType.FLOAT32,
            JvmType.FLOAT64
        );
        return numericTypes.contains(getType());
    }

    private int CompareArrayMaxSize(VariableType arg) {
        return
            Boolean.compare(this.getIsArray(), arg.getIsArray())
            + Integer.compare(this.getMaxSize(), arg.getMaxSize());
    }
}
