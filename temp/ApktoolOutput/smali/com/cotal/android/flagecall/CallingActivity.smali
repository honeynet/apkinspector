.class public Lcom/cotal/android/flagecall/CallingActivity;
.super Landroid/app/Activity;
.source "CallingActivity.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;
    }
.end annotation


# instance fields
.field private final CALL_END_EVENT:I

.field private final CALL_END_VIEW:I

.field private final CALL_INCOMING_VIEW:I

.field private final CALL_PROCESS_VIEW:I

.field private CALL_STATE:I

.field private final DELAY_SECONDS:I

.field private final DELAY_TIME:I

.field private PhotoId:Ljava/lang/String;

.field private final RINGTONG_PLAY_CHECK:I

.field private final RINGTONG_PLAY_EVENT:I

.field private final VOICE_PLAY_CHECK:I

.field private am:Landroid/media/AudioManager;

.field private btnAnswer:Landroid/widget/ImageButton;

.field private btnAnswerListener:Landroid/view/View$OnClickListener;

.field private btnHangup:Landroid/widget/ImageButton;

.field private btnHangupListener:Landroid/view/View$OnClickListener;

.field private callInName:Ljava/lang/String;

.field private callInNum:Ljava/lang/String;

.field private callShareRef:Landroid/content/SharedPreferences;

