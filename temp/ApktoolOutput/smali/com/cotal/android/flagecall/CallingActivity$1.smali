.class Lcom/cotal/android/flagecall/CallingActivity$1;
.super Ljava/lang/Object;
.source "CallingActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/CallingActivity;->initListener()V
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallingActivity$1;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    .line 185
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$1;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "v"

    .prologue
    .line 191
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity$1;->this$0:Lcom/cotal/android/flagecall/CallingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallingActivity;->access$9(Lcom/cotal/android/flagecall/CallingActivity;)V

    .line 192
    return-void
.end method
