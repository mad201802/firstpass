package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.firstpass.utils.decorators.Generated;

/**
 * This class is used to represent a category in the database.
 */
@Generated
@DatabaseTable(tableName = "categories")
public class CategoryModel {
    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;

    @DatabaseField()
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CategoryModel(String category) {
        this.category = category;
    }

    public CategoryModel(int id, String category) {
        this.id = id;
        this.category = category;
    }
    public CategoryModel() {}

    public String getName() {
        return category;
    }
}
