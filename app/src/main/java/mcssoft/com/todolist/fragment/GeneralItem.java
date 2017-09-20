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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.fragment.dialog.GeneralItemValue;
import mcssoft.com.todolist.utility.DateTime;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class that represents a General Item (in the list of General Items).
 */
public class GeneralItem extends Fragment implements TextWatcher, View.OnKeyListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isNameValid = false;
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
    public void onPrepareOptionsMenu(Menu menu) {
        if(isNameValid) {
            // so doesn't retrigger and get added twice.
            isNameValid = false;
            // add in menu item.
            menu.add(Menu.NONE, ID_GI_ADD_VALUE, Menu.FIRST, Resources.getInstance().getString(R.string.menu_gi_add));
        }
        super.onPrepareOptionsMenu(menu);
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
            case R.id.id_gi_save:
                if(validateName()) {
//                    Toast.makeText(getActivity(), "TO DO implement Save.", Toast.LENGTH_SHORT).show();
                    writeNewGeneralItem(inputName.getText().toString());
                }
                break;
            case ID_GI_ADD_VALUE:
                GeneralItemValue giv = new GeneralItemValue();
                giv.show(getActivity().getSupportFragmentManager(), null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean retVal = false;
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            // This will fire twice; ACTION_DOWN, then ACTION_UP. Only want to process on ACTION_DOWN.
            if(validateName()) {
                // hide the keyboard
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // force change in options menu.
                isNameValid = true;
                getActivity().invalidateOptionsMenu();
            }
        }
        return retVal;
    }

    //<editor-fold defaultstate="collapsed" desc="Region: TextWatcher">
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable editable) {
        validateName();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void writeNewGeneralItem(String genItemName) {//int[] refIds) {
        List<String> colVals = new ArrayList<>();
        DateTime dateTime = new DateTime();

        colVals.add(dateTime.getCompactedDateTime());      // general item identifier.
        colVals.add(dateTime.getFormattedDate(false));     // general item date.
        colVals.add(genItemName);                          // general item name.

        long rowId = Database.getInstance().createGeneralItem(colVals);
//        Database.getInstance().createShoppingListItems(rowId, refIds);
    }

    private void initialise() {
        layoutInputName = (TextInputLayout) rootView.findViewById(R.id.id_input_layout_name);
        layoutInputName.setHint(Resources.getInstance().getString(R.string.gif_name));
        inputName = (EditText) rootView.findViewById(R.id.id_et_input_name);
        inputName.addTextChangedListener(this);
        inputName.setOnKeyListener(this);
    }

    private boolean validateName() {
        String name = inputName.getText().toString().trim();
        if (name.isEmpty()) {
            layoutInputName.setError(Resources.getInstance().getString(R.string.gif_err_enter_name));
            requestFocus(inputName);
            return false;
        } else if(name.length() < 4) {
            layoutInputName.setError(Resources.getInstance().getString(R.string.gif_err_char_len));
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

    private Bundle args;
    private View rootView;
    private EditText inputName;
    private boolean isNameValid;
    private TextInputLayout layoutInputName;

    private static final int ID_GI_ADD_VALUE = 0x01;
}
