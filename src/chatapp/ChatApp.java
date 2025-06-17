package chatapp;

import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatApp {
    private static String savedFirstName;
    private static String savedLastName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        savedFirstName = firstName;

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        savedLastName = lastName;

        // Username must contain _ and up to 5 characters
        String username;
        while (true) {
            System.out.print("Enter username (must contain '_' and be at least 5 characters): ");
            username = scanner.nextLine();
            if (Login.checkUsername(username)) {
                break;
            }
            System.out.println("Invalid username! Must contain '_' and be at least.");
            System.out.println("Please try again.\n");
        }

        // Password requirements
        String password;
        while (true) {
            System.out.print("Enter password (at least 8 chars, 1 uppercase, 1 number, 1 special character): ");
            password = scanner.nextLine();
            if (Login.checkPasswordComplexity(password)) {
                break;
            }
            System.out.println("Invalid password! Must have:");
            System.out.println("- 8+ characters");
            System.out.println("- 1 uppercase letter");
            System.out.println("- 1 number");
            System.out.println("- 1 special character");
            System.out.println("Please try again.\n");
        }

        // Cellphone number validation (+27 + 8 digits = 11 characters total)
        String cellphone;
        while (true) {
            System.out.print("Enter SA cellphone number (+27 followed by 8 digits): ");
            cellphone = scanner.nextLine();
            if (Login.checkCellPhoneNumber(cellphone)) {
                break;
            }
            System.out.println("Invalid phone number! Must be in format +27XXXXXXXX (8 digits after +27)");
            System.out.println("Please try again.\n");
        }

        // Confirmation
        System.out.println("\nSUCCESS! All inputs are valid:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Cellphone: " + cellphone);

        // Login verification
        System.out.println("\n=== LOGIN VERIFICATION ===");
        while (true) {
            System.out.print("Enter username to login: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter password to login: ");
            String loginPassword = scanner.nextLine();

            if (loginUsername.equals(username) && loginPassword.equals(password)) {
                System.out.println("Welcome " + savedFirstName + ", " + savedLastName + "! It is great to see you again.");
                break;
            } else {
                System.out.println("Username or password incorrect, please try again.\n");
            }
        }

        // Start chat functionality
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
        List<Message> sentMessages = new ArrayList<>();

        mainLoop:
        while (true) {
            String choice = JOptionPane.showInputDialog(
                    "Choose option:\n1) Send Messages\n2) Show recent messages\n3) Quit");

            switch (choice) {
                case "1":
                    String countInput = JOptionPane.showInputDialog("How many messages to send?");
                    try {
                        int messageCount = Integer.parseInt(countInput);
                        for (int i = 0; i < messageCount; i++) {
                            String recipient = JOptionPane.showInputDialog("Message " + (i + 1) + ": Enter recipient number (+27XXXXXXXX):");
                            while (!Message.checkRecipientCell(recipient)) {
                                recipient = JOptionPane.showInputDialog("Invalid format! Must be +27 followed by 8 digits:");
                            }

                            String content = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                            while (content.length() > 250) {
                                content = JOptionPane.showInputDialog("Message too long! Max 250 characters:");
                            }

                            Message msg = new Message(recipient, content);
                            sentMessages.add(msg);

                            JOptionPane.showMessageDialog(null,
                                    "Message Sent!\nID: " + msg.getMessageID() +
                                            "\nHash: " + msg.getHash() +
                                            "\nTo: " + msg.getRecipient() +
                                            "\nContent: " + msg.getContent());
                        }

                        // Save messages to JSON
                        try (FileWriter file = new FileWriter("messages.json")) {
                            JSONArray jsonArray = new JSONArray();
                            for (Message msg : sentMessages) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("recipient", msg.getRecipient());
                                jsonObject.put("messageID", msg.getMessageID());
                                jsonObject.put("content", msg.getContent());
                                jsonObject.put("hash", msg.getHash());
                                jsonArray.put(jsonObject);
                            }
                            file.write(jsonArray.toString());
                            file.flush();
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Error writing JSON: " + e.getMessage());
}

                        JOptionPane.showMessageDialog(null,
                                "Total messages sent: " + Message.getTotalMessages());

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number");
                    }
                    break;

                case "2":
                    if (sentMessages.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No messages have been sent yet.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (Message msg : sentMessages) {
                            sb.append("To: ").append(msg.getRecipient())
                                    .append("\nID: ").append(msg.getMessageID())
                                    .append("\nContent: ").append(msg.getContent())
                                    .append("\nHash: ").append(msg.getHash())
                                    .append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString(), "Recent Messages", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case "3":
                    break mainLoop;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1-3");
            }
        }

        scanner.close();
        System.out.println("\nThank you for using QuickChat!");
    }
}