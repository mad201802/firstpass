package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "entries")
public class EntryModel {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField()
    private String username;

    @DatabaseField()
    private String password;


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


    public EntryModel() {
        // ORMLite needs a no-arg constructor
    }

    public EntryModel(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
