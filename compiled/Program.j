.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    bipush 7
    istore 0
    iload 0
    bipush 5
    if_icmplt END
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Resultado é maior que 5"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    END:
    return
.end method

