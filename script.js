
const messageInput = document.getElementById('messageInput');
const aInput = document.getElementById('aInput');
const bInput = document.getElementById('bInput');
const resultOutput = document.getElementById('resultOutput');
const errorMessage = document.getElementById('error-message');

// Greatest common divisor
const gcd = (a, b) => b === 0 ? a : gcd(b, a % b);

// Modular inverse
const modInverse = (a, m) => {
    for (let x = 1; x < m; x++) {
        if ((a * x) % m === 1) return x;
    }
    return null;
};

// Show error
const showError = (msg) => {
    errorMessage.textContent = msg;
    errorMessage.classList.add('show');
    resultOutput.textContent = 'Please fix the input and try again.';
    resultOutput.classList.add('output-error');
};

// Clear error
const clearError = () => {
    errorMessage.textContent = '';
    errorMessage.classList.remove('show');
    resultOutput.classList.remove('output-error');
};

// (can encrypt or decrypt)
const affineCipher = (text, a, b, decrypt = false) => {
    const m = 26;
    const aInv = decrypt ? modInverse(a, m) : null;
    if (decrypt && aInv === null) {
        showError("Modular inverse doesn't exist. Decryption impossible.");
        return '';
    }

    return [...text].map(char => {
        if (!/[a-z]/i.test(char)) return char;

        const isUpper = char === char.toUpperCase();
        const base = isUpper ? 65 : 97;
        let x = char.charCodeAt(0) - base;

        if (decrypt) {
            x = (aInv * (x - b + m)) % m;
        } else {
            x = (a * x + b) % m;
        }

        return String.fromCharCode(x + base);
    }).join('');
};

// Encrypt 
const handleEncrypt = () => {
    clearError();
    const message = messageInput.value;
    const a = parseInt(aInput.value);
    const b = parseInt(bInput.value);

    if (!message || isNaN(a) || isNaN(b)) {
        showError("Enter a message and valid 'a' and 'b' values.");
        return;
    }
    if (gcd(a, 26) !== 1) {
        showError("'a' and 26 must be coprime. Choose another 'a'.");
        return;
    }

    resultOutput.textContent = affineCipher(message, a, b, false);
};

// Decrypt 
const handleDecrypt = () => {
    clearError();
    const message = messageInput.value;
    const a = parseInt(aInput.value);
    const b = parseInt(bInput.value);

    if (!message || isNaN(a) || isNaN(b)) {
        showError("Enter a message and valid 'a' and 'b' values.");
        return;
    }
    if (gcd(a, 26) !== 1) {
        showError("'a' and 26 must be coprime. Choose another 'a'.");
        return;
    }

    resultOutput.textContent = affineCipher(message, a, b, true);
};
