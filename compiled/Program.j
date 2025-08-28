.class public Program
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    ldc2_w 3.4E+38
    dstore 0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    new java/lang/StringBuilder
    dup
    ldc "The type of myVar is: \n"
    invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V
    dload 0
    invokevirtual java/lang/StringBuilder/append(D)Ljava/lang/StringBuilder;
    invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method

