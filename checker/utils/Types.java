package checker.utils;

import java.util.Map;
import generated.jvmParser;

public class Types {
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
