.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    bipush 1
    istore 0
    FOR_START_0:
    iload 0
    bipush 5
    if_icmpgt FOR_END_0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 0
    invokevirtual java/io/PrintStream/println(I)V
    iinc 0 1
    goto FOR_START_0
    FOR_END_0:
    return
.end method

