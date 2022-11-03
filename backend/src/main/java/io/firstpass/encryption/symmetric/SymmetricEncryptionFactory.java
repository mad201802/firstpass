package io.firstpass.encryption.symmetric;

import io.firstpass.encryption.exceptions.UnknownAlgorithmException;
import io.firstpass.encryption.symmetric.algos.AES256;

public class SymmetricEncryptionFactory {

        public static ISymmetricEncryptionAlgorithm getSymmetricEncryption(String algorithm) {
            switch (algorithm) {
                case "aes256":
                    return new AES256("15065d771f7c8746bd30c125f9bb68a5ec7a84fccd7f0a82b38e760f39521c05", 1000);
                default:
                    throw new UnknownAlgorithmException("Unknown algorithm: " + algorithm);
            }
        }
}
