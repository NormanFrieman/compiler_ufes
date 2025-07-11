package checker.utils;

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
    public int compareTo(VariableType arg0) {
        if (
            (arg0.getType() == JvmType.FLOAT32 && this.getType() == JvmType.FLOAT64)
            || (arg0.getType() == JvmType.FLOAT64 && this.getType() == JvmType.FLOAT32))
            return 0;
        
        return
            Integer.compare(this.getType().value, arg0.getType().value)
            + Boolean.compare(this.getIsArray(), arg0.getIsArray())
            + Integer.compare(this.getMaxSize(), arg0.getMaxSize());
    }

    public String print() {
        return !IsArray
            ? JvmType.Names.get(getType().value)
            : "[" + getMaxSize() + "]" + JvmType.Names.get(getType().value);
    }
}
