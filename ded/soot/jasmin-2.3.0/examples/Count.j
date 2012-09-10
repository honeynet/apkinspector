; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/Count.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Counts from 0 to 9, printing out the value
; -------------------------------------------------------------------------

.class public examples/Count
.super java/lang/Object

;
; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
       ; set limits used by this method
       .limit locals 4
       .limit stack 3

       ; setup local variables:

       ;    1 - the PrintStream object held in java.lang.System.out
       getstatic java/lang/System/out Ljava/io/PrintStream;
       astore_1

       ;    2 - the integer 10 - the counter used in the loop
       bipush 10
       istore_2

       ; now loop 10 times printing out a number

     Loop:

       ; compute 10 - <local variable 2> ...
       bipush 10
       iload_2
       isub
       invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
       astore_3
       ; ... and print it
       aload_1    ; push the PrintStream object
       aload_3    ; push the string we just created - then ...
       invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

       ; decrement the counter and loop
       iinc 2 -1
       iload_2
       ifne Loop

       ; done
       return

.end method
