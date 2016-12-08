package com.ky3h.farmwork;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by lipengcheng on 2016/6/2.
 */
public class Fragment_type extends Fragment implements View.OnClickListener {
    private Button jingluo;
    private Button tizhi;
    private Button zangfu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_type, container, false);
        jingluo = (Button) view.findViewById(R.id.jingluo);
        tizhi = (Button) view.findViewById(R.id.tizhi);
        zangfu = (Button) view.findViewById(R.id.zangfu);
        jingluo.setOnClickListener(this);
        tizhi.setOnClickListener(this);
        zangfu.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent[] intent = {null};
        switch (v.getId()) {
            case R.id.jingluo:
                intent[0] = new Intent(getActivity(), OneSayActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.tizhi:
                break;
            case R.id.zangfu:
                break;
            default:
                break;


        }
    }
}
