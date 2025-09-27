                                 Affine Cipher Encryption and Decryption Tool (Java)
This project is a fully functional, console-based application implemented in Java that demonstrates the classical Affine Cipher cryptographic algorithm. It allows users to encrypt plaintext and decrypt ciphertext using modular arithmetic, ensuring the mathematical requirements for key validity are met.

🌟 Key Features
Core Affine Cipher Logic: Implements both the encryption formula E(x)=(ax+b)mod26 and the corresponding decryption formula D(y)=a 
−1
 (y−b)mod26.

Key Validation: Automatically checks the multiplicative key a by ensuring gcd(a,26)=1, which is necessary for the modular inverse to exist. This utilizes the Euclidean Algorithm (in the gcd method).

Modular Inverse Calculation: Finds the modular multiplicative inverse a 
−1
  required for decryption.

Interactive Console Loop: The tool runs continuously, allowing the user to perform multiple encryption and decryption operations using the same keys without restarting.

Chained Operations: Allows the user to optionally use the result of the previous operation (e.g., ciphertext) as the input for the next operation (e.g., decryption), streamlining testing.

Case and Non-Alphabetic Handling: Preserves spacing and punctuation while only ciphering the 26 letters of the English alphabet (case-insensitive conversion).

🛠️ Prerequisites
To compile and run this application, you need:

Java Development Kit (JDK): Version 8 or higher.

********************************************************************************* Web Implementation (HTML/JavaScript)  **************************************************************************************************

To provide a modern, accessible interface for the cipher, the project includes a client-side web application contained within a single HTML file. This version completely reimplements the Affine Cipher logic using JavaScript,
allowing the encryption and decryption to happen instantly within the user's browser, eliminating the need for a server.

Key Web Features
Intuitive Web Interface: A single-page application built with responsive HTML and modern CSS (Tailwind CSS classes) for easy use across various devices.

Client-Side Processing: All cryptographic logic (encryption, decryption, key validation) runs entirely in the browser using JavaScript.

Core Affine Cipher Logic: Implements the same mathematical formulas: E(x)=(ax+b)mod26 and
D(y)=a −1
 (y−b)mod26.

Real-time Key Validation: Checks the multiplicative key a to ensure gcd(a,26)=1 upon input change, providing immediate user feedback.

Seamless Copy Functionality: Allows users to easily copy the encrypted or decrypted result to the clipboard.

Non-Alphabetic Handling: Preserves spacing, numbers, and punctuation in the input text.
