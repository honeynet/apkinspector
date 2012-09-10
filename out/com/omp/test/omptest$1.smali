.class Lcom/omp/test/omptest$1;
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
    iput-object p1, p0, Lcom/omp/test/omptest$1;->this$0:Lcom/omp/test/omptest;

    .line 71
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 7
    .parameter "v"

    .prologue
    .line 77
    :try_start_0
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/omp/test/omptest;->access$0()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, ":9085/"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 78
    .local v2, str_url:Ljava/lang/String;
    invoke-static {}, Lcom/omp/test/omptest;->access$1()Ljava/lang/String;

    move-result-object v1

    .line 79
    .local v1, str_appid:Ljava/lang/String;
    iget-object v3, p0, Lcom/omp/test/omptest$1;->this$0:Lcom/omp/test/omptest;

    invoke-static {v3, v2, v1}, Lcom/omp/test/omptest;->access$2(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_20
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_20} :catch_21

    .line 85
    .end local v1           #str_appid:Ljava/lang/String;
    .end local v2           #str_url:Ljava/lang/String;
    :goto_20
    return-void

    .line 81
    :catch_21
    move-exception v3

    move-object v0, v3

    .line 83
    .local v0, ex:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_20
.end method
