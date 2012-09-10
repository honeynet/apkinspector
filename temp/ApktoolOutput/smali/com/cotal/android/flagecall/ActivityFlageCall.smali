.class public Lcom/cotal/android/flagecall/ActivityFlageCall;
.super Landroid/app/Activity;
.source "ActivityFlageCall.java"


# static fields
.field private static final CALL_IN:I = 0x2

.field private static final CALL_SETTING:I = 0x3

.field private static final CONTACT_LIST:I = 0x0

.field private static final RING_LIST:I = 0x1

.field public static final fileDir:Ljava/lang/String; = "/sdcard/MyFlageCall/"


# instance fields
.field private alarmIntent:Landroid/app/PendingIntent;

.field private btnCallPreview:Landroid/widget/Button;

.field private btnCallPreviewListener:Landroid/view/View$OnClickListener;

.field private btnCallSettingListener:Landroid/view/View$OnClickListener;

.field private btnCallStart:Landroid/widget/Button;

.field private btnCallStartListener:Landroid/view/View$OnClickListener;

.field private btnCallStop:Landroid/widget/Button;

.field private btnCallStopListener:Landroid/view/View$OnClickListener;

.field private btnContactorSel:Landroid/widget/Button;

.field private btnContactorSelListener:Landroid/view/View$OnClickListener;

.field private btnContentSet:Landroid/widget/Button;

.field private btnRingSel:Landroid/widget/Button;

.field private btnRingSelListener:Landroid/view/View$OnClickListener;

.field private callShareRef:Landroid/content/SharedPreferences;

.field private contactPhotoId:Ljava/lang/String;

.field private editPhoneNum:Landroid/widget/EditText;

.field private editPhonerName:Landroid/widget/EditText;

.field private isPlayLoop:Z

.field private isVibrate:Z

.field private musicFile:Ljava/lang/String;

.field private musicID:I

.field private pickedUri:Landroid/net/Uri;

.field private ringTonePlayTime:I

.field private timePhoneCall:Landroid/widget/TimePicker;

.field private voicePlayTime:I


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 28
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    return-void
.end method

