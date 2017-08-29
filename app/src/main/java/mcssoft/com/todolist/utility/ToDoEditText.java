package mcssoft.com.todolist.utility;

import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;
import android.view.inputmethod.InputMethodManager;

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
        this.context = context;
    }

    public ToDoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ToDoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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

    public void setCursorAndHint(boolean visible, @Nullable String hint) {
        if(visible) {
            this.setCursorVisible(true);
            this.setHint(BLANK);
        } else {
            this.setCursorVisible(false);
            if(hint != null) {
                this.setHint(hint);
            } else {
                setHint(BLANK);
            }
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    private Context context;
    private static final String BLANK = "";
    private BackPressedListener listener = null;
}
