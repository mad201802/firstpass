package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categories")
public class CategoryModel {
    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField()
    private String category;
}
