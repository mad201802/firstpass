package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.firstpass.utils.decorators.Generated;

/**
 * This class is used to represent a category in the database.
 */
@Generated
@DatabaseTable(tableName = "meta")
public class MetaModel {
    @DatabaseField(generatedId = true)
    public Integer id;
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

    //methods for id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MetaModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    //empty constructor for ormlite
    public MetaModel() {}
}
