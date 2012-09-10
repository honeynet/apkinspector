; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/VerifyTest.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Treats an int as an object - should alert the Verifier
; -------------------------------------------------------------------------

;
; This code demonstrates the verifier at work. See also VerifyTest1.j.
;
; The main() method below tries to clone the integer 100 - this
; is clearly an error since clone() expects an Object, not an integer.
;
; If you run this with no verification on, it is likely to crash the
; interpreter. Running this with the -verify option produces a
; Verifier error.
;

; This is similar to the Java code:
;
;    class VerifyTest {
;        public static void main(String args[]) {
;            int x = 100;
;            x.clone();
;        }
;     }


.class public examples/VerifyTest
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
    bipush 100
    invokevirtual java/lang/Object/clone()Ljava/lang/Object;
    return
.end method
