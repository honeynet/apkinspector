.class Lcom/omp/test/omptest$2;
.super Ljava/lang/Object;
.source "omptest.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/omp/test/omptest;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/omp/test/omptest;


# direct methods
.method constructor <init>(Lcom/omp/test/omptest;)V
    .registers 2
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/omp/test/omptest$2;->this$0:Lcom/omp/test/omptest;

    .line 87
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 8
    .parameter "v"

    .prologue
    .line 93
    :try_start_0
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/omp/test/omptest;->access$0()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v5, ":9085/"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 94
    .local v3, str_url:Ljava/lang/String;
    invoke-static {}, Lcom/omp/test/omptest;->access$1()Ljava/lang/String;

    move-result-object v1

    .line 95
    .local v1, str_appid:Ljava/lang/String;
    const-string v2, "00100000000000000169"

    .line 97
    .local v2, str_apppid:Ljava/lang/String;
    iget-object v4, p0, Lcom/omp/test/omptest$2;->this$0:Lcom/omp/test/omptest;

    invoke-static {v4, v3, v1, v2}, Lcom/omp/test/omptest;->access$3(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_22
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_22} :catch_23

    .line 103
    .end local v1           #str_appid:Ljava/lang/String;
    .end local v2           #str_apppid:Ljava/lang/String;
    .end local v3           #str_url:Ljava/lang/String;
    :goto_22
    return-void

    .line 99
    :catch_23
    move-exception v4

    move-object v0, v4

    .line 101
    .local v0, ex:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_22
.end method
