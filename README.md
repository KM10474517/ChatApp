# QuickChat (ChatApp)

A Java messaging application with input validation, unique message IDs and hashes, and JSON message storage. Built as part of my software development studies.

## Features

- **Account validation:**
  - Username must contain an underscore and be at most 5 characters
  - Password must be at least 8 characters with an uppercase letter, a number and a special character
  - Cell number must be in South African `+27` format
- **Login check** against the saved account details
- **Messaging menu** (Swing dialogs): send messages, view recently sent messages, or quit
- **Message rules:** messages are limited to 250 characters and each recipient number is validated
- **Message details:** every message gets a generated 10-digit ID and a hash built from its ID, number and words
- **Send, store or disregard:** stored messages are written to `messages.json`
- **Message management methods** in the `Message` class: search by ID or recipient, delete by hash, longest message, and a full report
- **Unit tests** (JUnit 5) for username, password and phone number validation

## Tech Stack

- Java
- Swing (`JOptionPane`) for dialogs
- `org.json` for JSON storage
- JUnit 5 for testing
- NetBeans (Ant) project

## Getting Started

**Requirements:** JDK 8 or newer, NetBeans, and the `org.json` library added to the project.

1. Clone the repo:
   ```
   git clone https://github.com/KM10474517/ChatApp.git
   ```
2. Open the project folder in NetBeans.
3. Add the `org.json` jar to the project libraries.
4. Run `ChatApp.java`.

## Running the Tests

Right-click the project in NetBeans and choose **Test**, or run `LoginTest` directly.

## Project Structure

| File | Purpose |
|------|---------|
| `src/chatapp/ChatApp.java` | Entry point: registration, login and messaging menu |
| `src/chatapp/Login.java` | Username, password and phone number validation |
| `src/chatapp/Message.java` | Message creation, IDs, hashes, storage and reports |
| `test/chatapp/LoginTest.java` | JUnit tests for validation |

## Author

Kamohelo Mopeli
