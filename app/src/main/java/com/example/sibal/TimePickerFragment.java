package com.example.sibal;

        import android.app.AlarmManager;
        import android.app.Dialog;
        import android.app.PendingIntent;
        import android.app.TimePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.widget.TimePicker;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.DialogFragment;

        import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private AlarmManager mAlarmManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        int hour = 0;
        int minute = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Calendar c = Calendar.getInstance();
            //시간 분 설정
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);}

        return new TimePickerDialog(getContext(), this, hour, minute, DateFormat.is24HourFormat(getContext()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,3);
        calendar.set(Calendar.MINUTE,2);

        Intent intent = new Intent(getContext(),MainActivity.class);
        PendingIntent operation = PendingIntent.getActivity(getContext(),0,intent,0);
        startActivity(intent); 
        mAlarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+calendar.getTimeInMillis(),operation);
    }
}
