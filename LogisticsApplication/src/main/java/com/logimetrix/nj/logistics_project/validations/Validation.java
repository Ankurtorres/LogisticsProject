package com.logimetrix.nj.logistics_project.validations;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by logimetrix on 1/8/16.
 */
public class Validation {

    private static final String email_regax="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String name_regax="^[a-zA-Z_ ]*$";
    static boolean result;

    public static boolean isNameValidation(EditText view, String msg){
        result=false;
        view.setError(null);
        if(!TextUtils.isEmpty(view.getText().toString()) && view.getText().length()>3 && view.getText().length()<30 &&
                view.getText().toString().matches(name_regax))
            result=true;
        else
        {view.setError(msg);
        view.requestFocus();}
        return result;
    }

    public static boolean isEmailValidation(EditText view, String msg){
        result=false;
        view.setError(null);
        if(!TextUtils.isEmpty(view.getText().toString()) && view.getText().toString().matches(email_regax))
            result=true;
        else
            view.setError(msg);
        return result;
    }

    public static boolean isPhoneValidation(EditText view, String msg){
        result=false;
        view.setError(null);
        if(!TextUtils.isEmpty(view.getText().toString()) && view.getText().toString().length()==10)
            result=true;
        else
            view.setError(msg);
        return result;
    }

    public static boolean isBlankField(EditText view, String msg){
        result=false;
        view.setError(null);
        if(!TextUtils.isEmpty(view.getText().toString()))
            result=true;
        else
            view.setError(msg);
        return result;
    }

}
