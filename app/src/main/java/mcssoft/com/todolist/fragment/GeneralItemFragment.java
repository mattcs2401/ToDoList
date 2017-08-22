package mcssoft.com.todolist.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.interfaces.BackPressedListener;
import mcssoft.com.todolist.utility.Resources;
import mcssoft.com.todolist.utility.ToDoEditText;

public class GeneralItemFragment extends Fragment
        implements View.OnClickListener, View.OnKeyListener, BackPressedListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.general_item_fragment, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_general_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameLabelEdit = (ToDoEditText) rootView.findViewById(R.id.id_et_nameLabel);
        nameLabelEdit.setOnClickListener(this);
        nameLabelEdit.setOnKeyListener(this);
        nameLabelEdit.setBackPressedListener(this);
        if(nameLabelEdit.getText().length() < 1) {
            nameLabelEdit.setHint(Resources.getInstance().getString(R.string.gif_name_label_hint));
        }
//        nameLabelEdit.setOnTouchListener(this);
        args = getArguments();
    }
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.id_general_item_save:
                // TBA
                break;
            case R.id.id_general_item_add_sub_item:
                // TBA
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.id_et_nameLabel:
                nameLabelEdit.setCursorVisible(true);
                nameLabelEdit.setHint("");
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean retVal = false;
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            ((ToDoEditText) view).setCursorVisible(false);
            hideKeyboard(view);
            if(getTextLength((ToDoEditText) view) < 1) {
                setHint((ToDoEditText) view);
            }
            retVal = true;
        }
        return retVal;
    }

    @Override
    public void onImeBack(ToDoEditText toDoEditText) {
        // Back key pressed while softkeyboard still showing.
        if(getTextLength(toDoEditText) < 1) {
            setHint(toDoEditText);
        }
        toDoEditText.setCursorVisible(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private int getTextLength(ToDoEditText toDoEditText) {
        return toDoEditText.getEditableText().length();
    }

    private void setHint(ToDoEditText toDoEditText) {
        int id = toDoEditText.getId();
        switch (id) {
            case R.id.id_et_nameLabel:
                nameLabelEdit.setHint(Resources.getInstance().getString(R.string.gif_name_label_hint));
                break;
        }
    }
    //</editor-fold>

    private ToDoEditText nameLabelEdit;
    private Bundle args;
    private View rootView;

}