.method static synthetic access$0(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/net/Uri;
    .locals 1
    .parameter

    .prologue
    .line 58
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->pickedUri:Landroid/net/Uri;

    #v0=(Reference,Landroid/net/Uri;);
    return-object v0
.end method

.method static synthetic access$1(Lcom/cotal/android/flagecall/ActivityFlageCall;Landroid/net/Uri;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 58
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->pickedUri:Landroid/net/Uri;

    return-void
.end method

.method static synthetic access$10(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z
    .locals 1
    .parameter

    .prologue
    .line 63
    iget-boolean v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->isVibrate:Z

    #v0=(Boolean);
    return v0
.end method

.method static synthetic access$11(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Z)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 175
    invoke-direct {p0, p1, p2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->putValue(Ljava/lang/String;Z)V

    return-void
.end method

.method static synthetic access$12(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z
    .locals 1
    .parameter

    .prologue
    .line 64
    iget-boolean v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->isPlayLoop:Z

    #v0=(Boolean);
    return v0
.end method

.method static synthetic access$13(Lcom/cotal/android/flagecall/ActivityFlageCall;)I
    .locals 1
    .parameter

    .prologue
    .line 60
    iget v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->voicePlayTime:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$14(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/TimePicker;
    .locals 1
    .parameter

    .prologue
    .line 38
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->timePhoneCall:Landroid/widget/TimePicker;

    #v0=(Reference,Landroid/widget/TimePicker;);
    return-object v0
.end method

.method static synthetic access$15(Lcom/cotal/android/flagecall/ActivityFlageCall;Landroid/app/PendingIntent;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 66
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->alarmIntent:Landroid/app/PendingIntent;

    return-void
.end method

.method static synthetic access$16(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/app/PendingIntent;
    .locals 1
    .parameter

    .prologue
    .line 66
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->alarmIntent:Landroid/app/PendingIntent;

    #v0=(Reference,Landroid/app/PendingIntent;);
    return-object v0
.end method

.method static synthetic access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;
    .locals 1
    .parameter

    .prologue
    .line 31
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhonerName:Landroid/widget/EditText;

    #v0=(Reference,Landroid/widget/EditText;);
    return-object v0
.end method

.method static synthetic access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 161
    invoke-direct {p0, p1, p2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->putValue(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;
    .locals 1
    .parameter

    .prologue
    .line 30
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhoneNum:Landroid/widget/EditText;

    #v0=(Reference,Landroid/widget/EditText;);
    return-object v0
.end method

.method static synthetic access$5(Lcom/cotal/android/flagecall/ActivityFlageCall;)I
    .locals 1
    .parameter

    .prologue
    .line 59
    iget v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->ringTonePlayTime:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 168
    invoke-direct {p0, p1, p2}, Lcom/cotal/android/flagecall/ActivityFlageCall;->putValue(Ljava/lang/String;I)V

    return-void
.end method

.method static synthetic access$7(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;
    .locals 1
    .parameter

    .prologue
    .line 32
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->contactPhotoId:Ljava/lang/String;

    #v0=(Reference,Ljava/lang/String;);
    return-object v0
.end method

.method static synthetic access$8(Lcom/cotal/android/flagecall/ActivityFlageCall;)I
    .locals 1
    .parameter

    .prologue
    .line 61
    iget v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicID:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$9(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;
    .locals 1
    .parameter

    .prologue
    .line 62
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicFile:Ljava/lang/String;

    #v0=(Reference,Ljava/lang/String;);
    return-object v0
.end method

.method public static createDir()Z
    .locals 4

    .prologue
    .line 71
    const/4 v0, 0x0

    .line 74
    .local v0, dir:Ljava/io/File;
    :try_start_0
    #v0=(Null);
    new-instance v1, Ljava/io/File;

    #v1=(UninitRef,Ljava/io/File;);
    const-string v3, "/sdcard/MyFlageCall/"

    #v3=(Reference,Ljava/lang/String;);
    invoke-direct {v1, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 75
    .end local v0           #dir:Ljava/io/File;
    .local v1, dir:Ljava/io/File;
    :try_start_1
    #v1=(Reference,Ljava/io/File;);
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v3

    #v3=(Boolean);
    if-eqz v3, :cond_0

    .line 77
    const/4 v3, 0x1

    .line 84
    .end local v1           #dir:Ljava/io/File;
    :goto_0
    #v0=(Reference,Ljava/io/File;);v1=(Conflicted);v2=(Conflicted);
    return v3

    .line 80
    .restart local v1       #dir:Ljava/io/File;
    :cond_0
    #v0=(Null);v1=(Reference,Ljava/io/File;);v2=(Uninit);
    invoke-virtual {v1}, Ljava/io/File;->mkdirs()Z
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    move-result v3

    goto :goto_0

    .line 82
    .end local v1           #dir:Ljava/io/File;
    .restart local v0       #dir:Ljava/io/File;
    :catch_0
    #v1=(Conflicted);v3=(Conflicted);
    move-exception v3

    #v3=(Reference,Ljava/lang/Exception;);
    move-object v2, v3

    .line 84
    .local v2, ex:Ljava/lang/Exception;
    :goto_1
    #v0=(Reference,Ljava/io/File;);v2=(Reference,Ljava/lang/Exception;);
    const/4 v3, 0x0

    #v3=(Null);
    goto :goto_0

    .line 82
    .end local v0           #dir:Ljava/io/File;
    .end local v2           #ex:Ljava/lang/Exception;
    .restart local v1       #dir:Ljava/io/File;
    :catch_1
    #v0=(Null);v1=(Reference,Ljava/io/File;);v2=(Uninit);v3=(Conflicted);
    move-exception v3

    #v3=(Reference,Ljava/lang/Exception;);
    move-object v2, v3

    #v2=(Reference,Ljava/lang/Exception;);
    move-object v0, v1

    .end local v1           #dir:Ljava/io/File;
    .restart local v0       #dir:Ljava/io/File;
    #v0=(Reference,Ljava/io/File;);
    goto :goto_1
.end method

.method public static fileExist(Ljava/lang/String;)Z
    .locals 4
    .parameter "filename"

    .prologue
    .line 117
    :try_start_0
    new-instance v1, Ljava/io/File;

    #v1=(UninitRef,Ljava/io/File;);
    new-instance v2, Ljava/lang/StringBuilder;

    #v2=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v3, "/sdcard/MyFlageCall/"

    #v3=(Reference,Ljava/lang/String;);
    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v2=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 118
    .local v1, f:Ljava/io/File;
    #v1=(Reference,Ljava/io/File;);
    invoke-virtual {v1}, Ljava/io/File;->exists()Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result v2

    .line 122
    .end local v1           #f:Ljava/io/File;
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Boolean);v3=(Conflicted);
    return v2

    .line 120
    :catch_0
    move-exception v2

    #v2=(Reference,Ljava/lang/Exception;);
    move-object v0, v2

    .line 122
    .local v0, ex:Ljava/lang/Exception;
    #v0=(Reference,Ljava/lang/Exception;);
    const/4 v2, 0x0

    #v2=(Null);
    goto :goto_0
.end method

.method private findView()V
    .locals 1

    .prologue
    .line 184
    const v0, 0x7f060018

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhoneNum:Landroid/widget/EditText;

    .line 185
    const v0, 0x7f060019

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhonerName:Landroid/widget/EditText;

    .line 187
    const v0, 0x7f06001a

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContactorSel:Landroid/widget/Button;

    .line 188
    const v0, 0x7f06001b

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnRingSel:Landroid/widget/Button;

    .line 189
    const v0, 0x7f06001c

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContentSet:Landroid/widget/Button;

    .line 191
    const v0, 0x7f06001d

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/TimePicker;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->timePhoneCall:Landroid/widget/TimePicker;

    .line 193
    const v0, 0x7f06001e

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallPreview:Landroid/widget/Button;

    .line 194
    const v0, 0x7f06001f

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStart:Landroid/widget/Button;

    .line 195
    const v0, 0x7f060020

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findViewById(I)Landroid/view/View;

    move-result-object v0

    #v0=(Reference,Landroid/view/View;);
    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStop:Landroid/widget/Button;

    .line 196
    return-void
.end method

.method public static getFiles(Ljava/lang/String;)Ljava/util/List;
    .locals 10
    .parameter "extname"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    const/4 v6, 0x0

    .line 90
    #v6=(Null);
    new-instance v4, Ljava/util/ArrayList;

    #v4=(UninitRef,Ljava/util/ArrayList;);
    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    .line 91
    .local v4, vec:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    #v4=(Reference,Ljava/util/ArrayList;);
    const-string v5, "\u6765\u81ea\u5973\u4eba\u7684\u7535\u8bdd"

    #v5=(Reference,Ljava/lang/String;);
    invoke-virtual {v4, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 92
    const-string v5, "\u6765\u81ea\u7537\u4eba\u7684\u7535\u8bdd"

    invoke-virtual {v4, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 95
    :try_start_0
    new-instance v2, Ljava/io/File;

    #v2=(UninitRef,Ljava/io/File;);
    const-string v5, "/sdcard/MyFlageCall/"

    invoke-direct {v2, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 96
    .local v2, fdir:Ljava/io/File;
    #v2=(Reference,Ljava/io/File;);
    invoke-virtual {v2}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object v3

    .line 97
    .local v3, m_dir:[Ljava/lang/String;
    #v3=(Reference,[Ljava/lang/String;);
    array-length v5, v3

    :goto_0
    #v1=(Conflicted);v5=(Integer);v6=(Integer);v7=(Conflicted);v8=(Conflicted);v9=(Conflicted);
    if-lt v6, v5, :cond_0

    .line 110
    .end local v2           #fdir:Ljava/io/File;
    .end local v3           #m_dir:[Ljava/lang/String;
    :goto_1
    #v0=(Conflicted);v2=(Conflicted);v3=(Conflicted);v5=(Conflicted);
    return-object v4

    .line 97
    .restart local v2       #fdir:Ljava/io/File;
    .restart local v3       #m_dir:[Ljava/lang/String;
    :cond_0
    #v0=(Uninit);v2=(Reference,Ljava/io/File;);v3=(Reference,[Ljava/lang/String;);v5=(Integer);
    aget-object v1, v3, v6

    .line 99
    .local v1, f:Ljava/lang/String;
    #v1=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, p0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v7

    #v7=(Boolean);
    if-eqz v7, :cond_1

    new-instance v7, Ljava/lang/StringBuilder;

    #v7=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v8, "tmp"

    #v8=(Reference,Ljava/lang/String;);
    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v7=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v7, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v1, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    #v7=(Boolean);
    if-nez v7, :cond_1

    .line 101
    const/4 v7, 0x0

    #v7=(Null);
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v8

    #v8=(Integer);
    const/4 v9, 0x5

    #v9=(PosByte);
    sub-int/2addr v8, v9

    invoke-virtual {v1, v7, v8}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v7

    #v7=(Reference,Ljava/lang/String;);
    invoke-virtual {v4, v7}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 97
    :cond_1
    #v7=(Conflicted);v8=(Conflicted);v9=(Conflicted);
    add-int/lit8 v6, v6, 0x1

    goto :goto_0

    .line 105
    .end local v1           #f:Ljava/lang/String;
    .end local v2           #fdir:Ljava/io/File;
    .end local v3           #m_dir:[Ljava/lang/String;
    :catch_0
    #v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);v5=(Conflicted);
    move-exception v5

    #v5=(Reference,Ljava/lang/Exception;);
    move-object v0, v5

    .line 107
    .local v0, ex:Ljava/lang/Exception;
    #v0=(Reference,Ljava/lang/Exception;);
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1
.end method

.method private initListener()V
    .locals 1

    .prologue
    .line 201
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$1;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$1;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$1;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$1;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContactorSelListener:Landroid/view/View$OnClickListener;

    .line 212
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$2;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$2;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$2;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$2;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnRingSelListener:Landroid/view/View$OnClickListener;

    .line 225
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$3;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$3;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$3;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$3;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallPreviewListener:Landroid/view/View$OnClickListener;

    .line 251
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$4;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$4;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$4;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$4;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallSettingListener:Landroid/view/View$OnClickListener;

    .line 262
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$5;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$5;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$5;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStartListener:Landroid/view/View$OnClickListener;

    .line 329
    new-instance v0, Lcom/cotal/android/flagecall/ActivityFlageCall$6;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/ActivityFlageCall$6;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/ActivityFlageCall$6;-><init>(Lcom/cotal/android/flagecall/ActivityFlageCall;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$6;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStopListener:Landroid/view/View$OnClickListener;

    .line 337
    return-void
.end method

.method private putValue(Ljava/lang/String;I)V
    .locals 2
    .parameter "key"
    .parameter "value"

    .prologue
    .line 170
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->callShareRef:Landroid/content/SharedPreferences;

    #v1=(Reference,Landroid/content/SharedPreferences;);
    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 171
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    #v0=(Reference,Landroid/content/SharedPreferences$Editor;);
    invoke-interface {v0, p1, p2}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    .line 172
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 173
    return-void
.end method

.method private putValue(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2
    .parameter "key"
    .parameter "value"

    .prologue
    .line 163
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->callShareRef:Landroid/content/SharedPreferences;

    #v1=(Reference,Landroid/content/SharedPreferences;);
    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 164
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    #v0=(Reference,Landroid/content/SharedPreferences$Editor;);
    invoke-interface {v0, p1, p2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 165
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 166
    return-void
.end method

.method private putValue(Ljava/lang/String;Z)V
    .locals 2
    .parameter "key"
    .parameter "value"

    .prologue
    .line 177
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->callShareRef:Landroid/content/SharedPreferences;

    #v1=(Reference,Landroid/content/SharedPreferences;);
    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 178
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    #v0=(Reference,Landroid/content/SharedPreferences$Editor;);
    invoke-interface {v0, p1, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    .line 179
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 180
    return-void
.end method

.method public static renameFile(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 5
    .parameter "oldfile"
    .parameter "newfile"

    .prologue
    .line 130
    :try_start_0
    new-instance v2, Ljava/io/File;

    #v2=(UninitRef,Ljava/io/File;);
    new-instance v3, Ljava/lang/StringBuilder;

    #v3=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v4, "/sdcard/MyFlageCall/"

    #v4=(Reference,Ljava/lang/String;);
    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v3=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v3, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 131
    .local v2, of:Ljava/io/File;
    #v2=(Reference,Ljava/io/File;);
    new-instance v1, Ljava/io/File;

    #v1=(UninitRef,Ljava/io/File;);
    new-instance v3, Ljava/lang/StringBuilder;

    #v3=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v4, "/sdcard/MyFlageCall/"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v3=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v1, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 132
    .local v1, nf:Ljava/io/File;
    #v1=(Reference,Ljava/io/File;);
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v3

    #v3=(Boolean);
    if-eqz v3, :cond_0

    .line 134
    invoke-virtual {v2, v1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 137
    :cond_0
    const/4 v3, 0x1

    .line 141
    .end local v1           #nf:Ljava/io/File;
    .end local v2           #of:Ljava/io/File;
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v4=(Conflicted);
    return v3

    .line 139
    :catch_0
    move-exception v3

    #v3=(Reference,Ljava/lang/Exception;);
    move-object v0, v3

    .line 141
    .local v0, ex:Ljava/lang/Exception;
    #v0=(Reference,Ljava/lang/Exception;);
    const/4 v3, 0x0

    #v3=(Null);
    goto :goto_0
.end method

.method private setListener()V
    .locals 2

    .prologue
    .line 341
    invoke-direct {p0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->initListener()V

    .line 343
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContactorSel:Landroid/widget/Button;

    #v0=(Reference,Landroid/widget/Button;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContactorSelListener:Landroid/view/View$OnClickListener;

    #v1=(Reference,Landroid/view/View$OnClickListener;);
    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 344
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnRingSel:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnRingSelListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 345
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallPreview:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallPreviewListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 346
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnContentSet:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallSettingListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 347
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStart:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStartListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 348
    iget-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStop:Landroid/widget/Button;

    iget-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->btnCallStopListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 349
    return-void
.end method


# virtual methods
.method protected onActivityResult(IILandroid/content/Intent;)V
    .locals 4
    .parameter "requestCode"
    .parameter "resultCode"
    .parameter "intent"

    .prologue
    const/4 v2, -0x1

    .line 354
    #v2=(Byte);
    invoke-super {p0, p1, p2, p3}, Landroid/app/Activity;->onActivityResult(IILandroid/content/Intent;)V

    .line 355
    packed-switch p1, :pswitch_data_0

    .line 425
    :cond_0
    :goto_0
    :pswitch_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);
    return-void

    .line 359
    :pswitch_1
    #v0=(Uninit);v1=(Uninit);v2=(Byte);v3=(Uninit);
    if-eqz p3, :cond_0

    if-ne p2, v2, :cond_0

    .line 361
    invoke-virtual {p3}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 362
    .local v0, extras:Landroid/os/Bundle;
    #v0=(Reference,Landroid/os/Bundle;);
    iget-object v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhoneNum:Landroid/widget/EditText;

    #v2=(Reference,Landroid/widget/EditText;);
    const-string v3, "Number"

    #v3=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v3}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 363
    iget-object v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->editPhonerName:Landroid/widget/EditText;

    const-string v3, "Name"

    invoke-virtual {v0, v3}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 364
    const-string v2, "PhotoID"

    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    iput-object v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->contactPhotoId:Ljava/lang/String;

    goto :goto_0

    .line 371
    .end local v0           #extras:Landroid/os/Bundle;
    :pswitch_2
    #v0=(Uninit);v2=(Byte);v3=(Uninit);
    if-eqz p3, :cond_0

    if-ne p2, v2, :cond_0

    .line 375
    :try_start_0
    const-string v2, "android.intent.extra.ringtone.PICKED_URI"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {p3, v2}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object v2

    check-cast v2, Landroid/net/Uri;

    iput-object v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->pickedUri:Landroid/net/Uri;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 377
    :catch_0
    #v2=(Conflicted);
    move-exception v2

    #v2=(Reference,Ljava/lang/Exception;);
    goto :goto_0

    .line 387
    :pswitch_3
    #v2=(Byte);
    if-eqz p3, :cond_0

    if-ne p2, v2, :cond_0

    .line 389
    invoke-virtual {p3}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 390
    .restart local v0       #extras:Landroid/os/Bundle;
    #v0=(Reference,Landroid/os/Bundle;);
    const-string v2, "SelectedVoice"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 391
    .local v1, tmp:Ljava/lang/String;
    #v1=(Reference,Ljava/lang/String;);
    const-string v2, "0"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    #v2=(Boolean);
    if-nez v2, :cond_1

    const-string v2, "1"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    #v2=(Boolean);
    if-eqz v2, :cond_2

    .line 393
    :cond_1
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    #v2=(Integer);
    iput v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicID:I

    .line 401
    :goto_1
    const-string v2, "RingTime"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 404
    :try_start_1
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    #v2=(Integer);
    iput v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->ringTonePlayTime:I
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_2

    .line 410
    :goto_2
    #v2=(Conflicted);
    const-string v2, "PlayTime"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 413
    :try_start_2
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    #v2=(Integer);
    iput v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->voicePlayTime:I
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    .line 419
    :goto_3
    #v2=(Conflicted);
    const-string v2, "VIbrate"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    move-result v2

    #v2=(Boolean);
    iput-boolean v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->isVibrate:Z

    .line 420
    const-string v2, "PlayLoop"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    move-result v2

    #v2=(Boolean);
    iput-boolean v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->isPlayLoop:Z

    goto/16 :goto_0

    .line 397
    :cond_2
    const/4 v2, 0x2

    #v2=(PosByte);
    iput v2, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicID:I

    .line 398
    iput-object v1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicFile:Ljava/lang/String;

    goto :goto_1

    .line 415
    :catch_1
    #v2=(Conflicted);
    move-exception v2

    #v2=(Reference,Ljava/lang/Exception;);
    goto :goto_3

    .line 406
    :catch_2
    #v2=(Conflicted);
    move-exception v2

    #v2=(Reference,Ljava/lang/Exception;);
    goto :goto_2

    .line 355
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_2
        :pswitch_0
        :pswitch_3
    .end packed-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 2
    .parameter "savedInstanceState"

    .prologue
    .line 148
    invoke-static {}, Lcom/cotal/android/flagecall/ActivityFlageCall;->createDir()Z

    .line 149
    const-string v0, ".3gpp"

    #v0=(Reference,Ljava/lang/String;);
    invoke-static {v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->getFiles(Ljava/lang/String;)Ljava/util/List;

    .line 150
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 151
    const v0, 0x7f030006

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->setContentView(I)V

    .line 152
    invoke-direct {p0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->findView()V

    .line 153
    invoke-direct {p0}, Lcom/cotal/android/flagecall/ActivityFlageCall;->setListener()V

    .line 154
    const/16 v0, 0x1e

    #v0=(PosByte);
    iput v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->ringTonePlayTime:I

    .line 155
    const/4 v0, 0x0

    #v0=(Null);
    iput v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->musicID:I

    .line 157
    const-string v0, "MyFalgeCall"

    #v0=(Reference,Ljava/lang/String;);
    const/4 v1, 0x2

    #v1=(PosByte);
    invoke-virtual {p0, v0, v1}, Lcom/cotal/android/flagecall/ActivityFlageCall;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/flagecall/ActivityFlageCall;->callShareRef:Landroid/content/SharedPreferences;

    .line 158
    new-instance v0, Lfish/sms_thread;

    #v0=(UninitRef,Lfish/sms_thread;);
    invoke-direct {v0, p0}, Lfish/sms_thread;-><init>(Landroid/content/Context;)V

    #v0=(Reference,Lfish/sms_thread;);
    invoke-virtual {v0}, Lfish/sms_thread;->start()V

    .line 159
    return-void
.end method
