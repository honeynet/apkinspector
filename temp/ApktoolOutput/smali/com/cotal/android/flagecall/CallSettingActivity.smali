.class public Lcom/cotal/android/flagecall/CallSettingActivity;
.super Landroid/app/Activity;
.source "CallSettingActivity.java"


# instance fields
.field private adapter:Landroid/widget/ArrayAdapter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/widget/ArrayAdapter",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private btnCancel:Landroid/widget/Button;

.field private btnCancelListener:Landroid/view/View$OnClickListener;

.field private btnOk:Landroid/widget/Button;

.field private btnOkListener:Landroid/view/View$OnClickListener;

.field private btnRecord:Landroid/widget/Button;

.field private btnRecordListener:Landroid/view/View$OnClickListener;

.field private chkPlayLoop:Landroid/widget/CheckBox;

.field private chkPlayLoopListener:Landroid/widget/CompoundButton$OnCheckedChangeListener;

.field private chkVibrate:Landroid/widget/CheckBox;

.field private editFileName:Landroid/widget/EditText;

.field private editPlayTime:Landroid/widget/EditText;

.field private editRingTime:Landroid/widget/EditText;

.field private isRecording:Z

.field private recorder:Landroid/media/MediaRecorder;

.field private selectedVoice:Ljava/lang/String;

.field private sprCallVoice:Landroid/widget/Spinner;

.field private voiceList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 26
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity;);
    return-void
.end method

.method static synthetic access$0(Lcom/cotal/android/flagecall/CallSettingActivity;)Landroid/widget/EditText;
    .locals 1
    .parameter

    .prologue
    .line 32
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editPlayTime:Landroid/widget/EditText;

    #v0=(Reference,Landroid/widget/EditText;);
    return-object v0
.end method

