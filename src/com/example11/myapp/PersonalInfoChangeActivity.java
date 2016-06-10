package com.example11.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
    public String value="";

    TextView titlemsg;
    EditText item;


    EditText item_addr;
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
        item_addr = (EditText) findViewById(R.id.item_addr);
        save = (Button) findViewById(R.id.save);
        type = getIntent().getIntExtra("item", 0);
        value = getIntent().getStringExtra("value");

        switch (type) {
            case 0:

                titlemsg.setText("填写姓名");
                item.setFilters(new InputFilter[]{ new  InputFilter.LengthFilter(5)});
                item.setSingleLine();
                item.setHint("填写姓名");

                break;

            case 2:

                titlemsg.setText("填写年龄");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.setSingleLine();
                item.addTextChangedListener(new EditChangedListener());
                item.setHint("填写年龄");

                break;

            case 3:

                titlemsg.setText("填写身高");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.addTextChangedListener(new EditChangedListener());
                item.setSingleLine();
                item.setHint("填写身高");

                break;

            case 4:

                titlemsg.setText("填写体重");
                item.setInputType(InputType.TYPE_CLASS_NUMBER);
                item.addTextChangedListener(new EditChangedListener());
                item.setSingleLine();
                item.setHint("填写体重");

                break;

            case 6:

                titlemsg.setText("填写收货地址");
//                item.setHeight(200);
//                item.setMaxLines(5);
//                item.setLines(5);
//                item.setHint("填写收货地址");
                item.setVisibility(View.GONE);
                item_addr.setVisibility(View.VISIBLE);


                break;

            default:
                break;

        }

        item.setText(value);
        save.setOnClickListener(onCLick);

    }

    View.OnClickListener onCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String itemStr = "";

            if(type == 6){
                itemStr = item_addr.getText().toString().trim();
            }else{
                itemStr = item.getText().toString().trim();
            }

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

            String num = item.getText().toString().trim();
            switch (type){
                case 0:
                    break;

                case 2:

                    if (!num.equals("")  && Integer.parseInt(num) > 14) {
                        item.setFilters(new InputFilter[]{ new  InputFilter.LengthFilter(2)});
                    } else {
                        item.setFilters(new InputFilter[]{ new  InputFilter.LengthFilter(3)});
                    }

                    break;

                case 3:

                    if (!num.equals("")  &&  Integer.parseInt(num) > 24) {
                        item.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
                    } else {
                        item.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                    }

                    break;

                case 4:

                    if (!num.equals("")  && Integer.parseInt(num) > 20) {
                        item.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
                    } else {
                        item.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
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

    @Override
    public void onBackPressed() {

        setResult(10);

        super.onBackPressed();
    }
}
