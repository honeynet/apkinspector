.class public Lcom/irdeto/omp/OmpClient;
.super Ljava/lang/Object;
.source "OmpClient.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "OmpClient"

.field private static ctx:Landroid/content/Context;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 39
    const-string v0, "LsOmpClient_jni"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    .line 69
    const/4 v0, 0x0

    sput-object v0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 36
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static EnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)Ljava/util/Hashtable;
    .registers 20
    .parameter "url"
    .parameter "appid"
    .parameter "httpmethod"
    .parameter "httphead"
    .parameter "httpbody"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/irdeto/omp/OmpException;
        }
    .end annotation

    .prologue
    .line 133
    :try_start_0
    sget-object v3, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v4, "IVCheck"

    invoke-static {v3, v4}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 134
    .local v3, checkIV:Ljava/lang/String;
    const-string v4, "1"

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_d
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_d} :catch_72

    move-result v3

    .end local v3           #checkIV:Ljava/lang/String;
    if-nez v3, :cond_63

    .line 145
    :cond_10
    :goto_10
    const-string v3, "0"

    .line 146
    .local v3, statusCode:Ljava/lang/String;
    const/4 v9, 0x0

    .line 148
    .local v9, ht:Ljava/util/Hashtable;
    if-eqz p0, :cond_1f

    if-eqz p1, :cond_1f

    if-eqz p2, :cond_1f

    if-eqz p3, :cond_1f

    sget-object v4, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    if-nez v4, :cond_25

    .line 149
    :cond_1f
    const/16 v3, 0x1f41

    invoke-static {v3}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .end local v3           #statusCode:Ljava/lang/String;
    move-result-object v3

    .line 153
    .restart local v3       #statusCode:Ljava/lang/String;
    :cond_25
    :try_start_25
    const-string v4, "0"

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_170

    .line 154
    sget-object v3, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .end local v3           #statusCode:Ljava/lang/String;
    sget-object v4, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v4, "phone"

    invoke-virtual {v3, v4}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/telephony/TelephonyManager;

    check-cast v3, Landroid/telephony/TelephonyManager;

    .line 155
    .local v3, telephonyManager:Landroid/telephony/TelephonyManager;
    invoke-virtual {v3}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v5

    .line 156
    .local v5, imsi:Ljava/lang/String;
    if-nez v5, :cond_7b

    .line 157
    new-instance p0, Lcom/irdeto/omp/OmpException;

    .end local p0
    const p1, 0x10005

    invoke-direct/range {p0 .. p1}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    .end local p1
    throw p0
    :try_end_4a
    .catch Ljava/lang/Exception; {:try_start_25 .. :try_end_4a} :catch_4a

    .line 220
    .end local v3           #telephonyManager:Landroid/telephony/TelephonyManager;
    .end local v5           #imsi:Ljava/lang/String;
    .end local p3
    :catch_4a
    move-exception p0

    move-object/from16 p1, v9

    .line 221
    .end local v9           #ht:Ljava/util/Hashtable;
    .end local p2
    .local p0, e:Ljava/lang/Exception;
    .local p1, ht:Ljava/util/Hashtable;
    :goto_4d
    const/16 p0, 0x1f54

    invoke-static {p0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .end local p0           #e:Ljava/lang/Exception;
    move-result-object p1

    .line 222
    .local p1, statusCode:Ljava/lang/String;
    new-instance p0, Ljava/util/Hashtable;

    invoke-direct {p0}, Ljava/util/Hashtable;-><init>()V

    .line 226
    .local p0, ht:Ljava/util/Hashtable;
    :goto_58
    const-string p2, "errorCode"

    move-object v0, p0

    move-object/from16 v1, p2

    move-object/from16 v2, p1

    invoke-virtual {v0, v1, v2}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 228
    return-object p0

    .line 137
    .local p0, url:Ljava/lang/String;
    .local p1, appid:Ljava/lang/String;
    .restart local p2
    .restart local p3
    :cond_63
    :try_start_63
    invoke-static {}, Lcom/irdeto/omp/OmpClient;->checkPermission()Z

    move-result v3

    if-nez v3, :cond_10

    .line 138
    new-instance v3, Lcom/irdeto/omp/OmpException;

    const v4, 0x10001

    invoke-direct {v3, v4}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v3
    :try_end_72
    .catch Ljava/lang/Exception; {:try_start_63 .. :try_end_72} :catch_72

    .line 140
    :catch_72
    move-exception v3

    .line 141
    .local v3, e:Ljava/lang/Exception;
    const-string v3, "OMP"

    .end local v3           #e:Ljava/lang/Exception;
    const-string v4, "EnablerCalling()--checkPermission() succeded!"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_10

    .line 159
    .local v3, telephonyManager:Landroid/telephony/TelephonyManager;
    .restart local v5       #imsi:Ljava/lang/String;
    .restart local v9       #ht:Ljava/util/Hashtable;
    :cond_7b
    const/4 v3, 0x5

    :try_start_7c
    new-array v4, v3, [Ljava/lang/String;

    .end local v3           #telephonyManager:Landroid/telephony/TelephonyManager;
    const/4 v3, 0x0

    const-string v6, "appid"

    aput-object v6, v4, v3

    const/4 v3, 0x1

    const-string v6, "token"

    aput-object v6, v4, v3

    const/4 v3, 0x2

    const-string v6, "counter"

    aput-object v6, v4, v3

    const/4 v3, 0x3

    const-string v6, "pid"

    aput-object v6, v4, v3

    const/4 v3, 0x4

    const-string v6, "apptype"

    aput-object v6, v4, v3

    .line 161
    .local v4, authArray:[Ljava/lang/String;
    invoke-virtual/range {p3 .. p3}, Ljava/util/Hashtable;->keys()Ljava/util/Enumeration;

    move-result-object v3

    .line 162
    .local v3, allKey:Ljava/util/Enumeration;
    invoke-virtual/range {p3 .. p3}, Ljava/util/Hashtable;->elements()Ljava/util/Enumeration;

    move-result-object p3

    .line 163
    .local p3, allValue:Ljava/util/Enumeration;
    new-instance v7, Ljava/lang/String;

    invoke-direct {v7}, Ljava/lang/String;-><init>()V

    .line 164
    .local v7, strGetValueFromPar:Ljava/lang/String;
    new-instance v10, Ljava/lang/StringBuffer;

    const/16 v6, 0x400

    invoke-direct {v10, v6}, Ljava/lang/StringBuffer;-><init>(I)V

    .line 165
    .local v10, sbFromPar:Ljava/lang/StringBuffer;
    const/4 v6, 0x0

    .line 167
    .end local v7           #strGetValueFromPar:Ljava/lang/String;
    .local v6, findAuth:I
    :goto_ac
    invoke-interface {v3}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v7

    if-eqz v7, :cond_115

    .line 169
    invoke-interface {v3}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/String;

    .line 170
    .local v11, strkey:Ljava/lang/String;
    invoke-interface/range {p3 .. p3}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 172
    .local v7, strValue:Ljava/lang/String;
    const-string v8, "Authorization"

    invoke-virtual {v11, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_100

    .line 173
    const/4 v6, 0x1

    .line 174
    const-string v8, "%s: %s"

    const/4 v12, 0x2

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    aput-object v11, v12, v13

    const/4 v13, 0x1

    aput-object v7, v12, v13

    invoke-static {v8, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    .line 175
    .local v7, tmp:Ljava/lang/String;
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v8, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .end local v7           #tmp:Ljava/lang/String;
    const-string v8, "appid=\"%s\",token=\"%s\",counter=\"%s\",pid=\"%s\",apptype=\"%s\"\r"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .restart local v7       #tmp:Ljava/lang/String;
    move-object v12, v7

    .end local v7           #tmp:Ljava/lang/String;
    .local v12, tmp:Ljava/lang/String;
    move v7, v6

    .line 179
    .end local v6           #findAuth:I
    .local v7, findAuth:I
    :goto_eb
    const/4 v6, 0x0

    .line 180
    .local v6, find:Z
    const/4 v8, 0x0

    .local v8, i:I
    :goto_ed
    array-length v13, v4

    if-ge v8, v13, :cond_f9

    .line 182
    aget-object v13, v4, v8

    invoke-virtual {v11, v13}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v13

    if-eqz v13, :cond_112

    .line 184
    const/4 v6, 0x1

    .line 189
    :cond_f9
    if-nez v6, :cond_fe

    .line 191
    invoke-virtual {v10, v12}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    :cond_fe
    move v6, v7

    .line 193
    .end local v7           #findAuth:I
    .local v6, findAuth:I
    goto :goto_ac

    .line 177
    .end local v8           #i:I
    .end local v12           #tmp:Ljava/lang/String;
    .local v7, strValue:Ljava/lang/String;
    :cond_100
    const-string v8, "%s:%s\r"

    const/4 v12, 0x2

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    aput-object v11, v12, v13

    const/4 v13, 0x1

    aput-object v7, v12, v13

    invoke-static {v8, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    .local v7, tmp:Ljava/lang/String;
    move-object v12, v7

    .end local v7           #tmp:Ljava/lang/String;
    .restart local v12       #tmp:Ljava/lang/String;
    move v7, v6

    .end local v6           #findAuth:I
    .local v7, findAuth:I
    goto :goto_eb

    .line 180
    .local v6, find:Z
    .restart local v8       #i:I
    :cond_112
    add-int/lit8 v8, v8, 0x1

    goto :goto_ed

    .line 194
    .end local v7           #findAuth:I
    .end local v8           #i:I
    .end local v11           #strkey:Ljava/lang/String;
    .end local v12           #tmp:Ljava/lang/String;
    .local v6, findAuth:I
    :cond_115
    if-nez v6, :cond_11f

    .line 195
    const-string p3, "Authorization: appid=\"%s\",token=\"%s\",counter=\"%s\",pid=\"%s\",apptype=\"%s\"\r"

    .end local p3           #allValue:Ljava/util/Enumeration;
    move-object v0, v10

    move-object/from16 v1, p3

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 198
    :cond_11f
    invoke-virtual {v10}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v7

    .line 199
    .local v7, strGetValueFromPar:Ljava/lang/String;
    const/4 v8, 0x0

    .line 200
    .local v8, strBody:Ljava/lang/String;
    if-eqz p4, :cond_12e

    .line 202
    new-instance v8, Ljava/lang/String;

    .end local v8           #strBody:Ljava/lang/String;
    move-object v0, v8

    move-object/from16 v1, p4

    invoke-direct {v0, v1}, Ljava/lang/String;-><init>([B)V

    .restart local v8       #strBody:Ljava/lang/String;
    :cond_12e
    move-object v3, p0

    move-object/from16 v4, p1

    move-object/from16 v6, p2

    .line 205
    invoke-static/range {v3 .. v8}, Lcom/irdeto/omp/OmpClient;->nEnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;
    :try_end_136
    .catch Ljava/lang/Exception; {:try_start_7c .. :try_end_136} :catch_4a

    .end local v3           #allKey:Ljava/util/Enumeration;
    .end local v4           #authArray:[Ljava/lang/String;
    .end local v6           #findAuth:I
    move-result-object p0

    .line 207
    .end local v9           #ht:Ljava/util/Hashtable;
    .local p0, ht:Ljava/util/Hashtable;
    if-nez p0, :cond_14a

    .line 208
    :try_start_139
    new-instance p1, Lcom/irdeto/omp/OmpException;

    .end local p1           #appid:Ljava/lang/String;
    const p2, 0x10005

    invoke-direct/range {p1 .. p2}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    .end local p2
    throw p1

    .line 220
    :catch_142
    move-exception p1

    move-object/from16 v14, p1

    move-object/from16 p1, p0

    .end local p0           #ht:Ljava/util/Hashtable;
    .local p1, ht:Ljava/util/Hashtable;
    move-object p0, v14

    goto/16 :goto_4d

    .line 211
    .restart local p0       #ht:Ljava/util/Hashtable;
    .local p1, appid:Ljava/lang/String;
    .restart local p2
    :cond_14a
    const-string p1, "errorCode"

    .end local p1           #appid:Ljava/lang/String;
    invoke-virtual/range {p0 .. p1}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 213
    .local p1, rs:Ljava/lang/String;
    if-eqz p1, :cond_168

    invoke-static/range {p1 .. p1}, Ljava/lang/Integer;->decode(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object p2

    .end local p2
    invoke-virtual/range {p2 .. p2}, Ljava/lang/Integer;->intValue()I

    move-result p2

    const/16 p3, 0x270f

    move/from16 v0, p2

    move/from16 v1, p3

    if-gt v0, v1, :cond_168

    .line 214
    move-object/from16 p1, p1

    .local p1, statusCode:Ljava/lang/String;
    goto/16 :goto_58

    .line 217
    .local p1, rs:Ljava/lang/String;
    :cond_168
    const/16 p1, 0x1f54

    invoke-static/range {p1 .. p1}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;
    :try_end_16d
    .catch Ljava/lang/Exception; {:try_start_139 .. :try_end_16d} :catch_142

    .end local p1           #rs:Ljava/lang/String;
    move-result-object p1

    .local p1, statusCode:Ljava/lang/String;
    goto/16 :goto_58

    .end local v5           #imsi:Ljava/lang/String;
    .end local v7           #strGetValueFromPar:Ljava/lang/String;
    .end local v8           #strBody:Ljava/lang/String;
    .end local v10           #sbFromPar:Ljava/lang/StringBuffer;
    .local v3, statusCode:Ljava/lang/String;
    .restart local v9       #ht:Ljava/util/Hashtable;
    .local p0, url:Ljava/lang/String;
    .local p1, appid:Ljava/lang/String;
    .restart local p2
    .local p3, httphead:Ljava/util/Hashtable;
    :cond_170
    move-object p0, v9

    .end local v9           #ht:Ljava/util/Hashtable;
    .local p0, ht:Ljava/util/Hashtable;
    move-object/from16 p1, v3

    .end local v3           #statusCode:Ljava/lang/String;
    .local p1, statusCode:Ljava/lang/String;
    goto/16 :goto_58
.end method

.method public static GetSubStatus(Ljava/lang/String;)Z
    .registers 2
    .parameter "appid"

    .prologue
    .line 578
    const/4 v0, 0x1

    .line 580
    .local v0, result:Z
    invoke-static {p0}, Lcom/irdeto/omp/OmpClient;->nGetSubStatus(Ljava/lang/String;)Z

    move-result v0

    .line 581
    return v0
.end method

.method public static InitOmpEnv(Landroid/content/Context;)I
    .registers 12
    .parameter "ctxObj"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 368
    const/4 v2, 0x0

    .line 369
    .local v2, result:I
    const-string v7, ""

    .line 370
    .local v7, storeName:Ljava/lang/String;
    const-string v0, ""

    .line 371
    .local v0, acvName:Ljava/lang/String;
    const-string v8, ""

    .line 372
    .local v8, xmlName:Ljava/lang/String;
    const/4 v4, 0x0

    .line 373
    .local v4, is:Ljava/io/InputStream;
    const/4 v3, 0x0

    .line 374
    .local v3, fos:Ljava/io/FileOutputStream;
    const/4 v6, 0x0

    .line 375
    .local v6, size:I
    const/4 v1, 0x0

    .line 377
    .local v1, buffer:[B
    if-nez p0, :cond_106

    .line 378
    const/16 v2, 0x1f41

    move v5, v2

    .line 384
    .end local v2           #result:I
    .local v5, result:I
    :goto_10
    :try_start_10
    invoke-static {p0}, Lcom/irdeto/security/StubInterface;->setAplicationContext(Landroid/content/Context;)V
    :try_end_13
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_13} :catch_10b

    .line 394
    :cond_13
    :goto_13
    if-nez v5, :cond_18e

    .line 395
    :try_start_15
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v9}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v9, "/Store.dat"

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 396
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v9}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v9, "/ACV.dat"

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 397
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v9}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v9, "/LsOmpClientSettings.xml"

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 399
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 400
    .local v2, file:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v2

    .end local v2           #file:Ljava/io/File;
    if-nez v2, :cond_95

    .line 401
    invoke-virtual {p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    const-string v9, "Store.dat"

    invoke-virtual {v2, v9}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v4

    .line 402
    invoke-virtual {v4}, Ljava/io/InputStream;->available()I

    move-result v6

    .line 403
    new-array v1, v6, [B

    .line 404
    invoke-virtual {v4, v1}, Ljava/io/InputStream;->read([B)I

    .line 405
    invoke-virtual {v4}, Ljava/io/InputStream;->close()V

    .line 407
    const-string v2, "Store.dat"

    const/4 v9, 0x0

    invoke-virtual {p0, v2, v9}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    :try_end_8d
    .catch Ljava/lang/Exception; {:try_start_15 .. :try_end_8d} :catch_11e

    move-result-object v2

    .line 408
    .end local v3           #fos:Ljava/io/FileOutputStream;
    .local v2, fos:Ljava/io/FileOutputStream;
    :try_start_8e
    invoke-virtual {v2, v1}, Ljava/io/FileOutputStream;->write([B)V

    .line 409
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V
    :try_end_94
    .catch Ljava/lang/Exception; {:try_start_8e .. :try_end_94} :catch_178

    move-object v3, v2

    .line 412
    .end local v2           #fos:Ljava/io/FileOutputStream;
    .restart local v3       #fos:Ljava/io/FileOutputStream;
    :cond_95
    :try_start_95
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 414
    .local v2, file:Ljava/io/File;
    invoke-virtual {p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    .end local v2           #file:Ljava/io/File;
    const-string v9, "ACV.dat"

    invoke-virtual {v2, v9}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v4

    .line 415
    invoke-virtual {v4}, Ljava/io/InputStream;->available()I

    move-result v6

    .line 416
    new-array v1, v6, [B

    .line 417
    invoke-virtual {v4, v1}, Ljava/io/InputStream;->read([B)I

    .line 418
    invoke-virtual {v4}, Ljava/io/InputStream;->close()V

    .line 420
    const-string v2, "ACV.dat"

    const/4 v9, 0x0

    invoke-virtual {p0, v2, v9}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v3

    .line 421
    invoke-virtual {v3, v1}, Ljava/io/FileOutputStream;->write([B)V

    .line 422
    invoke-virtual {v3}, Ljava/io/FileOutputStream;->close()V

    .line 425
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v8}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 426
    .restart local v2       #file:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v2

    .end local v2           #file:Ljava/io/File;
    if-nez v2, :cond_189

    .line 427
    invoke-virtual {p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    const-string v9, "LsOmpClientSettings.xml"

    invoke-virtual {v2, v9}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v4

    .line 428
    invoke-virtual {v4}, Ljava/io/InputStream;->available()I

    move-result v6

    .line 429
    new-array v1, v6, [B

    .line 430
    invoke-virtual {v4, v1}, Ljava/io/InputStream;->read([B)I

    .line 431
    invoke-virtual {v4}, Ljava/io/InputStream;->close()V

    .line 433
    const-string v2, "LsOmpClientSettings.xml"

    const/4 v9, 0x0

    invoke-virtual {p0, v2, v9}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    :try_end_e4
    .catch Ljava/lang/Exception; {:try_start_95 .. :try_end_e4} :catch_11e

    move-result-object v2

    .line 434
    .end local v3           #fos:Ljava/io/FileOutputStream;
    .local v2, fos:Ljava/io/FileOutputStream;
    :try_start_e5
    invoke-virtual {v2, v1}, Ljava/io/FileOutputStream;->write([B)V

    .line 435
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V
    :try_end_eb
    .catch Ljava/lang/Exception; {:try_start_e5 .. :try_end_eb} :catch_178

    move-object v3, v4

    .end local v4           #is:Ljava/io/InputStream;
    .local v3, is:Ljava/io/InputStream;
    move v4, v6

    .line 438
    .end local v6           #size:I
    .local v4, size:I
    :goto_ed
    :try_start_ed
    invoke-static {v7, v0, v8}, Lcom/irdeto/omp/OmpClient;->nSetFileName(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_f0
    .catch Ljava/lang/Exception; {:try_start_ed .. :try_end_f0} :catch_180

    move-object v6, v7

    .end local v7           #storeName:Ljava/lang/String;
    .local v6, storeName:Ljava/lang/String;
    move-object v7, v8

    .end local v8           #xmlName:Ljava/lang/String;
    .local v7, xmlName:Ljava/lang/String;
    :goto_f2
    move v10, v4

    .end local v4           #size:I
    .local v10, size:I
    move v4, v5

    .end local v5           #result:I
    .local v4, result:I
    move v5, v10

    .line 448
    .end local v10           #size:I
    .local v5, size:I
    :goto_f5
    :try_start_f5
    sget-object v0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .end local v0           #acvName:Ljava/lang/String;
    const-string v1, "IVCheck"

    .end local v1           #buffer:[B
    invoke-static {v0, v1}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 449
    .local v0, checkIV:Ljava/lang/String;
    const-string v1, "1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_102
    .catch Ljava/lang/Exception; {:try_start_f5 .. :try_end_102} :catch_162

    move-result v0

    .end local v0           #checkIV:Ljava/lang/String;
    if-nez v0, :cond_132

    .line 476
    .end local v2           #fos:Ljava/io/FileOutputStream;
    .end local v3           #is:Ljava/io/InputStream;
    .end local p0
    :cond_105
    :goto_105
    return v4

    .line 380
    .end local v5           #size:I
    .local v0, acvName:Ljava/lang/String;
    .restart local v1       #buffer:[B
    .local v2, result:I
    .local v3, fos:Ljava/io/FileOutputStream;
    .local v4, is:Ljava/io/InputStream;
    .local v6, size:I
    .local v7, storeName:Ljava/lang/String;
    .restart local v8       #xmlName:Ljava/lang/String;
    .restart local p0
    :cond_106
    sput-object p0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    move v5, v2

    .end local v2           #result:I
    .local v5, result:I
    goto/16 :goto_10

    .line 386
    :catch_10b
    move-exception v2

    .line 387
    .local v2, e:Ljava/lang/Exception;
    invoke-virtual {v2}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    .end local v2           #e:Ljava/lang/Exception;
    const-string v9, "Application Context is already set"

    invoke-virtual {v2, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-nez v2, :cond_13

    .line 389
    const/4 v2, 0x1

    invoke-static {v2}, Ljava/lang/System;->exit(I)V

    goto/16 :goto_13

    .line 440
    :catch_11e
    move-exception v2

    move v5, v6

    .end local v6           #size:I
    .local v5, size:I
    move-object v6, v7

    .end local v7           #storeName:Ljava/lang/String;
    .local v6, storeName:Ljava/lang/String;
    move-object v7, v8

    .line 441
    .end local v8           #xmlName:Ljava/lang/String;
    .restart local v2       #e:Ljava/lang/Exception;
    .local v7, xmlName:Ljava/lang/String;
    :goto_122
    const-string v8, "OmpClient"

    invoke-virtual {v2}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    .end local v2           #e:Ljava/lang/Exception;
    invoke-static {v8, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 442
    const/16 v2, 0x1f41

    .local v2, result:I
    move-object v10, v3

    .end local v3           #fos:Ljava/io/FileOutputStream;
    .local v10, fos:Ljava/io/FileOutputStream;
    move-object v3, v4

    .end local v4           #is:Ljava/io/InputStream;
    .local v3, is:Ljava/io/InputStream;
    move v4, v2

    .end local v2           #result:I
    .local v4, result:I
    move-object v2, v10

    .end local v10           #fos:Ljava/io/FileOutputStream;
    .local v2, fos:Ljava/io/FileOutputStream;
    goto :goto_f5

    .line 453
    .end local v0           #acvName:Ljava/lang/String;
    .end local v1           #buffer:[B
    :cond_132
    :try_start_132
    invoke-static {p0}, Lcom/irdeto/security/StubInterface;->setAplicationContext(Landroid/content/Context;)V

    .line 454
    const-string v2, "IVCheckDate"

    .line 456
    .local v2, xmlDateTag:Ljava/lang/String;
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyy/MM/dd"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    .line 458
    .local v0, currentDate:Ljava/lang/String;
    invoke-static {p0, v2}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 459
    .local v1, xmlDate:Ljava/lang/String;
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    .end local v3           #is:Ljava/io/InputStream;
    if-eqz v3, :cond_153

    if-nez v1, :cond_105

    .line 460
    :cond_153
    invoke-static {}, Lcom/irdeto/omp/OmpClient;->checkPermission()Z

    move-result v1

    .end local v1           #xmlDate:Ljava/lang/String;
    if-nez v1, :cond_174

    .line 461
    new-instance p0, Lcom/irdeto/omp/OmpException;

    .end local p0
    const v0, 0x10001

    invoke-direct {p0, v0}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    .end local v0           #currentDate:Ljava/lang/String;
    throw p0
    :try_end_162
    .catch Ljava/lang/Exception; {:try_start_132 .. :try_end_162} :catch_162

    .line 468
    .end local v2           #xmlDateTag:Ljava/lang/String;
    :catch_162
    move-exception p0

    .line 469
    .local p0, e:Ljava/lang/Exception;
    invoke-virtual {p0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p0

    .end local p0           #e:Ljava/lang/Exception;
    const-string v0, "Application Context is already set"

    invoke-virtual {p0, v0}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p0

    if-nez p0, :cond_105

    .line 471
    const/4 p0, 0x1

    invoke-static {p0}, Ljava/lang/System;->exit(I)V

    goto :goto_105

    .line 464
    .restart local v0       #currentDate:Ljava/lang/String;
    .restart local v2       #xmlDateTag:Ljava/lang/String;
    .local p0, ctxObj:Landroid/content/Context;
    :cond_174
    :try_start_174
    invoke-static {p0, v2, v0}, Lcom/irdeto/omp/OmpClient;->setXmlNodeValue(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_177
    .catch Ljava/lang/Exception; {:try_start_174 .. :try_end_177} :catch_162

    goto :goto_105

    .line 440
    .local v0, acvName:Ljava/lang/String;
    .local v1, buffer:[B
    .local v2, fos:Ljava/io/FileOutputStream;
    .local v4, is:Ljava/io/InputStream;
    .local v5, result:I
    .local v6, size:I
    .local v7, storeName:Ljava/lang/String;
    .restart local v8       #xmlName:Ljava/lang/String;
    :catch_178
    move-exception v3

    move v5, v6

    .end local v6           #size:I
    .local v5, size:I
    move-object v6, v7

    .end local v7           #storeName:Ljava/lang/String;
    .local v6, storeName:Ljava/lang/String;
    move-object v7, v8

    .end local v8           #xmlName:Ljava/lang/String;
    .local v7, xmlName:Ljava/lang/String;
    move-object v10, v2

    .end local v2           #fos:Ljava/io/FileOutputStream;
    .restart local v10       #fos:Ljava/io/FileOutputStream;
    move-object v2, v3

    move-object v3, v10

    .end local v10           #fos:Ljava/io/FileOutputStream;
    .local v3, fos:Ljava/io/FileOutputStream;
    goto :goto_122

    .end local v6           #storeName:Ljava/lang/String;
    .restart local v2       #fos:Ljava/io/FileOutputStream;
    .local v3, is:Ljava/io/InputStream;
    .local v4, size:I
    .local v5, result:I
    .local v7, storeName:Ljava/lang/String;
    .restart local v8       #xmlName:Ljava/lang/String;
    :catch_180
    move-exception v5

    move-object v6, v7

    .end local v7           #storeName:Ljava/lang/String;
    .restart local v6       #storeName:Ljava/lang/String;
    move-object v7, v8

    .end local v8           #xmlName:Ljava/lang/String;
    .local v7, xmlName:Ljava/lang/String;
    move-object v10, v2

    .end local v2           #fos:Ljava/io/FileOutputStream;
    .restart local v10       #fos:Ljava/io/FileOutputStream;
    move-object v2, v5

    move v5, v4

    .end local v4           #size:I
    .local v5, size:I
    move-object v4, v3

    .end local v3           #is:Ljava/io/InputStream;
    .local v4, is:Ljava/io/InputStream;
    move-object v3, v10

    .end local v10           #fos:Ljava/io/FileOutputStream;
    .local v3, fos:Ljava/io/FileOutputStream;
    goto :goto_122

    .local v5, result:I
    .local v6, size:I
    .local v7, storeName:Ljava/lang/String;
    .restart local v8       #xmlName:Ljava/lang/String;
    :cond_189
    move-object v2, v3

    .end local v3           #fos:Ljava/io/FileOutputStream;
    .restart local v2       #fos:Ljava/io/FileOutputStream;
    move-object v3, v4

    .end local v4           #is:Ljava/io/InputStream;
    .local v3, is:Ljava/io/InputStream;
    move v4, v6

    .end local v6           #size:I
    .local v4, size:I
    goto/16 :goto_ed

    .end local v2           #fos:Ljava/io/FileOutputStream;
    .local v3, fos:Ljava/io/FileOutputStream;
    .local v4, is:Ljava/io/InputStream;
    .restart local v6       #size:I
    :cond_18e
    move-object v2, v3

    .end local v3           #fos:Ljava/io/FileOutputStream;
    .restart local v2       #fos:Ljava/io/FileOutputStream;
    move-object v3, v4

    .end local v4           #is:Ljava/io/InputStream;
    .local v3, is:Ljava/io/InputStream;
    move v4, v6

    .end local v6           #size:I
    .local v4, size:I
    move-object v6, v7

    .end local v7           #storeName:Ljava/lang/String;
    .local v6, storeName:Ljava/lang/String;
    move-object v7, v8

    .end local v8           #xmlName:Ljava/lang/String;
    .local v7, xmlName:Ljava/lang/String;
    goto/16 :goto_f2
.end method

.method public static ProductQuery(Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;
    .registers 8
    .parameter "url"
    .parameter "appid"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/irdeto/omp/OmpException;
        }
    .end annotation

    .prologue
    .line 235
    :try_start_0
    sget-object v0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v1, "IVCheck"

    invoke-static {v0, v1}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 236
    .local v0, checkIV:Ljava/lang/String;
    const-string v1, "1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_d
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_d} :catch_72

    move-result v0

    .end local v0           #checkIV:Ljava/lang/String;
    if-nez v0, :cond_63

    .line 247
    :cond_10
    :goto_10
    const/4 v0, 0x0

    .line 248
    .local v0, resulthash:Ljava/util/Hashtable;
    const/4 v1, 0x0

    .line 249
    .local v1, ht:Ljava/util/Hashtable;
    const-string v0, "0"

    .line 251
    .local v0, statusCode:Ljava/lang/String;
    if-eqz p1, :cond_1c

    sget-object v2, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    if-eqz v2, :cond_1c

    if-nez p0, :cond_22

    .line 252
    :cond_1c
    const/16 v0, 0x1f41

    invoke-static {v0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .end local v0           #statusCode:Ljava/lang/String;
    move-result-object v0

    .line 256
    .restart local v0       #statusCode:Ljava/lang/String;
    :cond_22
    :try_start_22
    const-string v2, "0"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_131

    .line 257
    sget-object v0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .end local v0           #statusCode:Ljava/lang/String;
    sget-object v2, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v2, "phone"

    invoke-virtual {v0, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    check-cast v0, Landroid/telephony/TelephonyManager;

    .line 258
    .local v0, telephonyManager:Landroid/telephony/TelephonyManager;
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v2

    .line 259
    .local v2, imsi:Ljava/lang/String;
    if-nez v2, :cond_7b

    .line 260
    new-instance p0, Lcom/irdeto/omp/OmpException;

    .end local p0
    const p1, 0x10005

    invoke-direct {p0, p1}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    .end local p1
    throw p0
    :try_end_47
    .catch Ljava/lang/Exception; {:try_start_22 .. :try_end_47} :catch_47

    .line 300
    .end local v0           #telephonyManager:Landroid/telephony/TelephonyManager;
    .end local v2           #imsi:Ljava/lang/String;
    :catch_47
    move-exception p0

    move-object p1, v1

    .line 301
    .end local v1           #ht:Ljava/util/Hashtable;
    .local p0, e:Ljava/lang/Exception;
    .local p1, ht:Ljava/util/Hashtable;
    :goto_49
    const-string p1, "OmpClient"

    .end local p1           #ht:Ljava/util/Hashtable;
    invoke-virtual {p0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p0

    .end local p0           #e:Ljava/lang/Exception;
    invoke-static {p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 302
    const/16 p0, 0x1f54

    invoke-static {p0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object p1

    .line 303
    .local p1, statusCode:Ljava/lang/String;
    new-instance p0, Ljava/util/Hashtable;

    invoke-direct {p0}, Ljava/util/Hashtable;-><init>()V

    .line 306
    .local p0, ht:Ljava/util/Hashtable;
    :goto_5d
    const-string v0, "errorCode"

    invoke-virtual {p0, v0, p1}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 307
    return-object p0

    .line 239
    .local p0, url:Ljava/lang/String;
    .local p1, appid:Ljava/lang/String;
    :cond_63
    :try_start_63
    invoke-static {}, Lcom/irdeto/omp/OmpClient;->checkPermission()Z

    move-result v0

    if-nez v0, :cond_10

    .line 240
    new-instance v0, Lcom/irdeto/omp/OmpException;

    const v1, 0x10001

    invoke-direct {v0, v1}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v0
    :try_end_72
    .catch Ljava/lang/Exception; {:try_start_63 .. :try_end_72} :catch_72

    .line 242
    :catch_72
    move-exception v0

    .line 243
    .local v0, e:Ljava/lang/Exception;
    const-string v0, "OMP"

    .end local v0           #e:Ljava/lang/Exception;
    const-string v1, "ProductQuery()--checkPermission() succeded!"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_10

    .line 262
    .local v0, telephonyManager:Landroid/telephony/TelephonyManager;
    .restart local v1       #ht:Ljava/util/Hashtable;
    .restart local v2       #imsi:Ljava/lang/String;
    :cond_7b
    const/4 v0, 0x1

    :try_start_7c
    new-array v0, v0, [I

    .line 263
    .local v0, NumArray:[I
    invoke-static {p0, p1, v2, v0}, Lcom/irdeto/omp/OmpClient;->nProductQuery(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[I)Ljava/util/Hashtable;
    :try_end_81
    .catch Ljava/lang/Exception; {:try_start_7c .. :try_end_81} :catch_47

    move-result-object p0

    .line 265
    .end local v1           #ht:Ljava/util/Hashtable;
    .local p0, ht:Ljava/util/Hashtable;
    if-nez p0, :cond_92

    .line 266
    :try_start_84
    new-instance p1, Lcom/irdeto/omp/OmpException;

    .end local p1           #appid:Ljava/lang/String;
    const v0, 0x10005

    invoke-direct {p1, v0}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    .end local v0           #NumArray:[I
    throw p1

    .line 300
    .end local v2           #imsi:Ljava/lang/String;
    :catch_8d
    move-exception p1

    move-object v5, p1

    move-object p1, p0

    .end local p0           #ht:Ljava/util/Hashtable;
    .local p1, ht:Ljava/util/Hashtable;
    move-object p0, v5

    goto :goto_49

    .line 269
    .restart local v0       #NumArray:[I
    .restart local v2       #imsi:Ljava/lang/String;
    .restart local p0       #ht:Ljava/util/Hashtable;
    .local p1, appid:Ljava/lang/String;
    :cond_92
    const/4 p1, 0x0

    aget v2, v0, p1

    .line 270
    .end local p1           #appid:Ljava/lang/String;
    .local v2, strSize:I
    const/4 p1, 0x2

    if-le v2, p1, :cond_110

    .line 272
    new-instance p1, Ljava/lang/String;

    const-string v0, "appid"

    .end local v0           #NumArray:[I
    invoke-virtual {p0, v0}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [B

    check-cast v0, [B

    invoke-direct {p1, v0}, Ljava/lang/String;-><init>([B)V

    .line 273
    .local p1, strAppID:Ljava/lang/String;
    const-string v0, "appid"

    invoke-virtual {p0, v0, p1}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 275
    const/4 p1, 0x3

    sub-int p1, v2, p1

    rem-int/lit8 p1, p1, 0x2

    .end local p1           #strAppID:Ljava/lang/String;
    if-nez p1, :cond_110

    .line 276
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 278
    .local v1, recordList:Ljava/util/ArrayList;
    const/4 p1, 0x0

    .local p1, i:I
    :goto_b9
    const/4 v0, 0x3

    sub-int v0, v2, v0

    div-int/lit8 v0, v0, 0x2

    if-ge p1, v0, :cond_10b

    .line 280
    new-instance v0, Lcom/irdeto/omp/chargeRecord;

    invoke-direct {v0}, Lcom/irdeto/omp/chargeRecord;-><init>()V

    .line 281
    .local v0, record:Lcom/irdeto/omp/chargeRecord;
    new-instance v4, Ljava/lang/String;

    mul-int/lit8 v3, p1, 0x2

    invoke-static {v3}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, [B

    check-cast v3, [B

    invoke-direct {v4, v3}, Ljava/lang/String;-><init>([B)V

    iput-object v4, v0, Lcom/irdeto/omp/chargeRecord;->apppid:Ljava/lang/String;

    .line 282
    new-instance v4, Ljava/lang/String;

    mul-int/lit8 v3, p1, 0x2

    add-int/lit8 v3, v3, 0x1

    invoke-static {v3}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, [B

    check-cast v3, [B

    invoke-direct {v4, v3}, Ljava/lang/String;-><init>([B)V

    iput-object v4, v0, Lcom/irdeto/omp/chargeRecord;->apppdesc:Ljava/lang/String;

    .line 283
    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 284
    mul-int/lit8 v0, p1, 0x2

    invoke-static {v0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    .end local v0           #record:Lcom/irdeto/omp/chargeRecord;
    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/Hashtable;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 285
    mul-int/lit8 v0, p1, 0x2

    add-int/lit8 v0, v0, 0x1

    invoke-static {v0}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/util/Hashtable;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 278
    add-int/lit8 p1, p1, 0x1

    goto :goto_b9

    .line 288
    :cond_10b
    const-string p1, "chargeRecord"

    .end local p1           #i:I
    invoke-virtual {p0, p1, v1}, Ljava/util/Hashtable;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 291
    .end local v1           #recordList:Ljava/util/ArrayList;
    :cond_110
    const-string p1, "errorCode"

    invoke-virtual {p0, p1}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 293
    .local p1, rs:Ljava/lang/String;
    if-eqz p1, :cond_129

    invoke-static {p1}, Ljava/lang/Integer;->decode(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const/16 v1, 0x270f

    if-gt v0, v1, :cond_129

    .line 294
    move-object p1, p1

    .local p1, statusCode:Ljava/lang/String;
    goto/16 :goto_5d

    .line 297
    .local p1, rs:Ljava/lang/String;
    :cond_129
    const/16 p1, 0x1f54

    invoke-static {p1}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;
    :try_end_12e
    .catch Ljava/lang/Exception; {:try_start_84 .. :try_end_12e} :catch_8d

    .end local p1           #rs:Ljava/lang/String;
    move-result-object p1

    .local p1, statusCode:Ljava/lang/String;
    goto/16 :goto_5d

    .end local v2           #strSize:I
    .local v0, statusCode:Ljava/lang/String;
    .local v1, ht:Ljava/util/Hashtable;
    .local p0, url:Ljava/lang/String;
    .local p1, appid:Ljava/lang/String;
    :cond_131
    move-object p1, v0

    .end local v0           #statusCode:Ljava/lang/String;
    .local p1, statusCode:Ljava/lang/String;
    move-object p0, v1

    .end local v1           #ht:Ljava/util/Hashtable;
    .local p0, ht:Ljava/util/Hashtable;
    goto/16 :goto_5d
.end method

.method public static ProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    .registers 16
    .parameter "url"
    .parameter "appid"
    .parameter "apppid"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/irdeto/omp/OmpException;
        }
    .end annotation

    .prologue
    const/16 v11, 0x1f54

    const-string v12, "OMP"

    .line 75
    :try_start_4
    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v10, "IVCheck"

    invoke-static {v9, v10}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 76
    .local v1, checkIV:Ljava/lang/String;
    const-string v9, "1"

    invoke-virtual {v1, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_11
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_11} :catch_69

    move-result v9

    if-nez v9, :cond_5a

    .line 87
    .end local v1           #checkIV:Ljava/lang/String;
    :cond_14
    :goto_14
    const-string v6, "0"

    .line 88
    .local v6, statusCode:Ljava/lang/String;
    const/4 v7, 0x0

    .line 89
    .local v7, strtmp:Ljava/lang/String;
    const/4 v3, 0x0

    .line 91
    .local v3, ht:Ljava/util/Hashtable;
    if-eqz p0, :cond_22

    if-eqz p1, :cond_22

    if-eqz p2, :cond_22

    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    if-nez v9, :cond_28

    .line 92
    :cond_22
    const/16 v9, 0x1f41

    invoke-static {v9}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v6

    .line 96
    :cond_28
    :try_start_28
    const-string v9, "0"

    invoke-virtual {v6, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_99

    .line 98
    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    sget-object v10, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v10, "phone"

    invoke-virtual {v9, v10}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Landroid/telephony/TelephonyManager;

    move-object v0, v9

    check-cast v0, Landroid/telephony/TelephonyManager;

    move-object v8, v0

    .line 99
    .local v8, telephonyManager:Landroid/telephony/TelephonyManager;
    invoke-virtual {v8}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v4

    .line 101
    .local v4, imsi:Ljava/lang/String;
    if-nez v4, :cond_73

    .line 102
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10005

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9
    :try_end_4f
    .catch Ljava/lang/Exception; {:try_start_28 .. :try_end_4f} :catch_4f

    .line 120
    .end local v4           #imsi:Ljava/lang/String;
    .end local v8           #telephonyManager:Landroid/telephony/TelephonyManager;
    :catch_4f
    move-exception v9

    move-object v2, v9

    .line 121
    .local v2, e:Ljava/lang/Exception;
    const-string v9, "OMP"

    const-string v9, "ProductSub exception"

    invoke-static {v12, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    move v9, v11

    .line 125
    .end local v2           #e:Ljava/lang/Exception;
    :goto_59
    return v9

    .line 79
    .end local v3           #ht:Ljava/util/Hashtable;
    .end local v6           #statusCode:Ljava/lang/String;
    .end local v7           #strtmp:Ljava/lang/String;
    .restart local v1       #checkIV:Ljava/lang/String;
    :cond_5a
    :try_start_5a
    invoke-static {}, Lcom/irdeto/omp/OmpClient;->checkPermission()Z

    move-result v9

    if-nez v9, :cond_14

    .line 80
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10001

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9
    :try_end_69
    .catch Ljava/lang/Exception; {:try_start_5a .. :try_end_69} :catch_69

    .line 82
    .end local v1           #checkIV:Ljava/lang/String;
    :catch_69
    move-exception v9

    move-object v2, v9

    .line 83
    .restart local v2       #e:Ljava/lang/Exception;
    const-string v9, "OMP"

    const-string v9, "Productsub()--checkPermission() succeded!"

    invoke-static {v12, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_14

    .line 105
    .end local v2           #e:Ljava/lang/Exception;
    .restart local v3       #ht:Ljava/util/Hashtable;
    .restart local v4       #imsi:Ljava/lang/String;
    .restart local v6       #statusCode:Ljava/lang/String;
    .restart local v7       #strtmp:Ljava/lang/String;
    .restart local v8       #telephonyManager:Landroid/telephony/TelephonyManager;
    :cond_73
    :try_start_73
    invoke-static {p0, p1, p2, v4}, Lcom/irdeto/omp/OmpClient;->nProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;

    move-result-object v3

    .line 107
    if-nez v3, :cond_82

    .line 108
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10005

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9

    .line 111
    :cond_82
    const-string v9, "errorCode"

    invoke-virtual {v3, v9}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 113
    .local v5, rs:Ljava/lang/String;
    if-eqz v5, :cond_a2

    invoke-static {v5}, Ljava/lang/Integer;->decode(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I
    :try_end_93
    .catch Ljava/lang/Exception; {:try_start_73 .. :try_end_93} :catch_4f

    move-result v9

    const/16 v10, 0x270f

    if-gt v9, v10, :cond_a2

    .line 114
    move-object v6, v5

    .line 125
    .end local v4           #imsi:Ljava/lang/String;
    .end local v5           #rs:Ljava/lang/String;
    .end local v8           #telephonyManager:Landroid/telephony/TelephonyManager;
    :cond_99
    :goto_99
    invoke-static {v6}, Ljava/lang/Integer;->decode(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I

    move-result v9

    goto :goto_59

    .line 117
    .restart local v4       #imsi:Ljava/lang/String;
    .restart local v5       #rs:Ljava/lang/String;
    .restart local v8       #telephonyManager:Landroid/telephony/TelephonyManager;
    :cond_a2
    const/16 v9, 0x1f54

    :try_start_a4
    invoke-static {v9}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;
    :try_end_a7
    .catch Ljava/lang/Exception; {:try_start_a4 .. :try_end_a7} :catch_4f

    move-result-object v6

    goto :goto_99
.end method

.method public static ProductUnsub(Ljava/lang/String;Ljava/lang/String;)I
    .registers 14
    .parameter "url"
    .parameter "appid"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/irdeto/omp/OmpException;
        }
    .end annotation

    .prologue
    const/16 v11, 0x1f54

    .line 314
    :try_start_2
    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v10, "IVCheck"

    invoke-static {v9, v10}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 315
    .local v2, checkIV:Ljava/lang/String;
    const-string v9, "1"

    invoke-virtual {v2, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_f
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_f} :catch_70

    move-result v9

    if-nez v9, :cond_61

    .line 326
    .end local v2           #checkIV:Ljava/lang/String;
    :cond_12
    :goto_12
    const-string v7, "0"

    .line 327
    .local v7, statusCode:Ljava/lang/String;
    const/4 v4, 0x0

    .line 328
    .local v4, ht:Ljava/util/Hashtable;
    if-eqz p0, :cond_1d

    if-eqz p1, :cond_1d

    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    if-nez v9, :cond_23

    .line 329
    :cond_1d
    const/16 v9, 0x1f41

    invoke-static {v9}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v7

    .line 333
    :cond_23
    const/4 v9, 0x1

    new-array v1, v9, [I

    .line 336
    .local v1, arrLength:[I
    :try_start_26
    const-string v9, "0"

    invoke-virtual {v7, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_5c

    .line 337
    sget-object v9, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    sget-object v10, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v10, "phone"

    invoke-virtual {v9, v10}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Landroid/telephony/TelephonyManager;

    move-object v0, v9

    check-cast v0, Landroid/telephony/TelephonyManager;

    move-object v8, v0

    .line 338
    .local v8, telephonyManager:Landroid/telephony/TelephonyManager;
    invoke-virtual {v8}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    move-result-object v5

    .line 339
    .local v5, imsi:Ljava/lang/String;
    if-nez v5, :cond_7a

    .line 340
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10005

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9
    :try_end_4d
    .catch Ljava/lang/Exception; {:try_start_26 .. :try_end_4d} :catch_4d

    .line 357
    .end local v5           #imsi:Ljava/lang/String;
    .end local v8           #telephonyManager:Landroid/telephony/TelephonyManager;
    :catch_4d
    move-exception v9

    move-object v3, v9

    .line 358
    .local v3, e:Ljava/lang/Exception;
    const-string v9, "OmpClient"

    invoke-virtual {v3}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 359
    invoke-static {v11}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v7

    .line 362
    .end local v3           #e:Ljava/lang/Exception;
    :cond_5c
    :goto_5c
    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v9

    return v9

    .line 318
    .end local v1           #arrLength:[I
    .end local v4           #ht:Ljava/util/Hashtable;
    .end local v7           #statusCode:Ljava/lang/String;
    .restart local v2       #checkIV:Ljava/lang/String;
    :cond_61
    :try_start_61
    invoke-static {}, Lcom/irdeto/omp/OmpClient;->checkPermission()Z

    move-result v9

    if-nez v9, :cond_12

    .line 319
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10001

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9
    :try_end_70
    .catch Ljava/lang/Exception; {:try_start_61 .. :try_end_70} :catch_70

    .line 321
    .end local v2           #checkIV:Ljava/lang/String;
    :catch_70
    move-exception v9

    move-object v3, v9

    .line 322
    .restart local v3       #e:Ljava/lang/Exception;
    const-string v9, "OMP"

    const-string v10, "ProductUnsub()--checkPermission() succeded!"

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_12

    .line 342
    .end local v3           #e:Ljava/lang/Exception;
    .restart local v1       #arrLength:[I
    .restart local v4       #ht:Ljava/util/Hashtable;
    .restart local v5       #imsi:Ljava/lang/String;
    .restart local v7       #statusCode:Ljava/lang/String;
    .restart local v8       #telephonyManager:Landroid/telephony/TelephonyManager;
    :cond_7a
    :try_start_7a
    invoke-static {p0, p1, v5}, Lcom/irdeto/omp/OmpClient;->nProductUnsub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;

    move-result-object v4

    .line 344
    if-nez v4, :cond_89

    .line 346
    new-instance v9, Lcom/irdeto/omp/OmpException;

    const v10, 0x10005

    invoke-direct {v9, v10}, Lcom/irdeto/omp/OmpException;-><init>(I)V

    throw v9

    .line 349
    :cond_89
    const-string v9, "errorCode"

    invoke-virtual {v4, v9}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 350
    .local v6, rs:Ljava/lang/String;
    if-eqz v6, :cond_a1

    invoke-static {v6}, Ljava/lang/Integer;->decode(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I

    move-result v9

    const/16 v10, 0x270f

    if-gt v9, v10, :cond_a1

    .line 351
    move-object v7, v6

    goto :goto_5c

    .line 354
    :cond_a1
    const/16 v9, 0x1f54

    invoke-static {v9}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;
    :try_end_a6
    .catch Ljava/lang/Exception; {:try_start_7a .. :try_end_a6} :catch_4d

    move-result-object v7

    goto :goto_5c
.end method

.method public static declared-synchronized checkPermission()Z
    .registers 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const-string v4, "1"

    .line 46
    const-class v4, Lcom/irdeto/omp/OmpClient;

    monitor-enter v4

    const/4 v0, 0x0

    .line 47
    .local v0, bRet:Z
    :try_start_6
    sget-object v5, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    const-string v6, "IVCheck"

    invoke-static {v5, v6}, Lcom/irdeto/omp/OmpClient;->getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 48
    .local v1, checkIV:Ljava/lang/String;
    const-string v5, "1"

    invoke-virtual {v1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_13
    .catchall {:try_start_6 .. :try_end_13} :catchall_30

    move-result v5

    if-nez v5, :cond_19

    .line 49
    const/4 v0, 0x1

    .line 64
    :cond_17
    :goto_17
    monitor-exit v4

    return v0

    .line 53
    :cond_19
    const/4 v3, 0x0

    .line 55
    .local v3, retCode:Ljava/lang/String;
    :try_start_1a
    const-string v3, "1"

    .line 56
    const-string v5, "1"

    invoke-virtual {v3, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_21
    .catchall {:try_start_1a .. :try_end_21} :catchall_30
    .catch Ljava/lang/Exception; {:try_start_1a .. :try_end_21} :catch_26

    move-result v5

    if-eqz v5, :cond_17

    .line 57
    const/4 v0, 0x1

    goto :goto_17

    .line 60
    :catch_26
    move-exception v5

    move-object v2, v5

    .line 62
    .local v2, e:Ljava/lang/Exception;
    :try_start_28
    const-string v5, "OMP"

    const-string v6, "checkPermission() succeded!"

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2f
    .catchall {:try_start_28 .. :try_end_2f} :catchall_30

    goto :goto_17

    .line 46
    .end local v1           #checkIV:Ljava/lang/String;
    .end local v2           #e:Ljava/lang/Exception;
    .end local v3           #retCode:Ljava/lang/String;
    :catchall_30
    move-exception v5

    monitor-exit v4

    throw v5
.end method

.method private static getXmlNodeValue(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .registers 12
    .parameter "ctxObj"
    .parameter "nodeName"

    .prologue
    .line 481
    const-string v0, "IVCheckDate"

    .line 482
    .local v0, IVCheckTag:Ljava/lang/String;
    const-string v6, ""

    .line 483
    .local v6, xmlPath:Ljava/lang/String;
    const/4 v5, 0x0

    .line 484
    .local v5, xmlDate:Ljava/lang/String;
    if-nez p0, :cond_9

    .line 485
    const/4 v7, 0x0

    .line 506
    :goto_8
    return-object v7

    .line 487
    :cond_9
    sput-object p0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .line 490
    :try_start_b
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v8

    invoke-virtual {v8}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "/LsOmpClientSettings.xml"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 491
    invoke-static {}, Ljavax/xml/parsers/DocumentBuilderFactory;->newInstance()Ljavax/xml/parsers/DocumentBuilderFactory;

    move-result-object v3

    .line 492
    .local v3, docBuilderFactory:Ljavax/xml/parsers/DocumentBuilderFactory;
    invoke-virtual {v3}, Ljavax/xml/parsers/DocumentBuilderFactory;->newDocumentBuilder()Ljavax/xml/parsers/DocumentBuilder;

    move-result-object v2

    .line 493
    .local v2, docBuilder:Ljavax/xml/parsers/DocumentBuilder;
    new-instance v7, Ljava/io/File;

    invoke-direct {v7, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v7}, Ljavax/xml/parsers/DocumentBuilder;->parse(Ljava/io/File;)Lorg/w3c/dom/Document;

    move-result-object v1

    .line 495
    .local v1, doc:Lorg/w3c/dom/Document;
    invoke-interface {v1, v0}, Lorg/w3c/dom/Document;->getElementsByTagName(Ljava/lang/String;)Lorg/w3c/dom/NodeList;

    move-result-object v7

    const/4 v8, 0x0

    invoke-interface {v7, v8}, Lorg/w3c/dom/NodeList;->item(I)Lorg/w3c/dom/Node;

    move-result-object v7

    invoke-interface {v7}, Lorg/w3c/dom/Node;->getFirstChild()Lorg/w3c/dom/Node;

    move-result-object v7

    invoke-interface {v7}, Lorg/w3c/dom/Node;->getNodeValue()Ljava/lang/String;
    :try_end_47
    .catch Ljavax/xml/parsers/ParserConfigurationException; {:try_start_b .. :try_end_47} :catch_4a
    .catch Lorg/xml/sax/SAXException; {:try_start_b .. :try_end_47} :catch_69
    .catch Ljava/io/IOException; {:try_start_b .. :try_end_47} :catch_88

    move-result-object v5

    .end local v1           #doc:Lorg/w3c/dom/Document;
    .end local v2           #docBuilder:Ljavax/xml/parsers/DocumentBuilder;
    .end local v3           #docBuilderFactory:Ljavax/xml/parsers/DocumentBuilderFactory;
    :goto_48
    move-object v7, v5

    .line 506
    goto :goto_8

    .line 497
    :catch_4a
    move-exception v7

    move-object v4, v7

    .line 498
    .local v4, e:Ljavax/xml/parsers/ParserConfigurationException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML Parser configuration error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Ljavax/xml/parsers/ParserConfigurationException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48

    .line 500
    .end local v4           #e:Ljavax/xml/parsers/ParserConfigurationException;
    :catch_69
    move-exception v7

    move-object v4, v7

    .line 501
    .local v4, e:Lorg/xml/sax/SAXException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML SAX error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Lorg/xml/sax/SAXException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48

    .line 503
    .end local v4           #e:Lorg/xml/sax/SAXException;
    :catch_88
    move-exception v7

    move-object v4, v7

    .line 504
    .local v4, e:Ljava/io/IOException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML IO error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48
.end method

.method private static getXmlNodeValue2(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .registers 12
    .parameter "ctxObj"
    .parameter "nodeName"

    .prologue
    .line 510
    const-string v0, "IVCheck"

    .line 511
    .local v0, IVCheckTag:Ljava/lang/String;
    const-string v6, ""

    .line 512
    .local v6, xmlPath:Ljava/lang/String;
    const/4 v5, 0x0

    .line 513
    .local v5, xmlDate:Ljava/lang/String;
    if-nez p0, :cond_9

    .line 514
    const/4 v7, 0x0

    .line 535
    :goto_8
    return-object v7

    .line 516
    :cond_9
    sput-object p0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .line 519
    :try_start_b
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v8

    invoke-virtual {v8}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "/LsOmpClientSettings.xml"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 520
    invoke-static {}, Ljavax/xml/parsers/DocumentBuilderFactory;->newInstance()Ljavax/xml/parsers/DocumentBuilderFactory;

    move-result-object v3

    .line 521
    .local v3, docBuilderFactory:Ljavax/xml/parsers/DocumentBuilderFactory;
    invoke-virtual {v3}, Ljavax/xml/parsers/DocumentBuilderFactory;->newDocumentBuilder()Ljavax/xml/parsers/DocumentBuilder;

    move-result-object v2

    .line 522
    .local v2, docBuilder:Ljavax/xml/parsers/DocumentBuilder;
    new-instance v7, Ljava/io/File;

    invoke-direct {v7, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v7}, Ljavax/xml/parsers/DocumentBuilder;->parse(Ljava/io/File;)Lorg/w3c/dom/Document;

    move-result-object v1

    .line 524
    .local v1, doc:Lorg/w3c/dom/Document;
    invoke-interface {v1, v0}, Lorg/w3c/dom/Document;->getElementsByTagName(Ljava/lang/String;)Lorg/w3c/dom/NodeList;

    move-result-object v7

    const/4 v8, 0x0

    invoke-interface {v7, v8}, Lorg/w3c/dom/NodeList;->item(I)Lorg/w3c/dom/Node;

    move-result-object v7

    invoke-interface {v7}, Lorg/w3c/dom/Node;->getFirstChild()Lorg/w3c/dom/Node;

    move-result-object v7

    invoke-interface {v7}, Lorg/w3c/dom/Node;->getNodeValue()Ljava/lang/String;
    :try_end_47
    .catch Ljavax/xml/parsers/ParserConfigurationException; {:try_start_b .. :try_end_47} :catch_4a
    .catch Lorg/xml/sax/SAXException; {:try_start_b .. :try_end_47} :catch_69
    .catch Ljava/io/IOException; {:try_start_b .. :try_end_47} :catch_88

    move-result-object v5

    .end local v1           #doc:Lorg/w3c/dom/Document;
    .end local v2           #docBuilder:Ljavax/xml/parsers/DocumentBuilder;
    .end local v3           #docBuilderFactory:Ljavax/xml/parsers/DocumentBuilderFactory;
    :goto_48
    move-object v7, v5

    .line 535
    goto :goto_8

    .line 526
    :catch_4a
    move-exception v7

    move-object v4, v7

    .line 527
    .local v4, e:Ljavax/xml/parsers/ParserConfigurationException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML Parser configuration error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Ljavax/xml/parsers/ParserConfigurationException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48

    .line 529
    .end local v4           #e:Ljavax/xml/parsers/ParserConfigurationException;
    :catch_69
    move-exception v7

    move-object v4, v7

    .line 530
    .local v4, e:Lorg/xml/sax/SAXException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML SAX error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Lorg/xml/sax/SAXException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48

    .line 532
    .end local v4           #e:Lorg/xml/sax/SAXException;
    :catch_88
    move-exception v7

    move-object v4, v7

    .line 533
    .local v4, e:Ljava/io/IOException;
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "XML IO error: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v4}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_48
.end method

.method private static native nEnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;
.end method

.method private static native nGetSubStatus(Ljava/lang/String;)Z
.end method

.method private static native nProductQuery(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[I)Ljava/util/Hashtable;
.end method

.method private static native nProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;
.end method

.method private static native nProductUnsub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;
.end method

.method private static native nSetFileName(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
.end method

.method private static setXmlNodeValue(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .registers 8
    .parameter "ctxObj"
    .parameter "nodeName"
    .parameter "nodeValue"

    .prologue
    .line 539
    const-string v0, ""

    .line 540
    .local v0, xmlPath:Ljava/lang/String;
    sput-object p0, Lcom/irdeto/omp/OmpClient;->ctx:Landroid/content/Context;

    .line 542
    :try_start_4
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object p0

    .end local p0
    invoke-virtual {p0}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, "/LsOmpClientSettings.xml"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_1e
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_1e} :catch_bf

    move-result-object v3

    .line 543
    .end local v0           #xmlPath:Ljava/lang/String;
    .local v3, xmlPath:Ljava/lang/String;
    :try_start_1f
    const-string v2, ""

    .line 544
    .local v2, temp:Ljava/lang/String;
    new-instance p0, Ljava/io/File;

    invoke-direct {p0, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 545
    .local p0, file:Ljava/io/File;
    new-instance v0, Ljava/io/FileInputStream;

    invoke-direct {v0, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 546
    .local v0, fis:Ljava/io/FileInputStream;
    new-instance v1, Ljava/io/InputStreamReader;

    invoke-direct {v1, v0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    .line 547
    .local v1, isr:Ljava/io/InputStreamReader;
    new-instance p0, Ljava/io/BufferedReader;

    .end local p0           #file:Ljava/io/File;
    invoke-direct {p0, v1}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 548
    .local p0, br:Ljava/io/BufferedReader;
    new-instance v0, Ljava/lang/StringBuffer;

    .end local v0           #fis:Ljava/io/FileInputStream;
    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 550
    .local v0, buf:Ljava/lang/StringBuffer;
    const/4 v1, 0x1

    .local v1, i:I
    :goto_3b
    invoke-virtual {p0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_58

    invoke-virtual {v2, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_58

    .line 552
    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v0

    .line 553
    const-string v4, "line.separator"

    invoke-static {v4}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v0

    .line 550
    add-int/lit8 v1, v1, 0x1

    goto :goto_3b

    .line 555
    :cond_58
    new-instance v1, Ljava/lang/StringBuilder;

    .end local v1           #i:I
    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "<"

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v4, ">"

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    .end local p2
    const-string v1, "</"

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    .end local p1
    const-string p2, ">"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 556
    .local p1, target:Ljava/lang/String;
    invoke-virtual {v0, p1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object p1

    .end local v0           #buf:Ljava/lang/StringBuffer;
    .local p1, buf:Ljava/lang/StringBuffer;
    move-object p2, v2

    .line 558
    .end local v2           #temp:Ljava/lang/String;
    .local p2, temp:Ljava/lang/String;
    :goto_8a
    invoke-virtual {p0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p2

    if-eqz p2, :cond_9f

    .line 560
    const-string v0, "line.separator"

    invoke-static {v0}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object p1

    .line 561
    invoke-virtual {p1, p2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object p1

    goto :goto_8a

    .line 563
    :cond_9f
    invoke-virtual {p0}, Ljava/io/BufferedReader;->close()V

    .line 564
    new-instance p0, Ljava/io/FileOutputStream;

    .end local p0           #br:Ljava/io/BufferedReader;
    invoke-direct {p0, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;)V

    .line 565
    .local p0, fos:Ljava/io/FileOutputStream;
    new-instance p2, Ljava/io/PrintWriter;

    .end local p2           #temp:Ljava/lang/String;
    invoke-direct {p2, p0}, Ljava/io/PrintWriter;-><init>(Ljava/io/OutputStream;)V

    .line 566
    .local p2, pw:Ljava/io/PrintWriter;
    invoke-virtual {p1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object p0

    .end local p0           #fos:Ljava/io/FileOutputStream;
    invoke-virtual {p0}, Ljava/lang/String;->toCharArray()[C

    move-result-object p0

    invoke-virtual {p2, p0}, Ljava/io/PrintWriter;->write([C)V

    .line 567
    invoke-virtual {p2}, Ljava/io/PrintWriter;->flush()V

    .line 568
    invoke-virtual {p2}, Ljava/io/PrintWriter;->close()V
    :try_end_bd
    .catch Ljava/io/IOException; {:try_start_1f .. :try_end_bd} :catch_df

    move-object p0, v3

    .line 573
    .end local v3           #xmlPath:Ljava/lang/String;
    .end local p1           #buf:Ljava/lang/StringBuffer;
    .end local p2           #pw:Ljava/io/PrintWriter;
    .local p0, xmlPath:Ljava/lang/String;
    :goto_be
    return-void

    .line 570
    .end local p0           #xmlPath:Ljava/lang/String;
    .local v0, xmlPath:Ljava/lang/String;
    .local p1, nodeName:Ljava/lang/String;
    .local p2, nodeValue:Ljava/lang/String;
    :catch_bf
    move-exception p0

    move-object p1, v0

    .line 571
    .end local v0           #xmlPath:Ljava/lang/String;
    .end local p2           #nodeValue:Ljava/lang/String;
    .local p0, e:Ljava/io/IOException;
    .local p1, xmlPath:Ljava/lang/String;
    :goto_c1
    sget-object p2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "IO error: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p0}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object p0

    .end local p0           #e:Ljava/io/IOException;
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p2, p0}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    move-object p0, p1

    .end local p1           #xmlPath:Ljava/lang/String;
    .local p0, xmlPath:Ljava/lang/String;
    goto :goto_be

    .line 570
    .end local p0           #xmlPath:Ljava/lang/String;
    .restart local v3       #xmlPath:Ljava/lang/String;
    :catch_df
    move-exception p0

    move-object p1, v3

    .end local v3           #xmlPath:Ljava/lang/String;
    .restart local p1       #xmlPath:Ljava/lang/String;
    goto :goto_c1
.end method
