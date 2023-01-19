package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.firstpass.utils.decorators.Generated;

import java.util.Date;

/**
 * This class represents an entry that is encrypted.
 */
@Generated
@DatabaseTable(tableName = "entries")
public class EncryptedEntryModel {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private EncryptedModel username;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private EncryptedModel password;

    @DatabaseField()
    private String url;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CategoryModel category;

    @DatabaseField()
    private Date createdAt;

    @DatabaseField()
    private String expiresAt;

    @DatabaseField()
    private String notes;

    public EncryptedEntryModel() {}

    public EncryptedEntryModel(String name) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
