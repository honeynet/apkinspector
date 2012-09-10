.class Lcom/cotal/android/flagecall/ActivityFlageCall$1;
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$1;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    .line 201
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$1;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .parameter "view"

    .prologue
    .line 206
    new-instance v0, Landroid/content/Intent;

    #v0=(UninitRef,Landroid/content/Intent;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$1;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v1=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    const-class v2, Lcom/cotal/android/util/ContactsListActivity;

    #v2=(Reference,Ljava/lang/Class;);
    invoke-direct {v0, v1, v2}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 207
    .local v0, i:Landroid/content/Intent;
    #v0=(Reference,Landroid/content/Intent;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$1;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const/4 v2, 0x0

    #v2=(Null);
    invoke-virtual {v1, v0, v2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->startActivityForResult(Landroid/content/Intent;I)V

    .line 208
    return-void
.end method
