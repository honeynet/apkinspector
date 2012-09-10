.class Lcom/cotal/android/flagecall/CallingActivity$3;
.super Ljava/lang/Object;
.source "CallingActivity.java"

# interfaces
.implements Landroid/media/MediaPlayer$OnCompletionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/CallingActivity;->playCallAudio()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/cotal/android/flagecall/CallingActivity;


# direct methods
.method constructor <init>(Lcom/cotal/android/flagecall/CallingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallingActivity$3;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    .line 344
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$3;);
    return-void
.end method


# virtual methods
.method public onCompletion(Landroid/media/MediaPlayer;)V
    .locals 4
    .parameter "mp"

    .prologue
    .line 350
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$3;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    const/4 v1, 0x2

    #v1=(PosByte);
    invoke-static {v0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$7(Lcom/cotal/android/flagecall/CallingActivity;I)V

    .line 351
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$3;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity$3;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallingActivity;->access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;

    move-result-object v1

    const/4 v2, 0x1

    #v2=(One);
    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    const-wide/16 v2, 0x3e8

    #v2=(LongLo);v3=(LongHi);
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 352
    return-void
.end method
