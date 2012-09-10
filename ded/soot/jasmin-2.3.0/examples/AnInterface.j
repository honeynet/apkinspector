; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/AnInterface.j
; Author:    Jonathan Meyer, 1 Oct 1996
; Purpose:   A Java interface written in Jasmin
; -------------------------------------------------------------------------

;
; This file shows how to use Jasmin to define an interface. It
; is like the Java code:
; 
; interface public examples.AnInterface {
;     void foo();
; }
;
; See examples.Implementor for an example of a class that implements
; this interface.
;

.interface public examples/AnInterface
.super java/lang/Object     

; (Interfaces should either inherit from Object, or from 
;  another interface.)

;
; declare abstract method foo() - note that the method body is empty.
;
.method abstract foo()V
.end method


