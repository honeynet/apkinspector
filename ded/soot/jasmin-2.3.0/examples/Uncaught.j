; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/Uncaught.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Throws an exception - doesn't catch it
; -------------------------------------------------------------------------

;
; This example class contains a main() method that throws
; an exception but doesn't catch it -
;
.source Uncaught.j
.class public examples/Uncaught
.super java/lang/Object

; specify the initializer method (as for HelloWorld)

.method public <init>()V
    ; just call Object's initializer
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

; specify the "main" method - this throws an uncaught exception

.method public static main([Ljava/lang/String;)V
    .limit stack 2

    new java/lang/Exception
    dup
    invokenonvirtual java/lang/Exception/<init>()V
    athrow

    ; without this the verifier might complain ...
    return
.end method
