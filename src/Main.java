import java.io.*;
import java.net.*;

public class Main {
    static class User {
        String username;
        String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static void main(String[] args) {
        // Define test users
        User[] users = {
                new User("Hemida", "sec2_43243049"),
                new User("user2", "password2"),
                // Add more users as needed
        };

        try {
            ServerSocket serverSocket = new ServerSocket(1254);
            System.out.println("Waiting for client on port: " + serverSocket.getLocalPort()); // Listen

            while (true) { // Continuously accept client requests
                Socket clientSocket = serverSocket.accept();
                System.out.println("Just Connected to " + clientSocket.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                String message = in.readUTF();
                System.out.println(message);

                // Read client credentials from the message
                String[] parts = message.split("-_-");
                String username = parts[0];
                String password = parts[1];

                boolean isValid = false;
                for (User user : users) {
                    if (user.username.equals(username) && user.password.equals(password)) {
                        isValid = true;
                        break;
                    }
                }

                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                if (isValid) {
                    out.writeUTF("You have successfully logged in");
                } else {
                    out.writeUTF("Invalid username or password");
                }

                // Close the connection
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
