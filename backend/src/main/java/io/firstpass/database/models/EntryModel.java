package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class represents an entry that is encrypted.
 */
@DatabaseTable(tableName = "entries")
public class EntryModel {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private EncryptedModel username;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private EncryptedModel password;

//    @DatabaseField()
//    private String url;
//
//    @DatabaseField(foreign = true, foreignAutoRefresh = true)
//    private CategoryModel category;
//
//    @DatabaseField()
//    private String createdAt;
//
//    @DatabaseField()
//    private String expiresAt;
//
//    @DatabaseField()
//    private String notes;

    public EntryModel() {}

    public EntryModel(String name) {
        this.name = name;
    }

    public void setUsername(EncryptedModel username) {
        this.username = username;
    }

    public EncryptedModel getUsername() {
        return username;
    }

    public void setPassword(EncryptedModel password) {
        this.password = password;
    }

    public EncryptedModel getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }
}
