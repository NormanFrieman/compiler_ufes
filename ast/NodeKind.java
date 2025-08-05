package ast;

public enum NodeKind {
    PROGRAM_NODE,

    FUNCTION_DECLARATION_NODE,
    SCOPE_NODE,

    VAR_ASSIGN_NODE,
    VAR_UPDATE_NODE,
    VAR_USE_NODE,

    NULL_USE_NODE,

    FUNCTION_CALL_NODE,

    LOOP_NODE,
}
