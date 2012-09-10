.class public Lcom/irdeto/security/CryptoManager;
.super Ljava/lang/Object;
.source "CryptoManager.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field static final HMAC_SHA1_ALGORITHM:Ljava/lang/String; = "HmacSHA1"

.field static MYTAG:Ljava/lang/String; = null

.field static final myKey:Ljava/lang/String; = "1234567890abcdefg"


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 24
    const-string v0, "CryptoManager"

    sput-object v0, Lcom/irdeto/security/CryptoManager;->MYTAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 22
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static HMACSHA1([B)Ljava/lang/String;
    .registers 7
    .parameter "inputBytes"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const-string v5, "HmacSHA1"

    .line 45
    new-instance v2, Ljavax/crypto/spec/SecretKeySpec;

    const-string v3, "1234567890abcdefg"

    invoke-virtual {v3}, Ljava/lang/String;->getBytes()[B

    move-result-object v3

    const-string v4, "HmacSHA1"

    invoke-direct {v2, v3, v5}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 46
    .local v2, signingKey:Ljavax/crypto/spec/SecretKeySpec;
    const-string v3, "HmacSHA1"

    invoke-static {v5}, Ljavax/crypto/Mac;->getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;

    move-result-object v0

    .line 47
    .local v0, mac:Ljavax/crypto/Mac;
    invoke-virtual {v0, v2}, Ljavax/crypto/Mac;->init(Ljava/security/Key;)V

    .line 48
    invoke-virtual {v0, p0}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v1

    .line 50
    .local v1, rawHmac:[B
    invoke-static {v1}, Lcom/irdeto/security/common/HexUtils;->bytesToHex([B)[C

    move-result-object v3

    invoke-static {v3}, Ljava/lang/String;->valueOf([C)Ljava/lang/String;

    move-result-object v3

    return-object v3
.end method

.method private static buildSigningOutputMessage(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 5
    .parameter "entityName"
    .parameter "hash"

    .prologue
    .line 122
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 123
    .local v0, buf:Ljava/lang/StringBuffer;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Name: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\n"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 124
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "HmacSHA1: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 125
    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method private static buildSigningOutputMessageForAPK(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 5
    .parameter "entityName"
    .parameter "hash"

    .prologue
    .line 115
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 116
    .local v0, buf:Ljava/lang/StringBuffer;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "APKName: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\n"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 117
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "HmacSHA1: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 118
    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static getAPKDigitalSignature(Ljava/lang/String;Z)Ljava/lang/String;
    .registers 10
    .parameter "APKPath"
    .parameter "isNative"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 99
    const/4 v1, 0x0

    .line 100
    .local v1, entityName:Ljava/lang/String;
    const-string v4, "null"

    .line 101
    .local v4, message:Ljava/lang/String;
    new-instance v3, Lcom/irdeto/security/common/IOUtils;

    invoke-direct {v3}, Lcom/irdeto/security/common/IOUtils;-><init>()V

    .line 103
    .local v3, it:Lcom/irdeto/security/common/IOUtils;
    invoke-virtual {v3, p0}, Lcom/irdeto/security/common/IOUtils;->getContentBytesFromProc(Ljava/lang/String;)[B

    move-result-object v5

    .line 104
    .local v5, myData:[B
    new-instance v0, Lcom/irdeto/security/CryptoManager;

    invoke-direct {v0}, Lcom/irdeto/security/CryptoManager;-><init>()V

    .line 105
    .local v0, cm:Lcom/irdeto/security/CryptoManager;
    invoke-static {v5}, Lcom/irdeto/security/CryptoManager;->HMACSHA1([B)Ljava/lang/String;

    move-result-object v2

    .line 106
    .local v2, hash:Ljava/lang/String;
    const/16 v6, 0x2f

    invoke-virtual {p0, v6}, Ljava/lang/String;->lastIndexOf(I)I

    move-result v6

    add-int/lit8 v6, v6, 0x1

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v7

    invoke-virtual {p0, v6, v7}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    .line 107
    if-eqz p1, :cond_29

    .line 108
    move-object v4, v2

    .line 112
    :goto_28
    return-object v4

    .line 110
    :cond_29
    invoke-static {v1, v2}, Lcom/irdeto/security/CryptoManager;->buildSigningOutputMessageForAPK(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    goto :goto_28
.end method

.method public static getDigitalSignature(Ljava/lang/String;Z)Ljava/lang/String;
    .registers 14
    .parameter "className"
    .parameter "isNative"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 62
    const/4 v7, 0x0

    .line 63
    .local v7, myBytes2:[B
    const/4 v8, 0x0

    .line 64
    .local v8, myDestBytes:[B
    const-string v4, "null"

    .line 65
    .local v4, message:Ljava/lang/String;
    new-instance v3, Lcom/irdeto/security/common/IOUtils;

    invoke-direct {v3}, Lcom/irdeto/security/common/IOUtils;-><init>()V

    .line 67
    .local v3, it:Lcom/irdeto/security/common/IOUtils;
    :try_start_9
    invoke-virtual {v3, p0}, Lcom/irdeto/security/common/IOUtils;->getBytesFromObject(Ljava/lang/String;)[B
    :try_end_c
    .catchall {:try_start_9 .. :try_end_c} :catchall_66
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_c} :catch_3d

    move-result-object v6

    .line 69
    .local v6, myBytes1:[B
    :try_start_d
    invoke-virtual {v3, p0}, Lcom/irdeto/security/common/IOUtils;->getBytesFromSerializable(Ljava/lang/String;)[B
    :try_end_10
    .catchall {:try_start_d .. :try_end_10} :catchall_66
    .catch Ljava/lang/Exception; {:try_start_d .. :try_end_10} :catch_69

    move-result-object v7

    .line 72
    :goto_11
    if-eqz v7, :cond_34

    .line 73
    :try_start_13
    array-length v9, v6

    array-length v10, v7

    add-int/2addr v9, v10

    new-array v8, v9, [B

    .line 77
    :goto_18
    const/4 v9, 0x0

    const/4 v10, 0x0

    array-length v11, v6

    invoke-static {v6, v9, v8, v10, v11}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 78
    if-eqz v7, :cond_26

    .line 79
    const/4 v9, 0x0

    array-length v10, v6

    array-length v11, v7

    invoke-static {v7, v9, v8, v10, v11}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 81
    :cond_26
    new-instance v0, Lcom/irdeto/security/CryptoManager;

    invoke-direct {v0}, Lcom/irdeto/security/CryptoManager;-><init>()V

    .line 83
    .local v0, cm:Lcom/irdeto/security/CryptoManager;
    invoke-static {v8}, Lcom/irdeto/security/CryptoManager;->HMACSHA1([B)Ljava/lang/String;

    move-result-object v2

    .line 84
    .local v2, hash:Ljava/lang/String;
    if-eqz p1, :cond_38

    .line 85
    move-object v4, v2

    :goto_32
    move-object v5, v4

    .line 94
    .end local v0           #cm:Lcom/irdeto/security/CryptoManager;
    .end local v2           #hash:Ljava/lang/String;
    .end local v4           #message:Ljava/lang/String;
    .end local v6           #myBytes1:[B
    .local v5, message:Ljava/lang/String;
    :goto_33
    return-object v5

    .line 75
    .end local v5           #message:Ljava/lang/String;
    .restart local v4       #message:Ljava/lang/String;
    .restart local v6       #myBytes1:[B
    :cond_34
    array-length v9, v6

    new-array v8, v9, [B

    goto :goto_18

    .line 87
    .restart local v0       #cm:Lcom/irdeto/security/CryptoManager;
    .restart local v2       #hash:Ljava/lang/String;
    :cond_38
    invoke-static {p0, v2}, Lcom/irdeto/security/CryptoManager;->buildSigningOutputMessage(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    :try_end_3b
    .catchall {:try_start_13 .. :try_end_3b} :catchall_66
    .catch Ljava/lang/Exception; {:try_start_13 .. :try_end_3b} :catch_3d

    move-result-object v4

    goto :goto_32

    .line 90
    .end local v0           #cm:Lcom/irdeto/security/CryptoManager;
    .end local v2           #hash:Ljava/lang/String;
    .end local v6           #myBytes1:[B
    :catch_3d
    move-exception v9

    move-object v1, v9

    .line 91
    .local v1, e:Ljava/lang/Exception;
    :try_start_3f
    sget-object v9, Lcom/irdeto/security/CryptoManager;->MYTAG:Ljava/lang/String;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "getDigitalSignature, classname: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, " can\'t be identified, message: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 92
    throw v1
    :try_end_66
    .catchall {:try_start_3f .. :try_end_66} :catchall_66

    .line 94
    .end local v1           #e:Ljava/lang/Exception;
    :catchall_66
    move-exception v9

    move-object v5, v4

    .end local v4           #message:Ljava/lang/String;
    .restart local v5       #message:Ljava/lang/String;
    goto :goto_33

    .line 70
    .end local v5           #message:Ljava/lang/String;
    .restart local v4       #message:Ljava/lang/String;
    .restart local v6       #myBytes1:[B
    :catch_69
    move-exception v9

    goto :goto_11
.end method

.method public static hashFileContents(Ljava/lang/String;)Ljava/lang/String;
    .registers 6
    .parameter "fullFilePath"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 136
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 137
    .local v0, array:Ljava/util/ArrayList;
    const/4 v3, 0x0

    .line 138
    .local v3, startPos:I
    new-instance v1, Ljava/lang/StringBuffer;

    invoke-direct {v1}, Ljava/lang/StringBuffer;-><init>()V

    .line 139
    .local v1, buf:Ljava/lang/StringBuffer;
    invoke-static {p0, v0}, Lcom/irdeto/security/common/IOUtils;->getFileContents(Ljava/lang/String;Ljava/util/ArrayList;)V

    .line 140
    const/4 v2, 0x1

    .local v2, pos:I
    :goto_f
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-ge v2, v4, :cond_1f

    .line 141
    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v1, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/Object;)Ljava/lang/StringBuffer;

    .line 140
    add-int/lit8 v2, v2, 0x1

    goto :goto_f

    .line 143
    :cond_1f
    invoke-virtual {v1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->getBytes()[B

    move-result-object v4

    invoke-static {v4}, Lcom/irdeto/security/CryptoManager;->HMACSHA1([B)Ljava/lang/String;

    move-result-object v4

    return-object v4
.end method


# virtual methods
.method public SHA1([B)Ljava/lang/String;
    .registers 6
    .parameter "inputBytes"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 30
    const-string v2, "SHA-1"

    invoke-static {v2}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 31
    .local v0, md:Ljava/security/MessageDigest;
    const/16 v2, 0x28

    new-array v1, v2, [B

    .line 32
    .local v1, sha1hash:[B
    const/4 v2, 0x0

    array-length v3, p1

    invoke-virtual {v0, p1, v2, v3}, Ljava/security/MessageDigest;->update([BII)V

    .line 33
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v1

    .line 34
    invoke-static {v1}, Lcom/irdeto/security/common/HexUtils;->bytesToHex([B)[C

    move-result-object v2

    invoke-static {v2}, Ljava/lang/String;->valueOf([C)Ljava/lang/String;

    move-result-object v2

    return-object v2
.end method
