package com.example.sibal;


        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Vibrator;
        import android.widget.Toast;


public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //알람따른 토스트 다르게하기
        Toast.makeText(context,"Alarm", Toast.LENGTH_LONG).show();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);

        v.vibrate(3000);

    }

}