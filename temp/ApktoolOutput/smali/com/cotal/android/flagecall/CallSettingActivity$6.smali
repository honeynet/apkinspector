.class Lcom/cotal/android/flagecall/CallSettingActivity$6;
.super Ljava/lang/Object;
.source "CallSettingActivity.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemSelectedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/cotal/android/flagecall/CallSettingActivity;->addItemtoSpinner()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/widget/AdapterView$OnItemSelectedListener;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/cotal/android/flagecall/CallSettingActivity;


# direct methods
.method constructor <init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/cotal/android/flagecall/CallSettingActivity$6;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    .line 245
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$6;);
    return-void
.end method


# virtual methods
.method public onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 1
    .parameter
    .parameter "arg1"
    .parameter "arg2"
    .parameter "arg3"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .prologue
    .line 251
    .local p1, arg0:Landroid/widget/AdapterView;,"Landroid/widget/AdapterView<*>;"
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity$6;->this$0:Lcom/cotal/android/flagecall/CallSettingActivity;

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    invoke-static {v0, p3}, Lcom/cotal/android/flagecall/CallSettingActivity;->access$5(Lcom/cotal/android/flagecall/CallSettingActivity;I)V

    .line 252
    return-void
.end method

.method public onNothingSelected(Landroid/widget/AdapterView;)V
    .locals 0
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;)V"
        }
    .end annotation

    .prologue
    .line 258
    .local p1, arg0:Landroid/widget/AdapterView;,"Landroid/widget/AdapterView<*>;"
    return-void
.end method
