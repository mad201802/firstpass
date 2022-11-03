package io.firstpass;

import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;

public class FirstPass {

    public static void main(String[] args) {
        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
    }

}
