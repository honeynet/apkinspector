.class Lcom/omp/test/omptest$4;
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
    iput-object p1, p0, Lcom/omp/test/omptest$4;->this$0:Lcom/omp/test/omptest;

    .line 122
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 11
    .parameter "v"

    .prologue
    .line 130
    :try_start_0
    new-instance v4, Ljava/util/Hashtable;

    invoke-direct {v4}, Ljava/util/Hashtable;-><init>()V

    .line 134
    .local v4, ht_httphead:Ljava/util/Hashtable;
    const-string v8, "<request><operation>registrationrequest</operation><serviceid>JIL Store</serviceid><serviceversion>1.0</serviceversion><registrationrequest><timestamp>2010-07-28T09:27:23Z</timestamp><hash>nqD59MQDiqPIMHCGKOZ4kpvPBCQs68uPNe1W7jNkT74=</hash></registrationrequest></request>"

    .line 136
    .local v8, str_httpBody:Ljava/lang/String;
    invoke-virtual {v8}, Ljava/lang/String;->getBytes()[B

    move-result-object v6

    .line 139
    .local v6, byte_hb:[B
    invoke-static {}, Lcom/omp/test/omptest;->access$1()Ljava/lang/String;

    move-result-object v2

    .line 142
    .local v2, str_appId:Ljava/lang/String;
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-static {}, Lcom/omp/test/omptest;->access$0()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {v0, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v5, ":9083/1.0/GISService/mapgrid?x=10222&y=1393&zoom=16"

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 145
    .local v1, str_url:Ljava/lang/String;
    const-string v3, "POST"

    .line 147
    .local v3, str_method:Ljava/lang/String;
    iget-object v0, p0, Lcom/omp/test/omptest$4;->this$0:Lcom/omp/test/omptest;

    const/4 v5, 0x0

    invoke-static/range {v0 .. v5}, Lcom/omp/test/omptest;->access$5(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)V
    :try_end_2e
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2e} :catch_2f

    .line 153
    .end local v1           #str_url:Ljava/lang/String;
    .end local v2           #str_appId:Ljava/lang/String;
    .end local v3           #str_method:Ljava/lang/String;
    .end local v4           #ht_httphead:Ljava/util/Hashtable;
    .end local v6           #byte_hb:[B
    .end local v8           #str_httpBody:Ljava/lang/String;
    :goto_2e
    return-void

    .line 149
    :catch_2f
    move-exception v0

    move-object v7, v0

    .line 151
    .local v7, ex:Ljava/lang/Exception;
    invoke-virtual {v7}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_2e
.end method
