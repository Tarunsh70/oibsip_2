import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String profileInfo;

    public User(String username, String password, String profileInfo) {
        this.username = username;
        this.password = password;
        this.profileInfo = profileInfo;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getProfileInfo() { return profileInfo; }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class ExaminationSystem {
    private HashMap<String, User> users = new HashMap<>();
    private User currentUser;
    private Scanner scanner;
    private Timer timer;
    private boolean examInProgress;

    public ExaminationSystem() {
        scanner = new Scanner(System.in);
        initializeUsers();
    }

    private void initializeUsers() {
        // Initial user for testing
        users.put("testuser", new User("testuser", "password123", "User Profile Info"));
    }

    public void start() {
        System.out.println("Welcome to the Online Examination System");
        while (true) {
            System.out.println("1. Login\n2. Sign Up\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            if (choice == 1) {
                if (login()) {
                    mainMenu();
                }
            } else if (choice == 2) {
                signUp();
            } else {
                System.out.println("Exiting... Goodbye!");
                break;
            }
        }
    }

    private boolean login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    private void signUp() {
        System.out.print("Choose a Username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already taken. Please try a different username.");
            return;
        }

        System.out.print("Create a Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter profile information: ");
        String profileInfo = scanner.nextLine();

        // Add the new user to the system
        users.put(username, new User(username, password, profileInfo));
        System.out.println("Sign-up successful! You can now log in with your new credentials.");
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Update Profile\n2. Change Password\n3. Start Exam\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    startExam();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateProfile() {
        System.out.print("Enter new profile information: ");
        String newInfo = scanner.nextLine();
        currentUser.setProfileInfo(newInfo);
        System.out.println("Profile updated successfully.");
    }

    private void changePassword() {
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        if (currentUser.getPassword().equals(currentPassword)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Incorrect current password.");
        }
    }

    private void startExam() {
        if (examInProgress) {
            System.out.println("Exam is already in progress!");
            return;
        }

        examInProgress = true;
        timer = new Timer();
        System.out.println("Exam started. You have 60 seconds to complete.");

        // Start the timer for auto-submit
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (examInProgress) {
                    System.out.println("\nTime's up! Auto-submitting your exam...");
                    endExam();
                }
            }
        }, 60000); // Set for 1 minute (60,000 ms)

        // Sample questions
        String[][] questions = {
            {"1. What is the capital of France?", "a) Paris", "b) London", "c) Berlin", "d) Rome", "a"},
            {"2. Who wrote 'Romeo and Juliet'?", "a) Charles Dickens", "b) William Shakespeare", "c) Mark Twain", "d) Jane Austen", "b"},
            {"3. What is the largest planet in our Solar System?", "a) Earth", "b) Jupiter", "c) Mars", "d) Saturn", "b"},
            {"4. Which element has the chemical symbol 'O'?", "a) Oxygen", "b) Osmium", "c) Gold", "d) Iron", "a"},
            {"5. How many continents are there on Earth?", "a) 5", "b) 6", "c) 7", "d) 8", "c"},
            {"6. Who is known as the father of Computer Science?", "a) Charles Babbage", "b) Alan Turing", "c) Thomas Edison", "d) Isaac Newton", "a"},
            {"7. What is the square root of 64?", "a) 6", "b) 7", "c) 8", "d) 9", "c"},
            {"8. Which planet is known as the Red Planet?", "a) Venus", "b) Mars", "c) Mercury", "d) Jupiter", "b"},
            {"9. What is the largest mammal?", "a) Elephant", "b) Blue Whale", "c) Giraffe", "d) Polar Bear", "b"},
            {"10. What is the boiling point of water in Celsius?", "a) 90°C", "b) 100°C", "c) 110°C", "d) 120°C", "b"}
        };

        int score = 0;
        for (String[] question : questions) {
            System.out.println(question[0]);
            for (int i = 1; i <= 4; i++) {
                System.out.println(question[i]);
            }
            System.out.print("Your answer: ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(question[5])) {
                score++;
            }
        }

        System.out.println("You have completed the exam.");
        endExam();
        System.out.println("Your score: " + score + "/" + questions.length);
    }

    private void endExam() {
        if (timer != null) {
            timer.cancel();
        }
        examInProgress = false;
    }

    private void logout() {
        System.out.println("Logging out...");
        currentUser = null;
    }
}

public class Online_Examination
 {
    public static void main(String[] args) {
        ExaminationSystem system = new ExaminationSystem();
        system.start();
    }
}
