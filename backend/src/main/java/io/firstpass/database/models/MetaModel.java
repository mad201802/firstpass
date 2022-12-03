package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is used to represent a category in the io.firstpass.database.
 */
@DatabaseTable(tableName = "categories")
public class MetaModel {
    @DatabaseField()
    public String key;
    @DatabaseField()
    public String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetaModel(String key, String value) {
        this.key = key;
        this.value = value;
    }



    //empty constructor for ormlite
    public MetaModel() {}


}
