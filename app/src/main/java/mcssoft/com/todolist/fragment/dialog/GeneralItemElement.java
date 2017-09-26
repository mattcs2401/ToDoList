package mcssoft.com.todolist.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.interfaces.IGeneralElement;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to capture the value/element details entered for a General Item.
 */
public class GeneralItemElement extends DialogFragment
        implements DialogInterface.OnClickListener, TextWatcher, View.OnKeyListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNameValid = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.layout_general_value, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(rootView)
          .setTitle(Resources.getInstance().getString(R.string.giv_dialog_title))
          .setMessage(Resources.getInstance().getString(R.string.giv_dialog_message))
          .setNegativeButton(Resources.getInstance().getString(R.string.btn_cancel), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.btn_ok), this);

        return ab.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                if(isNameValid) {
                    iGeneralElement.onGeneralElement(inputName.getText().toString());
                }
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                break;
        }
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
                isNameValid = true;
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

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Establish the interface.
     * @param iGeneralElement The interface name.
     */
    public void setIGeneralElement(IGeneralElement iGeneralElement) {
        this.iGeneralElement = iGeneralElement;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
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

    private void initialise() {
        layoutInputName = (TextInputLayout) rootView.findViewById(R.id.id_gv_input_layout_name);
        layoutInputName.setHint(Resources.getInstance().getString(R.string.gif_name));
        inputName = (EditText) rootView.findViewById(R.id.id_et_gv_input_name);
        inputName.addTextChangedListener(this);
        inputName.setOnKeyListener(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private View rootView;                       // main view.
    private EditText inputName;                  // text/element entry.
    private boolean isNameValid;                 // flag, entered string meets criteria.
    private TextInputLayout layoutInputName;     // used as part of validation.
    private IGeneralElement iGeneralElement;     // interface.
    //</editor-fold>
}
