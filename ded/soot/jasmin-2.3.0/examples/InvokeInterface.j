; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/InvokeInterface.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Example of using invokeinterface
; -------------------------------------------------------------------------
;
; Demonstrates invoking an interface method
;

.class public examples/InvokeInterface
.super java/lang/Object

; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

;
; This is a rather silly example - since the result of calling the
; interface method isn't actually used. But it does illustrate how to
; use invokeinterface.
;

.method public example(Ljava/util/Enumeration;)V
    .limit stack 1
    .limit locals 3

    ; push local variable 1 (the Enumeration object)
    aload_1

    ; now call the hasMoreElements() interface method.
    invokeinterface java/util/Enumeration/hasMoreElements()Z 1

    ; store the integer result in local variable 2
    istore_2

    ; done
    return
.end method

.method public static main([Ljava/lang/String;)V
    return
.end method
