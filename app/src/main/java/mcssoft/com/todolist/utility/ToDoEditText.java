package mcssoft.com.todolist.utility;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

import mcssoft.com.todolist.interfaces.BackPressedListener;

/**
 * Custom EditText basically just to detect the Back buttton being pressed when an edit field has
 * the focus and the soft keyboard is showing.
 */
public class ToDoEditText extends AppCompatEditText {

    //<editor-fold defaultstate="collapsed" desc="Region: Constructors">
    /* Note: Need to override these otherwise app crashes. */

    public ToDoEditText(Context context) {
        super(context);
    }

    public ToDoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToDoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //</editor-fold>

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // TBA.
            String bp = "";
            onImeBack.onImeBack(this);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            // so menus don't show when soft keyboard showing.
            return true;
        }
        return false;
    }

    public void setBackPressedListener(BackPressedListener listener) {
        onImeBack = listener;
    }

    private BackPressedListener onImeBack;
}
/*
- Idea from: https://gist.github.com/xzzz9097/b5c630aa29b511a90b4f
             http://findnerd.com/list/view/How-to-detect-Android-Softkeyboard/11645/
- Implement interface ?
*/
