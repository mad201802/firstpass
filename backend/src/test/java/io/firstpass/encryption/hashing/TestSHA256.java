package io.firstpass.encryption.hashing;

import io.firstpass.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSHA256 {

    @Test()
    public void test_sha_256() {
        String text = "hello";
        String hash = SHA256.hash(text);
        Assertions.assertEquals("3338be694f50c5f338814986cdf0686453a888b84f424d792af4b9202398f392", hash);
    }
}
