.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    bipush 76
    istore 0
    iload 0
    bipush 90
    if_icmplt ELSE_0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Conceito A"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    goto END_0
    ELSE_0:
    iload 0
    bipush 75
    if_icmplt ELSE_1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Conceito B"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    goto END_0
    ELSE_1:
    iload 0
    bipush 60
    if_icmplt ELSE_2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Conceito C"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    goto END_0
    ELSE_2:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Reprovado"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    END_0:
    return
.end method

