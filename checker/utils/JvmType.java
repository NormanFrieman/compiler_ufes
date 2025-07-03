package checker.utils;

import java.util.Map;
import generated.jvmParser;

public enum JvmType {
    UINT(jvmParser.TYPE_UINT),
    INT(jvmParser.TYPE_INT),
    INT8(jvmParser.TYPE_INT8),
    INT16(jvmParser.TYPE_INT16),
    INT32(jvmParser.TYPE_INT32),
    INT64(jvmParser.TYPE_INT64),
    FLOAT32(jvmParser.TYPE_FLOAT32),
    FLOAT64(jvmParser.TYPE_FLOAT64),
    STRING(jvmParser.TYPE_STRING),
    BOOL(jvmParser.TYPE_BOOL);

    public final int value;

    JvmType(int value) {
        this.value = value;
    }

    public static Map<Integer, String> Names = Map.of(
        jvmParser.TYPE_UINT, "uint",
        jvmParser.TYPE_INT, "int",
        jvmParser.TYPE_INT8, "int8",
        jvmParser.TYPE_INT16, "int16",
        jvmParser.TYPE_INT32, "int32",
        jvmParser.TYPE_INT64, "int64",
        jvmParser.TYPE_FLOAT32, "float32",
        jvmParser.TYPE_FLOAT64, "float64",
        jvmParser.TYPE_STRING, "string",
        jvmParser.TYPE_BOOL, "bool"
    );
}
