.class Lcom/cotal/android/flagecall/CallSettingActivity$2;
.super Ljava/lang/Object;
.source "CallSettingActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/CallSettingActivity;->initListener()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/cotal/android/flagecall/CallSettingActivity;


# direct methods
.method constructor <init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$2;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    .line 103
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$2;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "v"

    .prologue
    .line 108
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity$2;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$1(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    .line 109
    return-void
.end method
