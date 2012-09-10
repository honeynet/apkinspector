.class Lcom/cotal/android/flagecall/ActivityFlageCall$6;
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$6;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    .line 329
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$6;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "view"

    .prologue
    .line 334
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$6;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-virtual {v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->finish()V

    .line 335
    return-void
.end method
