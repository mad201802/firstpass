package utils;

import java.io.*;
import java.util.Scanner;

public class CLI {

    public static Scanner scanner = new Scanner(System.in);

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
            System.out.print("Action: ");

            action = scanner.nextInt();
            handleAction(action);
        }
    }

    private static void handleAction(int action) {
        switch (action) {
            case 1:
                line("View passwords");
                break;
            case 2:
                line("Add password");
                break;
            case 3:
                line("Exiting...");
                break;
            default:
                line("Invalid action.");
                break;
        }
    }

    private static void login() {
        boolean unauthorized = true;

        line("Please enter your credentials to continue.");
        line();
        while (unauthorized) {
            String username = getUsername();
            String password = getPassword();
            line();

            line("Logging in...");
            if (isValidLogin(username, password)) {
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

    private static String getUsername() {
        System.out.print("Username: ");
        return scanner.nextLine();
    }

    private static boolean isValidLogin(String username, String password) {
        return username.equals("admin") && password.equals("password");
    }

    private static void line(String message) {
        System.out.println("## " + message);
    }
    private static void line() {
        System.out.println("##");
    }
}