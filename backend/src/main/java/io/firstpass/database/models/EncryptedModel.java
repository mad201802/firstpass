package io.firstpass.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.utils.decorators.Generated;

/**
 * This class represents an encrypted attribute of an entry.
 */
@Generated
@DatabaseTable(tableName = "encrypted")
public class EncryptedModel {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField()
    private String cipher;
    @DatabaseField()
    private String iv;

    public EncryptedModel() {}

    public EncryptedModel(String cipher, String iv) {
        this.cipher = cipher;
        this.iv = iv;
    }

    public CipherData getCipherData() {
        CipherData data = new CipherData();
        data.text = cipher;
        data.iv = iv;
        return data;
    }

    public int getId() {
        return id;
    }
}
