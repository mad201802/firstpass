import database.drivers.SQLiteDriver;
import logic.StrengthAnalyzer;

import java.sql.SQLException;

public class Firstpass {

    public static void main(String[] args) throws SQLException {

        StrengthAnalyzer analyzer = new StrengthAnalyzer();

        String testPass = "Ddgu463&)/";

        System.out.println("Nice!");
        System.out.println("Your password: " + testPass);
        System.out.println("Strength: " + analyzer.checkStrength(testPass));

        //Driver test/demo
        SQLiteDriver driver = new SQLiteDriver();
        driver.deleteAllEntries("Jan Hopsken");
        driver.addEntry("Jan Hopsken", "SuperCoolesPassword123");

        System.out.println(driver.getEntry("Jan Hopsken").getId());
        driver.updateEntry(7, "Jan Hopsken", "ASDFGHJKL");

        System.out.println(driver.getAllEntries());
        System.out.println("Entry: " + driver.getEntry("Jan Hopsken").getUsername() + ", " +
                driver.getEntry("Jan Hopsken").getPassword());


    }

}
