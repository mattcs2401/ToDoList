package mcssoft.com.todolist.utility;

import android.view.KeyEvent;
import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

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
        boolean retVal = false;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(listener != null) {
                listener.onImeBack(this);
            }
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            // so menus don't show when soft keyboard showing.
            retVal = true;
        }
        return retVal;
    }

    public void setBackPressedListener(BackPressedListener listener) {
        this.listener = listener;
    }

    private BackPressedListener listener = null;
}
