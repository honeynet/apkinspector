; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/Catch.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Catching and throwing exceptions
; -------------------------------------------------------------------------

;
; This hows how to throw and catch Exceptions in Jasmin
;

.class public examples/Catch
.super java/lang/Object

; standard initializer
.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object.<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V

    .limit locals 3
    .limit stack 5

    ; set up a handler to catch subclasses of java.lang.Exception
    .catch java/lang/Exception from Label1 to Label2 using Handler

    ; store System.out in local variable 1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    astore_1

    ; print out a message
    aload_1
    ldc " -- Before exception"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    ; construct an instance of Exception, initialize it with a string,
    ; throw it. This is like the Java statement :
    ;
    ;     throw new Exception("My exception");
    ;

Label1:
    new java/lang/Exception
    dup
    ldc "<my exception>"
    invokenonvirtual java/lang/Exception/<init>(Ljava/lang/String;)V
    athrow

Label2:
    aload_1
    ldc " -- After exception"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    return

    ; This is the handler for the exception

Handler:
    ; store the exception in local variable 2
    astore_2

    ; print out a message
    aload_1
    ldc " -- Caught exception: "
    invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V

    ; call getMessage() to retrieve the message from the Exception...
    aload_1
    aload_2
    invokevirtual java/lang/Throwable/getMessage()Ljava/lang/String;
    ; ... now print it
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

    ; return to the code
    goto Label2

.end method
