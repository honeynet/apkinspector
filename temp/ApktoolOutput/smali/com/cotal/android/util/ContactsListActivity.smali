.class public Lcom/cotal/android/util/ContactsListActivity;
.super Landroid/app/ListActivity;
.source "ContactsListActivity.java"


# instance fields
.field private curContacts:Landroid/database/Cursor;

.field private selContactName:Ljava/lang/String;

.field private selContactNumber:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 18
    invoke-direct {p0}, Landroid/app/ListActivity;-><init>()V

    #p0=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    return-void
.end method

.method private fillData()V
    .locals 7

    .prologue
    const/4 v5, 0x1

    #v5=(One);
    const/4 v6, 0x0

    .line 80
    #v6=(Null);
    invoke-direct {p0}, Lcom/cotal/android/util/ContactsListActivity;->getContacts()Z

    move-result v1

    #v1=(Boolean);
    if-nez v1, :cond_0

    .line 91
    :goto_0
    #v0=(Conflicted);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);v4=(Conflicted);v5=(Conflicted);
    return-void

    .line 85
    :cond_0
    #v0=(Uninit);v1=(Boolean);v2=(Uninit);v3=(Uninit);v4=(Uninit);v5=(One);
    new-array v4, v5, [Ljava/lang/String;

    .line 86
    #v4=(Reference,[Ljava/lang/String;);
    const-string v1, "display_name"

    #v1=(Reference,Ljava/lang/String;);
    aput-object v1, v4, v6

    .line 89
    .local v4, fields:[Ljava/lang/String;
    new-instance v0, Landroid/widget/SimpleCursorAdapter;

    #v0=(UninitRef,Landroid/widget/SimpleCursorAdapter;);
    const v2, 0x7f030004

    #v2=(Integer);
    iget-object v3, p0, Lcom/cotal/android/util/ContactsListActivity;->curContacts:Landroid/database/Cursor;

    #v3=(Reference,Landroid/database/Cursor;);
    new-array v5, v5, [I

    #v5=(Reference,[I);
    const v1, 0x7f060012

    #v1=(Integer);
    aput v1, v5, v6

    move-object v1, p0

    #v1=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleCursorAdapter;-><init>(Landroid/content/Context;ILandroid/database/Cursor;[Ljava/lang/String;[I)V

    .line 90
    .local v0, adapter:Landroid/widget/SimpleCursorAdapter;
    #v0=(Reference,Landroid/widget/SimpleCursorAdapter;);
    invoke-virtual {p0, v0}, Lcom/cotal/android/util/ContactsListActivity;->setListAdapter(Landroid/widget/ListAdapter;)V

    goto :goto_0
.end method

.method private getContacts()Z
    .locals 10

    .prologue
    const/4 v9, 0x1

    #v9=(One);
    const/4 v8, 0x0

    .line 97
    :try_start_0
    #v8=(Null);
    sget-object v1, Landroid/provider/ContactsContract$Contacts;->CONTENT_URI:Landroid/net/Uri;

    .line 98
    .local v1, uri:Landroid/net/Uri;
    #v1=(Reference,Landroid/net/Uri;);
    const/4 v0, 0x2

    #v0=(PosByte);
    new-array v2, v0, [Ljava/lang/String;

    #v2=(Reference,[Ljava/lang/String;);
    const/4 v0, 0x0

    .line 99
    #v0=(Null);
    const-string v7, "_id"

    #v7=(Reference,Ljava/lang/String;);
    aput-object v7, v2, v0

    const/4 v0, 0x1

    .line 100
    #v0=(One);
    const-string v7, "display_name"

    aput-object v7, v2, v0

    .line 103
    .local v2, projection:[Ljava/lang/String;
    const-string v3, "has_phone_number=\'1\'"

    .line 104
    .local v3, selection:Ljava/lang/String;
    #v3=(Reference,Ljava/lang/String;);
    const/4 v4, 0x0

    #v4=(Null);
    check-cast v4, [Ljava/lang/String;

    .line 105
    .local v4, selectionArgs:[Ljava/lang/String;
    #v4=(Reference,[Ljava/lang/String;);
    const-string v5, "display_name COLLATE LOCALIZED ASC"

    .local v5, sortOrder:Ljava/lang/String;
    #v5=(Reference,Ljava/lang/String;);
    move-object v0, p0

    .line 107
    #v0=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    invoke-virtual/range {v0 .. v5}, Lcom/cotal/android/util/ContactsListActivity;->managedQuery(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v0

    iput-object v0, p0, Lcom/cotal/android/util/ContactsListActivity;->curContacts:Landroid/database/Cursor;

    .line 108
    iget-object v0, p0, Lcom/cotal/android/util/ContactsListActivity;->curContacts:Landroid/database/Cursor;

    invoke-virtual {p0, v0}, Lcom/cotal/android/util/ContactsListActivity;->startManagingCursor(Landroid/database/Cursor;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v0, v9

    .line 113
    .end local v1           #uri:Landroid/net/Uri;
    .end local v2           #projection:[Ljava/lang/String;
    .end local v3           #selection:Ljava/lang/String;
    .end local v4           #selectionArgs:[Ljava/lang/String;
    .end local v5           #sortOrder:Ljava/lang/String;
    :goto_0
    #v0=(Boolean);v1=(Conflicted);v2=(Conflicted);v3=(Conflicted);v4=(Conflicted);v5=(Conflicted);v6=(Conflicted);v7=(Conflicted);
    return v0

    .line 111
    :catch_0
    #v0=(Conflicted);v6=(Uninit);
    move-exception v0

    #v0=(Reference,Ljava/lang/Exception;);
    move-object v6, v0

    .local v6, ex:Ljava/lang/Exception;
    #v6=(Reference,Ljava/lang/Exception;);
    move v0, v8

    .line 113
    #v0=(Null);
    goto :goto_0
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 28
    invoke-super {p0, p1}, Landroid/app/ListActivity;->onCreate(Landroid/os/Bundle;)V

    .line 29
    const v0, 0x7f050009

    #v0=(Integer);
    invoke-virtual {p0, v0}, Lcom/cotal/android/util/ContactsListActivity;->setTitle(I)V

    .line 30
    invoke-direct {p0}, Lcom/cotal/android/util/ContactsListActivity;->fillData()V

    .line 31
    const-string v0, ""

    #v0=(Reference,Ljava/lang/String;);
    iput-object v0, p0, Lcom/cotal/android/util/ContactsListActivity;->selContactName:Ljava/lang/String;

    .line 32
    const-string v0, ""

    iput-object v0, p0, Lcom/cotal/android/util/ContactsListActivity;->selContactNumber:Ljava/lang/String;

    .line 33
    return-void
.end method

.method protected onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V
    .locals 19
    .parameter "l"
    .parameter "v"
    .parameter "position"
    .parameter "id"

    .prologue
    .line 40
    sget-object v4, Landroid/provider/ContactsContract$Contacts;->CONTENT_URI:Landroid/net/Uri;

    .line 41
    .local v4, uri:Landroid/net/Uri;
    #v4=(Reference,Landroid/net/Uri;);
    const/4 v3, 0x2

    #v3=(PosByte);
    new-array v5, v3, [Ljava/lang/String;

    #v5=(Reference,[Ljava/lang/String;);
    const/4 v3, 0x0

    .line 42
    #v3=(Null);
    const-string v6, "display_name"

    #v6=(Reference,Ljava/lang/String;);
    aput-object v6, v5, v3

    const/4 v3, 0x1

    .line 43
    #v3=(One);
    const-string v6, "photo_id"

    aput-object v6, v5, v3

    .line 46
    .local v5, projection:[Ljava/lang/String;
    new-instance v3, Ljava/lang/StringBuilder;

    #v3=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v6, "_id=\'"

    invoke-direct {v3, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v3=(Reference,Ljava/lang/StringBuilder;);
    move-object v0, v3

    #v0=(Reference,Ljava/lang/StringBuilder;);
    move-wide/from16 v1, p4

    #v1=(LongLo);v2=(LongHi);
    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v6, "\'"

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 47
    .local v6, selection:Ljava/lang/String;
    const/4 v7, 0x0

    #v7=(Null);
    const/4 v8, 0x0

    #v8=(Null);
    move-object/from16 v3, p0

    invoke-virtual/range {v3 .. v8}, Lcom/cotal/android/util/ContactsListActivity;->managedQuery(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v14

    .line 48
    .local v14, cursor:Landroid/database/Cursor;
    #v14=(Reference,Landroid/database/Cursor;);
    const-string v18, ""

    .line 49
    .local v18, photoId:Ljava/lang/String;
    #v18=(Reference,Ljava/lang/String;);
    invoke-interface {v14}, Landroid/database/Cursor;->moveToFirst()Z

    move-result v3

    #v3=(Boolean);
    if-eqz v3, :cond_0

    .line 51
    const-string v3, "display_name"

    #v3=(Reference,Ljava/lang/String;);
    invoke-interface {v14, v3}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v16

    .line 52
    .local v16, nameFieldColumnIndex:I
    #v16=(Integer);
    move-object v0, v14

    move/from16 v1, v16

    #v1=(Integer);
    invoke-interface {v0, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v3

    move-object v0, v3

    move-object/from16 v1, p0

    #v1=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    iput-object v0, v1, Lcom/cotal/android/util/ContactsListActivity;->selContactName:Ljava/lang/String;

    .line 53
    const-string v3, "photo_id"

    invoke-interface {v14, v3}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v16

    .line 54
    move-object v0, v14

    move/from16 v1, v16

    #v1=(Integer);
    invoke-interface {v0, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v18

    .line 58
    .end local v16           #nameFieldColumnIndex:I
    :cond_0
    #v1=(Conflicted);v3=(Conflicted);v16=(Conflicted);
    sget-object v8, Landroid/provider/ContactsContract$CommonDataKinds$Phone;->CONTENT_URI:Landroid/net/Uri;

    #v8=(Reference,Landroid/net/Uri;);
    const/4 v9, 0x0

    #v9=(Null);
    new-instance v3, Ljava/lang/StringBuilder;

    #v3=(UninitRef,Ljava/lang/StringBuilder;);
    const-string v4, "contact_id = "

    .end local v4           #uri:Landroid/net/Uri;
    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    #v3=(Reference,Ljava/lang/StringBuilder;);
    move-object v0, v3

    move-wide/from16 v1, p4

    #v1=(LongLo);
    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    #v10=(Reference,Ljava/lang/String;);
    const/4 v11, 0x0

    #v11=(Null);
    const/4 v12, 0x0

    #v12=(Null);
    move-object/from16 v7, p0

    #v7=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    invoke-virtual/range {v7 .. v12}, Lcom/cotal/android/util/ContactsListActivity;->managedQuery(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v17

    .line 59
    .local v17, phone:Landroid/database/Cursor;
    #v17=(Reference,Landroid/database/Cursor;);
    invoke-interface/range {v17 .. v17}, Landroid/database/Cursor;->moveToNext()Z

    move-result v3

    #v3=(Boolean);
    if-eqz v3, :cond_1

    .line 61
    const-string v3, "data1"

    #v3=(Reference,Ljava/lang/String;);
    move-object/from16 v0, v17

    move-object v1, v3

    #v1=(Reference,Ljava/lang/String;);
    invoke-interface {v0, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v3

    #v3=(Integer);
    move-object/from16 v0, v17

    move v1, v3

    #v1=(Integer);
    invoke-interface {v0, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v3

    #v3=(Reference,Ljava/lang/String;);
    move-object v0, v3

    move-object/from16 v1, p0

    #v1=(Reference,Lcom/cotal/android/util/ContactsListActivity;);
    iput-object v0, v1, Lcom/cotal/android/util/ContactsListActivity;->selContactNumber:Ljava/lang/String;

    .line 65
    :cond_1
    #v1=(Conflicted);v3=(Conflicted);
    new-instance v13, Landroid/os/Bundle;

    #v13=(UninitRef,Landroid/os/Bundle;);
    invoke-direct {v13}, Landroid/os/Bundle;-><init>()V

    .line 66
    .local v13, bundle:Landroid/os/Bundle;
    #v13=(Reference,Landroid/os/Bundle;);
    const-string v3, "Name"

    #v3=(Reference,Ljava/lang/String;);
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/cotal/android/util/ContactsListActivity;->selContactName:Ljava/lang/String;

    move-object v4, v0

    invoke-virtual {v13, v3, v4}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 67
    const-string v3, "Number"

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/cotal/android/util/ContactsListActivity;->selContactNumber:Ljava/lang/String;

    move-object v4, v0

    invoke-virtual {v13, v3, v4}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 68
    const-string v3, "PhotoID"

    move-object v0, v13

    move-object v1, v3

    #v1=(Reference,Ljava/lang/String;);
    move-object/from16 v2, v18

    #v2=(Reference,Ljava/lang/String;);
    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 69
    new-instance v15, Landroid/content/Intent;

    #v15=(UninitRef,Landroid/content/Intent;);
    invoke-direct {v15}, Landroid/content/Intent;-><init>()V

    .line 70
    .local v15, mIntent:Landroid/content/Intent;
    #v15=(Reference,Landroid/content/Intent;);
    invoke-virtual {v15, v13}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    .line 71
    const/4 v3, -0x1

    #v3=(Byte);
    move-object/from16 v0, p0

    move v1, v3

    #v1=(Byte);
    move-object v2, v15

    invoke-virtual {v0, v1, v2}, Lcom/cotal/android/util/ContactsListActivity;->setResult(ILandroid/content/Intent;)V

    .line 72
    invoke-virtual/range {p0 .. p0}, Lcom/cotal/android/util/ContactsListActivity;->finish()V

    .line 73
    return-void
.end method
