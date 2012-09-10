.class public Lcom/irdeto/security/common/HexUtils;
.super Ljava/lang/Object;
.source "HexUtils.java"


# static fields
.field private static final kDigits:[C


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 10
    const/16 v0, 0x10

    new-array v0, v0, [C

    fill-array-data v0, :array_a

    sput-object v0, Lcom/irdeto/security/common/HexUtils;->kDigits:[C

    return-void

    :array_a
    .array-data 0x2
        0x30t 0x0t
        0x31t 0x0t
        0x32t 0x0t
        0x33t 0x0t
        0x34t 0x0t
        0x35t 0x0t
        0x36t 0x0t
        0x37t 0x0t
        0x38t 0x0t
        0x39t 0x0t
        0x61t 0x0t
        0x62t 0x0t
        0x63t 0x0t
        0x64t 0x0t
        0x65t 0x0t
        0x66t 0x0t
    .end array-data
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static byteToHex(B)[C
    .registers 8
    .parameter "raw"

    .prologue
    .line 32
    const/4 v2, 0x1

    .line 33
    .local v2, length:I
    const/4 v5, 0x2

    new-array v0, v5, [C

    .line 35
    .local v0, hex:[C
    add-int/lit16 v5, p0, 0x100

    rem-int/lit16 v4, v5, 0x100

    .line 36
    .local v4, value:I
    shr-int/lit8 v1, v4, 0x4

    .line 37
    .local v1, highIndex:I
    and-int/lit8 v3, v4, 0xf

    .line 38
    .local v3, lowIndex:I
    const/4 v5, 0x0

    sget-object v6, Lcom/irdeto/security/common/HexUtils;->kDigits:[C

    aget-char v6, v6, v1

    aput-char v6, v0, v5

    .line 39
    const/4 v5, 0x1

    sget-object v6, Lcom/irdeto/security/common/HexUtils;->kDigits:[C

    aget-char v6, v6, v3

    aput-char v6, v0, v5

    .line 41
    return-object v0
.end method

.method public static byteToString([B)Ljava/lang/String;
    .registers 4
    .parameter "anArray"

    .prologue
    .line 78
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 79
    .local v0, buffer:Ljava/lang/StringBuffer;
    const/4 v1, 0x0

    .local v1, i:I
    :goto_6
    array-length v2, p0

    if-ge v1, v2, :cond_16

    .line 81
    aget-byte v2, p0, v1

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    .line 82
    const-string v2, ","

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 79
    add-int/lit8 v1, v1, 0x1

    goto :goto_6

    .line 84
    :cond_16
    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v2

    return-object v2
.end method

.method public static bytesToHex([B)[C
    .registers 9
    .parameter "raw"

    .prologue
    .line 17
    array-length v3, p0

    .line 18
    .local v3, length:I
    mul-int/lit8 v6, v3, 0x2

    new-array v0, v6, [C

    .line 19
    .local v0, hex:[C
    const/4 v2, 0x0

    .local v2, i:I
    :goto_6
    if-ge v2, v3, :cond_29

    .line 21
    aget-byte v6, p0, v2

    add-int/lit16 v6, v6, 0x100

    rem-int/lit16 v5, v6, 0x100

    .line 22
    .local v5, value:I
    shr-int/lit8 v1, v5, 0x4

    .line 23
    .local v1, highIndex:I
    and-int/lit8 v4, v5, 0xf

    .line 24
    .local v4, lowIndex:I
    mul-int/lit8 v6, v2, 0x2

    add-int/lit8 v6, v6, 0x0

    sget-object v7, Lcom/irdeto/security/common/HexUtils;->kDigits:[C

    aget-char v7, v7, v1

    aput-char v7, v0, v6

    .line 25
    mul-int/lit8 v6, v2, 0x2

    add-int/lit8 v6, v6, 0x1

    sget-object v7, Lcom/irdeto/security/common/HexUtils;->kDigits:[C

    aget-char v7, v7, v4

    aput-char v7, v0, v6

    .line 19
    add-int/lit8 v2, v2, 0x1

    goto :goto_6

    .line 27
    .end local v1           #highIndex:I
    .end local v4           #lowIndex:I
    .end local v5           #value:I
    :cond_29
    return-object v0
.end method

.method public static hexToBytes([C)[B
    .registers 9
    .parameter "hex"

    .prologue
    const/16 v7, 0x10

    .line 46
    array-length v6, p0

    div-int/lit8 v2, v6, 0x2

    .line 47
    .local v2, length:I
    new-array v4, v2, [B

    .line 48
    .local v4, raw:[B
    const/4 v1, 0x0

    .local v1, i:I
    :goto_8
    if-ge v1, v2, :cond_2c

    .line 50
    mul-int/lit8 v6, v1, 0x2

    aget-char v6, p0, v6

    invoke-static {v6, v7}, Ljava/lang/Character;->digit(CI)I

    move-result v0

    .line 51
    .local v0, high:I
    mul-int/lit8 v6, v1, 0x2

    add-int/lit8 v6, v6, 0x1

    aget-char v6, p0, v6

    invoke-static {v6, v7}, Ljava/lang/Character;->digit(CI)I

    move-result v3

    .line 52
    .local v3, low:I
    shl-int/lit8 v6, v0, 0x4

    or-int v5, v6, v3

    .line 53
    .local v5, value:I
    const/16 v6, 0x7f

    if-le v5, v6, :cond_26

    add-int/lit16 v5, v5, -0x100

    .line 54
    :cond_26
    int-to-byte v6, v5

    aput-byte v6, v4, v1

    .line 48
    add-int/lit8 v1, v1, 0x1

    goto :goto_8

    .line 56
    .end local v0           #high:I
    .end local v3           #low:I
    .end local v5           #value:I
    :cond_2c
    return-object v4
.end method

.method public static hexValue(Ljava/lang/String;)Ljava/lang/String;
    .registers 3
    .parameter "aValue"

    .prologue
    .line 66
    if-eqz p0, :cond_8

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_a

    :cond_8
    const/4 v0, 0x0

    .line 68
    :goto_9
    return-object v0

    :cond_a
    new-instance v0, Ljava/lang/String;

    invoke-virtual {p0}, Ljava/lang/String;->getBytes()[B

    move-result-object v1

    invoke-static {v1}, Lcom/irdeto/security/common/HexUtils;->bytesToHex([B)[C

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/String;-><init>([C)V

    goto :goto_9
.end method
