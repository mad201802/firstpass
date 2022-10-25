import logic.StrengthAnalyzer;

public class Firstpass {

    public static void main(String[] args) {

        StrengthAnalyzer analyzer = new StrengthAnalyzer();

        String testPass = "Ddgu463&)/";

        System.out.println("Nice!");
        System.out.println("Your password: " + testPass);
        System.out.println("Strength: " + analyzer.checkStrength(testPass));
    }

}
