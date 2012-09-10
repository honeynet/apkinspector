; --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
; File:      jasmin/examples/HelloWeb.j
; Author:    Jonathan Meyer, 10 July 1996
; Purpose:   Demonstration of a Jasmin-created applet
; -------------------------------------------------------------------------
; HelloWeb.j

; This demonstrates how you can use Jasmin to create an applet.

; The code below is like the Java code:
;
;     import java.applet.*;
;     import java.awt.*;
;
;     public class HelloWeb extends Applet {
;        private Font font;
;
;        public void init() {
;            font = new Font("Helvetica", Font.BOLD, 48);
;        }
;
;        public void paint(Graphics g) {
;            g.setFont(font);
;            g.drawString("Hello World!", 25, 50);
;        }
;     }


.class public HelloWeb
.super java/applet/Applet

.field private font Ljava/awt/Font;


; my init() method - allocate a font and assign it to this.font.

.method public init()V
    .limit stack 5

    ; Create a new Font and call its constructor with
    ; "Helvetica", 1 (i.e. Font.BOLD), and 48.

    new java/awt/Font
    dup
    ldc "Helvetica"
    iconst_1
    bipush 48
    invokenonvirtual java/awt/Font/<init>(Ljava/lang/String;II)V

    ; now store the Font on the stack in this.font
    aload_0
    swap
    putfield HelloWeb/font Ljava/awt/Font;

    ; done
    return
.end method

; my paint() method - draws the string "Hello World!" using this.font.

.method public paint(Ljava/awt/Graphics;)V
    .limit stack 4
    .limit locals 2

    ; local variable 0 holds <this>
    ; local variable 1 holds the java.awt.Graphics instance ('g').

    ; g.setFont(this.font);
    aload_1
    aload_0
    getfield HelloWeb/font Ljava/awt/Font;
    invokevirtual java/awt/Graphics/setFont(Ljava/awt/Font;)V

    ; g.drawString("Hello Web!", 25, 50);
    aload_1
    ldc "Hello Web!"
    bipush 25
    bipush 50
    invokevirtual java/awt/Graphics/drawString(Ljava/lang/String;II)V

    ; done
    return
.end method


; standard constructor
.method public <init>()V
    aload_0
    invokenonvirtual java/applet/Applet/<init>()V
    return
.end method