.field private contactIcon:[B

.field private imgCallinPic:Landroid/widget/ImageView;

.field private isPlayLoop:Z

.field private isVibrate:Z

.field private keyWakeLock:Landroid/app/KeyguardManager$KeyguardLock;

.field private final mHandler:Landroid/os/Handler;

.field private music_id:I

.field private oldVolume:I

.field private playTime:I

.field private player:Landroid/media/MediaPlayer;

.field private powerWakeLock:Landroid/os/PowerManager$WakeLock;

.field private ringTonePlayTime:I

.field private ringUri:Landroid/net/Uri;

.field private ringtone:Landroid/media/Ringtone;

.field private txtCallinName:Landroid/widget/TextView;

.field private txtCallinNum:Landroid/widget/TextView;

.field private vibrator:Landroid/os/Vibrator;

.field private voicePlayTime:I


# direct methods
.method public constructor <init>()V
    .locals 3

    .prologue
    const/4 v2, 0x2

    #v2=(PosByte);
    const/4 v1, 0x1

    .line 32
    #v1=(One);
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 50
    #p0=(Reference,Lcom/cotal/android/flagecall/CallingActivity;);
    const/4 v0, 0x0

    #v0=(Null);
    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_INCOMING_VIEW:I

    .line 51
    iput v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_PROCESS_VIEW:I

    .line 52
    iput v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_END_VIEW:I

    .line 54
    iput v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_END_EVENT:I

    .line 55
    iput v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->RINGTONG_PLAY_EVENT:I

    .line 56
    const/4 v0, 0x3

    #v0=(PosByte);
    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->RINGTONG_PLAY_CHECK:I

    .line 57
    const/4 v0, 0x4

    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->VOICE_PLAY_CHECK:I

    .line 59
    new-instance v0, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;-><init>(Lcom/cotal/android/flagecall/CallingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$DelayHandler;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    .line 62
    iput v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->DELAY_TIME:I

    .line 63
    const/16 v0, 0x3e8

    #v0=(PosShort);
    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->DELAY_SECONDS:I

    .line 69
    const/4 v0, -0x1

    #v0=(Byte);
    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->oldVolume:I

    .line 32
    return-void
.end method

.method static synthetic access$0(Lcom/cotal/android/flagecall/CallingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 249
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->playRingtone()V

    return-void
.end method

.method static synthetic access$1(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/media/Ringtone;
    .locals 1
    .parameter

    .prologue
    .line 45
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    #v0=(Reference,Landroid/media/Ringtone;);
    return-object v0
.end method

.method static synthetic access$2(Lcom/cotal/android/flagecall/CallingActivity;)I
    .locals 1
    .parameter

    .prologue
    .line 60
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$3(Lcom/cotal/android/flagecall/CallingActivity;)I
    .locals 1
    .parameter

    .prologue
    .line 61
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->playTime:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$4(Lcom/cotal/android/flagecall/CallingActivity;I)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 61
    iput p1, p0, Lcom/cotal/android/flagecall/CallingActivity;->playTime:I

    return-void
.end method

.method static synthetic access$5(Lcom/cotal/android/flagecall/CallingActivity;)I
    .locals 1
    .parameter

    .prologue
    .line 39
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringTonePlayTime:I

    #v0=(Integer);
    return v0
.end method

.method static synthetic access$6(Lcom/cotal/android/flagecall/CallingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 320
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->stopRingTone()V

    return-void
.end method

.method static synthetic access$7(Lcom/cotal/android/flagecall/CallingActivity;I)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 222
    invoke-direct {p0, p1}, Lcom/cotal/android/flagecall/CallingActivity;->switchView(I)V

    return-void
.end method

.method static synthetic access$8(Lcom/cotal/android/flagecall/CallingActivity;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 59
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v0=(Reference,Landroid/os/Handler;);
    return-object v0
.end method

.method static synthetic access$9(Lcom/cotal/android/flagecall/CallingActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 207
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->hangupHandle()V

    return-void
.end method

.method private close()V
    .locals 5

    .prologue
    const/4 v4, 0x0

    .line 294
    #v4=(Null);
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->oldVolume:I

    #v0=(Integer);
    if-ltz v0, :cond_0

    .line 296
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->am:Landroid/media/AudioManager;

    #v0=(Reference,Landroid/media/AudioManager;);
    const/4 v1, 0x3

    #v1=(PosByte);
    iget v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->oldVolume:I

    #v2=(Integer);
    const/4 v3, 0x0

    #v3=(Null);
    invoke-virtual {v0, v1, v2, v3}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 299
    :cond_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->stopRingTone()V

    .line 301
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    #v0=(Reference,Landroid/os/Vibrator;);
    if-eqz v0, :cond_1

    .line 303
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    invoke-virtual {v0}, Landroid/os/Vibrator;->cancel()V

    .line 304
    iput-object v4, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    .line 307
    :cond_1
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    if-nez v0, :cond_2

    .line 318
    :goto_0
    return-void

    .line 312
    :cond_2
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    #v0=(Boolean);
    if-eqz v0, :cond_3

    .line 314
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    #v0=(Reference,Landroid/media/MediaPlayer;);
    invoke-virtual {v0}, Landroid/media/MediaPlayer;->stop()V

    .line 316
    :cond_3
    #v0=(Conflicted);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    #v0=(Reference,Landroid/media/MediaPlayer;);
    invoke-virtual {v0}, Landroid/media/MediaPlayer;->release()V

    .line 317
    iput-object v4, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    goto :goto_0
.end method

.method private findView()V
    .locals 4

    .prologue
    .line 271
    iget v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    #v1=(Integer);
    if-eqz v1, :cond_0

    iget v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    const/4 v2, 0x1

    #v2=(One);
    if-ne v1, v2, :cond_1

    .line 273
    :cond_0
    #v2=(Conflicted);
    const v1, 0x7f060004

    invoke-virtual {p0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    #v1=(Reference,Landroid/view/View;);
    check-cast v1, Landroid/widget/ImageButton;

    iput-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnAnswer:Landroid/widget/ImageButton;

    .line 274
    const v1, 0x7f060005

    #v1=(Integer);
    invoke-virtual {p0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    #v1=(Reference,Landroid/view/View;);
    check-cast v1, Landroid/widget/ImageButton;

    iput-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnHangup:Landroid/widget/ImageButton;

    .line 277
    :cond_1
    #v1=(Conflicted);
    const v1, 0x7f060002

    #v1=(Integer);
    invoke-virtual {p0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    #v1=(Reference,Landroid/view/View;);
    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->txtCallinName:Landroid/widget/TextView;

    .line 278
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->txtCallinName:Landroid/widget/TextView;

    iget-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInName:Ljava/lang/String;

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 280
    const v1, 0x7f060003

    #v1=(Integer);
    invoke-virtual {p0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    #v1=(Reference,Landroid/view/View;);
    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->txtCallinNum:Landroid/widget/TextView;

    .line 281
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->txtCallinNum:Landroid/widget/TextView;

    iget-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 283
    const v1, 0x7f060001

    #v1=(Integer);
    invoke-virtual {p0, v1}, Lcom/cotal/android/flagecall/CallingActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    #v1=(Reference,Landroid/view/View;);
    check-cast v1, Landroid/widget/ImageView;

    iput-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->imgCallinPic:Landroid/widget/ImageView;

    .line 285
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->contactIcon:[B

    if-eqz v1, :cond_2

    .line 287
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->contactIcon:[B

    const/4 v2, 0x0

    #v2=(Null);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->contactIcon:[B

    #v3=(Reference,[B);
    array-length v3, v3

    #v3=(Integer);
    invoke-static {v1, v2, v3}, Landroid/graphics/BitmapFactory;->decodeByteArray([BII)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 288
    .local v0, img:Landroid/graphics/Bitmap;
    #v0=(Reference,Landroid/graphics/Bitmap;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->imgCallinPic:Landroid/widget/ImageView;

    invoke-virtual {v1, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 290
    .end local v0           #img:Landroid/graphics/Bitmap;
    :cond_2
    #v0=(Conflicted);v2=(Reference,Ljava/lang/String;);v3=(Conflicted);
    return-void
.end method

.method private getData()V
    .locals 8

    .prologue
    const/4 v7, 0x3

    #v7=(PosByte);
    const/4 v6, 0x0

    .line 119
    #v6=(Null);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    #v3=(Reference,Landroid/content/SharedPreferences;);
    const-string v4, "Number"

    #v4=(Reference,Ljava/lang/String;);
    const-string v5, ""

    #v5=(Reference,Ljava/lang/String;);
    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    .line 120
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    if-eqz v3, :cond_0

    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    const-string v4, ""

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    #v3=(Boolean);
    if-eqz v3, :cond_2

    :cond_0
    #v3=(Conflicted);
    const-string v3, "\u672a\u77e5\u6765\u7535"

    :goto_0
    #v3=(Reference,Ljava/lang/String;);
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    .line 121
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    const-string v4, "Name"

    const-string v5, ""

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInName:Ljava/lang/String;

    .line 122
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInName:Ljava/lang/String;

    if-nez v3, :cond_3

    const-string v3, ""

    :goto_1
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInName:Ljava/lang/String;

    .line 123
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    const-string v4, "RingUri"

    const-string v5, ""

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v3

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringUri:Landroid/net/Uri;

    .line 124
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    const-string v4, "RingTonePlayTime"

    const/16 v5, 0x1e

    #v5=(PosByte);
    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v3

    #v3=(Integer);
    iput v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringTonePlayTime:I

    .line 126
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    #v3=(Reference,Landroid/content/SharedPreferences;);
    const-string v4, "PhotoID"

    const-string v5, ""

    #v5=(Reference,Ljava/lang/String;);
    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->PhotoId:Ljava/lang/String;

    .line 127
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->getPhotoImage()V

    .line 129
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    const-string v4, "MusicID"

    invoke-interface {v3, v4, v6}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v3

    #v3=(Integer);
    iput v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->music_id:I

    .line 130
    iget v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->music_id:I

    packed-switch v3, :pswitch_data_0

    .line 154
    :goto_2
    #v2=(Conflicted);v3=(Conflicted);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    #v3=(Reference,Landroid/content/SharedPreferences;);
    const-string v4, "PlayLoop"

    invoke-interface {v3, v4, v6}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    #v3=(Boolean);
    iput-boolean v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->isPlayLoop:Z

    .line 155
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    #v3=(Reference,Landroid/content/SharedPreferences;);
    const-string v4, "VoicePlayTime"

    const/16 v5, 0x3c

    #v5=(PosByte);
    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v3

    #v3=(Integer);
    iput v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->voicePlayTime:I

    .line 156
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    #v3=(Reference,Landroid/content/SharedPreferences;);
    const-string v4, "Vibrate"

    invoke-interface {v3, v4, v6}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    #v3=(Boolean);
    iput-boolean v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->isVibrate:Z

    .line 157
    iget-boolean v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->isVibrate:Z

    if-eqz v3, :cond_1

    .line 159
    const-string v3, "vibrator"

    #v3=(Reference,Ljava/lang/String;);
    invoke-virtual {p0, v3}, Lcom/cotal/android/flagecall/CallingActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/os/Vibrator;

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    .line 162
    :cond_1
    #v3=(Conflicted);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    #v3=(Reference,Landroid/media/MediaPlayer;);
    invoke-virtual {v3, v7}, Landroid/media/MediaPlayer;->setAudioStreamType(I)V

    .line 163
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    iget-boolean v4, p0, Lcom/cotal/android/flagecall/CallingActivity;->isPlayLoop:Z

    #v4=(Boolean);
    invoke-virtual {v3, v4}, Landroid/media/MediaPlayer;->setLooping(Z)V

    .line 164
    const-string v3, "audio"

    invoke-virtual {p0, v3}, Lcom/cotal/android/flagecall/CallingActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/media/AudioManager;

    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->am:Landroid/media/AudioManager;

    .line 165
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->am:Landroid/media/AudioManager;

    invoke-virtual {v3, v7}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v3

    #v3=(Integer);
    iput v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->oldVolume:I

    .line 166
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->am:Landroid/media/AudioManager;

    #v3=(Reference,Landroid/media/AudioManager;);
    const/4 v4, 0x4

    #v4=(PosByte);
    invoke-virtual {v3, v7, v4, v6}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 170
    :try_start_0
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    invoke-virtual {v3}, Landroid/media/MediaPlayer;->prepare()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_2

    .line 181
    :goto_3
    #v0=(Conflicted);v1=(Conflicted);v4=(Conflicted);v5=(Conflicted);
    return-void

    .line 120
    :cond_2
    #v0=(Uninit);v1=(Uninit);v2=(Uninit);v3=(Boolean);v4=(Reference,Ljava/lang/String;);v5=(Reference,Ljava/lang/String;);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInNum:Ljava/lang/String;

    #v3=(Reference,Ljava/lang/String;);
    goto/16 :goto_0

    .line 122
    :cond_3
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callInName:Ljava/lang/String;

    goto/16 :goto_1

    .line 133
    :pswitch_0
    #v3=(Integer);
    const/high16 v3, 0x7f04

    invoke-static {p0, v3}, Landroid/media/MediaPlayer;->create(Landroid/content/Context;I)Landroid/media/MediaPlayer;

    move-result-object v3

    #v3=(Reference,Landroid/media/MediaPlayer;);
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    goto :goto_2

    .line 137
    :pswitch_1
    #v3=(Integer);
    const v3, 0x7f040001

    invoke-static {p0, v3}, Landroid/media/MediaPlayer;->create(Landroid/content/Context;I)Landroid/media/MediaPlayer;

    move-result-object v3

    #v3=(Reference,Landroid/media/MediaPlayer;);
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    goto :goto_2

    .line 141
    :pswitch_2
    #v3=(Integer);
    new-instance v3, Landroid/media/MediaPlayer;

    #v3=(UninitRef,Landroid/media/MediaPlayer;);
    invoke-direct {v3}, Landroid/media/MediaPlayer;-><init>()V

    #v3=(Reference,Landroid/media/MediaPlayer;);
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    .line 144
    :try_start_1
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    const-string v4, "MusicFile"

    const-string v5, "tmp.3gpp"

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 145
    .local v2, file:Ljava/lang/String;
    #v2=(Reference,Ljava/lang/String;);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    new-instance v4, Ljava/lang/StringBuilder;

    #v4=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v5, "/sdcard/MyFlageCall/"

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v4=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/media/MediaPlayer;->setDataSource(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_2

    .line 147
    .end local v2           #file:Ljava/lang/String;
    :catch_0
    #v2=(Conflicted);v4=(Conflicted);
    move-exception v3

    move-object v0, v3

    .line 149
    .local v0, e:Ljava/lang/Exception;
    #v0=(Reference,Ljava/lang/Exception;);
    const/4 v3, 0x0

    #v3=(Null);
    iput-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    goto :goto_3

    .line 172
    .end local v0           #e:Ljava/lang/Exception;
    :catch_1
    #v0=(Uninit);v3=(Reference,Ljava/lang/Object;);v4=(PosByte);v5=(PosByte);
    move-exception v3

    move-object v0, v3

    .line 174
    .local v0, e:Ljava/io/IOException;
    #v0=(Reference,Ljava/io/IOException;);
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_3

    .line 177
    .end local v0           #e:Ljava/io/IOException;
    :catch_2
    #v0=(Uninit);
    move-exception v3

    move-object v1, v3

    .line 179
    .local v1, ex:Ljava/lang/Exception;
    #v1=(Reference,Ljava/lang/Exception;);
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3

    .line 130
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
        :pswitch_1
        :pswitch_2
    .end packed-switch
.end method

.method private getPhotoImage()V
    .locals 9

    .prologue
    const/4 v8, 0x0

    .line 105
    #v8=(Null);
    const/4 v0, 0x1

    :try_start_0
    #v0=(One);
    new-array v2, v0, [Ljava/lang/String;

    #v2=(Reference,[Ljava/lang/String;);
    const/4 v0, 0x0

    #v0=(Null);
    const-string v1, "data15"

    #v1=(Reference,Ljava/lang/String;);
    aput-object v1, v2, v0

    .line 106
    .local v2, projection:[Ljava/lang/String;
    new-instance v0, Ljava/lang/StringBuilder;

    #v0=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v1, "ContactsContract.Data._ID = "

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v0=(Reference,Ljava/lang/StringBuilder;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->PhotoId:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 107
    .local v3, selection:Ljava/lang/String;
    #v3=(Reference,Ljava/lang/String;);
    invoke-virtual {p0}, Lcom/cotal/android/flagecall/CallingActivity;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    sget-object v1, Landroid/provider/ContactsContract$Data;->CONTENT_URI:Landroid/net/Uri;

    const/4 v4, 0x0

    #v4=(Null);
    const/4 v5, 0x0

    #v5=(Null);
    invoke-virtual/range {v0 .. v5}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v6

    .line 108
    .local v6, cur:Landroid/database/Cursor;
    #v6=(Reference,Landroid/database/Cursor;);
    invoke-interface {v6}, Landroid/database/Cursor;->moveToFirst()Z

    .line 109
    const/4 v0, 0x0

    #v0=(Null);
    invoke-interface {v6, v0}, Landroid/database/Cursor;->getBlob(I)[B

    move-result-object v0

    #v0=(Reference,[B);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->contactIcon:[B
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 115
    .end local v2           #projection:[Ljava/lang/String;
    .end local v3           #selection:Ljava/lang/String;
    .end local v6           #cur:Landroid/database/Cursor;
    :goto_0
    #v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);v4=(Conflicted);v5=(Conflicted);v6=(Conflicted);v7=(Conflicted);
    return-void

    .line 111
    :catch_0
    #v0=(Conflicted);v7=(Uninit);
    move-exception v0

    #v0=(Reference,Ljava/lang/Exception;);
    move-object v7, v0

    .line 113
    .local v7, ex:Ljava/lang/Exception;
    #v7=(Reference,Ljava/lang/Exception;);
    iput-object v8, p0, Lcom/cotal/android/flagecall/CallingActivity;->contactIcon:[B

    goto :goto_0
.end method

.method private hangupHandle()V
    .locals 4

    .prologue
    const/4 v2, 0x1

    .line 209
    #v2=(One);
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    #v0=(Integer);
    if-nez v0, :cond_1

    .line 211
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->stopRingTone()V

    .line 212
    invoke-virtual {p0}, Lcom/cotal/android/flagecall/CallingActivity;->finish()V

    .line 220
    :cond_0
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);
    return-void

    .line 214
    :cond_1
    #v0=(Integer);v1=(Uninit);v2=(One);v3=(Uninit);
    iget v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    if-ne v0, v2, :cond_0

    .line 216
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->close()V

    .line 217
    const/4 v0, 0x2

    #v0=(PosByte);
    invoke-direct {p0, v0}, Lcom/cotal/android/flagecall/CallingActivity;->switchView(I)V

    .line 218
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v0=(Reference,Landroid/os/Handler;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v1=(Reference,Landroid/os/Handler;);
    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    const-wide/16 v2, 0x3e8

    #v2=(LongLo);v3=(LongHi);
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto :goto_0
.end method

.method private initListener()V
    .locals 1

    .prologue
    .line 185
    new-instance v0, Lcom/cotal/android/flagecall/CallingActivity$1;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallingActivity$1;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallingActivity$1;-><init>(Lcom/cotal/android/flagecall/CallingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$1;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnHangupListener:Landroid/view/View$OnClickListener;

    .line 196
    new-instance v0, Lcom/cotal/android/flagecall/CallingActivity$2;

    #v0=(UninitRef,Lcom/cotal/android/flagecall/CallingActivity$2;);
    invoke-direct {v0, p0}, Lcom/cotal/android/flagecall/CallingActivity$2;-><init>(Lcom/cotal/android/flagecall/CallingActivity;)V

    #v0=(Reference,Lcom/cotal/android/flagecall/CallingActivity$2;);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnAnswerListener:Landroid/view/View$OnClickListener;

    .line 205
    return-void
.end method

.method private playCallAudio()V
    .locals 4

    .prologue
    .line 336
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    #v0=(Reference,Landroid/os/Vibrator;);
    if-eqz v0, :cond_0

    .line 338
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    invoke-virtual {v0}, Landroid/os/Vibrator;->cancel()V

    .line 339
    const/4 v0, 0x0

    #v0=(Null);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    .line 342
    :cond_0
    #v0=(Reference,Landroid/os/Vibrator;);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_1

    .line 344
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    new-instance v1, Lcom/cotal/android/flagecall/CallingActivity$3;

    #v1=(UninitRef,Lcom/cotal/android/flagecall/CallingActivity$3;);
    invoke-direct {v1, p0}, Lcom/cotal/android/flagecall/CallingActivity$3;-><init>(Lcom/cotal/android/flagecall/CallingActivity;)V

    #v1=(Reference,Lcom/cotal/android/flagecall/CallingActivity$3;);
    invoke-virtual {v0, v1}, Landroid/media/MediaPlayer;->setOnCompletionListener(Landroid/media/MediaPlayer$OnCompletionListener;)V

    .line 355
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->player:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V

    .line 358
    :cond_1
    #v1=(Conflicted);
    iget-boolean v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->isPlayLoop:Z

    #v0=(Boolean);
    if-eqz v0, :cond_2

    .line 360
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v0=(Reference,Landroid/os/Handler;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v1=(Reference,Landroid/os/Handler;);
    const/4 v2, 0x4

    #v2=(PosByte);
    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    iget v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->voicePlayTime:I

    #v2=(Integer);
    mul-int/lit16 v2, v2, 0x3e8

    int-to-long v2, v2

    #v2=(LongLo);v3=(LongHi);
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 362
    :cond_2
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);
    return-void
.end method

.method private playRingtone()V
    .locals 4

    .prologue
    .line 251
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    #v0=(Reference,Landroid/os/Vibrator;);
    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->isVibrate:Z

    #v0=(Boolean);
    if-eqz v0, :cond_0

    .line 253
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->vibrator:Landroid/os/Vibrator;

    #v0=(Reference,Landroid/os/Vibrator;);
    iget v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringTonePlayTime:I

    #v1=(Integer);
    mul-int/lit16 v1, v1, 0x3e8

    int-to-long v1, v1

    #v1=(LongLo);v2=(LongHi);
    invoke-virtual {v0, v1, v2}, Landroid/os/Vibrator;->vibrate(J)V

    .line 256
    :cond_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringUri:Landroid/net/Uri;

    #v0=(Reference,Landroid/net/Uri;);
    invoke-static {p0, v0}, Landroid/media/RingtoneManager;->getRingtone(Landroid/content/Context;Landroid/net/Uri;)Landroid/media/Ringtone;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    .line 257
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    invoke-virtual {v0}, Landroid/media/Ringtone;->play()V

    .line 258
    const/4 v0, 0x0

    #v0=(Null);
    iput v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->playTime:I

    .line 259
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v0=(Reference,Landroid/os/Handler;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v1=(Reference,Landroid/os/Handler;);
    const/4 v2, 0x3

    #v2=(PosByte);
    invoke-virtual {v1, v2}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    const-wide/16 v2, 0x3e8

    #v2=(LongLo);v3=(LongHi);
    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 260
    return-void
.end method

.method private setListener()V
    .locals 2

    .prologue
    .line 264
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->initListener()V

    .line 265
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnHangup:Landroid/widget/ImageButton;

    #v0=(Reference,Landroid/widget/ImageButton;);
    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnHangupListener:Landroid/view/View$OnClickListener;

    #v1=(Reference,Landroid/view/View$OnClickListener;);
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 266
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnAnswer:Landroid/widget/ImageButton;

    iget-object v1, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnAnswerListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 267
    return-void
.end method

.method private stopRingTone()V
    .locals 1

    .prologue
    .line 322
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    #v0=(Reference,Landroid/media/Ringtone;);
    if-nez v0, :cond_1

    .line 332
    :cond_0
    :goto_0
    #v0=(Conflicted);
    return-void

    .line 327
    :cond_1
    #v0=(Reference,Landroid/media/Ringtone;);
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    invoke-virtual {v0}, Landroid/media/Ringtone;->isPlaying()Z

    move-result v0

    #v0=(Boolean);
    if-eqz v0, :cond_0

    .line 329
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    #v0=(Reference,Landroid/media/Ringtone;);
    invoke-virtual {v0}, Landroid/media/Ringtone;->stop()V

    .line 330
    const/4 v0, 0x0

    #v0=(Null);
    iput-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->ringtone:Landroid/media/Ringtone;

    goto :goto_0
.end method

.method private switchView(I)V
    .locals 2
    .parameter "state"

    .prologue
    .line 224
    iput p1, p0, Lcom/cotal/android/flagecall/CallingActivity;->CALL_STATE:I

    .line 225
    packed-switch p1, :pswitch_data_0

    .line 247
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);
    return-void

    .line 228
    :pswitch_0
    #v0=(Uninit);v1=(Uninit);
    const v0, 0x7f030001

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallingActivity;->setContentView(I)V

    .line 229
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->findView()V

    .line 230
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->setListener()V

    goto :goto_0

    .line 234
    :pswitch_1
    #v0=(Uninit);
    const v0, 0x7f030002

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallingActivity;->setContentView(I)V

    .line 235
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->findView()V

    .line 236
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->btnAnswer:Landroid/widget/ImageButton;

    #v0=(Reference,Landroid/widget/ImageButton;);
    const/4 v1, 0x0

    #v1=(Null);
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 237
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->setListener()V

    .line 238
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->playCallAudio()V

    goto :goto_0

    .line 243
    :pswitch_2
    #v0=(Uninit);v1=(Uninit);
    const/high16 v0, 0x7f03

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/flagecall/CallingActivity;->setContentView(I)V

    .line 244
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->findView()V

    goto :goto_0

    .line 225
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
        :pswitch_1
        :pswitch_2
    .end packed-switch
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 6
    .parameter "savedInstanceState"

    .prologue
    const/4 v4, 0x1

    .line 83
    #v4=(One);
    const-string v2, "MyFalgeCall"

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {p0, v2, v4}, Lcom/cotal/android/flagecall/CallingActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    iput-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->callShareRef:Landroid/content/SharedPreferences;

    .line 84
    const-string v2, "keyguard"

    invoke-virtual {p0, v2}, Lcom/cotal/android/flagecall/CallingActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    #v0=(Reference,Ljava/lang/Object;);
    check-cast v0, Landroid/app/KeyguardManager;

    .line 85
    .local v0, km:Landroid/app/KeyguardManager;
    const-string v2, "power"

    invoke-virtual {p0, v2}, Lcom/cotal/android/flagecall/CallingActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    #v1=(Reference,Ljava/lang/Object;);
    check-cast v1, Landroid/os/PowerManager;

    .line 86
    .local v1, pm:Landroid/os/PowerManager;
    const-string v2, "MyFalgeCallKeyLock"

    invoke-virtual {v0, v2}, Landroid/app/KeyguardManager;->newKeyguardLock(Ljava/lang/String;)Landroid/app/KeyguardManager$KeyguardLock;

    move-result-object v2

    iput-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->keyWakeLock:Landroid/app/KeyguardManager$KeyguardLock;

    .line 87
    const v2, 0x1000000a

    #v2=(Integer);
    const-string v3, "MyFalgeCallPowerLock"

    #v3=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, v2, v3}, Landroid/os/PowerManager;->newWakeLock(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;

    move-result-object v2

    #v2=(Reference,Landroid/os/PowerManager$WakeLock;);
    iput-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->powerWakeLock:Landroid/os/PowerManager$WakeLock;

    .line 88
    invoke-virtual {v1}, Landroid/os/PowerManager;->isScreenOn()Z

    move-result v2

    #v2=(Boolean);
    if-nez v2, :cond_0

    .line 90
    iget-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->keyWakeLock:Landroid/app/KeyguardManager$KeyguardLock;

    #v2=(Reference,Landroid/app/KeyguardManager$KeyguardLock;);
    invoke-virtual {v2}, Landroid/app/KeyguardManager$KeyguardLock;->disableKeyguard()V

    .line 92
    :cond_0
    #v2=(Conflicted);
    iget-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->powerWakeLock:Landroid/os/PowerManager$WakeLock;

    #v2=(Reference,Landroid/os/PowerManager$WakeLock;);
    invoke-virtual {v2}, Landroid/os/PowerManager$WakeLock;->acquire()V

    .line 94
    invoke-virtual {p0, v4}, Lcom/cotal/android/flagecall/CallingActivity;->requestWindowFeature(I)Z

    .line 95
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 96
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->getData()V

    .line 97
    const/4 v2, 0x0

    #v2=(Null);
    invoke-direct {p0, v2}, Lcom/cotal/android/flagecall/CallingActivity;->switchView(I)V

    .line 98
    iget-object v2, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    #v2=(Reference,Landroid/os/Handler;);
    iget-object v3, p0, Lcom/cotal/android/flagecall/CallingActivity;->mHandler:Landroid/os/Handler;

    const/4 v4, 0x2

    #v4=(PosByte);
    invoke-virtual {v3, v4}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v3

    const-wide/16 v4, 0x3e8

    #v4=(LongLo);v5=(LongHi);
    invoke-virtual {v2, v3, v4, v5}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 99
    return-void
.end method

.method protected onDestroy()V
    .locals 1

    .prologue
    .line 425
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    .line 426
    invoke-direct {p0}, Lcom/cotal/android/flagecall/CallingActivity;->close()V

    .line 427
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->keyWakeLock:Landroid/app/KeyguardManager$KeyguardLock;

    #v0=(Reference,Landroid/app/KeyguardManager$KeyguardLock;);
    invoke-virtual {v0}, Landroid/app/KeyguardManager$KeyguardLock;->reenableKeyguard()V

    .line 428
    iget-object v0, p0, Lcom/cotal/android/flagecall/CallingActivity;->powerWakeLock:Landroid/os/PowerManager$WakeLock;

    invoke-virtual {v0}, Landroid/os/PowerManager$WakeLock;->release()V

    .line 429
    return-void
.end method
