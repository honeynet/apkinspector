.class public Lcom/irdeto/omp/OmpException;
.super Ljava/lang/Exception;
.source "OmpException.java"


# instance fields
.field private code:I


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 26
    invoke-direct {p0}, Ljava/lang/Exception;-><init>()V

    .line 21
    const v0, 0x10001

    iput v0, p0, Lcom/irdeto/omp/OmpException;->code:I

    .line 27
    return-void
.end method

.method public constructor <init>(I)V
    .registers 3
    .parameter "code"

    .prologue
    .line 40
    invoke-static {p1}, Lcom/irdeto/omp/OmpException;->codeToMessage(I)Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 21
    const v0, 0x10001

    iput v0, p0, Lcom/irdeto/omp/OmpException;->code:I

    .line 41
    iput p1, p0, Lcom/irdeto/omp/OmpException;->code:I

    .line 42
    return-void
.end method

.method public constructor <init>(Ljava/lang/String;)V
    .registers 3
    .parameter "message"

    .prologue
    .line 33
    invoke-direct {p0, p1}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    .line 21
    const v0, 0x10001

    iput v0, p0, Lcom/irdeto/omp/OmpException;->code:I

    .line 34
    return-void
.end method

.method private static codeToMessage(I)Ljava/lang/String;
    .registers 3
    .parameter "code"

    .prologue
    .line 56
    new-instance v0, Ljava/lang/String;

    invoke-direct {v0}, Ljava/lang/String;-><init>()V

    .line 57
    .local v0, msg:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 58
    return-object v0
.end method


# virtual methods
.method public code()I
    .registers 2

    .prologue
    .line 48
    iget v0, p0, Lcom/irdeto/omp/OmpException;->code:I

    return v0
.end method
