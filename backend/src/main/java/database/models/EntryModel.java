package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "passwords")
public class EntryModel {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField()
    private String username;

    @DatabaseField()
    private String password;

    @DatabaseField()
    private String url;

    @DatabaseField()
    private String createdAt;

    @DatabaseField()
    private String expiresAt;

    @DatabaseField()
    private String notes;

}
