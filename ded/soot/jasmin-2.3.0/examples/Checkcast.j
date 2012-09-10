; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/Checkcast.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Catching and throwing exceptions
; -------------------------------------------------------------------------



;
; Simple test for checkcast instruction
;

.class examples/Checkcast
.super java/lang/Object

;
; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
   .limit stack 2

   ; push System.out onto the stack
   getstatic java/lang/System/out Ljava/io/PrintStream;

   ; check that it is a PrintStream
   checkcast java/io/PrintStream

   ; done
   return
.end method
