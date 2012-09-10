.class public Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;
.super Landroid/os/Handler;
.source "CallingActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/cotal/android/flagecall/CallingActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4
    name = "DelayHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/cotal/android/flagecall/CallingActivity;


# direct methods
.method protected constructor <init>(Lcom/cotal/android/flagecall/CallingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 365
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;);
    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 6
    .parameter "msg"

    .prologue
    const/4 v5, 0x2

    #v5=(PosByte);
    const-wide/16 v3, 0x3e8

    #v3=(LongLo);v4=(LongHi);
    const/4 v2, 0x1

    .line 369
    #v2=(One);
    iget v0, p1, Landroid/os/Message;->what:I

    #v0=(Integer);
    packed-switch v0, :pswitch_data_0

    .line 418
    :cond_0
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);v2=(PosByte);
    return-void

    .line 372
    :pswitch_0
    #v0=(Integer);v1=(Uninit);v2=(One);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-virtual {v0}, Lcom/cotal/android/flagecall/CallingActivity;->finish()V

    goto :goto_0

    .line 376
    :pswitch_1
    #v0=(Integer);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$0(Lcom/cotal/android/flagecall/CallingActivity;)V

    goto :goto_0

    .line 381
    :pswitch_2
    #v0=(Integer);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$1(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/media/Ringtone;

    move-result-object v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$2(Lcom/cotal/android/flagecall/CallingActivity;)I

    move-result v0

    #v0=(Integer);
    if-nez v0, :cond_0

    .line 386
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$3(Lcom/cotal/android/flagecall/CallingActivity;)I

    move-result v1

    #v1=(Integer);
    add-int/lit8 v1, v1, 0x1

    invoke-static {v0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$4(Lcom/cotal/android/flagecall/CallingActivity;I)V

    .line 388
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$3(Lcom/cotal/android/flagecall/CallingActivity;)I

    move-result v0

    #v0=(Integer);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$5(Lcom/cotal/android/flagecall/CallingActivity;)I

    move-result v1

    #v1=(Integer);
    if-le v0, v1, :cond_1

    .line 390
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$6(Lcom/cotal/android/flagecall/CallingActivity;)V

    .line 391
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0, v5}, Lcom/cotal/android/flagecall/CallingActivity;->access$7(Lcom/cotal/android/flagecall/CallingActivity;I)V

    .line 392
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v1

    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v0, v1, v3, v4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto :goto_0

    .line 395
    :cond_1
    #v0=(Integer);v1=(Integer);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v1

    const/4 v2, 0x3

    #v2=(PosByte);
    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v0, v1, v3, v4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 396
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$1(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/media/Ringtone;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/Ringtone;->isPlaying()Z

    move-result v0

    #v0=(Boolean);
    if-nez v0, :cond_0

    .line 402
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$1(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/media/Ringtone;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/Ringtone;->play()V

    goto :goto_0

    .line 409
    :pswitch_3
    #v0=(Integer);v1=(Uninit);v2=(One);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$2(Lcom/cotal/android/flagecall/CallingActivity;)I

    move-result v0

    #v0=(Integer);
    if-ne v0, v2, :cond_0

    .line 414
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0, v5}, Lcom/cotal/android/flagecall/CallingActivity;->access$7(Lcom/cotal/android/flagecall/CallingActivity;I)V

    .line 415
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v1

    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v0, v1, v3, v4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto/16 :goto_0

    .line 369
    #v0=(Unknown);v1=(Unknown);v2=(Unknown);v3=(Unknown);v4=(Unknown);v5=(Unknown);p0=(Unknown);p1=(Unknown);
    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_1
        :pswitch_2
        :pswitch_3
    .end packed-switch
.end method
