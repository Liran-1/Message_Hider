package com.example.secretcoder.binary;

import androidx.annotation.NonNull;

import com.example.secretcoder.constants.Constants;
import com.example.secretcoder.encyptions.AESUtil;

public class BinaryDecoder {


    public String decodeText(String encodedMessage, String key, String encryptionAlgorithm) {
        String hiddenMessage, encryptedBinaryMessage, encryptedMessage, decodedText;

        // Get the Zero-Width string from the encoded message
        hiddenMessage = getZeroWidthStringFromMessage(encodedMessage);
        // Convert the Zero-Width string to a binary string
        encryptedBinaryMessage = convertZeroWidthToBinaryString(hiddenMessage);
        // Convert the binary string to a encrypted message
        encryptedMessage = convertBinaryToTextString(encryptedBinaryMessage);
        // Decrypt the message using AES encryption
        decodedText = decryptMessage(encryptionAlgorithm, encryptedMessage, key);
        return decodedText;
    }

    @NonNull
    private String getZeroWidthStringFromMessage(@NonNull String message) {
        StringBuilder zwString = new StringBuilder();
        int messageLen = message.length();
        for (int i = 0; i < messageLen; i++) {
            char currentChar = message.charAt(i);
            if (currentChar == Constants.ZWNJ_CHAR_AS_ZERO)
                zwString.append(Constants.ZWNJ_CHAR_AS_ZERO);
            if (currentChar == Constants.ZWJ_CHAR_AS_ONE)
                zwString.append(Constants.ZWJ_CHAR_AS_ONE);
            if (currentChar == Constants.ZERO_WIDTH_SPACE_CHAR)
                zwString.append(Constants.ZERO_WIDTH_SPACE_CHAR);
        }

        return zwString.toString();
    }

    @NonNull
    private String convertZeroWidthToBinaryString(@NonNull String inputBinary) {
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

    @NonNull
    private String convertBinaryToTextString(@NonNull String encryptedBinaryMessage) {
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
        String cipherText;
        cipherText = AESUtil.decrypt(encryptionAlgorithm, secret, key);
        return cipherText;
    }

}
