; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/MultiArrays.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Examples involving multi-dimensional arrays
; -------------------------------------------------------------------------
;
; This illustrates how to use multi-dimensional arrays in the Java VM
; (though it doesn't actually do anything very interesting with the arrays.)
;

.class public examples/MultiArrays
.super java/lang/Object

; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V

   .limit locals 4
   .limit stack 5

   ; this is like:
   ;    new int[2][5][]
   iconst_2
   iconst_5
   multianewarray [[[I 2

   ; store the result in local variable 1
   astore_1

   aload_1
   iconst_1
   aaload     ; stack now contains x[0]
   astore_2   ; store the array in local variable 2

   ; create a new array of 50 ints and store it in x[1][1]
   aload_2
   iconst_1
   bipush 50
   newarray int
   aastore

   ; create a new array of 60 ints and store it in x[1][2]
   aload_2
   iconst_2
   bipush 60
   newarray int
   aastore

   return
.end method
