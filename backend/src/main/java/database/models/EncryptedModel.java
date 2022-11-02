package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "encrypted")
public class EncryptedModel {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField()
    private String cipher;

    @DatabaseField()
    private String iv;
}
