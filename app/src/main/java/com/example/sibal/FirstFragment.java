package com.example.sibal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FirstFragment extends Fragment
{
    SwipeRefreshLayout refreshLayout;
    TextView textView;

    String[] events={"이렇게는 못삽니다","사퇴할래요","앱 좆같네","이 시발"};

    public FirstFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_first, container, false);

       //firstFragmentControl

        for(int i=0; i< ((MainActivity)getActivity()).number; i++ ){
            int index = (int)(Math.random()* ((MainActivity)getActivity()).number);
            String temp =  events[i];
            events[i] =  events[index];
            events[index] = temp;
        }
        refreshLayout = layout.findViewById(R.id.refreshLayout);
        textView = layout.findViewById(R.id.event0);
        textView.setText(events[0]);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).count = (++((MainActivity)getActivity()).count)%((MainActivity)getActivity()).number;
                textView.setText(events[((MainActivity)getActivity()).count]);
                refreshf();

            }
        });





        return layout;
    }
    private void refreshf(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}