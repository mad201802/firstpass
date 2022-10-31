import logic.StrengthAnalyzer;

import java.util.Scanner;

public class FirstPass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StrengthAnalyzer strengthAnalyzer = new StrengthAnalyzer();


        // TODO: Diese Funktionalität über ein Hauptmenü zugänglich machen
        System.out.println("Please enter a password you want to test: ");
        String passwordToTest = scanner.nextLine();
        System.out.println();
        System.out.println("Your password is " + strengthAnalyzer.checkStrength(passwordToTest));
        

    }

}
