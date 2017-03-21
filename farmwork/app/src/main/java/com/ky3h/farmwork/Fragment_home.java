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
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 6, Color.WHITE));
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

    public static class MessageType {
        public static final int MESSAGE_UPDATE_TIME = 1;
        public static final int MESSAGE_UPDATE_WEATHER = 2;
        public static final int MESSAGE_UPDATE_CITY = 3;
        public static final int MESSAGE_LOGIN = 4;
        public static final int MESSAGE_REGIST = 5;
        public static final int MESSAGE_GET_CAPTCHA = 6;
        public static final int MESSAGE_MODIFY_PASSWORD = 7;
        public static final int MESSAGE_ADD_MEMBER = 8;
        public static final int MESSAGE_GET_MEMBER = 9;
        public static final int MESSAGE_GET_LECTURE = 10;
        public static final int MESSAGE_BIND_CARD = 11;
        public static final int MESSAGE_GET_CARDS = 12;
        public static final int MESSAGE_DELETE_MEMBER = 13;
        public static final int MESSAGE_MODIFY_MEMBER = 14;
        public static final int MESSAGE_RESERVE_FREE_LECTURE = 15;
        public static final int MESSAGE_RESERVE_NOT_FREE_LECTURE = 16;
        public static final int MESSAGE_GET_MY_LECTURE = 17;
        public static final int MESSAGE_GET_CAPTCHA_AGAIN = 18;
        public static final int MESSAGE_CREATE_NEW_PASSWORD = 19;
        public static final int MESSAGE_GET_QUESTIONNAIRE = 20;
        public static final int MESSAGE_UPLOAD_ECG_FILE = 21;
        public static final int MESSAGE_GET_REPORT = 22;
        public static final int MESSAGE_COMMIT_CONSULTATION = 23;
        public static final int MESSAGE_UPLOAD_FILE = 24;
        public static final int MESSAGE_GET_MY_CONSULTATION = 25;
        public static final int MESSAGE_GET_HEALTH_INFORMATION_CATEGORY = 26;
        public static final int MESSAGE_GET_DEPARTMENT = 27;
        public static final int MESSAGE_GET_NEW_INFORMATION = 28;
        public static final int MESSAGE_GET_INFORMATION_BY_ID = 29;
        public static final int MESSAGE_GET_AREA = 30;
        public static final int MESSAGE_GET_HOSPITAL = 31;
        public static final int MESSAGE_GET_PARENT_AREA = 32;
        public static final int MESSAGE_GET_RECORD_INDEX_DATA = 33;
        public static final int MESSAGE_GET_ID_BY_AREA = 34;
        public static final int MESSAGE_CREATE_ORDER = 35;
        public static final int MESSAGE_GET_MESSAGE = 36;
        public static final int MESSAGE_GET_DOCTORS = 37;
        public static final int MESSAGE_UPDATE_USER_INFO = 38;
        public static final int MESSAGE_RESERVE_DOCTOR = 39;
        public static final int MESSAGE_GET_RESERVES = 40;
        public static final int MESSAGE_GET_VIDEO_RESERVES = 41;
        public static final int MESSAGE_GET_VIDEO_ACCOUNT = 42;
        public static final int MESSAGE_CANCLE_ORDER = 43;
        public static final int MESSAGE_GET_PRIVATE_HOSPITALS = 44;
        public static final int MESSAGE_UPLOAD_DATA = 45;
        public static final int MESSAGE_SET_PRIVATE_HOSPITAL = 46;
        public static final int MESSAGE_GET_BLOOD_SUGAR_DATA = 47;
        public static final int MESSAGE_UPLOAD_AUDIO = 48;
        public static final int MESSAGE_GET_REPORT_BY_SN = 49;
        public static final int MESSAGE_GET_SUBJECT_CATEGORY_ID = 50;
        public static final int MESSAGE_COMMIT_ANSWER = 51;
        public static final int MESSAGE_GET_SCHEDULE_LIST = 52;
        public static final int MESSAGE_QUERY_REMOTE_FAILED = 53;
        public static final int MESSAGE_GET_HEALTH_ADVISER_CATEGORY = 54;
        public static final int MESSAGE_SET_HEALTH_ADVISOR = 55;
        public static final int MESSAGE_GET_HEALTH_ADVISORS_BY_CATEGORY = 56;
        public static final int MESSAGE_GET_SELECTED_HEALTH_ADVISOR_BY_CATEGORY = 57;
        public static final int MESSAGE_GET_HEALTH_ADVISOR_LEVEL = 58;
        public static final int MESSAGE_ORDER_ID = 59;
        public static final int MESSAGE_GET_HEALTH_TIPS = 60;
        public static final int MESSAGE_GET_ADVERTISEMENT = 61;
        public static final int MESSAGE_GET_VISCERA_IDENTITYS = 62;
        public static final int MESSAGE_GET_PAYMENT = 63;
        public static final int MESSAGE_CHECK_LOGIN = 64;
        public static final int MESSAGE_CHECK_IS_CANCLED = 65;
        public static final int MESSAGE_GET_ECG_REPORT = 66;
    }
}
