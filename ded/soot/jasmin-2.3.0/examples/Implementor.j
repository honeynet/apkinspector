; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/HelloWorld.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Shows how to define a class that implements an interface
; -------------------------------------------------------------------------

;
; This class implements the examples.AnInterface interface - see
; AnInterface.j
;
.class public examples/Implementor
.super java/lang/Object
.implements examples/AnInterface

;
; standard initializer
;
.method public <init>()V
   aload_0

   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

;
; implement the foo()V method - this is an interface method
;
.method public foo()V
   .limit stack 2

   ; print a simple message
   getstatic java/lang/System/out Ljava/io/PrintStream;
   ldc "Hello Interface"
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

   ; done
   return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 2

    ; create a new one of me
    new examples/Implementor
    dup
    invokenonvirtual examples/Implementor/<init>()V
   
    ; now call my interface method foo()
    invokeinterface examples/AnInterface/foo()V 1

    return
.end method

