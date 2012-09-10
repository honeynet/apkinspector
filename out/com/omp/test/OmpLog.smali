.class public Lcom/omp/test/OmpLog;
.super Ljava/lang/Object;
.source "OmpLog.java"


# instance fields
.field private m_logFile:Ljava/io/File;

.field private final mstr_logFile:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .registers 4

    .prologue
    const-string v2, "/sdcard/logFiles.txt"

    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 12
    const-string v1, "/sdcard/logFiles.txt"

    iput-object v2, p0, Lcom/omp/test/OmpLog;->mstr_logFile:Ljava/lang/String;

    .line 13
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    .line 19
    :try_start_c
    new-instance v1, Ljava/io/File;

    const-string v2, "/sdcard/logFiles.txt"

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    iput-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    .line 20
    iget-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->createNewFile()Z

    .line 21
    iget-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_28

    .line 23
    iget-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->createNewFile()Z

    .line 34
    :goto_27
    return-void

    .line 27
    :cond_28
    invoke-direct {p0}, Lcom/omp/test/OmpLog;->clearLog()V
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_c .. :try_end_2b} :catch_2c

    goto :goto_27

    .line 30
    :catch_2c
    move-exception v1

    move-object v0, v1

    .line 32
    .local v0, ex:Ljava/lang/Exception;
    sget-object v1, Ljava/lang/System;->err:Ljava/io/PrintStream;

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    goto :goto_27
.end method

.method private clearLog()V
    .registers 4

    .prologue
    .line 128
    :try_start_0
    iget-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 129
    new-instance v1, Ljava/io/File;

    const-string v2, "/sdcard/logFiles.txt"

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    iput-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    .line 130
    iget-object v1, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v1}, Ljava/io/File;->createNewFile()Z
    :try_end_13
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_13} :catch_14

    .line 136
    :goto_13
    return-void

    .line 132
    :catch_14
    move-exception v1

    move-object v0, v1

    .line 134
    .local v0, ex:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_13
.end method

.method private getCurrentTime()Ljava/lang/String;
    .registers 4

    .prologue
    .line 140
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v0

    .line 141
    .local v0, cal:Ljava/util/Calendar;
    new-instance v1, Ljava/text/SimpleDateFormat;

    const-string v2, "yyyy-MM-dd HH:mm:ss"

    invoke-direct {v1, v2}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 143
    .local v1, sdf:Ljava/text/SimpleDateFormat;
    invoke-virtual {v0}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v2

    return-object v2
.end method

.method private getLogType(Ljava/lang/String;)Ljava/lang/String;
    .registers 4
    .parameter "str_type"

    .prologue
    .line 148
    const/4 v0, 0x0

    .line 150
    .local v0, str_Ret:Ljava/lang/String;
    const-string v1, "s"

    if-ne v1, p1, :cond_8

    .line 152
    const-string v0, "Start"

    .line 170
    :cond_7
    :goto_7
    return-object v0

    .line 154
    :cond_8
    const-string v1, "p"

    if-ne v1, p1, :cond_f

    .line 156
    const-string v0, "Pass"

    goto :goto_7

    .line 158
    :cond_f
    const-string v1, "f"

    if-ne v1, p1, :cond_16

    .line 160
    const-string v0, "Fail"

    goto :goto_7

    .line 162
    :cond_16
    const-string v1, "d"

    if-ne v1, p1, :cond_1d

    .line 164
    const-string v0, "Debug"

    goto :goto_7

    .line 166
    :cond_1d
    const-string v1, "e"

    if-ne v1, p1, :cond_7

    .line 168
    const-string v0, "Error"

    goto :goto_7
.end method


