package com.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.test.demo.R;
import com.test.utils.Contant;
import net.tsz.afinal.FinalBitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by chendi on 2016/6/4.
 */
public class GoodsAdapter extends BaseAdapter {

    Context Acontext;
    List<Map<String, String>> Alist;
    ViewHold_02 VH;
    LayoutInflater layoutInflater;

    FinalBitmap fb;

    boolean onScroll = false;

    public void onSetScrollState(boolean onScrolling) {
        onScroll = onScrolling;
    }

    public GoodsAdapter(Context context,
                        List<Map<String, String>> list, FinalBitmap fb) {
        Acontext = context;
        this.fb = fb;
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

            convertView = layoutInflater.inflate(R.layout.good_item_layout, null);

            VH = new ViewHold_02();
            VH.img_header = (ImageView) convertView.findViewById(R.id.photo);

            VH.name = (TextView) convertView.findViewById(R.id.name);
            VH.money = (TextView) convertView.findViewById(R.id.money);
            VH.descption = (TextView) convertView.findViewById(R.id.descption);

            convertView.setTag(VH);

        } else {
            VH = (ViewHold_02) convertView.getTag();
        }

        if(!onScroll){
            fb.display(VH.img_header, Contant.HOST + Alist.get(position).get("photo").toString());
        }
        VH.name.setText( Alist.get(position).get("name").toString());
        VH.money.setText("￥" + Alist.get(position).get("money").toString() + "元");
        VH.descption.setText( Alist.get(position).get("dec").toString());

        return convertView;
    }

    public static class ViewHold_02 {

        ImageView img_header;

        TextView name;
        TextView money;
        TextView descption;

    }
}
