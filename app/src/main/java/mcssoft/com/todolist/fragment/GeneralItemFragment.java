package mcssoft.com.todolist.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.utility.Resources;

public class GeneralItemFragment extends Fragment
        implements View.OnClickListener, View.OnKeyListener, View.OnTouchListener { //}, View.OnFocusChangeListener { //} {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.general_item_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameLabelEdit = (EditText) rootView.findViewById(R.id.id_et_nameLabel);
        nameLabelEdit.setOnClickListener(this);
        nameLabelEdit.setOnKeyListener(this);
//        nameLabelEdit.setOnTouchListener(this);
//        nameLabelEdit.setCursorVisible(false);
//        nameLabelEdit.setFocusable(true);
        args = getArguments();
    }
    //</editor-fold>


    @Override
    public void onClick(View view) {
//        if(view instanceof FloatingActionButton) {
        String bp = "";
//        }
        if(view instanceof EditText) {

            nameLabelEdit.setCursorVisible(true);
//            showSoftKeyboard(view);
        }

    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_ENTER ) {
//            if( event.getAction() == KeyEvent.ACTION_DOWN ) {
                nameLabelEdit.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        nameLabelEdit.setCursorVisible(true);
        return false;
    }

    private EditText nameLabelEdit;
    private Bundle args;
    private View rootView;

}
