package com.example.secretcoder.binary;

import androidx.annotation.NonNull;

import com.example.secretcoder.constants.Constants;
import com.example.secretcoder.encyptions.AESUtil;

public class BinaryEncoder {

    public BinaryEncoder() {
    }

    public String encodeText(String message, String secret, String key, String encryptionAlgorithm) {
        String encryptedMessaged, encryptedInputBinary, hiddenMessage, encodedText;

        // Encrypt the message using AES encryption
        encryptedMessaged = encryptMessage(encryptionAlgorithm, secret, key);
        if (encryptedMessaged == null)
            return null;
        // Convert the encrypted message to a binary string
        encryptedInputBinary = convertInputToBinaryString(encryptedMessaged);
        // Convert the binary string to a Zero-Width string
        hiddenMessage = convertBinaryToZeroWidthString(encryptedInputBinary);
        // Insert the Zero-Width string into the original message
        encodedText = insertZeroWidthStringToMessage(message, hiddenMessage);
        return encodedText;
    }

    private String encryptMessage(String encryptionAlgorithm, String secret, String key) {
        String cipherText;
        cipherText = AESUtil.encrypt(encryptionAlgorithm, secret, key);
        return cipherText;
    }

    @NonNull
    private String convertInputToBinaryString(String input) {
        byte[] inputBytes = convertStringToBytes(input);
        String inputBinary;
        inputBinary = convertBytesToBinary(inputBytes);
        return inputBinary;
    }

    @NonNull
    private String convertBinaryToZeroWidthString(@NonNull String inputBinary) {
        int secretBinaryLength = inputBinary.length();
        StringBuilder zeroWidthBinaryMessage = new StringBuilder();
        for (int i = 0; i < secretBinaryLength; i++) {
            if (inputBinary.charAt(i) == Constants.ZERO_CHAR)
                zeroWidthBinaryMessage.append(Constants.ZWNJ_CHAR_AS_ZERO);//0 char
            else if (inputBinary.charAt(i) == Constants.ONE_CHAR)
                zeroWidthBinaryMessage.append(Constants.ZWJ_CHAR_AS_ONE);//1 char
            else if (inputBinary.charAt(i) == Constants.SPACE_CHAR)
                zeroWidthBinaryMessage.append(Constants.ZERO_WIDTH_SPACE_CHAR);//space char
        }
        return zeroWidthBinaryMessage.toString();
    }

    @NonNull
    private String insertZeroWidthStringToMessage(@NonNull String message, String hiddenMessage) {
        String encodedMessage = String.valueOf(message.charAt(0));
        encodedMessage += hiddenMessage;
        encodedMessage += (message.substring(1));
        return encodedMessage;
    }

    private byte[] convertStringToBytes(@NonNull String input) {
        byte[] inputBytes;
        inputBytes = input.getBytes();
        return inputBytes;
    }

    @NonNull
    private String convertBytesToBinary(@NonNull byte[] inputBytes) {
        StringBuilder inputBinary = new StringBuilder();
        for (byte b : inputBytes) {
            inputBinary.append(convertByteToBinary(b));
            inputBinary.append(' ');
        }
        return inputBinary.toString();
    }

    @NonNull
    private String convertByteToBinary(int value) {
        StringBuilder inputBinary = new StringBuilder();
        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
            inputBinary.append((value & 128) == 0 ? 0 : 1);
            value <<= 1;
        }
        return inputBinary.toString();
    }

}
