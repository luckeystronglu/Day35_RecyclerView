package com.qf.day35_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 2016/10/13.9:08
 * 1.由4个方法变成了3个方法
 * 2.item的重用无须关心
 * 3.强制要求实现一个ViewHolder
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private Context context;
    private List<String> datas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context){
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<String> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * ViewHolder.tv = view.findXXXXXX
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(datas.get(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tv;

        /**
         * 接收item的布局对象
         * @param itemView
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onLongClick(v, getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int postion);
    }
}
