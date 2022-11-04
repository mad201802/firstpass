package io.firstpass.utils;

import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.manager.PasswordManager;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class CLI {

    public static Scanner scanner = new Scanner(System.in);
    public static IEncryptedDatabase database;

    static {
        try {
            database = new SQLiteDriver("passwords.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ISymmetricEncryptionAlgorithm encryptionAlgorithm = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");


    public static PasswordManager passwordManager= new PasswordManager(database, encryptionAlgorithm, "masterpassword");

    public static void run() {
        line("Firstpass CLI v1.0");
        line("(c) 2022, Firstpass, Inc.");
        line("All rights reserved.");
        line();

        login();

        int action = -1;
        while (action != 3) {
            line();
            line("Please select an action:");
            line("1. View passwords");
            line("2. Add password");
            line("3. Exit");
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
                line("Exiting...");
                break;
            default:
                line("Invalid action.");
                break;
        }
    }

    public static void getAllPasswords() {
        line("View passwords");
        line("--------------");
        passwordManager.getAllEntries().forEach(entryModel -> {
            line("ID: " + entryModel.getId());
            line("Name: " + entryModel.getName());
            line("Username: " + entryModel.getUsername());
            line("Password: " + entryModel.getPassword());
            line();
        });
    }

    public static void addPassword(){
        line("Add password");
        String name = getUserInput("Name:");
        String username = getUserInput("Username:");
        String password = getUserInput("Password:");
        passwordManager.addEntry(name, username, password);
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
        return masterpassword.equals("masterpassword");
    }

    private static void line(String message) {
        System.out.println("## " + message);
    }
    private static void line() {
        System.out.println("##");
    }
}