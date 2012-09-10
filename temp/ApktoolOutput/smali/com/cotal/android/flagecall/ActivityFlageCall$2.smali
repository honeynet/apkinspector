.class Lcom/cotal/android/flagecall/ActivityFlageCall$2;
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$2;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    .line 212
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$2;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .parameter "arg0"

    .prologue
    .line 217
    new-instance v0, Landroid/content/Intent;

    #v0=(UninitRef,Landroid/content/Intent;);
    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 218
    .local v0, intent:Landroid/content/Intent;
    #v0=(Reference,Landroid/content/Intent;);
    const-string v1, "android.intent.action.RINGTONE_PICKER"

    #v1=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 219
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$2;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const/4 v2, 0x1

    #v2=(One);
    invoke-virtual {v1, v0, v2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->startActivityForResult(Landroid/content/Intent;I)V

    .line 220
    return-void
.end method
