package iloc.touch.mobile.com.iadrlc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Admin on 7/14/2015.
 */
public class ILocMobileBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            Intent background = new Intent(context, BackgroundService.class);
            context.startService(background);
        }
    }
}
