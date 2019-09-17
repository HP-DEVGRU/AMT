package com.example.sibal;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SecondFragment extends Fragment
{
    TextView txt_AlarmName;
    ImageButton alarm_Setting;
    ImageView im_alarm1;
    public SecondFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_second, container, false);


        txt_AlarmName = (TextView) layout.findViewById(R.id.txt_AlarmName);
       txt_AlarmName.setText(((MainActivity)getActivity()).name);
        alarm_Setting = (ImageButton)layout.findViewById(R.id.alarm_Setting);
        alarm_Setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
          public void onClick(View v)
            {
                txt_AlarmName.setText("");
                im_alarm1.setImageResource(R.mipmap.alarm0);
                ((MainActivity)getActivity()).name = "";
                ((MainActivity)getActivity()).content = "";
                ((MainActivity)getActivity()).time = 0;
                ((MainActivity)getActivity()).type = -1;
                ((MainActivity)getActivity()).saves();
                ((MainActivity)getActivity()).noteDelet();

            }
        });
        ((MainActivity)getActivity()).load();im_alarm1 = (ImageView)layout.findViewById(R.id.im_alarm1);
        switch(((MainActivity)getActivity()).type)
        {
            case 0:
                im_alarm1.setImageResource(R.mipmap.alam1);
                break;
            case 1:

                im_alarm1.setImageResource(R.mipmap.alam2);
                break;
            case 2:
                im_alarm1.setImageResource(R.mipmap.alam3);
                break;
            case 3:
                im_alarm1.setImageResource(R.mipmap.alam4);
                break;
            default:
                im_alarm1.setImageResource(R.mipmap.alarm0);
        }

        return layout;
    }

}