package com.ky3h.farmwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ky3h.farmwork.R;
import com.ky3h.farmwork.bean.Good;
import com.ky3h.farmwork.utils.Cheeses;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lipengcheng on 2016/6/22.
 * you are sb
 */
public class MyRecyclearAdapter extends RecyclerView.Adapter<MyRecyclearAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private OnItemClickListner onItemClickListner;
    private List<Good> list;

    @Override
    public void onClick(View v) {
        if (onItemClickListner != null) {
            onItemClickListner.onItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemClickListner != null) {
            onItemClickListner.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    public static interface OnItemClickListner {
        void onItemClick(View view,  int position);

        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public MyRecyclearAdapter(Context context, List<Good> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_item, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getGoodName());

        holder.circleImageView.setImageResource(list.get(position).getGoodResId());
        Glide.with(holder.circleImageView.getContext())
                .load(Cheeses.getRandomCheeseDrawable())
                .fitCenter()
                .into(holder.circleImageView);
        holder.mView.setTag( position);


    }


    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView textView;
        public CircleImageView circleImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            circleImageView = (CircleImageView) itemView.findViewById(R.id.avatar);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Good good, int position) {
        list.add(good);
        notifyItemInserted(position);
    }
}
