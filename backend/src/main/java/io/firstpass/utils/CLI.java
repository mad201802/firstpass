package io.firstpass.utils;

import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.logic.StrengthAnalyzer;
import io.firstpass.manager.PasswordManager;
import io.firstpass.manager.models.EntryModel;
import io.firstpass.utils.decorators.Generated;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

@Generated
public class CLI {

    public static final String MASTER_PASSWORD = "password";
    public static final String DATABASE_PATH = "mypasswords.db";
    public static Scanner scanner = new Scanner(System.in);
    public static IEncryptedDatabase database;

    static {
        try {
            database = new SQLiteDriver(DATABASE_PATH, "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ISymmetricEncryptionAlgorithm encryptionAlgorithm = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");

    public static PasswordManager passwordManager= new PasswordManager(database, encryptionAlgorithm, MASTER_PASSWORD);
    public static StrengthAnalyzer strengthAnalyzer = new StrengthAnalyzer();

    public static void run() {
        line("Firstpass CLI v1.0.0");
        line("(c) 2022, Firstpass, Inc.");
        line("All rights reserved.");
        line();

        login();

        int action = -1;
        while (action != 4) {
            line();
            line("Please select an action:");
            line("1. View passwords");
            line("2. Add password");
            line("3. Test a password's strength");
            line("4. Exit");
            line();

            action = Integer.parseInt(getUserInput("Action: "));
            handleAction(action);
        }
    }

    private static void handleAction(int action) {
        switch (action) {
            case 1:
                getAllPasswords();
                break;
            case 2:
                addPassword();
                break;
            case 3:
                checkPasswordStrength();
                break;
            case 4:
                line("Exiting...");
                break;
            default:
                line("Invalid action.");
                break;
        }
    }

    public static void getAllPasswords() {
        ArrayList<EntryModel> entries = passwordManager.getAllEntries();
        if (entries.size() == 0) {
            line("=====");
            line("No passwords found.");
            line("=====");
            return;
        }

        line("View passwords");
        line("--------------");
        entries.forEach(entryModel -> {
            line("===== " + entryModel.getId() + ": " + entryModel.getName() + " =====");
            line("Username/E-Mail: " + entryModel.getUsername());
            line("Password: " + entryModel.getPassword());
            line();
        });
    }

    public static void addPassword(){
        line("Add password");
        String name = getUserInput("Name: ");
        String username = getUserInput("Username/E-Mail: ");
        String password = getUserInput("Password: ");
        if (passwordManager.createEntry(name, username, password, 0, null, null) != -1) {
            line("Password added successfully.");
        } else {
            line("Failed to add password.");
        }
    }

    public static void checkPasswordStrength() {
        line("Check password strength");
        String password = getUserInput("Password to test:");
        // line(strengthAnalyzer.checkStrength(password));
        line(strengthAnalyzer.computeEntropy(password));
        // line(strengthAnalyzer.entropyToStrength(strengthAnalyzer.computeEntropy(password)));
    }

    private static void login() {
        boolean unauthorized = true;

        line("Please enter your credentials to continue.");
        line();
        while (unauthorized) {

            String masterpassword = getPassword();
            line();

            line("Logging in...");
            if (isValidLogin(masterpassword)) {
                unauthorized = false;
            } else {
                line("Invalid credentials.");
                line();
            }
        }

        line("Login successful.");
    }

    public static String getPassword() {
        System.out.print("Password: ");
        try {
            Console con = System.console();

            if (con != null) {
                char[] password = con.readPassword();
                return new String(password);
            } else {
                return scanner.nextLine();
            }
        } catch (Exception e) {
            return "";
        }
    }


    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static boolean isValidLogin(String masterpassword) {
        return masterpassword.equals(MASTER_PASSWORD);
    }

    private static void line(String message) {
        System.out.println("   " + message);
    }
    private static void line(StrengthAnalyzer.PasswordStrength strength) {
        System.out.println("   The password you entered is " + strength);
    }
    private static void line(double passwordEntropy) {
        System.out.println("  Entropy of your password: " + passwordEntropy);
    }
    private static void line() {
        System.out.println("  ");
    }
}