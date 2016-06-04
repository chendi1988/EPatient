package com.example11.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example11.myapp.R;

/**
 * Created by chendi on 2016/6/4.
 */
public class DialogWaiting extends ProgressDialog {

    public TextView tv;

    public DialogWaiting(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_waiting_bg);
        setCancelable(false);
    }

    public void setContent(String msg){

        tv = (TextView) findViewById(R.id.msg);
        tv.setText(msg);
        tv.setVisibility(View.VISIBLE);


    }
}
