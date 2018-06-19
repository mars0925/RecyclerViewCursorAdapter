package com.example.mars0925.myapplication;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mars0925 on 2018/6/19.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Cursor mCursor;
    private static final int COL_DISPLAY_NAME = 1;
    private static final int PHONE_NUMBER = 2;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);//移動游標到一個絕對的位置

        String name = mCursor.getString(COL_DISPLAY_NAME);//依照索引值取出字串
        String num = mCursor.getString(PHONE_NUMBER);

        TextView nametv = holder.name;
        TextView numtv = holder.num;

        nametv.setText(name);
        numtv.setText(num);

    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView num;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            num = itemView.findViewById(R.id.textView2);
        }
    }

    /*自訂swapCursor()方法來更新Cursor，mCursor 為資料集，並由 notifyDataSetChanged() 通知資料更新*/
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
