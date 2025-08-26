.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    bipush 4
    istore 0
    iload 0
    bipush 2
    irem
    bipush 0
    if_icmpne ELSE_0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Resultado é par"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    goto END_0
    ELSE_0:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Resultado é ímpar"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    END_0:
    return
.end method

