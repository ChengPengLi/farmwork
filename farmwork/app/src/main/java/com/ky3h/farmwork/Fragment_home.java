package com.ky3h.farmwork;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ky3h.farmwork.adapter.MyRecyclearAdapter;
import com.ky3h.farmwork.bean.Good;
import com.ky3h.farmwork.utils.Cheeses;
import com.ky3h.farmwork.utils.RecycleViewDivider;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lipengcheng on 2016/6/2.
 */
public class Fragment_home extends Fragment {

    private RecyclerView recyclerView;
    private List<Good> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclearView);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 6, Color.BLACK));
        recyclerView.setHasFixedSize(true);


        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getRandomSublist(Cheeses.sCheeseStrings, 20);
        final MyRecyclearAdapter myRecyclearAdapter = new MyRecyclearAdapter(getActivity(), list);
        recyclerView.setAdapter(myRecyclearAdapter);
//        瀑布流显示
//       SpacesItemDecoration decoration = new SpacesItemDecoration(30);
//        recyclerView.addItemDecoration(decoration);
        //ListView 显示
        myRecyclearAdapter.setOnItemClickListner(new MyRecyclearAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View view, int positon) {
                Intent intent = new Intent(getActivity(), GoodInfo.class);
                intent.putExtra("GoodInfo", list.get(positon));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                Toast.makeText(getActivity(), 111 + "长监听", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(list.get(position).getGoodResId());
                builder.setTitle("确认删除么？");
                AlertDialog dialog = builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (myRecyclearAdapter != null) {
                            myRecyclearAdapter.remove(position);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        return view;
    }

    private List<Good> getRandomSublist(String[] array, int amount) {
        list = new ArrayList<Good>();
        Random random = new Random();
        while (list.size() < amount) {
            list.add(new Good(Cheeses.getRandomCheeseDrawable(), array[random.nextInt(array.length)]));
        }
        return list;
    }
}
