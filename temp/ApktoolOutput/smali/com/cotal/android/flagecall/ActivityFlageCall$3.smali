.class Lcom/cotal/android/flagecall/ActivityFlageCall$3;
.super Ljava/lang/Object;
.source "ActivityFlageCall.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/ActivityFlageCall;->initListener()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;


# direct methods
.method constructor <init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    .line 225
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$3;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 230
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v1=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$0(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/net/Uri;

    move-result-object v1

    if-nez v1, :cond_0

    .line 232
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    sget-object v2, Landroid/provider/Settings$System;->DEFAULT_RINGTONE_URI:Landroid/net/Uri;

    #v2=(Reference,Landroid/net/Uri;);
    invoke-static {v1, v2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$1(Lcom/cotal/android/flagecall/ActivityFlageCall;Landroid/net/Uri;)V

    .line 235
    :cond_0
    #v2=(Conflicted);
    new-instance v0, Landroid/content/Intent;

    #v0=(UninitRef,Landroid/content/Intent;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-class v2, Lcom/cotal/android/flagecall/CallingActivity;

    #v2=(Reference,Ljava/lang/Class;);
    invoke-direct {v0, v1, v2}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 236
    .local v0, intent:Landroid/content/Intent;
    #v0=(Reference,Landroid/content/Intent;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "Name"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v3=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v3

    invoke-virtual {v3}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v3

    invoke-interface {v3}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 237
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "Number"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v3

    invoke-virtual {v3}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v3

    invoke-interface {v3}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 238
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "RingUri"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$0(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/net/Uri;

    move-result-object v3

    invoke-virtual {v3}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 239
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "RingTonePlayTime"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$5(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v3

    #v3=(Integer);
    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 240
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "PhotoID"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v3=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$7(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 241
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "MusicID"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$8(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v3

    #v3=(Integer);
    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 242
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "MusicFile"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v3=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$9(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 243
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "Vibrate"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$10(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z

    move-result v3

    #v3=(Boolean);
    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$11(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Z)V

    .line 244
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "PlayLoop"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v3=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$12(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z

    move-result v3

    #v3=(Boolean);
    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$11(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Z)V

    .line 245
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v2, "VoicePlayTime"

    iget-object v3, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v3=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$13(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v3

    #v3=(Integer);
    invoke-static {v1, v2, v3}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 246
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const/4 v2, 0x2

    #v2=(PosByte);
    invoke-virtual {v1, v0, v2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->startActivityForResult(Landroid/content/Intent;I)V

    .line 247
    return-void
.end method
