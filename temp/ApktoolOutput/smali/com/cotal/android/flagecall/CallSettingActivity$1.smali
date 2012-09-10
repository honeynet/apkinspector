.class Lcom/cotal/android/flagecall/CallSettingActivity$1;
.super Ljava/lang/Object;
.source "CallSettingActivity.java"

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


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
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$1;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    .line 85
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$1;);
    return-void
.end method


# virtual methods
.method public onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 2
    .parameter "buttonView"
    .parameter "isChecked"

    .prologue
    .line 91
    if-eqz p2, :cond_0

    .line 93
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity$1;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$0(Lcom/cotal/android/flagecall/CallSettingActivity;)Landroid/widget/EditText;

    move-result-object v0

    const/4 v1, 0x1

    #v1=(One);
    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setEnabled(Z)V

    .line 99
    :goto_0
    #v1=(Boolean);
    return-void

    .line 97
    :cond_0
    #v0=(Uninit);v1=(Uninit);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity$1;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$0(Lcom/cotal/android/flagecall/CallSettingActivity;)Landroid/widget/EditText;

    move-result-object v0

    const/4 v1, 0x0

    #v1=(Null);
    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setEnabled(Z)V

    goto :goto_0
.end method
