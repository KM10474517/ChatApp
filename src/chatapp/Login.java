package chatapp;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {

    
    // Username must contain _ and upto 5 characters
    public static boolean checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("ERROR: Username cannot be empty");
            return false;
        }
        
        if (!username.contains("_")) {
            System.out.println("ERROR: Username must contain '_'");
            return false;
        }
        
        if (username.length() > 5) {
            System.out.println("ERROR: Username must be ≤5 characters");
            return false;
        }
        
        return true;
    }

    // Password must be greater than 8 characters, 1 uppercase, 1 number and 1 special character
    public static boolean checkPasswordComplexity(String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("ERROR: Password cannot be empty");
            return false;
        }
        
        if (password.length() < 8) {
            System.out.println("ERROR: Password must be ≥8 characters");
            return false;
        }
        
        if (password.equals(password.toLowerCase())) {
            System.out.println("ERROR: Password needs 1 uppercase letter");
            return false;
        }
        
        if (!password.matches(".*\\d.*")) {
            System.out.println("ERROR: Password needs 1 number");
            return false;
        }
        
        if (password.matches("[A-Za-z0-9]*")) {
            System.out.println("ERROR: Password needs 1 special character");
            return false;
        }
        
        return true;
    }

    // Cellphone number with SA country code and 10 digits
    public static boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null || cellNumber.isEmpty()) {
            System.out.println("ERROR: Cellphone cannot be empty");
            return false;
        }
        
        if (!cellNumber.startsWith("+")) {
            System.out.println("ERROR: Must start with '+'");
            return false;
        }
        
        String digits = cellNumber.substring(1);
        if (digits.length() != 11) {
            System.out.println("ERROR: Must have exactly 10 digits after '+'");
            return false;
        }
        
        if (!digits.matches("\\d+")) {
            System.out.println("ERROR: Only digits 0-9 allowed after '+'");
            return false;
        }
        
        return true;
    }
    //"The South African cellphone validation uses a regex adapted from DeepSeek Chat (2024), enforcing the format +27[7/8] followed by 8 digits."

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter SA cellphone number (format +27xxxxxxxxx): ");
        String cellNumber = scanner.nextLine();
        
        if (isValidSouthAfricanCellNumber(cellNumber)) {
            System.out.println("Valid South African number!");
        } else {
            System.out.println("Invalid SA number or incorrect format.");
        }
    }

    public static boolean isValidSouthAfricanCellNumber(String cellNumber) {
        String regex = "^\\+27[678]\\d{8}$";
        return Pattern.matches(regex, cellNumber);
    }
}