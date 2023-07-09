package com.example.secretcoder.binary;

import com.example.secretcoder.constants.Constants;
import com.example.secretcoder.encyptions.AESUtil;

public class BinaryEncoder {

    public BinaryEncoder(){
    }

    public String encodeText(String message, String secret, String key, String encryptionAlgorithm) {

        // Encrypt the message using AES encryption
        String encryptedMessaged = encryptMessage(encryptionAlgorithm, secret, key);
        if(encryptedMessaged == null)
            return null;
        // Convert the encrypted message to a binary string
        String encryptedInputBinary = convertInputToBinaryString(encryptedMessaged);
        // Convert the binary string to a Zero-Width string
        String hiddenMessage = convertBinaryToZeroWidthString(encryptedInputBinary);
        // Insert the Zero-Width string into the original message
        String encodedText = insertZeroWidthStringToMessage(message, hiddenMessage);
        return encodedText;
    }

    private String encryptMessage(String encryptionAlgorithm, String secret, String key) {
        String cipherText = AESUtil.encrypt(encryptionAlgorithm, secret, key);
        return cipherText;
    }

    private String convertInputToBinaryString(String input) {
        byte[] inputBytes = convertStringToBytes(input);
        String inputBinary = convertBytesToBinary(inputBytes);
        return inputBinary;
    }

    private String convertBinaryToZeroWidthString(String inputBinary) {
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

    private String insertZeroWidthStringToMessage(String message, String hiddenMessage) {
        String encodedMessage = String.valueOf(message.charAt(0));
        encodedMessage += hiddenMessage;
        encodedMessage += (message.substring(1));
        return encodedMessage;
    }

    private byte[] convertStringToBytes(String input) {
        byte[] inputBytes = input.getBytes();
        return inputBytes;
    }

    private String convertBytesToBinary(byte[] inputBytes) {
        StringBuilder inputBinary = new StringBuilder();
        for (byte b : inputBytes) {
            int value = b;
            inputBinary.append(convertByteToBinary(value));
            inputBinary.append(" ");
        }
        return inputBinary.toString();
    }

    private String convertByteToBinary(int value) {
        StringBuilder inputBinary = new StringBuilder();
        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
            inputBinary.append((value & 128) == 0 ? 0 : 1);
            value <<= 1;
        }
        return inputBinary.toString();
    }

}
