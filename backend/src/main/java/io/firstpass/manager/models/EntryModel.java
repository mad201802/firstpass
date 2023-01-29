package io.firstpass.manager.models;

import io.firstpass.utils.decorators.Generated;

/**
 * This class is used to represent an entry in the database
 */
@Generated
public class EntryModel {
    private final int id;
    private String name;
    private String username;
    private String password;
    private int category;
    private String notes;
    private String url;

    public EntryModel(int id, String name, String username, String password, int category, String notes, String url) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = category;
        this.notes = notes;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCategory() {
        return category;
    }

    public String getNotes() {
        return notes;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
