package com.example11.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by chendi on 2016/6/7.
 */
public class PersonalInfoChangeActivity extends Activity {

    public int type = 0;

    TextView titlemsg;
    EditText item;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_change_item);

        initView();

    }

    public void initView() {

        titlemsg = (TextView) findViewById(R.id.titlemsg);
        item = (EditText) findViewById(R.id.item);
        save = (Button) findViewById(R.id.save);
        type = getIntent().getIntExtra("item", 0);
        switch (type) {
            case 0:

                titlemsg.setText("填写姓名");
                item.setMaxEms(5);
                item.setSingleLine();
                item.setHint("填写姓名");
                save.setOnClickListener(onCLick);

                break;

            case 2:

                titlemsg.setText("填写年龄");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.setSingleLine();
                item.addTextChangedListener(new EditChangedListener());
                item.setHint("填写年龄");
                save.setOnClickListener(onCLick);

                break;

            case 3:

                titlemsg.setText("填写身高");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.addTextChangedListener(new EditChangedListener());
                item.setSingleLine();
                item.setHint("填写身高");
                save.setOnClickListener(onCLick);

                break;

            case 4:

                titlemsg.setText("填写体重");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.addTextChangedListener(new EditChangedListener());
                item.setSingleLine();
                item.setHint("填写体重");
                save.setOnClickListener(onCLick);

                break;

            case 6:

                titlemsg.setText("填写收货地址");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.addTextChangedListener(new EditChangedListener());
                item.setMaxLines(5);
                item.setLines(5);
                item.setHint("填写收货地址");
                save.setOnClickListener(onCLick);

                break;

            default:
                break;

        }

    }

    View.OnClickListener onCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String itemStr = item.getText().toString().trim();

            if (!itemStr.equals("") && itemStr.length() > 0) {
                Intent intent = new Intent();
                intent.putExtra("key", itemStr);
                setResult(type, intent);
                finish();
            }

        }
    };

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            switch (type){
                case 0:
                    break;

                case 2:

                    if (Integer.parseInt(item.getText().toString().trim()) > 14) {
                        item.setMaxEms(2);
                    } else {
                        item.setMaxEms(3);
                    }

                    break;

                case 3:

                    if (Integer.parseInt(item.getText().toString().trim()) > 24) {
                        item.setMaxEms(2);
                    } else {
                        item.setMaxEms(3);
                    }

                    break;

                case 4:

                    if (Integer.parseInt(item.getText().toString().trim()) > 20) {
                        item.setMaxEms(2);
                    } else {
                        item.setMaxEms(3);
                    }

                    break;

                case 5:
                    break;

                default:
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
