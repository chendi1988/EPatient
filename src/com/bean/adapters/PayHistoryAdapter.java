package com.bean.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bean.yihuanton.R;

import java.util.List;
import java.util.Map;

/**
 * Created by chendi on 2016/6/4.
 */
public class PayHistoryAdapter extends BaseAdapter {

    Context Acontext;
    List<Map<String, String>> Alist;
    ViewHold_02 VH;
    LayoutInflater layoutInflater;


    boolean onScroll = false;

    public void onSetScrollState(boolean onScrolling) {
        onScroll = onScrolling;
    }

    public PayHistoryAdapter(Context context,
                             List<Map<String, String>> list) {
        Acontext = context;
        Alist = list;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return Alist.size();
    }

    @Override
    public Object getItem(int position) {
        if (Alist != null) {
            return Alist.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.pay_history_list_item, null);

            VH = new ViewHold_02();

            VH.money = (TextView) convertView.findViewById(R.id.money);
            VH.time = (TextView) convertView.findViewById(R.id.time);
            VH.descption = (TextView) convertView.findViewById(R.id.descption);

            convertView.setTag(VH);

        } else {
            VH = (ViewHold_02) convertView.getTag();
        }

        VH.time.setText(Alist.get(position).get("createtime").toString());
        VH.money.setText("消费 " + Alist.get(position).get("paymoney").toString() + " 元");
        VH.descption.setText(Alist.get(position).get("goodinfo") != null ? Alist.get(position).get("goodinfo") : "null");

        return convertView;
    }

    public static class ViewHold_02 {

        TextView money;
        TextView time;
        TextView descption;

    }
}
