package com.test.adapters;

import android.app.Activity;
import android.content.Context;
import com.test.demo.R;

public class AnimAdapterUtil {

    static int version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);

    public static void anim_translate_next(Context cn) {
        if (version >= 5) {
            ((Activity) cn).overridePendingTransition(
                    R.anim.anim_translate_next_in,
                    R.anim.anim_translate_next_out); // ��Ϊ�Զ���Ķ���Ч��
        }
    }

    public static void anim_translate_back(Context cn) {
        if (version >= 5) {
            ((Activity) cn).overridePendingTransition(
                    R.anim.anim_translate_back_in,
                    R.anim.anim_translate_back_out); // ��Ϊ�Զ���Ķ���Ч��
        }
    }

    public static void anim_login_in(Context cn) {
        if (version >= 5) {
            ((Activity) cn).overridePendingTransition(
                    R.anim.anim_translate_bottom_in,
                    R.anim.anim_translate_no_change);
        }
    }

    public static void anim_login_out(Context cn) {
        if (version >= 5) {
            ((Activity) cn).overridePendingTransition(
                    R.anim.anim_translate_no_change,
                    R.anim.anim_translate_back_out);
        }
    }

}
