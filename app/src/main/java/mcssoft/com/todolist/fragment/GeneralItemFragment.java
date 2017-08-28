package mcssoft.com.todolist.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.fragment.dialog.GeneralItemValue;
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
        initialise();
        args = getArguments();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.id_general_item_save:
                if(nameLabelEdit.getTextLength() < 1) {
                    Toast.makeText(getActivity(), "General item must have a label.", Toast.LENGTH_SHORT).show();
                    nameLabelEdit.setCursorAndHint(true, null);
                } else {
                    Toast.makeText(getActivity(), "TODO implement Save.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.id_general_item_add_value:
//                if(nameLabelEdit.getTextLength() < 1) {
//                    Toast.makeText(getActivity(), "General item must have a label.", Toast.LENGTH_SHORT).show();
//                    nameLabelEdit.setCursorAndHint(true, null);
//                } else {
                    GeneralItemValue giv = new GeneralItemValue();
                    //FragmentManager fm = getActivity().getS
                    giv.show(getActivity().getSupportFragmentManager(), null);
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_et_nameLabel:
                nameLabelEdit.setCursorAndHint(true, null);
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean retVal = false;
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            nameLabelEdit.setCursorVisible(false);
            nameLabelEdit.hideKeyboard();
            if(nameLabelEdit.getTextLength() < 1) {
                nameLabelEdit.setHint(Resources.getInstance().getString(R.string.gif_name_label_hint));
            } else {
                // TBA
            }
            retVal = true;
        }
        return retVal;
    }

    @Override
    public void onImeBack(ToDoEditText toDoEditText) {
        // Back key pressed while softkeyboard still showing.
        if(nameLabelEdit.getTextLength() < 1) {
            nameLabelEdit.setHint(Resources.getInstance().getString(R.string.gif_name_label_hint));
        }
        toDoEditText.setCursorVisible(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void initialise() {
        nameLabelEdit = (ToDoEditText) rootView.findViewById(R.id.id_et_nameLabel);
        nameLabelEdit.setOnClickListener(this);
        nameLabelEdit.setOnKeyListener(this);
        nameLabelEdit.setBackPressedListener(this);
        if(nameLabelEdit.getText().length() < 1) {
            nameLabelEdit.setHint(Resources.getInstance().getString(R.string.gif_name_label_hint));
        }
    }
    //</editor-fold>

    private Bundle args;
    private View rootView;
    private ToDoEditText nameLabelEdit;
}