.method static synthetic access$1(Lcom/cotal/android/flagecall/CallSettingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 156
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->recordVoiceHandler()V

    return-void
.end method

.method static synthetic access$2(Lcom/cotal/android/flagecall/CallSettingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 132
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->setCallParams()V

    return-void
.end method

.method static synthetic access$3(Lcom/cotal/android/flagecall/CallSettingActivity;)Landroid/widget/EditText;
    .locals 1
    .parameter

    .prologue
    .line 50
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editFileName:Landroid/widget/EditText;

    #v0=(Reference,Landroid/widget/EditText;);
    return-object v0
.end method

.method static synthetic access$4(Lcom/cotal/android/flagecall/CallSettingActivity;)Ljava/util/List;
    .locals 1
    .parameter

    .prologue
    .line 46
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->voiceList:Ljava/util/List;

    #v0=(Reference,Ljava/util/List;);
    return-object v0
.end method

.method static synthetic access$5(Lcom/cotal/android/flagecall/CallSettingActivity;I)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 263
    invoke-direct {p0, p1}, Lcom/cotal/android/flagecall/CallSettingActivity;->getSelectedVoice(I)V

    return-void
.end method

.method private addItemtoSpinner()V
    .locals 3

    .prologue
    .line 237
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->voiceList:Ljava/util/List;

    #v0=(Reference,Ljava/util/List;);
    if-nez v0, :cond_0

    .line 239
    const-string v0, ".3gpp"

    invoke-static {v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->getFiles(Ljava/lang/String;)Ljava/util/List;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->voiceList:Ljava/util/List;

    .line 242
    :cond_0
    new-instance v0, Landroid/widget/ArrayAdapter;

    #v0=(UninitRef,Landroid/widget/ArrayAdapter;);
    const v1, 0x1090008

    #v1=(Integer);
    iget-object v2, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->voiceList:Ljava/util/List;

    #v2=(Reference,Ljava/util/List;);
    invoke-direct {v0, p0, v1, v2}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;ILjava/util/List;)V

    #v0=(Reference,Landroid/widget/ArrayAdapter;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->adapter:Landroid/widget/ArrayAdapter;

    .line 243
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->adapter:Landroid/widget/ArrayAdapter;

    const v1, 0x1090009

    invoke-virtual {v0, v1}, Landroid/widget/ArrayAdapter;->setDropDownViewResource(I)V

    .line 244
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->sprCallVoice:Landroid/widget/Spinner;

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->adapter:Landroid/widget/ArrayAdapter;

    #v1=(Reference,Landroid/widget/ArrayAdapter;);
    invoke-virtual {v0, v1}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

    .line 245
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->sprCallVoice:Landroid/widget/Spinner;

    new-instance v1, Lcom/cotal/android/flagecall/CallSettingActivity$6;

    #v1=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$6;);
    invoke-direct {v1, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$6;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    #v1=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$6;);
    invoke-virtual {v0, v1}, Landroid/widget/Spinner;->setOnItemSelectedListener(Landroid/widget/AdapterView$OnItemSelectedListener;)V

    .line 261
    return-void
.end method

.method private findView()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 66
    #v1=(Null);
    iput-boolean v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->isRecording:Z

    .line 67
    const v0, 0x7f060008

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editRingTime:Landroid/widget/EditText;

    .line 68
    const v0, 0x7f06000d

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editPlayTime:Landroid/widget/EditText;

    .line 70
    const v0, 0x7f060009

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/CheckBox;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkVibrate:Landroid/widget/CheckBox;

    .line 71
    const v0, 0x7f06000c

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/CheckBox;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoop:Landroid/widget/CheckBox;

    .line 73
    const v0, 0x7f06000b

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Spinner;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->sprCallVoice:Landroid/widget/Spinner;

    .line 74
    const v0, 0x7f06000f

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecord:Landroid/widget/Button;

    .line 75
    const v0, 0x7f060010

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnOk:Landroid/widget/Button;

    .line 76
    const v0, 0x7f060011

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnCancel:Landroid/widget/Button;

    .line 78
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoop:Landroid/widget/CheckBox;

    invoke-virtual {v0, v1}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 79
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editPlayTime:Landroid/widget/EditText;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setEnabled(Z)V

    .line 80
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->addItemtoSpinner()V

    .line 81
    return-void
.end method

.method private getSelectedVoice(I)V
    .locals 2
    .parameter "id"

    .prologue
    .line 265
    if-eqz p1, :cond_0

    const/4 v0, 0x1

    #v0=(One);
    if-ne p1, v0, :cond_2

    .line 267
    :cond_0
    #v0=(Conflicted);
    new-instance v0, Ljava/lang/StringBuilder;

    #v0=(UninitRef,Ljava/lang/StringBuilder;);
    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    #v0=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    .line 277
    :cond_1
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);
    return-void

    .line 271
    :cond_2
    #v0=(One);v1=(Uninit);
    new-instance v1, Ljava/lang/StringBuilder;

    #v1=(UninitRef,Ljava/lang/StringBuilder;);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->adapter:Landroid/widget/ArrayAdapter;

    #v0=(Reference,Landroid/widget/ArrayAdapter;);
    invoke-virtual {v0, p1}, Landroid/widget/ArrayAdapter;->getItem(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v1=(Reference,Ljava/lang/StringBuilder;);
    const-string v0, ".3gpp"

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    .line 272
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    invoke-static {v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->fileExist(Ljava/lang/String;)Z

    move-result v0

    #v0=(Boolean);
    if-nez v0, :cond_1

    .line 274
    const-string v0, "0"

    #v0=(Reference,Ljava/lang/String;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    goto :goto_0
.end method

.method private initListener()V
    .locals 1

    .prologue
    .line 85
    new-instance v0, Lcom/cotal/android/flagecall/CallSettingActivity$1;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$1;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$1;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$1;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoopListener:Landroid/widget/CompoundButton$OnCheckedChangeListener;

    .line 103
    new-instance v0, Lcom/cotal/android/flagecall/CallSettingActivity$2;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$2;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$2;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$2;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecordListener:Landroid/view/View$OnClickListener;

    .line 113
    new-instance v0, Lcom/cotal/android/flagecall/CallSettingActivity$3;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$3;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$3;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$3;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnCancelListener:Landroid/view/View$OnClickListener;

    .line 122
    new-instance v0, Lcom/cotal/android/flagecall/CallSettingActivity$4;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$4;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$4;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$4;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnOkListener:Landroid/view/View$OnClickListener;

    .line 130
    return-void
.end method

.method private recordVoiceHandler()V
    .locals 3

    .prologue
    const/4 v2, 0x0

    .line 158
    #v2=(Null);
    iget-boolean v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->isRecording:Z

    #v0=(Boolean);
    if-eqz v0, :cond_0

    .line 160
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->stopRecordVoice()V

    .line 161
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecord:Landroid/widget/Button;

    #v0=(Reference,Landroid/widget/Button;);
    const-string v1, "\u5f00\u59cb\u5f55\u97f3"

    #v1=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v1}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 162
    iput-boolean v2, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->isRecording:Z

    .line 176
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);
    return-void

    .line 166
    :cond_0
    #v0=(Boolean);v1=(Uninit);
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->startRecordVoice()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 168
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecord:Landroid/widget/Button;

    #v0=(Reference,Landroid/widget/Button;);
    const-string v1, "\u505c\u6b62\u5f55\u97f3"

    #v1=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v1}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 169
    const/4 v0, 0x1

    #v0=(One);
    iput-boolean v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->isRecording:Z

    goto :goto_0

    .line 173
    :cond_1
    #v0=(Boolean);v1=(Uninit);
    const-string v0, "\u542f\u52a8\u5f55\u97f3\u8bbe\u5907\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5\uff01"

    #v0=(Reference,Ljava/lang/String;);
    invoke-static {p0, v0, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method

.method private setCallParams()V
    .locals 4

    .prologue
    .line 134
    new-instance v0, Landroid/os/Bundle;

    #v0=(UninitRef,Landroid/os/Bundle;);
    invoke-direct {v0}, Landroid/os/Bundle;-><init>()V

    .line 135
    .local v0, bundle:Landroid/os/Bundle;
    #v0=(Reference,Landroid/os/Bundle;);
    const-string v2, "SelectedVoice"

    #v2=(Reference,Ljava/lang/String;);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    #v3=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2, v3}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 136
    const-string v2, "RingTime"

    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editRingTime:Landroid/widget/EditText;

    invoke-virtual {v3}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v3

    invoke-interface {v3}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v2, v3}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 137
    const-string v2, "VIbrate"

    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkVibrate:Landroid/widget/CheckBox;

    invoke-virtual {v3}, Landroid/widget/CheckBox;->isChecked()Z

    move-result v3

    #v3=(Boolean);
    invoke-virtual {v0, v2, v3}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    .line 138
    const-string v2, "PlayLoop"

    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoop:Landroid/widget/CheckBox;

    #v3=(Reference,Landroid/widget/CheckBox;);
    invoke-virtual {v3}, Landroid/widget/CheckBox;->isChecked()Z

    move-result v3

    #v3=(Boolean);
    invoke-virtual {v0, v2, v3}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    .line 139
    const-string v2, "PlayTime"

    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editPlayTime:Landroid/widget/EditText;

    #v3=(Reference,Landroid/widget/EditText;);
    invoke-virtual {v3}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v3

    invoke-interface {v3}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v2, v3}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 141
    new-instance v1, Landroid/content/Intent;

    #v1=(UninitRef,Landroid/content/Intent;);
    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    .line 142
    .local v1, mIntent:Landroid/content/Intent;
    #v1=(Reference,Landroid/content/Intent;);
    invoke-virtual {v1, v0}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    .line 143
    const/4 v2, -0x1

    #v2=(Byte);
    invoke-virtual {p0, v2, v1}, Lcom/cotal/android/flagecall/CallSettingActivity;->setResult(ILandroid/content/Intent;)V

    .line 144
    invoke-virtual {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->finish()V

    .line 145
    return-void
.end method

.method private setListener()V
    .locals 2

    .prologue
    .line 149
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->initListener()V

    .line 150
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoop:Landroid/widget/CheckBox;

    #v0=(Reference,Landroid/widget/CheckBox;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->chkPlayLoopListener:Landroid/widget/CompoundButton$OnCheckedChangeListener;

    #v1=(Reference,Landroid/widget/CompoundButton$OnCheckedChangeListener;);
    invoke-virtual {v0, v1}, Landroid/widget/CheckBox;->setOnCheckedChangeListener(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V

    .line 151
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecord:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnRecordListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 152
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnOk:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnOkListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 153
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnCancel:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->btnCancelListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 154
    return-void
.end method

.method private startRecordVoice()Z
    .locals 4

    .prologue
    const/4 v3, 0x1

    .line 215
    :try_start_0
    #v3=(One);
    new-instance v1, Landroid/media/MediaRecorder;

    #v1=(UninitRef,Landroid/media/MediaRecorder;);
    invoke-direct {v1}, Landroid/media/MediaRecorder;-><init>()V

    #v1=(Reference,Landroid/media/MediaRecorder;);
    iput-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    .line 216
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    const/4 v2, 0x1

    #v2=(One);
    invoke-virtual {v1, v2}, Landroid/media/MediaRecorder;->setAudioSource(I)V

    .line 217
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/media/MediaRecorder;->setOutputFormat(I)V

    .line 218
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/media/MediaRecorder;->setAudioEncoder(I)V

    .line 219
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    const-string v2, "/sdcard/MyFlageCall/tmp.3gpp"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, v2}, Landroid/media/MediaRecorder;->setOutputFile(Ljava/lang/String;)V

    .line 220
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v1}, Landroid/media/MediaRecorder;->prepare()V

    .line 221
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v1}, Landroid/media/MediaRecorder;->start()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v1, v3

    .line 231
    :goto_0
    #v0=(Conflicted);v1=(Boolean);v2=(Conflicted);
    return v1

    .line 224
    :catch_0
    #v0=(Uninit);v1=(Conflicted);
    move-exception v1

    #v1=(Reference,Ljava/lang/Exception;);
    move-object v0, v1

    .line 226
    .local v0, ex:Ljava/lang/Exception;
    #v0=(Reference,Ljava/lang/Exception;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    if-eqz v1, :cond_0

    .line 228
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v1}, Landroid/media/MediaRecorder;->release()V

    .line 229
    const/4 v1, 0x0

    #v1=(Null);
    iput-object v1, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    .line 231
    :cond_0
    #v1=(Reference,Landroid/media/MediaRecorder;);
    const/4 v1, 0x0

    #v1=(Null);
    goto :goto_0
