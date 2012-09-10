.class public Lcom/irdeto/security/common/IOUtils;
.super Ljava/lang/Object;
.source "IOUtils.java"


# instance fields
.field final MYTAG:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 26
    const-string v0, "IOUtils"

    iput-object v0, p0, Lcom/irdeto/security/common/IOUtils;->MYTAG:Ljava/lang/String;

    return-void
.end method

.method public static getFileContents(Ljava/lang/String;Ljava/util/ArrayList;)V
    .registers 6
    .parameter "fullFilePath"
    .parameter "array"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 80
    const/4 v1, 0x0

    .line 81
    .local v1, tmpString:Ljava/lang/String;
    new-instance v0, Ljava/io/BufferedReader;

    new-instance v2, Ljava/io/FileReader;

    new-instance v3, Ljava/io/File;

    invoke-direct {v3, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-direct {v2, v3}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    invoke-direct {v0, v2}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 83
    .local v0, buf:Ljava/io/BufferedReader;
    :goto_10
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_1a

    .line 84
    invoke-virtual {p1, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_10

    .line 86
    :cond_1a
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V

    .line 87
    return-void
.end method


# virtual methods
.method public getBytesFromObject(Ljava/lang/String;)[B
    .registers 10
    .parameter "myClassName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 30
    const/4 v3, 0x0

    .line 31
    .local v3, objBytes:[B
    const/4 v4, 0x0

    .line 33
    .local v4, objectStream:Ljava/io/ObjectOutputStream;
    :try_start_2
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    .line 34
    .local v1, cls:Ljava/lang/Class;
    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 35
    .local v0, byteStream:Ljava/io/ByteArrayOutputStream;
    new-instance v5, Ljava/io/ObjectOutputStream;

    invoke-direct {v5, v0}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V
    :try_end_10
    .catchall {:try_start_2 .. :try_end_10} :catchall_29
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_10} :catch_23

    .line 36
    .end local v4           #objectStream:Ljava/io/ObjectOutputStream;
    .local v5, objectStream:Ljava/io/ObjectOutputStream;
    :try_start_10
    invoke-virtual {v5, v1}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 37
    invoke-virtual {v5}, Ljava/io/ObjectOutputStream;->flush()V

    .line 38
    invoke-virtual {v0}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v3

    .line 39
    invoke-virtual {v5}, Ljava/io/ObjectOutputStream;->close()V
    :try_end_1d
    .catchall {:try_start_10 .. :try_end_1d} :catchall_34
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_1d} :catch_37

    .line 46
    if-eqz v5, :cond_22

    :try_start_1f
    invoke-virtual {v5}, Ljava/io/ObjectOutputStream;->close()V
    :try_end_22
    .catch Ljava/lang/Exception; {:try_start_1f .. :try_end_22} :catch_30

    .line 49
    :cond_22
    :goto_22
    return-object v3

    .line 41
    .end local v0           #byteStream:Ljava/io/ByteArrayOutputStream;
    .end local v1           #cls:Ljava/lang/Class;
    .end local v5           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v4       #objectStream:Ljava/io/ObjectOutputStream;
    :catch_23
    move-exception v6

    move-object v2, v6

    .line 42
    .local v2, e:Ljava/lang/Exception;
    :goto_25
    :try_start_25
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    .line 43
    throw v2
    :try_end_29
    .catchall {:try_start_25 .. :try_end_29} :catchall_29

    .line 45
    .end local v2           #e:Ljava/lang/Exception;
    :catchall_29
    move-exception v6

    .line 46
    :goto_2a
    if-eqz v4, :cond_2f

    :try_start_2c
    invoke-virtual {v4}, Ljava/io/ObjectOutputStream;->close()V
    :try_end_2f
    .catch Ljava/lang/Exception; {:try_start_2c .. :try_end_2f} :catch_32

    .line 47
    :cond_2f
    :goto_2f
    throw v6

    .end local v4           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v0       #byteStream:Ljava/io/ByteArrayOutputStream;
    .restart local v1       #cls:Ljava/lang/Class;
    .restart local v5       #objectStream:Ljava/io/ObjectOutputStream;
    :catch_30
    move-exception v6

    goto :goto_22

    .end local v0           #byteStream:Ljava/io/ByteArrayOutputStream;
    .end local v1           #cls:Ljava/lang/Class;
    .end local v5           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v4       #objectStream:Ljava/io/ObjectOutputStream;
    :catch_32
    move-exception v7

    goto :goto_2f

    .line 45
    .end local v4           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v0       #byteStream:Ljava/io/ByteArrayOutputStream;
    .restart local v1       #cls:Ljava/lang/Class;
    .restart local v5       #objectStream:Ljava/io/ObjectOutputStream;
    :catchall_34
    move-exception v6

    move-object v4, v5

    .end local v5           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v4       #objectStream:Ljava/io/ObjectOutputStream;
    goto :goto_2a

    .line 41
    .end local v4           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v5       #objectStream:Ljava/io/ObjectOutputStream;
    :catch_37
    move-exception v6

    move-object v2, v6

    move-object v4, v5

    .end local v5           #objectStream:Ljava/io/ObjectOutputStream;
    .restart local v4       #objectStream:Ljava/io/ObjectOutputStream;
    goto :goto_25
.end method

.method public getBytesFromSerializable(Ljava/lang/String;)[B
    .registers 11
    .parameter "myClassName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 53
    const/4 v4, 0x0

    .line 55
    .local v4, retBytes:[B
    :try_start_1
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 56
    .local v0, cls:Ljava/lang/Class;
    invoke-static {v0}, Ljava/io/ObjectStreamClass;->lookup(Ljava/lang/Class;)Ljava/io/ObjectStreamClass;

    move-result-object v3

    .line 57
    .local v3, osc:Ljava/io/ObjectStreamClass;
    if-nez v3, :cond_16

    .line 58
    new-instance v2, Ljava/lang/Exception;

    const-string v8, "Null ObjectStreamClass object"

    invoke-direct {v2, v8}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 59
    .local v2, e2:Ljava/lang/Exception;
    throw v2
    :try_end_13
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_13} :catch_13

    .line 65
    .end local v0           #cls:Ljava/lang/Class;
    .end local v2           #e2:Ljava/lang/Exception;
    .end local v3           #osc:Ljava/io/ObjectStreamClass;
    :catch_13
    move-exception v8

    move-object v1, v8

    .line 66
    .local v1, e:Ljava/lang/Exception;
    throw v1

    .line 61
    .end local v1           #e:Ljava/lang/Exception;
    .restart local v0       #cls:Ljava/lang/Class;
    .restart local v3       #osc:Ljava/io/ObjectStreamClass;
    :cond_16
    :try_start_16
    invoke-virtual {v3}, Ljava/io/ObjectStreamClass;->getSerialVersionUID()J

    move-result-wide v6

    .line 62
    .local v6, serialNum:J
    new-instance v8, Ljava/lang/Long;

    invoke-direct {v8, v6, v7}, Ljava/lang/Long;-><init>(J)V

    invoke-virtual {v8}, Ljava/lang/Long;->toString()Ljava/lang/String;

    move-result-object v5

    .line 63
    .local v5, s:Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->getBytes()[B
    :try_end_26
    .catch Ljava/lang/Exception; {:try_start_16 .. :try_end_26} :catch_13

    move-result-object v4

    .line 68
    return-object v4
.end method

.method public getContentBytesFromProc(Ljava/lang/String;)[B
    .registers 22
    .parameter "filePath"

    .prologue
    .line 91
    const/4 v8, 0x0

    .line 92
    .local v8, entries:Ljava/util/Enumeration;
    const/4 v12, 0x0

    .line 93
    .local v12, jarFile:Ljava/util/jar/JarFile;
    const/4 v11, 0x0

    .line 94
    .local v11, is:Ljava/io/InputStream;
    const/4 v7, 0x0

    .line 95
    .local v7, data:[B
    const/4 v15, 0x0

    .line 96
    .local v15, retData:[B
    const/4 v14, 0x0

    .line 97
    .local v14, lastData:[B
    const/16 v16, 0x2710

    .line 99
    .local v16, unitBlockSize:I
    :try_start_8
    new-instance v13, Ljava/util/jar/JarFile;

    move-object v0, v13

    move-object/from16 v1, p1

    invoke-direct {v0, v1}, Ljava/util/jar/JarFile;-><init>(Ljava/lang/String;)V
    :try_end_10
    .catchall {:try_start_8 .. :try_end_10} :catchall_132
    .catch Ljava/lang/Exception; {:try_start_8 .. :try_end_10} :catch_134

    .line 100
    .end local v12           #jarFile:Ljava/util/jar/JarFile;
    .local v13, jarFile:Ljava/util/jar/JarFile;
    :try_start_10
    invoke-virtual {v13}, Ljava/util/jar/JarFile;->entries()Ljava/util/Enumeration;

    move-result-object v8

    .line 101
    :cond_14
    invoke-interface {v8}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v17

    if-eqz v17, :cond_36

    .line 102
    invoke-interface {v8}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/util/jar/JarEntry;

    .line 103
    .local v9, entry:Ljava/util/jar/JarEntry;
    invoke-virtual {v9}, Ljava/util/jar/JarEntry;->isDirectory()Z

    move-result v17

    if-nez v17, :cond_14

    invoke-virtual {v9}, Ljava/util/jar/JarEntry;->getName()Ljava/lang/String;

    move-result-object v17

    const-string v18, "classes.dex"

    invoke-virtual/range {v17 .. v18}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v17

    if-eqz v17, :cond_14

    .line 104
    invoke-virtual {v13, v9}, Ljava/util/jar/JarFile;->getInputStream(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;

    move-result-object v11

    .line 107
    .end local v9           #entry:Ljava/util/jar/JarEntry;
    :cond_36
    const/4 v5, 0x0

    .line 108
    .local v5, bytes_read:I
    const/4 v6, 0x0

    .line 109
    .local v6, current_bytes_read:I
    const/16 v17, 0x0

    move/from16 v0, v17

    new-array v0, v0, [B

    move-object v14, v0

    .line 110
    :goto_3f
    const/16 v17, -0x1

    move v0, v6

    move/from16 v1, v17

    if-eq v0, v1, :cond_62

    .line 111
    move/from16 v0, v16

    new-array v0, v0, [B

    move-object v7, v0

    .line 112
    const/16 v17, 0x0

    move-object v0, v7

    array-length v0, v0

    move/from16 v18, v0

    move-object v0, v11

    move-object v1, v7

    move/from16 v2, v17

    move/from16 v3, v18

    invoke-virtual {v0, v1, v2, v3}, Ljava/io/InputStream;->read([BII)I

    move-result v6

    .line 113
    const/16 v17, -0x1

    move v0, v6

    move/from16 v1, v17

    if-ne v0, v1, :cond_df

    .line 121
    :cond_62
    if-nez v5, :cond_124

    .line 122
    const-string v17, "IOUtils"

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "getContentBytesFromProc, Read zero byte from proc "

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    move-object/from16 v0, v18

    move-object/from16 v1, p1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 123
    new-instance v17, Ljava/io/IOException;

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "Read zero byte from proc"

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    move-object/from16 v0, v18

    move-object/from16 v1, p1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-direct/range {v17 .. v18}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v17
    :try_end_9d
    .catchall {:try_start_10 .. :try_end_9d} :catchall_11c
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_9d} :catch_9d

    .line 125
    .end local v5           #bytes_read:I
    .end local v6           #current_bytes_read:I
    :catch_9d
    move-exception v17

    move-object/from16 v10, v17

    move-object v12, v13

    .line 126
    .end local v13           #jarFile:Ljava/util/jar/JarFile;
    .local v10, ioe:Ljava/lang/Exception;
    .restart local v12       #jarFile:Ljava/util/jar/JarFile;
    :goto_a1
    :try_start_a1
    const-string v17, "IOUtils"

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "getContentBytesFromProc, exception message: "

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual {v10}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_bd
    .catchall {:try_start_a1 .. :try_end_bd} :catchall_132

    .line 128
    if-eqz v12, :cond_c2

    .line 130
    :try_start_bf
    invoke-virtual {v12}, Ljava/util/jar/JarFile;->close()V
    :try_end_c2
    .catch Ljava/lang/Exception; {:try_start_bf .. :try_end_c2} :catch_12e

    .line 134
    .end local v10           #ioe:Ljava/lang/Exception;
    :cond_c2
    :goto_c2
    const-string v17, "IOUtils"

    new-instance v18, Ljava/lang/StringBuilder;

    invoke-direct/range {v18 .. v18}, Ljava/lang/StringBuilder;-><init>()V

    const-string v19, "getContentBytesFromProc, finish mapping contents, length: "

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v18

    move-object v0, v15

    array-length v0, v0

    move/from16 v19, v0

    invoke-virtual/range {v18 .. v19}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v18

    invoke-static/range {v17 .. v18}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 135
    return-object v15

    .line 114
    .end local v12           #jarFile:Ljava/util/jar/JarFile;
    .restart local v5       #bytes_read:I
    .restart local v6       #current_bytes_read:I
    .restart local v13       #jarFile:Ljava/util/jar/JarFile;
    :cond_df
    add-int/2addr v5, v6

    .line 115
    :try_start_e0
    new-array v15, v5, [B

    .line 116
    const/16 v17, 0x0

    const/16 v18, 0x0

    move-object v0, v14

    array-length v0, v0

    move/from16 v19, v0

    move-object v0, v14

    move/from16 v1, v17

    move-object v2, v15

    move/from16 v3, v18

    move/from16 v4, v19

    invoke-static {v0, v1, v2, v3, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 117
    const/16 v17, 0x0

    move-object v0, v14

    array-length v0, v0

    move/from16 v18, v0

    move-object v0, v7

    move/from16 v1, v17

    move-object v2, v15

    move/from16 v3, v18

    move v4, v6

    invoke-static {v0, v1, v2, v3, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V

    .line 118
    new-array v14, v5, [B

    .line 119
    const/16 v17, 0x0

    const/16 v18, 0x0

    move-object v0, v15

    array-length v0, v0

    move/from16 v19, v0

    move-object v0, v15

    move/from16 v1, v17

    move-object v2, v14

    move/from16 v3, v18

    move/from16 v4, v19

    invoke-static {v0, v1, v2, v3, v4}, Ljava/lang/System;->arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V
    :try_end_11a
    .catchall {:try_start_e0 .. :try_end_11a} :catchall_11c
    .catch Ljava/lang/Exception; {:try_start_e0 .. :try_end_11a} :catch_9d

    goto/16 :goto_3f

    .line 128
    .end local v5           #bytes_read:I
    .end local v6           #current_bytes_read:I
    :catchall_11c
    move-exception v17

    move-object v12, v13

    .end local v13           #jarFile:Ljava/util/jar/JarFile;
    .restart local v12       #jarFile:Ljava/util/jar/JarFile;
    :goto_11e
    if-eqz v12, :cond_123

    .line 130
    :try_start_120
    invoke-virtual {v12}, Ljava/util/jar/JarFile;->close()V
    :try_end_123
    .catch Ljava/lang/Exception; {:try_start_120 .. :try_end_123} :catch_130

    .line 131
    :cond_123
    :goto_123
    throw v17

    .line 128
    .end local v12           #jarFile:Ljava/util/jar/JarFile;
    .restart local v5       #bytes_read:I
    .restart local v6       #current_bytes_read:I
    .restart local v13       #jarFile:Ljava/util/jar/JarFile;
    :cond_124
    if-eqz v13, :cond_139

    .line 130
    :try_start_126
    invoke-virtual {v13}, Ljava/util/jar/JarFile;->close()V
    :try_end_129
    .catch Ljava/lang/Exception; {:try_start_126 .. :try_end_129} :catch_12b

    move-object v12, v13

    .line 131
    .end local v13           #jarFile:Ljava/util/jar/JarFile;
    .restart local v12       #jarFile:Ljava/util/jar/JarFile;
    goto :goto_c2

    .end local v12           #jarFile:Ljava/util/jar/JarFile;
    .restart local v13       #jarFile:Ljava/util/jar/JarFile;
    :catch_12b
    move-exception v17

    move-object v12, v13

    .end local v13           #jarFile:Ljava/util/jar/JarFile;
    .restart local v12       #jarFile:Ljava/util/jar/JarFile;
    goto :goto_c2

    .end local v5           #bytes_read:I
    .end local v6           #current_bytes_read:I
    .restart local v10       #ioe:Ljava/lang/Exception;
    :catch_12e
    move-exception v17

    goto :goto_c2

    .end local v10           #ioe:Ljava/lang/Exception;
    :catch_130
    move-exception v18

    goto :goto_123

    .line 128
    :catchall_132
    move-exception v17

    goto :goto_11e

    .line 125
    :catch_134
    move-exception v17

    move-object/from16 v10, v17

    goto/16 :goto_a1

    .end local v12           #jarFile:Ljava/util/jar/JarFile;
    .restart local v5       #bytes_read:I
    .restart local v6       #current_bytes_read:I
    .restart local v13       #jarFile:Ljava/util/jar/JarFile;
    :cond_139
    move-object v12, v13

    .end local v13           #jarFile:Ljava/util/jar/JarFile;
    .restart local v12       #jarFile:Ljava/util/jar/JarFile;
    goto :goto_c2
.end method

.method public writeToPrintStream(Ljava/lang/String;ZLjava/lang/String;)V
    .registers 6
    .parameter "fullFilePath"
    .parameter "apppend"
    .parameter "message"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 73
    new-instance v0, Ljava/io/PrintStream;

    new-instance v1, Ljava/io/FileOutputStream;

    invoke-direct {v1, p1, p2}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;Z)V

    invoke-direct {v0, v1}, Ljava/io/PrintStream;-><init>(Ljava/io/OutputStream;)V

    .line 74
    .local v0, printStream:Ljava/io/PrintStream;
    invoke-virtual {v0, p3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 75
    invoke-virtual {v0}, Ljava/io/PrintStream;->flush()V

    .line 76
    return-void
.end method
