package com.chopperhl.androidkit.util;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author cginechen
 * @date 2016-11-07
 * <p>
 * https://github.com/yshrsmz/KeyboardVisibilityEvent/blob/master/keyboardvisibilityevent/src/main/java/net/yslibrary/android/keyboardvisibilityevent/KeyboardVisibilityEvent.java
 */

public class KeyboardHelper {
    /**
     * 显示软键盘的延迟时间
     */
    public static final int SHOW_KEYBOARD_DELAY_TIME = 200;
    private static final String TAG = "KeyboardHelper";


    public static void showKeyboard(final EditText editText, boolean delay) {
        showKeyboard(editText, delay ? SHOW_KEYBOARD_DELAY_TIME : 0);
    }


    /**
     * 针对给定的editText显示软键盘（editText会先获得焦点）. 可以和{@link #hideKeyboard(View)}
     * 搭配使用，进行键盘的显示隐藏控制。
     */

    public static void showKeyboard(final EditText editText, int delay) {
        if (null == editText) return;

        if (!editText.requestFocus()) {
            Log.w(TAG, "showSoftInput() can not get focus");
            return;
        }
        if (delay > 0) {
            editText.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }, delay);
        } else {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 隐藏软键盘 可以和{@link #showKeyboard(EditText, boolean)}搭配使用，进行键盘的显示隐藏控制。
     *
     * @param view 当前页面上任意一个可用的view
     */
    public static boolean hideKeyboard(final View view) {
        if (null == view)
            return false;

        InputMethodManager inputManager = (InputMethodManager) view.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 即使当前焦点不在editText，也是可以隐藏的。
        return inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