# virtual methods
.method public getLog()Ljava/util/ArrayList;
    .registers 12
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const/4 v10, 0x1

    .line 74
    :try_start_1
    new-instance v6, Ljava/util/ArrayList;

    invoke-direct {v6}, Ljava/util/ArrayList;-><init>()V

    .line 75
    .local v6, retList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    new-instance v2, Ljava/io/FileInputStream;

    iget-object v8, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-direct {v2, v8}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 76
    .local v2, fileInStream:Ljava/io/FileInputStream;
    invoke-virtual {v2}, Ljava/io/FileInputStream;->available()I

    move-result v8

    new-array v0, v8, [B

    .line 77
    .local v0, byteInData:[B
    invoke-virtual {v2, v0}, Ljava/io/FileInputStream;->read([B)I

    .line 79
    new-instance v8, Ljava/lang/String;

    invoke-direct {v8, v0}, Ljava/lang/String;-><init>([B)V

    const-string v9, "\\n"

    invoke-virtual {v8, v9}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    .line 80
    .local v7, strLine:[Ljava/lang/String;
    array-length v5, v7

    .line 81
    .local v5, n_totalLine:I
    move v4, v5

    .line 82
    .local v4, n_find:I
    :goto_23
    if-gtz v4, :cond_36

    .line 93
    :cond_25
    move v3, v4

    .line 95
    .local v3, n_curLine:I
    iget-object v8, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v8}, Ljava/io/File;->canRead()Z

    move-result v8

    if-eqz v8, :cond_32

    .line 97
    if-lez v3, :cond_53

    .line 99
    :goto_30
    if-le v3, v5, :cond_45

    .line 115
    :cond_32
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V

    .line 116
    return-object v6

    .line 84
    .end local v3           #n_curLine:I
    :cond_36
    sub-int v8, v4, v10

    aget-object v8, v7, v8

    const-string v9, "Start"

    invoke-virtual {v8, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v8

    if-nez v8, :cond_25

    .line 90
    add-int/lit8 v4, v4, -0x1

    goto :goto_23

    .line 101
    .restart local v3       #n_curLine:I
    :cond_45
    sub-int v8, v3, v10

    aget-object v8, v7, v8

    invoke-virtual {v8}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v6, v8}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 102
    add-int/lit8 v3, v3, 0x1

    goto :goto_30

    .line 107
    :cond_53
    const/4 v3, 0x0

    .line 108
    :goto_54
    if-ge v3, v5, :cond_32

    .line 110
    aget-object v8, v7, v3

    invoke-virtual {v6, v8}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_5b
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_5b} :catch_5e

    .line 111
    add-int/lit8 v3, v3, 0x1

    goto :goto_54

    .line 118
    .end local v0           #byteInData:[B
    .end local v2           #fileInStream:Ljava/io/FileInputStream;
    .end local v3           #n_curLine:I
    .end local v4           #n_find:I
    .end local v5           #n_totalLine:I
    .end local v6           #retList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    .end local v7           #strLine:[Ljava/lang/String;
    :catch_5e
    move-exception v8

    move-object v1, v8

    .line 120
    .local v1, ex:Ljava/lang/Exception;
    throw v1
.end method

.method public writeLog(Ljava/lang/String;Ljava/lang/String;)V
    .registers 11
    .parameter "str_type"
    .parameter "str_Log"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 38
    invoke-direct {p0, p1}, Lcom/omp/test/OmpLog;->getLogType(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 39
    .local v5, str_logType:Ljava/lang/String;
    const/4 v0, 0x0

    .line 40
    .local v0, bufferWritter:Ljava/io/BufferedWriter;
    const/4 v3, 0x0

    .line 44
    .local v3, fileOutStream:Ljava/io/FileOutputStream;
    :try_start_6
    iget-object v6, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    invoke-virtual {v6}, Ljava/io/File;->canWrite()Z

    move-result v6

    if-eqz v6, :cond_5f

    .line 46
    new-instance v6, Ljava/lang/StringBuilder;

    const/16 v7, 0x5b

    invoke-static {v7}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v7

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/omp/test/OmpLog;->getCurrentTime()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const/16 v7, 0x7c

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const/16 v7, 0x5d

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "  "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "\n"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    .line 47
    new-instance v4, Ljava/io/FileOutputStream;

    iget-object v6, p0, Lcom/omp/test/OmpLog;->m_logFile:Ljava/io/File;

    const/4 v7, 0x1

    invoke-direct {v4, v6, v7}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_4d
    .catchall {:try_start_6 .. :try_end_4d} :catchall_71
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_4d} :catch_63

    .line 48
    .end local v3           #fileOutStream:Ljava/io/FileOutputStream;
    .local v4, fileOutStream:Ljava/io/FileOutputStream;
    :try_start_4d
    new-instance v1, Ljava/io/BufferedWriter;

    new-instance v6, Ljava/io/OutputStreamWriter;

    invoke-direct {v6, v4}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    invoke-direct {v1, v6}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V
    :try_end_57
    .catchall {:try_start_4d .. :try_end_57} :catchall_80
    .catch Ljava/lang/Exception; {:try_start_4d .. :try_end_57} :catch_87

    .line 49
    .end local v0           #bufferWritter:Ljava/io/BufferedWriter;
    .local v1, bufferWritter:Ljava/io/BufferedWriter;
    :try_start_57
    invoke-virtual {v1, p2}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 50
    invoke-virtual {v1}, Ljava/io/BufferedWriter;->flush()V
    :try_end_5d
    .catchall {:try_start_57 .. :try_end_5d} :catchall_83
    .catch Ljava/lang/Exception; {:try_start_57 .. :try_end_5d} :catch_8b

    move-object v3, v4

    .end local v4           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v3       #fileOutStream:Ljava/io/FileOutputStream;
    move-object v0, v1

    .line 61
    .end local v1           #bufferWritter:Ljava/io/BufferedWriter;
    .restart local v0       #bufferWritter:Ljava/io/BufferedWriter;
    :cond_5f
    :try_start_5f
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_62
    .catch Ljava/lang/Exception; {:try_start_5f .. :try_end_62} :catch_7b

    .line 68
    :goto_62
    return-void

    .line 53
    :catch_63
    move-exception v6

    move-object v2, v6

    .line 55
    .local v2, ex:Ljava/lang/Exception;
    :goto_65
    :try_start_65
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_68
    .catchall {:try_start_65 .. :try_end_68} :catchall_71

    .line 61
    :try_start_68
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_6b
    .catch Ljava/lang/Exception; {:try_start_68 .. :try_end_6b} :catch_6c

    goto :goto_62

    .line 63
    :catch_6c
    move-exception v2

    .line 65
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_62

    .line 58
    .end local v2           #ex:Ljava/lang/Exception;
    :catchall_71
    move-exception v6

    .line 61
    :goto_72
    :try_start_72
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_75
    .catch Ljava/lang/Exception; {:try_start_72 .. :try_end_75} :catch_76

    .line 67
    :goto_75
    throw v6

    .line 63
    :catch_76
    move-exception v2

    .line 65
    .restart local v2       #ex:Ljava/lang/Exception;
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_75

    .line 63
    .end local v2           #ex:Ljava/lang/Exception;
    :catch_7b
    move-exception v2

    .line 65
    .restart local v2       #ex:Ljava/lang/Exception;
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_62

    .line 58
    .end local v2           #ex:Ljava/lang/Exception;
    .end local v3           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v4       #fileOutStream:Ljava/io/FileOutputStream;
    :catchall_80
    move-exception v6

    move-object v3, v4

    .end local v4           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v3       #fileOutStream:Ljava/io/FileOutputStream;
    goto :goto_72

    .end local v0           #bufferWritter:Ljava/io/BufferedWriter;
    .end local v3           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v1       #bufferWritter:Ljava/io/BufferedWriter;
    .restart local v4       #fileOutStream:Ljava/io/FileOutputStream;
    :catchall_83
    move-exception v6

    move-object v3, v4

    .end local v4           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v3       #fileOutStream:Ljava/io/FileOutputStream;
    move-object v0, v1

    .end local v1           #bufferWritter:Ljava/io/BufferedWriter;
    .restart local v0       #bufferWritter:Ljava/io/BufferedWriter;
    goto :goto_72

    .line 53
    .end local v3           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v4       #fileOutStream:Ljava/io/FileOutputStream;
    :catch_87
    move-exception v6

    move-object v2, v6

    move-object v3, v4

    .end local v4           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v3       #fileOutStream:Ljava/io/FileOutputStream;
    goto :goto_65

    .end local v0           #bufferWritter:Ljava/io/BufferedWriter;
    .end local v3           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v1       #bufferWritter:Ljava/io/BufferedWriter;
    .restart local v4       #fileOutStream:Ljava/io/FileOutputStream;
    :catch_8b
    move-exception v6

    move-object v2, v6

    move-object v3, v4

    .end local v4           #fileOutStream:Ljava/io/FileOutputStream;
    .restart local v3       #fileOutStream:Ljava/io/FileOutputStream;
    move-object v0, v1

    .end local v1           #bufferWritter:Ljava/io/BufferedWriter;
    .restart local v0       #bufferWritter:Ljava/io/BufferedWriter;
    goto :goto_65
.end method
