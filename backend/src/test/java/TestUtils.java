import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Utils;

public class TestUtils {

    @Test
    public void test_bytesToBase64() {
        byte[] bytes = new byte[] { 1, 2, 3, 4, 5 };
        String base64 = Utils.bytesToBase64(bytes);
        Assertions.assertEquals("AQIDBAU=", base64);
    }

    @Test
    public void test_base64ToBytes() {
        String base64 = "AQIDBAU=";
        byte[] bytes = Utils.base64ToBytes(base64);
        Assertions.assertArrayEquals(new byte[] { 1, 2, 3, 4, 5 }, bytes);
    }
}
