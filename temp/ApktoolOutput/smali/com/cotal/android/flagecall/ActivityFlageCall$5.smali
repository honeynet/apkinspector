.class Lcom/cotal/android/flagecall/ActivityFlageCall$5;
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
    iput-object p1, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    .line 262
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    #p0=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall$5;);
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 13
    .parameter "v"

    .prologue
    const/4 v12, 0x0

    .line 267
    #v12=(Null);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$0(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/net/Uri;

    move-result-object v8

    if-nez v8, :cond_0

    .line 269
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    sget-object v9, Landroid/provider/Settings$System;->DEFAULT_RINGTONE_URI:Landroid/net/Uri;

    #v9=(Reference,Landroid/net/Uri;);
    invoke-static {v8, v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$1(Lcom/cotal/android/flagecall/ActivityFlageCall;Landroid/net/Uri;)V

    .line 272
    :cond_0
    #v9=(Conflicted);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$14(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/TimePicker;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/TimePicker;->getCurrentHour()Ljava/lang/Integer;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/Integer;->intValue()I

    move-result v2

    .line 273
    .local v2, hour:I
    #v2=(Integer);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$14(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/TimePicker;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/TimePicker;->getCurrentMinute()Ljava/lang/Integer;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/Integer;->intValue()I

    move-result v4

    .line 274
    .local v4, min:I
    #v4=(Integer);
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v1

    .line 275
    .local v1, calendar:Ljava/util/Calendar;
    #v1=(Reference,Ljava/util/Calendar;);
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v8

    #v8=(LongLo);v9=(LongHi);
    invoke-virtual {v1, v8, v9}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 276
    const/16 v8, 0xb

    #v8=(PosByte);
    invoke-virtual {v1, v8, v2}, Ljava/util/Calendar;->set(II)V

    .line 277
    const/16 v8, 0xc

    invoke-virtual {v1, v8, v4}, Ljava/util/Calendar;->set(II)V

    .line 278
    invoke-virtual {v1}, Ljava/util/Calendar;->getTimeInMillis()J

    move-result-wide v8

    #v8=(LongLo);
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v10

    #v10=(LongLo);v11=(LongHi);
    sub-long v6, v8, v10

    .line 280
    .local v6, xx:J
    #v6=(LongLo);v7=(LongHi);
    const-wide/32 v8, 0xc350

    cmp-long v8, v6, v8

    #v8=(Byte);
    if-gez v8, :cond_1

    .line 282
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    const-string v9, "\u6765\u7535\u65f6\u95f4\u5fc5\u987b\u5927\u4e8e\u5f53\u524d\u65f6\u95f4"

    #v9=(Reference,Ljava/lang/String;);
    invoke-static {v8, v9, v12}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/Toast;->show()V

    .line 324
    :goto_0
    #v0=(Conflicted);v3=(Conflicted);v5=(Conflicted);v10=(Conflicted);
    return-void

    .line 287
    :cond_1
    #v0=(Uninit);v3=(Uninit);v5=(Uninit);v8=(Byte);v9=(LongHi);v10=(LongLo);
    new-instance v3, Landroid/content/Intent;

    #v3=(UninitRef,Landroid/content/Intent;);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    const-class v9, Lcom/cotal/android/flagecall/CallingActivity;

    #v9=(Reference,Ljava/lang/Class;);
    invoke-direct {v3, v8, v9}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 288
    .local v3, intent:Landroid/content/Intent;
    #v3=(Reference,Landroid/content/Intent;);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "Name"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v10

    invoke-virtual {v10}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v10

    invoke-interface {v10}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 289
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "Number"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v10

    invoke-virtual {v10}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v10

    invoke-interface {v10}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 290
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "RingUri"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$0(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/net/Uri;

    move-result-object v10

    invoke-virtual {v10}, Landroid/net/Uri;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 291
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "RingTonePlayTime"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$5(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v10

    #v10=(Integer);
    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 292
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "PhotoID"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$7(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 293
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "MusicID"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$8(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v10

    #v10=(Integer);
    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 294
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "MusicFile"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$9(Lcom/cotal/android/flagecall/ActivityFlageCall;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$3(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Ljava/lang/String;)V

    .line 295
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "Vibrate"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$10(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z

    move-result v10

    #v10=(Boolean);
    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$11(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Z)V

    .line 296
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "PlayLoop"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$12(Lcom/cotal/android/flagecall/ActivityFlageCall;)Z

    move-result v10

    #v10=(Boolean);
    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$11(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;Z)V

    .line 297
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "VoicePlayTime"

    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$13(Lcom/cotal/android/flagecall/ActivityFlageCall;)I

    move-result v10

    #v10=(Integer);
    invoke-static {v8, v9, v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$6(Lcom/cotal/android/flagecall/ActivityFlageCall;Ljava/lang/String;I)V

    .line 299
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    iget-object v9, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v9, v12, v3, v12}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$15(Lcom/cotal/android/flagecall/ActivityFlageCall;Landroid/app/PendingIntent;)V

    .line 300
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    const-string v9, "alarm"

    invoke-virtual {v8, v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    #v0=(Reference,Ljava/lang/Object;);
    check-cast v0, Landroid/app/AlarmManager;

    .line 301
    .local v0, am:Landroid/app/AlarmManager;
    invoke-virtual {v1}, Ljava/util/Calendar;->getTimeInMillis()J

    move-result-wide v8

    #v8=(LongLo);v9=(LongHi);
    iget-object v10, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v10=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v10}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$16(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/app/PendingIntent;

    move-result-object v10

    invoke-virtual {v0, v12, v8, v9, v10}, Landroid/app/AlarmManager;->set(IJLandroid/app/PendingIntent;)V

    .line 303
    const-string v5, "\u6709\u672a\u77e5\u7535\u8bdd\u63a5\u5165"

    .line 304
    .local v5, msg:Ljava/lang/String;
    #v5=(Reference,Ljava/lang/String;);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v8

    invoke-interface {v8}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v8

    const-string v9, ""

    #v9=(Reference,Ljava/lang/String;);
    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    #v8=(Boolean);
    if-nez v8, :cond_3

    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v8

    invoke-interface {v8}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v8

    const-string v9, ""

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    #v8=(Boolean);
    if-nez v8, :cond_3

    .line 306
    new-instance v8, Ljava/lang/StringBuilder;

    #v8=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v9, "\u6709\u6765\u81ea"

    invoke-direct {v8, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v8=(Reference,Ljava/lang/StringBuilder;);
    iget-object v9, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v9

    invoke-virtual {v9}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v9

    invoke-interface {v9}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, "("

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 307
    iget-object v9, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v9

    invoke-virtual {v9}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v9

    invoke-interface {v9}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, ")\u7684\u7535\u8bdd"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 306
    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 320
    :cond_2
    :goto_1
    #v8=(Conflicted);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    new-instance v9, Ljava/lang/StringBuilder;

    #v9=(UninitRef,Ljava/lang/StringBuilder;);
    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    #v9=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, ":"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    const/4 v10, 0x1

    #v10=(One);
    invoke-static {v8, v9, v10}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/Toast;->show()V

    .line 323
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-virtual {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->finish()V

    goto/16 :goto_0

    .line 309
    :cond_3
    #v8=(Boolean);v10=(Reference,Landroid/app/PendingIntent;);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v8

    invoke-interface {v8}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v8

    const-string v9, ""

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    #v8=(Boolean);
    if-nez v8, :cond_4

    .line 311
    new-instance v8, Ljava/lang/StringBuilder;

    #v8=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v9, "\u6709\u6765\u81ea"

    invoke-direct {v8, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v8=(Reference,Ljava/lang/StringBuilder;);
    iget-object v9, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$2(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v9

    invoke-virtual {v9}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v9

    invoke-interface {v9}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 312
    const-string v9, "\u7684\u7535\u8bdd"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 311
    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_1

    .line 314
    :cond_4
    #v8=(Boolean);
    iget-object v8, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    #v8=(Reference,Lcom/cotal/android/flagecall/ActivityFlageCall;);
    invoke-static {v8}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v8

    invoke-virtual {v8}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v8

    invoke-interface {v8}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v8

    const-string v9, ""

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    #v8=(Boolean);
    if-nez v8, :cond_2

    .line 316
    new-instance v8, Ljava/lang/StringBuilder;

    #v8=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v9, "\u6709\u6765\u81ea"

    invoke-direct {v8, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v8=(Reference,Ljava/lang/StringBuilder;);
    iget-object v9, p0, Lcom/cotal/android/flagecall/ActivityFlageCall$5;->this$0:Lcom/cotal/android/flagecall/ActivityFlageCall;

    invoke-static {v9}, Lcom/cotal/android/flagecall/ActivityFlageCall;->access$4(Lcom/cotal/android/flagecall/ActivityFlageCall;)Landroid/widget/EditText;

    move-result-object v9

    invoke-virtual {v9}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v9

    invoke-interface {v9}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 317
    const-string v9, "\u7684\u7535\u8bdd"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 316
    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    goto/16 :goto_1
.end method
