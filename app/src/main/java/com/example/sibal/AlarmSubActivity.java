package com.example.sibal;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class AlarmSubActivity extends AppCompatActivity {


    private AlarmManager mAlarmManager;
    Button btnSet, btn_AddAlarm;
    EditText etTime,etAlarmName,etAlarmContent;
    TextView etAlarmType,etAlarmSet;
    public int tmp = 0,type=0;
    public String name,content; int time=0;

    String texts[] = new String[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        load();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmselect);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btnSet = (Button) findViewById(R.id.btnSet);
        // btn_AddAlarm = (Button) findViewById(R.id.btn_AddAlarm);
        etTime = (EditText) findViewById(R.id.etAlarm);
        etAlarmContent = (EditText) findViewById(R.id.etAlarmContent);
        etAlarmName = (EditText) findViewById(R.id.etAlarmName);
        etAlarmType = (TextView) findViewById(R.id.etAlarmType);
        etAlarmSet = (TextView) findViewById(R.id.etAlarmSet);

        if(tmp==3){
            etAlarmName.setText(name);
            etAlarmContent.setText(content);
            etTime.setText(""+time);
            if(type == 0)
                etAlarmType.setText("날씨");
            else if(type == 1)
                etAlarmType.setText("장소");
            else if (type ==2)
                etAlarmType.setText("시간");
            else if (type == 3)
                etAlarmType.setText("기타");
        }
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    time= Integer.parseInt(etTime.getText().toString());
                    if(time >0 && time <=180) {
                        if (type == 0 || type == 3) {
                            name = etAlarmName.getText().toString();
                            content = etAlarmContent.getText().toString();

                            showAlarmDialog();
                            saves();
                        } else {
                            name = etAlarmName.getText().toString();
                            content = etAlarmContent.getText().toString();

                            Intent i = new Intent(AlarmSubActivity.this, Alarm.class);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
                            PendingIntent operation = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pi);
                            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, operation);
                            tmp = 1;
                            saves();
                            Intent inten = new Intent(AlarmSubActivity.this, MainActivity.class);
                            startActivity(inten);
                            finish();
                        }
                    }


                    else{
                        Toast.makeText(AlarmSubActivity.this,"0~180 사이의 시간을 입력해주세요", Toast.LENGTH_LONG).show();
                    }

                } catch(NumberFormatException e) {

                    Toast.makeText(AlarmSubActivity.this,"0~180 사이의 시간을 입력해주세요", Toast.LENGTH_LONG).show();

                }






            }
        });


    }
    public void onClick_alarmtype(View view){

        CharSequence[] oItems = {"1.날씨", "2.장소", "3.시간","4.기타"};


        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        oDialog.setTitle("알람 종류를 선택하세요")
                .setSingleChoiceItems(oItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type = which;
                        if(which == 0)
                            etAlarmType.setText("날씨");
                        else if(which == 1)
                            etAlarmType.setText("장소");
                        else if (which ==2)
                            etAlarmType.setText("시간");
                        else if (which == 3)
                            etAlarmType.setText("기타");
                    }
                })
                .setNeutralButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();

    }

    public void onClick_alarmsemi(View view){
        CharSequence[] oItems ;
        if(type==0){
            oItems = new CharSequence[4];
            oItems[0]="알림최소확률 : 20%";
            texts[0] = "알림최소확률 : 20%";
            oItems[1]="알림최소확률 : 40%";
            texts[1] = "알림최소확률 : 40%";
            oItems[2]="알림최소확률 : 60%";
            texts[2] = "알림최소확률 : 60%";
            oItems[3]="알림최소확률 : 80%";
            texts[3] = "알림최소확률 : 80%";
        }
        else if(type==1)
        {
            oItems = new CharSequence[5];
            oItems[0]="최소인식거리 : 10M";
            texts[0] = "최소인식거리 : 10M";
            oItems[1]="최소인식거리 : 30M";
            texts[1] = "최소인식거리 : 30M";
            oItems[2]="최소인식거리 : 50M";
            texts[2] = "최소인식거리 : 50M";
            oItems[3]="최소인식거리 : 80M";
            texts[3] = "최소인식거리 : 80M";
            oItems[4]="최소인식거리 : 100M";
            texts[4] = "최소인식거리 : 100M";
        }
        else if(type==2)
        {
            oItems = new CharSequence[7];
            oItems[0]="요일설정 : 월요일";
            texts[0] = "요일설정 : 월요일";
            oItems[1]="요일설정 : 화요일";
            texts[1] = "요일설정 : 월요일";
            oItems[2]="요일설정 : 수요일";
            texts[2] = "요일설정 : 월요일";
            oItems[3]="요일설정 : 목요일";
            texts[3] = "요일설정 : 월요일";
            oItems[4]="요일설정 : 금요일";
            texts[4] = "요일설정 : 월요일";
            oItems[5]="요일설정 : 토요일";
            texts[5] = "요일설정 : 월요일";
            oItems[6]="요일설정 : 일요일";
            texts[6] = "요일설정 : 월요일";
        }
        else {
            oItems = new CharSequence[4];
            oItems[0] = "기타메뉴 1";
            texts[0] = "기타메뉴 1";
            oItems[1] = "기타메뉴 2";
            texts[1] = "기타메뉴 2";
            oItems[2] = "기타메뉴 3";
            texts[2] = "기타메뉴 3";
            oItems[3] = "기타메뉴 4";
            texts[3] = "기타메뉴 4";
        }

        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        oDialog.setTitle("알람 종류를 선택하세요")
                .setSingleChoiceItems(oItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0)
                            etAlarmSet.setText(texts[0]);
                        else if(which == 1)
                            etAlarmSet.setText(texts[1]);
                        else if (which ==2)
                            etAlarmSet.setText(texts[2]);
                        else if (which == 3)
                            etAlarmSet.setText(texts[3]);

                        else if (which == 4)
                            etAlarmSet.setText(texts[4]);

                        else if (which == 5)
                            etAlarmSet.setText(texts[5]);

                        else if (which == 6)
                            etAlarmSet.setText(texts[6]);

                    }
                })
                .setNeutralButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();

    }



    public void showAlarmDialog(){
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(),"timePicker");
    }




    /////
    public void saves() {
        try {
            FileOutputStream os = openFileOutput("savefile.txt", MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(os);
            writer.println("" + tmp);
            writer.println("" + type   );
            writer.println("" + name);
            writer.println("" + content);
            writer.println("" + time);
            writer.close();
        } catch (
                FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void load(){
        try {
            FileInputStream oss = openFileInput("savefile.txt");
            Scanner scan = new Scanner(oss);
            while(scan.hasNext()){
                tmp = Integer.parseInt(scan.next());
                type = Integer.parseInt(scan.next());
                name = scan.next();
                content = scan.next();

                time = Integer.parseInt(scan.next());
            }
        }catch (
                Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}