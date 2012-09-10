; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/VerifyTest1.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Trys to pull one on the verifier
; -------------------------------------------------------------------------

; This file illustrates the bytecode verifier at work - the
; code in the example() method below seems reasonable, but
; Java's bytecode verifier will fail the code because the two points leading
; to the Loop label (from the top of the method and from the ifne
; statement) have different stack states.  Instead, a different approach
; must be adopted  - e.g. by allocating an array, or simply writing:
;
;    aconst_null
;    aconst_null
;    aconst_null
;    aconst_null

; Note that many interpreters will run this code OK if you don't use
; a verifier. The code itself is well behaved (it doesn't trash the
; interpreter), but the approach it uses is disallowed by the verifier.
;

; Compile the example, then run it using:
;
;     % java -verify VerifyTest1
;     VERIFIER ERROR VerifyTest1.example()V:
;     Inconsistent stack height 1 != 0
;

.class public examples/VerifyTest1
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public example()V
    .limit locals 2
    .limit stack  10

    ; this tries to push four nulls onto the stack
    ; using a loop - Java's verifier will fail this program

    iconst_4     ; store 4 in local variable 1 (used as a counter)
    istore_1

  Loop:
    aconst_null  ; push null onto the stack
    iinc 1 -1    ; decrement local variable 4 (the counter variable)
    iload_1
    ifne Loop    ; jump back to Loop unless the variable has reached 0

    return
.end method

.method public static main([Ljava/lang/String;)V
    ; - do nothing : this is only to illustrate the bytecode verifier at work.
    return
.end method
