.class Lcom/cotal/android/flagecall/CallSettingActivity$5;
.super Ljava/lang/Object;
.source "CallSettingActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/CallSettingActivity;->stopRecordVoice()V
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$5;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    .line 194
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$5;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "whichButton"

    .prologue
    .line 198
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$5;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    #v1=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    invoke-static {v1}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$3(Lcom/cotal/android/flagecall/CallSettingActivity;)Landroid/widget/EditText;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 199
    .local v0, name:Ljava/lang/String;
    #v0=(Reference,Ljava/lang/String;);
    const-string v1, ""

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    #v1=(Boolean);
    if-eqz v1, :cond_0

    .line 201
    const-string v0, "\u4e34\u65f6\u5f55\u97f3"

    .line 204
    :cond_0
    const-string v1, "tmp.3gpp"

    #v1=(Reference,Ljava/lang/String;);
    new-instance v2, Ljava/lang/StringBuilder;

    #v2=(UninitRef,Ljava/lang/StringBuilder;);
    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    #v3=(Reference,Ljava/lang/String;);
    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v2=(Reference,Ljava/lang/StringBuilder;);
    const-string v3, ".3gpp"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->renameFile(Ljava/lang/String;Ljava/lang/String;)Z

    .line 205
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$5;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    invoke-static {v1}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$4(Lcom/cotal/android/flagecall/CallSettingActivity;)Ljava/util/List;

    move-result-object v1

    invoke-interface {v1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 206
    return-void
.end method
