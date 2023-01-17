package io.firstpass.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestStrengthAnalyzer {
    @Test
    public void test_computeEntropy() {
        StrengthAnalyzer strengthAnalyzer = new StrengthAnalyzer();
        double entropy1 =  Math.round(strengthAnalyzer.computeEntropy("abcdefg") * 100.0)/100.0;
        double entropy2 =  Math.round(strengthAnalyzer.computeEntropy("Abcdefg") * 100.0)/100.0;
        double entropy3 =  Math.round(strengthAnalyzer.computeEntropy("Abcdef1") * 100.0)/100.0;
        double entropy4 =  Math.round(strengthAnalyzer.computeEntropy("Abcde1!") * 100.0)/100.0;


        Assertions.assertNotEquals(35.00, entropy1);
        Assertions.assertEquals(34.01, entropy1);

        Assertions.assertNotEquals(40.01, entropy2);
        Assertions.assertEquals(41.01, entropy2);

        Assertions.assertNotEquals(42.62, entropy3);
        Assertions.assertEquals(42.61, entropy3);

        Assertions.assertNotEquals(46.00, entropy4);
        Assertions.assertEquals(45.99, entropy4);
    }
}
