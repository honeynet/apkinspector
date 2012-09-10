; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/MultiANewArray.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Example of multanewarray instruction
; -------------------------------------------------------------------------
;
; This illustrates how to use multianewarray to allocate
; an array.
;

.class public examples/MultiANewArray
.super java/lang/Object

; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V

   .limit locals 4
   .limit stack 2

   ;
   ; This allocates an array like:
   ;
   ;      String s[][] = new String[2][5];
   ;
   iconst_2
   iconst_5
   multianewarray [[Ljava/lang/String; 2
   astore_1

   return
.end method
