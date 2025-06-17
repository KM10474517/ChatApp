package chatapp;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Message {
    // MESSAGE FIELDS
    private String messageID;
    private String recipient;
    private String content;
    private String hash;
    private static int totalMessages = 0;
    private boolean isSent;
    private boolean isStored;
    // END MESSAGE FIELDS

    // ARRAY STORAGE
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    // END ARRAY STORAGE

    // CONSTRUCTOR
    public Message(String recipient, String content) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.content = content;
        this.hash = createMessageHash();
        totalMessages++;
        this.isSent = false;
        this.isStored = false;
    }
    // END CONSTRUCTOR

    // ID GENERATION
    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000000));
    }
    // END ID GENERATION

    // HASH CREATION
    public String createMessageHash() {
        String[] words = content.split(" ");
        String firstWord = words[0];
        String lastWord = words.length > 1 ? words[words.length - 1] : "";
        return (messageID.substring(0, 2) + ":" + totalMessages + ":" + firstWord +
                (lastWord.isEmpty() ? "" : "_" + lastWord)).toUpperCase();
    }
    // END HASH CREATION

    // VALIDATION METHODS
    public static boolean checkMessageID(String id) {
        return id != null && id.matches("\\d{10}");
    }

    public static boolean checkRecipientCell(String cell) {
        return cell != null && cell.matches("^\\+27[678]\\d{8}$");
    }

    public static boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }
    // END VALIDATION METHODS

    // MESSAGE ACTIONS
    public String sendMessage() {
        this.isSent = true;
        sentMessages.add(this);
        messageHashes.add(this.hash);
        messageIDs.add(this.messageID);
        return "Message sent to " + recipient;
    }

    public String storeMessage() {
        this.isStored = true;
        storedMessages.add(this);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("messageID", this.messageID);
        jsonMessage.put("recipient", this.recipient);
        jsonMessage.put("content", this.content);
        jsonMessage.put("hash", this.hash);
        jsonMessage.put("isSent", this.isSent);
        jsonMessage.put("isStored", this.isStored);

        String filePath = "messages.json";

        try {
            // Read existing messages if file exists
            JSONArray messagesArray;
            if (Files.exists(Paths.get(filePath))) {
                String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
                messagesArray = new JSONArray(fileContent);
            } else {
                messagesArray = new JSONArray();
            }

            // Add new message
            messagesArray.put(jsonMessage);

            // Write back to file
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(messagesArray.toString(4)); // Pretty print with 4-space indent
            }

            return "Message stored successfully";

        } catch (IOException e) {
            return "Failed to store message: " + e.getMessage();
        }
    }

    public String disregardMessage() {
        disregardedMessages.add(this);
        return "Message disregarded";
    }
    // END MESSAGE ACTIONS

    // DISPLAY METHODS
    public String printMessageDetails() {
        return "ID: " + messageID + "\n" +
               "Hash: " + hash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Content: " + content + "\n" +
               "Status: " + (isSent ? "SENT" : isStored ? "STORED" : "PENDING");
    }

    public static String printAllMessages(List<Message> messages) {
        StringBuilder sb = new StringBuilder();
        for (Message msg : messages) {
            sb.append(msg.printMessageDetails()).append("\n\n");
        }
        sb.append("TOTAL MESSAGES: ").append(totalMessages);
        return sb.toString();
    }
    // END DISPLAY METHODS

    // MESSAGE OPERATIONS
    public static void displaySendersAndRecipients() {
        System.out.println("\n=== Senders & Recipients ===");
        for (Message msg : sentMessages) {
            System.out.println("System -> " + msg.getRecipient());
        }
    }

    public static void displayLongestMessage() {
        if (sentMessages.isEmpty()) return;

        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getContent().length() > longest.getContent().length()) {
                longest = msg;
            }
        }
        System.out.println("\n=== Longest Message (" + longest.getContent().length() + " chars) ===");
        System.out.println(longest.getContent());
    }

    public static void searchByID(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(id)) {
                System.out.println("\n=== Message Found ===");
                System.out.println("To: " + msg.getRecipient());
                System.out.println("Message: " + msg.getContent());
                return;
            }
        }
        System.out.println("Message not found");
    }

    public static void searchByRecipient(String recipient) {
        System.out.println("\n=== Messages to " + recipient + " ===");
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("- " + msg.getContent());
            }
        }
    }

    public static void deleteByHash(String hash) {
        for (Message msg : sentMessages) {
            if (msg.getHash().equals(hash)) {
                sentMessages.remove(msg);
                System.out.println("Message deleted");
                return;
            }
        }
        System.out.println("Message not found");
    }

    public static void displayFullReport() {
        System.out.println("\n=== FULL MESSAGE REPORT ===");
        System.out.println("Total messages: " + sentMessages.size());
        for (Message msg : sentMessages) {
            System.out.println(msg.printMessageDetails());
            System.out.println("---------------------");
        }
    }
    // END MESSAGE OPERATIONS

    // GETTERS
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getHash() {
        return hash;
    }

    public boolean isSent() {
        return isSent;
    }

    public boolean isStored() {
        return isStored;
    }

    public static int getTotalMessages() {
        return totalMessages;
    }
}