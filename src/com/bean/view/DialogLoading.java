package com.bean.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bean.yihuanton.R;
/**
 * Created by chendi on 2016/6/4.
 */
public class DialogLoading extends ProgressDialog {

    public TextView tv;

    public DialogLoading(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_bg);
        setCancelable(false);
    }

    public void setContent(String msg){

        tv = (TextView) findViewById(R.id.msg);
        tv.setText(msg);
        tv.setVisibility(View.VISIBLE);


    }
}
