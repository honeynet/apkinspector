.class public Lcom/omp/test/omptest;
.super Landroid/app/Activity;
.source "omptest.java"


# static fields
.field private static app_ID:Ljava/lang/String;

.field private static app_URL:Ljava/lang/String;


# instance fields
.field private appid:Landroid/widget/TextView;

.field private apppid:Landroid/widget/TextView;

.field private b_Flag:Z

.field private btn_enablercalling:Landroid/view/View$OnClickListener;

.field private btn_query:Landroid/view/View$OnClickListener;

.field private btn_sub:Landroid/view/View$OnClickListener;

.field private btn_unsub:Landroid/view/View$OnClickListener;

.field private edittext_display:Landroid/widget/EditText;

.field private httpbody:Landroid/widget/TextView;

.field private httpmethod:Landroid/widget/TextView;

.field private m_log:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private omplog:Lcom/omp/test/OmpLog;

.field private str_Log:Ljava/lang/String;

.field private url:Landroid/widget/TextView;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 23
    const-string v0, "001000000000000218"

    sput-object v0, Lcom/omp/test/omptest;->app_ID:Ljava/lang/String;

    .line 28
    const-string v0, "http://218.206.178.21"

    sput-object v0, Lcom/omp/test/omptest;->app_URL:Ljava/lang/String;

    .line 21
    return-void
.end method

.method public constructor <init>()V
    .registers 2

    .prologue
    .line 21
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 37
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/omp/test/omptest;->m_log:Ljava/util/ArrayList;

    .line 38
    new-instance v0, Lcom/omp/test/OmpLog;

    invoke-direct {v0}, Lcom/omp/test/OmpLog;-><init>()V

    iput-object v0, p0, Lcom/omp/test/omptest;->omplog:Lcom/omp/test/OmpLog;

    .line 39
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 40
    const-string v0, ""

    iput-object v0, p0, Lcom/omp/test/omptest;->str_Log:Ljava/lang/String;

    .line 71
    new-instance v0, Lcom/omp/test/omptest$1;

    invoke-direct {v0, p0}, Lcom/omp/test/omptest$1;-><init>(Lcom/omp/test/omptest;)V

    iput-object v0, p0, Lcom/omp/test/omptest;->btn_query:Landroid/view/View$OnClickListener;

    .line 87
    new-instance v0, Lcom/omp/test/omptest$2;

    invoke-direct {v0, p0}, Lcom/omp/test/omptest$2;-><init>(Lcom/omp/test/omptest;)V

    iput-object v0, p0, Lcom/omp/test/omptest;->btn_sub:Landroid/view/View$OnClickListener;

    .line 105
    new-instance v0, Lcom/omp/test/omptest$3;

    invoke-direct {v0, p0}, Lcom/omp/test/omptest$3;-><init>(Lcom/omp/test/omptest;)V

    iput-object v0, p0, Lcom/omp/test/omptest;->btn_unsub:Landroid/view/View$OnClickListener;

    .line 122
    new-instance v0, Lcom/omp/test/omptest$4;

    invoke-direct {v0, p0}, Lcom/omp/test/omptest$4;-><init>(Lcom/omp/test/omptest;)V

    iput-object v0, p0, Lcom/omp/test/omptest;->btn_enablercalling:Landroid/view/View$OnClickListener;

    .line 21
    return-void
.end method

