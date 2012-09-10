; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/ANewArray.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Shows how to use anewarray instruction
; -------------------------------------------------------------------------
;
; This class demonstrates how to allocate a multidimensional
; array using anewarray.
;

.class public examples/ANewArray
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
   .limit stack 4
   .limit locals 2

    ;
    ; Allocates an array like:
    ;      String x[][] = new String[2][5]
    ;

    ; Allocate spine for array and store it in local var 1
    ; (i.e. String[2][])

    iconst_2
    anewarray [Ljava/lang/String;
    astore_1

    ; allocate first array of String[5] and store it in index 0
    aload_1
    iconst_0
    bipush 5
    anewarray java/lang/String
    aastore

    ; allocate second array of String[5] and store it in index 1
    aload_1
    iconst_1
    bipush 5
    anewarray java/lang/String
    aastore

    ; done ...
    return
.end method
