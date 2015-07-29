package iloc.touch.mobile.com.iadrlc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Admin on 7/14/2015.
 */
public class ILocMobileBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
//        {
//            Intent service = new Intent(context, BackgroundService.class);
//            service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startService(service);
        Toast.makeText(context, "BroadcastReceiver Created",Toast.LENGTH_LONG).show();
        Intent service = new Intent(context, MainActivity.class);
         context.startActivity(service);
//        }
    }
}
