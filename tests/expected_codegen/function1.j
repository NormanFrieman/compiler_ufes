.class public Program
.super java/lang/Object
.method public static function(I)I
    .limit stack 10
    .limit locals 10
    iload 0
    bipush 2
    imul
    ireturn
.end method
.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "Hello World"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method

