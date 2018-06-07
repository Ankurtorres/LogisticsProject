package com.logimetrix.nj.logistics_project.Request;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Ankur_ on 8/22/2017.
 */
public class BaseClass {
    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    String Sync="0";
}