.method static synthetic access$0()Ljava/lang/String;
    .registers 1

    .prologue
    .line 28
    sget-object v0, Lcom/omp/test/omptest;->app_URL:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1()Ljava/lang/String;
    .registers 1

    .prologue
    .line 23
    sget-object v0, Lcom/omp/test/omptest;->app_ID:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$2(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;)V
    .registers 3
    .parameter
    .parameter
    .parameter

    .prologue
    .line 231
    invoke-direct {p0, p1, p2}, Lcom/omp/test/omptest;->testProductQuery(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$3(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .registers 4
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 299
    invoke-direct {p0, p1, p2, p3}, Lcom/omp/test/omptest;->testProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$4(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;)V
    .registers 3
    .parameter
    .parameter
    .parameter

    .prologue
    .line 440
    invoke-direct {p0, p1, p2}, Lcom/omp/test/omptest;->testProductUnsub(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$5(Lcom/omp/test/omptest;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)V
    .registers 6
    .parameter
    .parameter
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 369
    invoke-direct/range {p0 .. p5}, Lcom/omp/test/omptest;->testEnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)V

    return-void
.end method

.method private errorDialog(Ljava/lang/String;Ljava/lang/String;)V
    .registers 9
    .parameter "str_title"
    .parameter "str_message"

    .prologue
    .line 160
    :try_start_0
    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v2

    .line 161
    .local v2, layoutInflater:Landroid/view/LayoutInflater;
    const/high16 v3, 0x7f03

    const/4 v4, 0x0

    invoke-virtual {v2, v3, v4}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    .line 163
    .local v0, errorMessage:Landroid/view/View;
    new-instance v3, Landroid/app/AlertDialog$Builder;

    invoke-direct {v3, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v3, p1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    .line 164
    invoke-virtual {v3, p2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    .line 165
    invoke-virtual {v3, v0}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    .line 166
    const-string v4, "Close"

    .line 167
    new-instance v5, Lcom/omp/test/omptest$5;

    invoke-direct {v5, p0}, Lcom/omp/test/omptest$5;-><init>(Lcom/omp/test/omptest;)V

    .line 166
    invoke-virtual {v3, v4, v5}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    .line 174
    invoke-virtual {v3}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;
    :try_end_2a
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2a} :catch_2b

    .line 180
    .end local v0           #errorMessage:Landroid/view/View;
    .end local v2           #layoutInflater:Landroid/view/LayoutInflater;
    :goto_2a
    return-void

    .line 176
    :catch_2b
    move-exception v3

    move-object v1, v3

    .line 178
    .local v1, ex:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_2a
.end method

.method private testEnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)V
    .registers 13
    .parameter "str_url"
    .parameter "str_appid"
    .parameter "str_heepmethod"
    .parameter "ht_httphead"
    .parameter "byte_httpbody"

    .prologue
    const-string v2, "Fail to invoke the API of \'EnablerCalling\',the error code is "

    const-string v6, "EnablerCalling Failed"

    const-string v5, "f"

    const-string v2, "errorCode"

    const-string v2, "d"

    .line 373
    :try_start_a
    new-instance v1, Ljava/util/Hashtable;

    invoke-direct {v1}, Ljava/util/Hashtable;-><init>()V

    .line 375
    .local v1, ht_Ret:Ljava/util/Hashtable;
    const-string v2, "s"

    const-string v3, "Start to test API of EnablerCalling"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 376
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the EnablerCalling with parameter of \'url\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 377
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the EnablerCalling with parameter of \'appid\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 378
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the EnablerCalling with parameter of \'httpmethod\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 379
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the EnablerCalling with parameter of \'httphead\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, "key1"

    invoke-virtual {p4, v4}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 384
    invoke-static {p1, p2, p3, p4, p5}, Lcom/irdeto/omp/OmpClient;->EnablerCalling(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Hashtable;[B)Ljava/util/Hashtable;

    move-result-object v1

    .line 387
    const-string v2, "errorCode"

    invoke-virtual {v1, v2}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "0"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_113

    .line 389
    const-string v2, "errorCode"

    invoke-virtual {v1, v2}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "8020"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_a3

    .line 391
    const-string v2, "f"

    const-string v3, "Fail to invoke the API of \'EnablerCalling\',you haven\'t subscribe a product"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 392
    const-string v2, "\u80fd\u529b\u8c03\u7528\u5931\u8d25"

    const-string v3, "\u80fd\u529b\u8c03\u7528\u5931\u8d25\uff0c\u672a\u8ba2\u8d2d\u6b64\u4ea7\u54c1\u5e94\u7528"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    .line 411
    .end local v1           #ht_Ret:Ljava/util/Hashtable;
    :goto_a2
    return-void

    .line 396
    .restart local v1       #ht_Ret:Ljava/util/Hashtable;
    :cond_a3
    const-string v2, "f"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'EnablerCalling\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, "errorCode"

    invoke-virtual {v1, v4}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 397
    const-string v2, "EnablerCalling Failed"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'EnablerCalling\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, "errorCode"

    invoke-virtual {v1, v4}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_df
    .catch Ljava/lang/Exception; {:try_start_a .. :try_end_df} :catch_e0

    goto :goto_a2

    .line 405
    .end local v1           #ht_Ret:Ljava/util/Hashtable;
    :catch_e0
    move-exception v2

    move-object v0, v2

    .line 407
    .local v0, ex:Ljava/lang/Exception;
    const-string v2, "f"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Fail to invoke the API of \'EnablerCalling\',the error message is "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v5, v2}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 408
    const-string v2, "EnablerCalling Failed"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Fail to invoke the API of \'EnablerCalling\',the error message is "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v6, v2}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_a2

    .line 402
    .end local v0           #ex:Ljava/lang/Exception;
    .restart local v1       #ht_Ret:Ljava/util/Hashtable;
    :cond_113
    :try_start_113
    const-string v2, "p"

    const-string v3, "Test API of \'EnablerCalling\' successfully"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_11a
    .catch Ljava/lang/Exception; {:try_start_113 .. :try_end_11a} :catch_e0

    goto :goto_a2
.end method

.method private testProductQuery(Ljava/lang/String;Ljava/lang/String;)V
    .registers 14
    .parameter "str_url"
    .parameter "str_appid"

    .prologue
    const/4 v8, 0x0

    const-string v10, "ProductQuery Failed"

    const-string v9, "Fail to invoke the API of \'ProductQuery\',the error message is "

    const-string v5, "Fail to invoke the API of \'ProductQuery\',the error code is "

    const-string v5, "d"

    .line 235
    :try_start_9
    const-string v4, ""

    .line 236
    .local v4, str_recordList:Ljava/lang/String;
    new-instance v1, Ljava/util/Hashtable;

    invoke-direct {v1}, Ljava/util/Hashtable;-><init>()V

    .line 238
    .local v1, ht_Ret:Ljava/util/Hashtable;
    const-string v5, "s"

    const-string v6, "Start to test API of ProductQuery"

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 239
    const-string v5, "d"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Invoke ProductQuery with parameter of \'url\' with the value is: "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 240
    const-string v5, "d"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Invoke ProductQuery with parameter of \'appid\' with the value is: "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 241
    invoke-static {p1, p2}, Lcom/irdeto/omp/OmpClient;->ProductQuery(Ljava/lang/String;Ljava/lang/String;)Ljava/util/Hashtable;

    move-result-object v1

    .line 243
    const-string v5, "errorCode"

    invoke-virtual {v1, v5}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v5

    const-string v6, "0"

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_99

    .line 245
    const/4 v5, 0x0

    iput-boolean v5, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 246
    const-string v5, "f"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Fail to invoke the API of \'ProductQuery\',the error code is "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v7, "errorcode"

    invoke-virtual {v1, v7}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 247
    const-string v5, "ProductQuery Failed"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Fail to invoke the API of \'ProductQuery\',the error code is "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v7, "errorcode"

    invoke-virtual {v1, v7}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    .line 269
    .end local v1           #ht_Ret:Ljava/util/Hashtable;
    .end local v4           #str_recordList:Ljava/lang/String;
    :goto_98
    return-void

    .line 251
    .restart local v1       #ht_Ret:Ljava/util/Hashtable;
    .restart local v4       #str_recordList:Ljava/lang/String;
    :cond_99
    const-string v5, "chargeRecord"

    invoke-virtual {v1, v5}, Ljava/util/Hashtable;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/ArrayList;

    .line 252
    .local v3, recordList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/irdeto/omp/chargeRecord;>;"
    const/4 v2, 0x0

    .local v2, i:I
    :goto_a2
    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-lt v2, v5, :cond_f9

    .line 258
    const-string v5, "d"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "The RecordList returned from ProductQuery is "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 259
    const-string v5, "p"

    const-string v6, "Test of \'ProductQuery\' successfully"

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_c3
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_c3} :catch_c4

    goto :goto_98

    .line 262
    .end local v1           #ht_Ret:Ljava/util/Hashtable;
    .end local v2           #i:I
    .end local v3           #recordList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/irdeto/omp/chargeRecord;>;"
    .end local v4           #str_recordList:Ljava/lang/String;
    :catch_c4
    move-exception v5

    move-object v0, v5

    .line 264
    .local v0, ex:Ljava/lang/Exception;
    iput-boolean v8, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 265
    const-string v5, "f"

    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Fail to invoke the API of \'ProductQuery\',the error message is "

    invoke-direct {v6, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {p0, v5, v6}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 266
    const-string v5, "ProductQuery Failed"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Fail to invoke the API of \'ProductQuery\',the error message is "

    invoke-direct {v5, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-direct {p0, v10, v5}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_98

    .line 254
    .end local v0           #ex:Ljava/lang/Exception;
    .restart local v1       #ht_Ret:Ljava/util/Hashtable;
    .restart local v2       #i:I
    .restart local v3       #recordList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/irdeto/omp/chargeRecord;>;"
    .restart local v4       #str_recordList:Ljava/lang/String;
    :cond_f9
    :try_start_f9
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {v6, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/irdeto/omp/chargeRecord;

    iget-object v5, v5, Lcom/irdeto/omp/chargeRecord;->apppid:Ljava/lang/String;

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 255
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v6, "-"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 256
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-direct {v6, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/irdeto/omp/chargeRecord;

    iget-object v5, v5, Lcom/irdeto/omp/chargeRecord;->apppdesc:Ljava/lang/String;

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_13d
    .catch Ljava/lang/Exception; {:try_start_f9 .. :try_end_13d} :catch_c4

    move-result-object v4

    .line 252
    add-int/lit8 v2, v2, 0x1

    goto/16 :goto_a2
.end method

.method private testProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .registers 12
    .parameter "str_url"
    .parameter "str_appid"
    .parameter "str_apppid"

    .prologue
    const/4 v5, 0x0

    const-string v7, "Fail to invoke the API of \'ProductSub\',the error message is "

    const-string v2, "Fail to invoke the API of \'ProductSub\',the error code is "

    const-string v6, "f"

    const-string v2, "d"

    .line 303
    const/4 v1, 0x0

    .line 304
    .local v1, n_ret:I
    :try_start_a
    const-string v2, "s"

    const-string v3, "Start to test API of ProductSub"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 305
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the ProductSub with parameter of \'url\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 306
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the ProductSub with parameter of \'appid\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 307
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the ProductSub with parameter of \'apppid\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 309
    invoke-static {p1, p2, p3}, Lcom/irdeto/omp/OmpClient;->ProductSub(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    .line 311
    if-eqz v1, :cond_d2

    .line 313
    const/16 v2, 0x4b0

    if-ne v2, v1, :cond_69

    .line 315
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 316
    const-string v2, "f"

    const-string v3, "Fail to invoke the API of \'ProductSub\',you have subscribe this product already!"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 317
    const-string v2, "\u4ea7\u54c1\u8ba2\u8d2d\u5931\u8d25"

    const-string v3, "\u4ea7\u54c1\u8ba2\u8d2d\u8c03\u7528\u5931\u8d25,\u5df2\u7ecf\u8ba2\u8d2d\u4e86\u6b64\u5e94\u7528"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    .line 339
    :goto_68
    return-void

    .line 321
    :cond_69
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 322
    const-string v2, "f"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductSub\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 323
    const-string v2, "ProductSub Failed"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductSub\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_9c
    .catch Ljava/lang/Exception; {:try_start_a .. :try_end_9c} :catch_9d

    goto :goto_68

    .line 332
    :catch_9d
    move-exception v2

    move-object v0, v2

    .line 334
    .local v0, ex:Ljava/lang/Exception;
    iput-boolean v5, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 335
    const-string v2, "f"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Fail to invoke the API of \'ProductSub\',the error message is "

    invoke-direct {v2, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v6, v2}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 336
    const-string v2, "ProductSub Failed"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductSub\',the error message is "

    invoke-direct {v3, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_68

    .line 328
    .end local v0           #ex:Ljava/lang/Exception;
    :cond_d2
    :try_start_d2
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "The API of \'ProductSub\' response as:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 329
    const-string v2, "p"

    const-string v3, "Test API of \'ProductSub\' successfully"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_f1
    .catch Ljava/lang/Exception; {:try_start_d2 .. :try_end_f1} :catch_9d

    goto/16 :goto_68
.end method

.method private testProductUnsub(Ljava/lang/String;Ljava/lang/String;)V
    .registers 11
    .parameter "str_url"
    .parameter "str_appid"

    .prologue
    const/4 v5, 0x0

    const-string v7, "Fail to invoke the API of \'ProductUnsub\',the error message is "

    const-string v2, "Fail to invoke the API of \'ProductUnsub\',the error code is "

    const-string v6, "f"

    const-string v2, "d"

    .line 444
    :try_start_9
    const-string v2, "s"

    const-string v3, "Start to test API of ProductUnsub"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 445
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the ProductUnsub with parameter of \'url\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 446
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Invoke the ProductUnsub with parameter of \'appid\' with the value is: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 448
    invoke-static {p1, p2}, Lcom/irdeto/omp/OmpClient;->ProductUnsub(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    .line 450
    .local v1, n_Ret:I
    if-eqz v1, :cond_bd

    .line 452
    const/16 v2, 0x4b1

    if-ne v2, v1, :cond_54

    .line 454
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 455
    const-string v2, "f"

    const-string v3, "Fail to invoke the API of \'ProductUnsub\',you haven\'t subscribe the product"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 456
    const-string v2, "\u9000\u8ba2\u4ea7\u54c1\u5931\u8d25"

    const-string v3, "\u9000\u8ba2\u4ea7\u54c1\u5931\u8d25\uff0c\u672a\u8ba2\u8d2d\u6b64\u5e94\u7528"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    .line 478
    .end local v1           #n_Ret:I
    :goto_53
    return-void

    .line 460
    .restart local v1       #n_Ret:I
    :cond_54
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 461
    const-string v2, "f"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductUnsub\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 462
    const-string v2, "ProductUnsub Failed"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductUnsub\',the error code is "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_87
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_87} :catch_88

    goto :goto_53

    .line 471
    .end local v1           #n_Ret:I
    :catch_88
    move-exception v2

    move-object v0, v2

    .line 473
    .local v0, ex:Ljava/lang/Exception;
    iput-boolean v5, p0, Lcom/omp/test/omptest;->b_Flag:Z

    .line 474
    const-string v2, "f"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Fail to invoke the API of \'ProductUnsub\',the error message is "

    invoke-direct {v2, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v6, v2}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 475
    const-string v2, "ProductUnsub Failed"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Fail to invoke the API of \'ProductUnsub\',the error message is "

    invoke-direct {v3, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->errorDialog(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_53

    .line 467
    .end local v0           #ex:Ljava/lang/Exception;
    .restart local v1       #n_Ret:I
    :cond_bd
    :try_start_bd
    const-string v2, "d"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "The API of \'ProductUnsub\' response as:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 468
    const-string v2, "p"

    const-string v3, "Test API of \'ProductUnsub\' successfully"

    invoke-direct {p0, v2, v3}, Lcom/omp/test/omptest;->wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_dc
    .catch Ljava/lang/Exception; {:try_start_bd .. :try_end_dc} :catch_88

    goto/16 :goto_53
.end method

.method private wdisplayLog(Ljava/lang/String;Ljava/lang/String;)V
    .registers 9
    .parameter "str_logType"
    .parameter "str_log"

    .prologue
    .line 186
    const-string v2, ""

    .line 190
    .local v2, str_Display:Ljava/lang/String;
    :try_start_2
    iget-object v3, p0, Lcom/omp/test/omptest;->omplog:Lcom/omp/test/OmpLog;

    invoke-virtual {v3, p1, p2}, Lcom/omp/test/OmpLog;->writeLog(Ljava/lang/String;Ljava/lang/String;)V

    .line 191
    iget-object v3, p0, Lcom/omp/test/omptest;->omplog:Lcom/omp/test/OmpLog;

    invoke-virtual {v3}, Lcom/omp/test/OmpLog;->getLog()Ljava/util/ArrayList;

    move-result-object v3

    iput-object v3, p0, Lcom/omp/test/omptest;->m_log:Ljava/util/ArrayList;

    .line 192
    const/4 v1, 0x0

    .local v1, n:I
    :goto_10
    iget-object v3, p0, Lcom/omp/test/omptest;->m_log:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-lt v1, v3, :cond_2f

    .line 196
    iget-object v3, p0, Lcom/omp/test/omptest;->edittext_display:Landroid/widget/EditText;

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    sget-object v5, Landroid/widget/TextView$BufferType;->SPANNABLE:Landroid/widget/TextView$BufferType;

    invoke-virtual {v3, v4, v5}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;Landroid/widget/TextView$BufferType;)V

    .line 197
    iget-object v3, p0, Lcom/omp/test/omptest;->edittext_display:Landroid/widget/EditText;

    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v4

    const/4 v5, 0x1

    sub-int/2addr v4, v5

    invoke-virtual {v3, v4}, Landroid/widget/EditText;->setSelection(I)V

    .line 203
    .end local v1           #n:I
    :goto_2e
    return-void

    .line 194
    .restart local v1       #n:I
    :cond_2f
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-static {v2}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v4, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v3, p0, Lcom/omp/test/omptest;->m_log:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_4d
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_4d} :catch_51

    move-result-object v2

    .line 192
    add-int/lit8 v1, v1, 0x1

    goto :goto_10

    .line 199
    .end local v1           #n:I
    :catch_51
    move-exception v3

    move-object v0, v3

    .line 201
    .local v0, ex:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_2e
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .registers 10
    .parameter "savedInstanceState"

    .prologue
    const-string v7, "test"

    .line 45
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 46
    const-string v6, "test"

    const-string v6, "on Create"

    invoke-static {v7, v6}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 47
    const v6, 0x7f030001

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->setContentView(I)V

    .line 48
    const v6, 0x7f050003

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    .line 49
    .local v2, button_query:Landroid/widget/Button;
    const v6, 0x7f050004

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    .line 50
    .local v3, button_sub:Landroid/widget/Button;
    const v6, 0x7f050006

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    .line 51
    .local v4, button_unsub:Landroid/widget/Button;
    const v6, 0x7f050007

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    .line 53
    .local v1, button_enablercalling:Landroid/widget/Button;
    iget-object v6, p0, Lcom/omp/test/omptest;->btn_query:Landroid/view/View$OnClickListener;

    invoke-virtual {v2, v6}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 54
    iget-object v6, p0, Lcom/omp/test/omptest;->btn_sub:Landroid/view/View$OnClickListener;

    invoke-virtual {v3, v6}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 55
    iget-object v6, p0, Lcom/omp/test/omptest;->btn_unsub:Landroid/view/View$OnClickListener;

    invoke-virtual {v4, v6}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 56
    iget-object v6, p0, Lcom/omp/test/omptest;->btn_enablercalling:Landroid/view/View$OnClickListener;

    invoke-virtual {v1, v6}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 58
    const v6, 0x7f050009

    invoke-virtual {p0, v6}, Lcom/omp/test/omptest;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/EditText;

    iput-object v6, p0, Lcom/omp/test/omptest;->edittext_display:Landroid/widget/EditText;

    .line 60
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 63
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    :try_start_5a
    const-string v6, "test"

    const-string v7, "before init"

    invoke-static {v6, v7}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 64
    invoke-static {p0}, Lcom/irdeto/omp/OmpClient;->InitOmpEnv(Landroid/content/Context;)I
    :try_end_64
    .catch Ljava/lang/Exception; {:try_start_5a .. :try_end_64} :catch_65

    .line 69
    :goto_64
    return-void

    .line 65
    :catch_65
    move-exception v6

    move-object v5, v6

    .line 67
    .local v5, e:Ljava/lang/Exception;
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_64
.end method
