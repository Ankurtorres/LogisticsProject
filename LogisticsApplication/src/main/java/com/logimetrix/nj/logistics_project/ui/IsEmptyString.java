package com.logimetrix.nj.logistics_project.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ankur_ on 8/8/2017.
 */

public class IsEmptyString {
    private String text;

    public static boolean IsEmpty(View view) {
        String text=null;
        if (view instanceof TextView)
            text=((TextView) view).getText().toString();
            else
        if(view instanceof EditText)
            text= ((EditText) view).getText().toString();

        if(text!=null&&text.trim().isEmpty())
        return true;
        else
        return false;

    }
}
