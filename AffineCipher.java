import java.util.Scanner;

/**
 * Implements the Affine Cipher for encryption and decryption.
 * The cipher uses the formula: E(x) = (ax + b) mod m
 * Where m (M) is 26 (the size of the English alphabet).
 */
public class AffineCipher {

    // Global modulus M = 26 (for English alphabet)
    private static final int M = 26;

    /**
     * Calculates the Greatest Common Divisor (GCD) of two numbers using the Euclidean algorithm.
     * This is essential for validating the key 'a'.
     
     */
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Finds the modular multiplicative inverse of 'a' modulo 'm'.
     * This is required for decryption: D(y) = a^-1 (y - b) mod m.
     * @param a The number to find the inverse for.
     * @param m The modulus (26).
     * @return The modular inverse a^-1, or -1 if none exists.
     */
    private static int modInverse(int a, int m) {
        a = (a % m + m) % m; // Ensure 'a' is positive
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    /**
     * Encrypts plaintext using the Affine Cipher formula.
     * @param plaintext The text to encrypt.
     * @param a The multiplicative key (must be coprime with 26).
     * @param b The additive key.
     * @return The encrypted ciphertext.
     */
    public static String encrypt(String plaintext, int a, int b) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = plaintext.toUpperCase();

        for (char c : plaintext.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                // Convert character to 0-25 index
                int x = c - 'A';
                // Apply formula: E(x) = (a * x + b) mod M
                int encryptedIndex = (a * x + b) % M;
                // Convert 0-25 index back to character
                char encryptedChar = (char) (encryptedIndex + 'A');
                ciphertext.append(encryptedChar);
            } else {
                // Keep non-alphabetic characters
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    /**
     * Decrypts ciphertext using the Affine Cipher formula.
     * @param ciphertext The text to decrypt.
     * @param a The multiplicative key.
     * @param b The additive key.
     * @return The decrypted plaintext.
     */
    public static String decrypt(String ciphertext, int a, int b) {
        int aInverse = modInverse(a, M);
        if (aInverse == -1) {
            return "[Error: Key 'a' is invalid, modular inverse does not exist.]";
        }

        StringBuilder plaintext = new StringBuilder();
        ciphertext = ciphertext.toUpperCase();

        for (char c : ciphertext.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                // Convert character to 0-25 index
                int y = c - 'A';
                
                // Apply formula: D(y) = a^-1 (y - b) mod M
                // Ensure positive intermediate result before modulo M
                int decryptedIndex = (aInverse * (y - b + M)) % M; 
                
                // Convert 0-25 index back to character
                char decryptedChar = (char) (decryptedIndex + 'A');
                plaintext.append(decryptedChar);
            } else {
                // Keep non-alphabetic characters
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    /**
     * Main method to run the interactive console tool.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = -1;
        int b = -1;
        String currentText = ""; // Stores the result of the last operation

        System.out.println(" Affine Cipher Tool ");

        // --- Key Input and Validation (Runs once) ---
        boolean validA = false;
        while (!validA) {
            System.out.print("Enter multiplicative key 'a' (must be coprime with 26): ");
            if (scanner.hasNextInt()) {
                a = scanner.nextInt();
                if (gcd(a, M) == 1) {
                    validA = true;
                    System.out.println("[Success] Key 'a' is valid.");
                } else {
                    System.out.printf("[Error] GCD(%d, 26) is not 1. Try a different number (e.g., 3, 5, 7, 11, 15...).%n", a);
                }
            } else {
                System.out.println("[Error] Invalid input. Please enter an integer.");
                scanner.next(); // Consume invalid input
            }
        }

        System.out.print("Enter additive key 'b' (any integer 0-25): ");
        if (scanner.hasNextInt()) {
            b = scanner.nextInt();
        }
        scanner.nextLine(); // Consume newline after 'b' input

        // --- Main Operation Loop ---
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("\n New Operation ");
            
            // 1. Input Text Management
            String inputText;
            if (!currentText.isEmpty()) {
                System.out.printf("Previous Result: \"%s\"%n", currentText);
                System.out.print("Use previous result as input? (y/n, default: y): ");
                String usePrevious = scanner.nextLine().trim().toLowerCase();
                
                if (usePrevious.equals("n")) {
                    System.out.print("Enter new text: ");
                    inputText = scanner.nextLine();
                } else {
                    inputText = currentText;
                    System.out.println("Using previous result as input.");
                }
            } else {
                System.out.print("Enter text: ");
                inputText = scanner.nextLine();
            }

            // 2. Operation Choice
            System.out.println("\nSelect operation:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.print("Enter choice (1 or 2): ");
            String choice = scanner.nextLine().trim();

            String result;
            
            // 3. Execution
            if (choice.equals("1")) {
                result = encrypt(inputText, a, b);
                System.out.println("\nEncrypted Text (Ciphertext): " + result);
            } else if (choice.equals("2")) {
                result = decrypt(inputText, a, b);
                System.out.println("\nDecrypted Text (Plaintext): " + result);
            } else {
                System.out.println("[Error] Invalid choice.");
                result = currentText; // Don't modify currentText on error
            }
            
            // 4. Update and Continue Check
            // Only update currentText if the operation was successful
            if (choice.equals("1") || choice.equals("2")) {
                currentText = result; 
            }
            
            System.out.print("\nDo you want to perform another operation? (y/n): ");
            String continuation = scanner.nextLine().trim().toLowerCase();
            if (continuation.equals("n")) {
                continueRunning = false;
            }
        }

        System.out.println("Thank You!");
        scanner.close();
    }
}
