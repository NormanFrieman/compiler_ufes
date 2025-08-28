.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    bipush 5
    istore 0
    bipush 1
    istore 1
    FOR_START_0:
    iload 0
    bipush 0
    if_icmplt FOR_END_0
    iload 1
    iload 0
    imul
    bipush 1
    iadd
    istore 1
    iinc 0 -1
    goto FOR_START_0
    FOR_END_0:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    new java/lang/StringBuilder
    dup
    ldc "5! = \n"
    invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V
    iload 1
    invokevirtual java/lang/StringBuilder/append(I)Ljava/lang/StringBuilder;
    invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method

