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
        int charIndex = 1, messageLen = message.length();
        for (int i = 0; i < messageLen; i++) {
            char currentChar = message.charAt(i);
            if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO)
                zwString.append(Constants.ZWNJ_CHAR_AS_ZERO);
            if (currentChar == Constants.ZWJ_CHAR_AS_ONE)
                zwString.append(Constants.ZWJ_CHAR_AS_ONE);
            if (currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
                zwString.append(Constants.ZERO_WIDTH_SPACE_CHAR);
        }
//        while (true) {
//            char currentChar = message.charAt(charIndex);
//            if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO ||
//                    currentChar == Constants.ZWJ_CHAR_AS_ONE ||
//                    currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
//                break;
//            else {
//                if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO)
//                    zwString.append(Constants.ZERO_CHAR);
//                if (currentChar == Constants.ZWJ_CHAR_AS_ONE)
//                    zwString.append(Constants.ONE_CHAR);
//                if (currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
//                    zwString.append(Constants.SPACE_CHAR);
//            }
//            charIndex++;
//        }
        return zwString.toString();
    }

    private String convertZeroWidthToBinaryString(String inputBinary) {
        int secretBinaryLength = inputBinary.length();
        StringBuilder binaryMessage = new StringBuilder();
        for (int i = 0; i < secretBinaryLength; i++) {
            char currentChar = inputBinary.charAt(i);
            if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO)
                binaryMessage.append(Constants.ZERO_CHAR);//0 char
            else if (currentChar == Constants.ZWJ_CHAR_AS_ONE)
                binaryMessage.append(Constants.ONE_CHAR);//1 char
            else if (currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
                binaryMessage.append(Constants.SPACE_CHAR);//space char
        }
        return binaryMessage.toString();
    }

    private String convertBinaryToTextString(String encryptedBinaryMessage) {
        StringBuilder asciiString = new StringBuilder();
        int messageLen = encryptedBinaryMessage.length();
        for (int i = 0; i < messageLen; i += Constants.BYTE_SIZE) {
            if(encryptedBinaryMessage.charAt(i) == Constants.SPACE_CHAR)
                i++;
            if(i + Constants.BYTE_SIZE > messageLen)
                break;
            String binaryChar = encryptedBinaryMessage.substring(i, i + Constants.BYTE_SIZE);
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
