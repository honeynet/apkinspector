; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/Arrays.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Example using JVM's anewarray and aaload/aastore
; -------------------------------------------------------------------------

;
; This illustrates how to use the various JVM array instructions - though
; it doesn't actually do anything very interesting with the arrays.
;

.class public examples/Arrays
.super java/lang/Object

; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
   .limit locals 2
   .limit stack 4

   ; creates a new array of strings,
   ; like:
   ;      String[] myarray = new String[2];
   iconst_2
   anewarray java/lang/String
   astore_1  ; stores this in local variable 1

   ; this is like the code:
   ;      myarray[0] = args[0];

   aload_1     ; push my array on the stack
   iconst_0
   aload_0     ; push the array argument to main() on the stack
   iconst_0
   aaload      ; get its zero'th entry
   aastore     ; and store it in my zero'th entry

   ; now print out myarray[0]

   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload_1
   iconst_0
   aaload
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

   ; done
   return
.end method
