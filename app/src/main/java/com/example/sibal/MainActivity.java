package com.example.sibal;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ViewPager vp;
    int number = 4;
    int count = 0;
    public int tmp = 0,type=0;
    public String name,content;
    TextView txt_AlarmName;
    int time;
    private DrawerLayout drawerLayout;
    private View drawerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        load();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        vp = (ViewPager)findViewById(R.id.vp);


        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(1);


        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        Button buttonOpenDrawer = findViewById(R.id.opendrawer);
        buttonOpenDrawer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button buttonCloseDrawer = findViewById(R.id.closedrawer);
        buttonCloseDrawer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(myDrawerListener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        if (tmp == 1){
            tmp++;saves();
        }
        else if (tmp == 2) { // 알람 번호 따른 차이
            //만약 껏을경우 안끈경우 구분해서 moveTaskToBack 할지 여부 정하기
            show(type);
            tmp =0;
            saves();
            moveTaskToBack(true);
        }
    }


    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new SecondFragment();
                case 1: {
                    return new FirstFragment();

                }
                case 2:
                    return new ThirdFragment();
                default:
                    return null;
            }
        }



        @Override
        public int getCount()
        {
            return 3;
        }
    }

    DrawerLayout.DrawerListener myDrawerListener = new DrawerLayout.DrawerListener() {

        public void onDrawerClosed(@NonNull View drawerView) {
        }
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        public void onDrawerStateChanged(int newState) {
            String state;
            switch (newState) {
                case DrawerLayout.STATE_IDLE:
                    state = "STATE_IDLE";
                    break;
                case DrawerLayout.STATE_DRAGGING:
                    state = "STATE_DRAGGING";
                    break;
                case DrawerLayout.STATE_SETTLING:
                    state = "STATE_SETTLING";
                    break;
                default:
                    state = "unknown!";
            }

        }
    };



    public void onClick_RemakeAlarm(View view) {

        if(time==0){
            Toast.makeText(MainActivity.this,"수정할 알람이 없습니다", Toast.LENGTH_SHORT).show();

        }
        else {
            tmp = 3;
            saves();
            Intent intents = new Intent(MainActivity.this, AlarmSubActivity.class);
            startActivity(intents);
            finish();
        }
    }


    public void onClick_AddAlarm(View view){
        if(time==0){
            saves();
            Intent intents = new Intent(MainActivity.this, AlarmSubActivity.class);
            startActivity(intents);
            finish();
        }
        else {
            Toast.makeText(MainActivity.this,"알람 생성할 칸이 부족합니다", Toast.LENGTH_SHORT).show();
        }
    }

public void noteDelet(){
    Toast.makeText(MainActivity.this,"삭제할 알람이 없습니다", Toast.LENGTH_SHORT).show();
}

    public void show(int nmb) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default");

        if(nmb == 0)builder.setSmallIcon(R.mipmap.alam1); //type따른 사진변경
        else if(nmb ==1)builder.setSmallIcon(R.mipmap.alam2); //type따른 사진변경
        else if(nmb ==2)builder.setSmallIcon(R.mipmap.alam3); //type따른 사진변경
        else if(nmb ==3)builder.setSmallIcon(R.mipmap.alam4); //type따른 사진변경
        builder.setContentTitle(name);
        builder.setContentText(content);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Bitmap largeIcon1,largeIcon2,largeIcon3,largeIcon4 ;
        largeIcon1 = BitmapFactory.decodeResource(getResources(), R.mipmap.alam1);
        largeIcon2 = BitmapFactory.decodeResource(getResources(), R.mipmap.alam2);
        largeIcon3 = BitmapFactory.decodeResource(getResources(), R.mipmap.alam3);
        largeIcon4 = BitmapFactory.decodeResource(getResources(), R.mipmap.alam4);
        if(nmb == 0)builder.setLargeIcon(largeIcon1);
        else if(nmb ==1)builder.setLargeIcon(largeIcon2);
        else if(nmb ==2)builder.setLargeIcon(largeIcon3);
        else if(nmb ==3)builder.setLargeIcon(largeIcon4);

        builder.setColor(Color.RED);
        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};    //효과 다르게 - -강좌참고
        builder.setVibrate(vibrate);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());
    }

    public void removeNotification(View view) {
        hide();

    }

    private void hide() {
        NotificationManagerCompat.from(this).cancel(1);
    }


    public void onClick_map(View view){
        Intent intent_map = new Intent(this, MapActivity.class);
        startActivity(intent_map);
    }







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