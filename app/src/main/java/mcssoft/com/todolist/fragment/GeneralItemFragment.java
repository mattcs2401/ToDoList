package mcssoft.com.todolist.fragment;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import mcssoft.com.todolist.R;

public class GeneralItemFragment extends Fragment
        implements View.OnClickListener, View.OnKeyListener {

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
//                if(checkLength()) {
//                    Toast.makeText(getActivity(), "TODO implement Save.", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.id_general_item_add_value:
//                if(checkLength()) {
//                    GeneralItemValue giv = new GeneralItemValue();
//                    giv.show(getActivity().getSupportFragmentManager(), null);
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_et_input_name:
                String bp = "";
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean retVal = false;
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            String bp = "";
            // This will fire twice; ACTION_DOWN, then ACTION_UP. Only want to process on ACTION_DOWN.
//            inputName.setCursorVisible(false);
        }
        return retVal;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void initialise() {
        layoutInputName = (TextInputLayout) rootView.findViewById(R.id.id_input_layout_name);
        layoutInputName.setHint("Name");
        inputName = (EditText) rootView.findViewById(R.id.id_et_input_name);
//        inputName.addTextChangedListener(new ToDoTextWatcher(inputName));
        inputName.setOnClickListener(this);
        inputName.setOnKeyListener(this);
//        inputName.setBackPressedListener(this);
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            layoutInputName.setError("Enter a name for this General item,");
            requestFocus(inputName);
            return false;
        } else {
            layoutInputName.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: TextWatcher">
    private class ToDoTextWatcher implements TextWatcher {

        public ToDoTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable editable) {
            switch(view.getId()) {
                case R.id.id_et_input_name:
                    validateName();
                    break;
            }
        }

        private View view;
    }
    //</editor-fold>

    private Bundle args;
    private View rootView;
    private EditText inputName;
    private TextInputLayout layoutInputName;
}