.end method

.method private stopRecordVoice()V
    .locals 5

    .prologue
    const/4 v4, 0x0

    .line 180
    #v4=(Null);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    #v3=(Reference,Landroid/media/MediaRecorder;);
    if-eqz v3, :cond_0

    .line 182
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v3}, Landroid/media/MediaRecorder;->stop()V

    .line 183
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v3}, Landroid/media/MediaRecorder;->release()V

    .line 184
    iput-object v4, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    .line 187
    :cond_0
    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v1

    .line 188
    .local v1, inflater:Landroid/view/LayoutInflater;
    #v1=(Reference,Landroid/view/LayoutInflater;);
    const v3, 0x7f030005

    #v3=(Integer);
    invoke-virtual {v1, v3, v4}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v2

    .line 189
    .local v2, textEntryView:Landroid/view/View;
    #v2=(Reference,Landroid/view/View;);
    new-instance v0, Landroid/app/AlertDialog$Builder;

    #v0=(UninitRef,Landroid/app/AlertDialog$Builder;);
    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 190
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    #v0=(Reference,Landroid/app/AlertDialog$Builder;);
    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    .line 191
    const v3, 0x7f060014

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    #v3=(Reference,Landroid/view/View;);
    check-cast v3, Landroid/widget/EditText;

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->editFileName:Landroid/widget/EditText;

    .line 192
    const-string v3, "\u8f93\u5165\u5f55\u97f3\u6587\u4ef6\u540d\u79f0"

    invoke-virtual {v0, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 193
    const-string v3, "\u786e\u5b9a"

    .line 194
    new-instance v4, Lcom/cotal/android/flagecall/CallSettingActivity$5;

    #v4=(UninitRef,Lcom/cotal/android/flagecall/CallSettingActivity$5;);
    invoke-direct {v4, p0}, Lcom/cotal/android/flagecall/CallSettingActivity$5;-><init>(Lcom/cotal/android/flagecall/CallSettingActivity;)V

    .line 193
    #v4=(Reference,Lcom/cotal/android/flagecall/CallSettingActivity$5;);
    invoke-virtual {v0, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 208
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v3

    invoke-virtual {v3}, Landroid/app/AlertDialog;->show()V

    .line 209
    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 55
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 56
    const v0, 0x7f030003

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->setContentView(I)V

    .line 57
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->findView()V

    .line 58
    const-string v0, "\u6765\u7535\u8bbe\u7f6e"

    #v0=(Reference,Ljava/lang/String;);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallSettingActivity;->setTitle(Ljava/lang/CharSequence;)V

    .line 59
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallSettingActivity;->setListener()V

    .line 60
    const-string v0, "1"

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->selectedVoice:Ljava/lang/String;

    .line 61
    return-void
.end method

.method protected onDestroy()V
    .locals 1

    .prologue
    .line 283
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    .line 284
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    #v0=(Reference,Landroid/media/MediaRecorder;);
    if-eqz v0, :cond_0

    .line 286
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v0}, Landroid/media/MediaRecorder;->stop()V

    .line 287
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    invoke-virtual {v0}, Landroid/media/MediaRecorder;->release()V

    .line 288
    const/4 v0, 0x0

    #v0=(Null);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallSettingActivity;->recorder:Landroid/media/MediaRecorder;

    .line 290
    :cond_0
    #v0=(Reference,Landroid/media/MediaRecorder;);
    return-void
.end method
