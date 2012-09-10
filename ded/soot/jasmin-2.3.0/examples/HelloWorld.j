; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/HelloWorld.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Prints out "Hello World!"
; -------------------------------------------------------------------------


.class public examples/HelloWorld
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

   ; push a string onto the stack
   ldc "Hello World!"

   ; call the PrintStream.println() method.
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

   ; done
   return
.end method
