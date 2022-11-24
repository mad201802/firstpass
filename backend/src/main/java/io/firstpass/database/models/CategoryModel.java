package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is used to represent a category in the io.firstpass.database.
 */
@DatabaseTable(tableName = "categories")
public class CategoryModel {
    @DatabaseField(id = true)
    public Integer id;

    @DatabaseField()
    public String category;

    public CategoryModel() {}

}
