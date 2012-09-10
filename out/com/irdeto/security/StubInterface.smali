.class public final Lcom/irdeto/security/StubInterface;
.super Ljava/lang/Object;
.source "StubInterface.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static MYTAG:Ljava/lang/String; = null

.field private static final VOUCHER_NAME:Ljava/lang/String; = "irdeto_java_access.dat"

.field private static apkName:Ljava/lang/String;

.field private static apkPath:Ljava/lang/String;

.field private static context:Landroid/content/Context;

.field private static locker:Ljava/lang/Object;

.field private static voucherFullPath:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 2

    .prologue
    const-string v1, "null"

    .line 21
    const/4 v0, 0x0

    sput-object v0, Lcom/irdeto/security/StubInterface;->context:Landroid/content/Context;

    .line 24
    const-string v0, "null"

    sput-object v1, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    .line 25
    const-string v0, "null"

    sput-object v1, Lcom/irdeto/security/StubInterface;->voucherFullPath:Ljava/lang/String;

    .line 26
    const-string v0, "null"

    sput-object v1, Lcom/irdeto/security/StubInterface;->apkPath:Ljava/lang/String;

    .line 27
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lcom/irdeto/security/StubInterface;->locker:Ljava/lang/Object;

    .line 28
    const-string v0, "StubInterface"

    sput-object v0, Lcom/irdeto/security/StubInterface;->MYTAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 43
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 44
    sget-object v1, Lcom/irdeto/security/StubInterface;->locker:Ljava/lang/Object;

    monitor-enter v1

    .line 45
    :try_start_6
    sget-object v2, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    const-string v3, "null"

    invoke-virtual {v2, v3}, Ljava/lang/String;->compareToIgnoreCase(Ljava/lang/String;)I

    move-result v2

    if-nez v2, :cond_1b

    .line 46
    new-instance v0, Ljava/lang/Exception;

    const-string v2, "Application Context is not set, use construtor with the Context, "

    invoke-direct {v0, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 47
    .local v0, exception:Ljava/lang/Exception;
    throw v0

    .line 49
    .end local v0           #exception:Ljava/lang/Exception;
    :catchall_18
    move-exception v2

    monitor-exit v1
    :try_end_1a
    .catchall {:try_start_6 .. :try_end_1a} :catchall_18

    throw v2

    :cond_1b
    :try_start_1b
    monitor-exit v1
    :try_end_1c
    .catchall {:try_start_1b .. :try_end_1c} :catchall_18

    .line 50
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .registers 4
    .parameter "ctx"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 35
    sget-object v0, Lcom/irdeto/security/StubInterface;->locker:Ljava/lang/Object;

    monitor-enter v0

    .line 36
    :try_start_6
    invoke-static {p1}, Lcom/irdeto/security/StubInterface;->initContext(Landroid/content/Context;)V

    .line 37
    monitor-exit v0

    .line 38
    return-void

    .line 37
    :catchall_b
    move-exception v1

    monitor-exit v0
    :try_end_d
    .catchall {:try_start_6 .. :try_end_d} :catchall_b

    throw v1
.end method

.method public static getAPKName()Ljava/lang/String;
    .registers 1

    .prologue
    .line 86
    sget-object v0, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    return-object v0
.end method

.method public static getAPKPath()Ljava/lang/String;
    .registers 1

    .prologue
    .line 99
    sget-object v0, Lcom/irdeto/security/StubInterface;->apkPath:Ljava/lang/String;

    return-object v0
.end method

.method public static getApplicationContext()Landroid/content/Context;
    .registers 1

    .prologue
    .line 52
    sget-object v0, Lcom/irdeto/security/StubInterface;->context:Landroid/content/Context;

    return-object v0
.end method

.method public static getVoucherFullPath()Ljava/lang/String;
    .registers 1

    .prologue
    .line 90
    sget-object v0, Lcom/irdeto/security/StubInterface;->voucherFullPath:Ljava/lang/String;

    return-object v0
.end method

.method private static initContext(Landroid/content/Context;)V
    .registers 5
    .parameter "ctx"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 66
    sget-object v1, Lcom/irdeto/security/StubInterface;->locker:Ljava/lang/Object;

    monitor-enter v1

    .line 67
    :try_start_3
    sget-object v2, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    const-string v3, "null"

    invoke-virtual {v2, v3}, Ljava/lang/String;->compareToIgnoreCase(Ljava/lang/String;)I

    move-result v2

    if-eqz v2, :cond_18

    .line 68
    new-instance v0, Ljava/lang/Exception;

    const-string v2, "Application Context is already set, ignore"

    invoke-direct {v0, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 69
    .local v0, exception:Ljava/lang/Exception;
    throw v0

    .line 79
    .end local v0           #exception:Ljava/lang/Exception;
    :catchall_15
    move-exception v2

    monitor-exit v1
    :try_end_17
    .catchall {:try_start_3 .. :try_end_17} :catchall_15

    throw v2

    .line 71
    :cond_18
    :try_start_18
    sput-object p0, Lcom/irdeto/security/StubInterface;->context:Landroid/content/Context;

    .line 72
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    sput-object v2, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    .line 73
    sget-object v2, Lcom/irdeto/security/StubInterface;->apkName:Ljava/lang/String;

    if-nez v2, :cond_2c

    .line 74
    new-instance v0, Ljava/lang/Exception;

    const-string v2, "Invalid APK name resulting from Application Context!"

    invoke-direct {v0, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 75
    .restart local v0       #exception:Ljava/lang/Exception;
    throw v0

    .line 77
    .end local v0           #exception:Ljava/lang/Exception;
    :cond_2c
    invoke-static {p0}, Lcom/irdeto/security/StubInterface;->setupInternalVoucher(Landroid/content/Context;)V

    .line 78
    invoke-static {p0}, Lcom/irdeto/security/StubInterface;->setupAPKPath(Landroid/content/Context;)V

    .line 79
    monitor-exit v1
    :try_end_33
    .catchall {:try_start_18 .. :try_end_33} :catchall_15

    .line 80
    return-void
.end method

.method private static migrateVoucher(Ljava/io/InputStream;Ljava/lang/String;)V
    .registers 8
    .parameter "inputStream"
    .parameter "outputFileName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 111
    const/16 v5, 0x400

    new-array v0, v5, [B

    .line 112
    .local v0, buf:[B
    const/4 v3, 0x0

    .line 115
    .local v3, outputStream:Ljava/io/FileOutputStream;
    :try_start_5
    new-instance v4, Ljava/io/FileOutputStream;

    new-instance v5, Ljava/io/File;

    invoke-direct {v5, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-direct {v4, v5}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V
    :try_end_f
    .catchall {:try_start_5 .. :try_end_f} :catchall_24
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_f} :catch_3e

    .line 116
    .end local v3           #outputStream:Ljava/io/FileOutputStream;
    .local v4, outputStream:Ljava/io/FileOutputStream;
    :goto_f
    :try_start_f
    invoke-virtual {p0, v0}, Ljava/io/InputStream;->read([B)I

    move-result v2

    .local v2, len:I
    const/4 v5, -0x1

    if-eq v2, v5, :cond_30

    .line 117
    const/4 v5, 0x0

    invoke-virtual {v4, v0, v5, v2}, Ljava/io/FileOutputStream;->write([BII)V
    :try_end_1a
    .catchall {:try_start_f .. :try_end_1a} :catchall_3b
    .catch Ljava/lang/Exception; {:try_start_f .. :try_end_1a} :catch_1b

    goto :goto_f

    .line 119
    .end local v2           #len:I
    :catch_1b
    move-exception v5

    move-object v1, v5

    move-object v3, v4

    .line 120
    .end local v4           #outputStream:Ljava/io/FileOutputStream;
    .local v1, e:Ljava/lang/Exception;
    .restart local v3       #outputStream:Ljava/io/FileOutputStream;
    :goto_1e
    :try_start_1e
    new-instance v5, Ljava/lang/Exception;

    invoke-direct {v5, v1}, Ljava/lang/Exception;-><init>(Ljava/lang/Throwable;)V

    throw v5
    :try_end_24
    .catchall {:try_start_1e .. :try_end_24} :catchall_24

    .line 122
    .end local v1           #e:Ljava/lang/Exception;
    :catchall_24
    move-exception v5

    :goto_25
    if-eqz v3, :cond_2a

    invoke-virtual {v3}, Ljava/io/FileOutputStream;->close()V

    .line 123
    :cond_2a
    if-eqz p0, :cond_2f

    invoke-virtual {p0}, Ljava/io/InputStream;->close()V

    :cond_2f
    throw v5

    .line 122
    .end local v3           #outputStream:Ljava/io/FileOutputStream;
    .restart local v2       #len:I
    .restart local v4       #outputStream:Ljava/io/FileOutputStream;
    :cond_30
    if-eqz v4, :cond_35

    invoke-virtual {v4}, Ljava/io/FileOutputStream;->close()V

    .line 123
    :cond_35
    if-eqz p0, :cond_3a

    invoke-virtual {p0}, Ljava/io/InputStream;->close()V

    .line 125
    :cond_3a
    return-void

    .line 122
    .end local v2           #len:I
    :catchall_3b
    move-exception v5

    move-object v3, v4

    .end local v4           #outputStream:Ljava/io/FileOutputStream;
    .restart local v3       #outputStream:Ljava/io/FileOutputStream;
    goto :goto_25

    .line 119
    :catch_3e
    move-exception v5

    move-object v1, v5

    goto :goto_1e
.end method

.method public static setAplicationContext(Landroid/content/Context;)V
    .registers 3
    .parameter "ctx"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 60
    sget-object v0, Lcom/irdeto/security/StubInterface;->locker:Ljava/lang/Object;

    monitor-enter v0

    .line 61
    :try_start_3
    invoke-static {p0}, Lcom/irdeto/security/StubInterface;->initContext(Landroid/content/Context;)V

    .line 62
    monitor-exit v0

    .line 63
    return-void

    .line 62
    :catchall_8
    move-exception v1

    monitor-exit v0
    :try_end_a
    .catchall {:try_start_3 .. :try_end_a} :catchall_8

    throw v1
.end method

.method private static setupAPKPath(Landroid/content/Context;)V
    .registers 5
    .parameter "ctx"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    .line 93
    sget-object v1, Lcom/irdeto/security/StubInterface;->context:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getApplicationInfo()Landroid/content/pm/ApplicationInfo;

    move-result-object v0

    .line 94
    .local v0, aInfo:Landroid/content/pm/ApplicationInfo;
    iget-object v1, v0, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;

    sput-object v1, Lcom/irdeto/security/StubInterface;->apkPath:Ljava/lang/String;

    .line 95
    sget-object v1, Lcom/irdeto/security/StubInterface;->MYTAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Attach process 1 : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Lcom/irdeto/security/StubInterface;->apkPath:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 96
    sget-object v1, Lcom/irdeto/security/StubInterface;->MYTAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Attach process 2 : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, v0, Landroid/content/pm/ApplicationInfo;->publicSourceDir:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 97
    return-void
.end method

.method private static setupInternalVoucher(Landroid/content/Context;)V
    .registers 7
    .parameter "ctx"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .prologue
    const-string v5, "irdeto_java_access.dat"

    .line 103
    invoke-virtual {p0}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    .line 104
    .local v0, aManager:Landroid/content/res/AssetManager;
    const-string v3, "irdeto_java_access.dat"

    invoke-virtual {v0, v5}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v2

    .line 105
    .local v2, is:Ljava/io/InputStream;
    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v1

    .line 106
    .local v1, filefir:Ljava/io/File;
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "/"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "irdeto_java_access.dat"

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    sput-object v3, Lcom/irdeto/security/StubInterface;->voucherFullPath:Ljava/lang/String;

    .line 107
    sget-object v3, Lcom/irdeto/security/StubInterface;->voucherFullPath:Ljava/lang/String;

    invoke-static {v2, v3}, Lcom/irdeto/security/StubInterface;->migrateVoucher(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 108
    return-void
.end method
