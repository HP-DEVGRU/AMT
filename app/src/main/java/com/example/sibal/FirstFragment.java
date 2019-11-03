package com.example.sibal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    ImageView image_;

    public FirstFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

    }

    public void setImage(int i){
switch(i){
    case 0: image_.setImageResource(R.drawable.image_bag);break;
    case 1:image_.setImageResource(R.drawable.image_ear);break;
    case 2:image_.setImageResource(R.drawable.image_rain);break;
    case 3:image_.setImageResource(R.drawable.image_wal);break;
}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_first, container, false);

       //firstFragmentControl
       image_= (ImageView)layout.findViewById(R.id.image_);
        setImage(((MainActivity)getActivity()).count );
        refreshLayout = layout.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).count = (++((MainActivity)getActivity()).count)%((MainActivity)getActivity()).number;
                setImage(((MainActivity)getActivity()).count );
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