package com.example.secretcoder.binary;

import com.example.secretcoder.constants.Constants;
import com.example.secretcoder.encyptions.AESUtil;

public class BinaryDecoder {


    public String decodeText(String encodedMessage, String key, String encryptionAlgorithm) {

        // Get the Zero-Width string from the encoded message
        String hiddenMessage = getZeroWidthStringFromMessage(encodedMessage);
        // Convert the Zero-Width string to a binary string
        String encryptedBinaryMessage = convertZeroWidthToBinaryString(hiddenMessage);
        // Convert the binary string to a encrypted message
        String encryptedMessage = convertBinaryToTextString(encryptedBinaryMessage);
        // Decrypt the message using AES encryption
        String decodedText = decryptMessage(encryptionAlgorithm, encryptedMessage, key);
        return decodedText;
    }

    private String getZeroWidthStringFromMessage(String message) {
        StringBuilder zwString = new StringBuilder();
        int charIndex = 1;
        while (true) {
            char currentChar = message.charAt(charIndex);
            if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO ||
                    currentChar == Constants.ZWJ_CHAR_AS_ONE ||
                    currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
                break;
            else {
                if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO)
                    zwString.append(Constants.ZERO_CHAR);
                if (currentChar == Constants.ZWJ_CHAR_AS_ONE)
                    zwString.append(Constants.ONE_CHAR);
                if (currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
                    zwString.append(Constants.SPACE_CHAR);
            }
            charIndex++;
        }
        return zwString.toString();
    }

    private String convertZeroWidthToBinaryString(String inputBinary) {
        int secretBinaryLength = inputBinary.length();
        StringBuilder binaryMessage = new StringBuilder();
        for (int i = 0; i < secretBinaryLength; i++) {
            if (inputBinary.charAt(i) == Constants.ZERO_CHAR)
                binaryMessage.append(Constants.ZWNJ_CHAR_AS_ZERO);//0 char
            else if (inputBinary.charAt(i) == Constants.ONE_CHAR)
                binaryMessage.append(Constants.ZWJ_CHAR_AS_ONE);//1 char
            else if (inputBinary.charAt(i) == Constants.SPACE_CHAR)
                binaryMessage.append(Constants.ZERO_WIDTH_SPACE_CHAR);//space char
        }
        return binaryMessage.toString();
    }

    private String convertBinaryToTextString(String encryptedBinaryMessage) {
        StringBuilder asciiString = new StringBuilder();
        for (int i = 0; i < encryptedBinaryMessage.length(); i += 8) {
            String binaryChar = encryptedBinaryMessage.substring(i, i + 8);
            int asciiValue = Integer.parseInt(binaryChar, 2);
            asciiString.append((char) asciiValue);
        }
        return asciiString.toString();
    }

    private String decryptMessage(String encryptionAlgorithm, String secret, String key) {
        String cipherText = AESUtil.decrypt(encryptionAlgorithm, secret, key);
        return cipherText;
    }

//    private String convertInputToBinaryString(String input) {
//        byte[] inputBytes = convertStringToBytes(input);
//        String inputBinary = convertBytesToBinary(inputBytes);
//        return inputBinary;
//    }
//
//    private byte[] convertStringToBytes(String input) {
//        byte[] inputBytes = input.getBytes();
//        return inputBytes;
//    }
//
//    private String convertBytesToBinary(byte[] inputBytes) {
//        StringBuilder inputBinary = new StringBuilder();
//        for (byte b : inputBytes) {
//            int value = b;
//            inputBinary.append(convertByteToBinary(value));
//            inputBinary.append(" ");
//        }
//        return inputBinary.toString();
//    }
//
//    private String convertByteToBinary(int value) {
//        StringBuilder inputBinary = new StringBuilder();
//        for (int i = 0; i < Constants.BYTE_SIZE; i++) {
//            inputBinary.append((value & 128) == 0 ? 0 : 1);
//            value <<= 1;
//        }
//        return inputBinary.toString();
//    }

}
