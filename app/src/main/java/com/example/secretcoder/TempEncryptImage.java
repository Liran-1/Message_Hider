//package com.example.secretcoder;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//public class TempEncryptImage {
//
//    /**
//     * Encrypts a message into the specified image using steganography.
//     *
//     * @param imagePath  The path of the image to use as the carrier image.
//     * @param message    The message to encrypt.
//     * @param secretKey  The secret key used for encryption.
//     * @throws IOException If an error occurs while reading or writing the image file.
//     */
//    public static void encryptMessageIntoImage(String imagePath, String message, String secretKey) throws IOException {
//        BufferedImage image = ImageIO.read(new File(imagePath));
//        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
//        byte[] encryptedMessageBytes = encryptMessage(messageBytes, secretKey);
//        String encryptedMessageBinary = convertBytesToBinary(encryptedMessageBytes);
//
//        int messageLength = encryptedMessageBinary.length();
//        int imageWidth = image.getWidth();
//        int imageHeight = image.getHeight();
//
//        if (messageLength > imageWidth * imageHeight) {
//            throw new IllegalArgumentException("Message is too large to fit in the image.");
//        }
//
//        int bitIndex = 0;
//        for (int y = 0; y < imageHeight; y++) {
//            for (int x = 0; x < imageWidth; x++) {
//                int rgb = image.getRGB(x, y);
//                int modifiedRGB = modifyPixel(rgb, encryptedMessageBinary.charAt(bitIndex));
//                image.setRGB(x, y, modifiedRGB);
//                bitIndex++;
//                if (bitIndex >= messageLength) {
//                    break;
//                }
//            }
//            if (bitIndex >= messageLength) {
//                break;
//            }
//        }
//
//        String outputImagePath = "encrypted_image.png";
//        ImageIO.write(image, "png", new File(outputImagePath));
//        System.out.println("Image encrypted successfully to: " + outputImagePath);
//    }
//
//    /**
//     * Decrypts the message from the specified image using steganography.
//     *
//     * @param imagePath  The path of the image containing the encrypted message.
//     * @param secretKey  The secret key used for decryption.
//     * @return The decrypted message.
//     * @throws IOException If an error occurs while reading the image file.
//     */
//    public static String decryptMessageFromImage(String imagePath, String secretKey) throws IOException {
//        BufferedImage image = ImageIO.read(new File(imagePath));
//        int imageWidth = image.getWidth();
//        int imageHeight = image.getHeight();
//
//        StringBuilder messageBinary = new StringBuilder();
//        for (int y = 0; y < imageHeight; y++) {
//            for (int x = 0; x < imageWidth; x++) {
//                int rgb = image.getRGB(x, y);
//                char bit = extractBit(rgb);
//                messageBinary.append(bit);
//            }
//        }
//
//        byte[] encryptedMessageBytes = convertBinaryToBytes(messageBinary.toString());
//        byte[] decryptedMessageBytes = decryptMessage(encryptedMessageBytes, secretKey);
//        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
//
//        return decryptedMessage;
//    }
//
//    /**
//     * Encrypts a message using the AES algorithm.
//     *
//     * @param message    The message to encrypt.
//     * @param secretKey  The secret key used for encryption.
//     * @return The encrypted message.
//     * @throws NoSuchAlgorithmException       If the AES algorithm is not available.
//     * @throws NoSuchPaddingException         If the padding scheme is not available.
//     * @throws InvalidKeyException            If the secret key is invalid.
//     * @throws InvalidAlgorithmParameterException If the initialization vector is invalid.
//     * @throws IllegalBlockSizeException      If the block size is invalid.
//     * @throws BadPaddingException            If the padding is invalid.
//     */
//    private static byte[] encryptMessage(byte[] message, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        byte[] iv = new byte[cipher.getBlockSize()];
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
//        return cipher.doFinal(message);
//    }
//
//    /**
//     * Decrypts an encrypted message using the AES algorithm.
//     *
//     * @param encryptedMessage  The encrypted message to decrypt.
//     * @param secretKey         The secret key used for decryption.
//     * @return The decrypted message.
//     * @throws NoSuchAlgorithmException       If the AES algorithm is not available.
//     * @throws NoSuchPaddingException         If the padding scheme is not available.
//     * @throws InvalidKeyException            If the secret key is invalid.
//     * @throws InvalidAlgorithmParameterException If the initialization vector is invalid.
//     * @throws IllegalBlockSizeException      If the block size is invalid.
//     * @throws BadPaddingException            If the padding is invalid.
//     */
//    private static byte[] decryptMessage(byte[] encryptedMessage, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        byte[] iv = new byte[cipher.getBlockSize()];
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
//        return cipher.doFinal(encryptedMessage);
//    }
//
//    // Private helper methods
//
//    /**
//     * Converts an array of bytes to a binary string representation.
//     *
//     * @param bytes The array of bytes to convert.
//     * @return The binary string representation of the bytes.
//     */
//    private static String convertBytesToBinary(byte[] bytes) {
//        StringBuilder binary = new StringBuilder();
//        for (byte b : bytes) {
//            String binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
//            binary.append(binaryByte);
//        }
//        return binary.toString();
//    }
//
//    /**
//     * Converts a binary string representation to an array of bytes.
//     *
//     * @param binary The binary string to convert.
//     * @return The array of bytes.
//     */
//    private static byte[] convertBinaryToBytes(String binary) {
//        int length = binary.length();
//        byte[] bytes = new byte[length / 8];
//        for (int i = 0; i < length; i += 8) {
//            String binaryByte = binary.substring(i, i + 8);
//            bytes[i / 8] = (byte) Integer.parseInt(binaryByte, 2);
//        }
//        return bytes;
//    }
//
//    /**
//     * Modifies the least significant bit of a pixel value based on a binary bit.
//     *
//     * @param rgb The original pixel value.
//     * @param bit The binary bit to embed.
//     * @return The modified pixel value.
//     */
//    private static int modifyPixel(int rgb, char bit) {
//        int maskedRGB = rgb & 0xFFFFFFFE; // Mask out the least significant bit
//        int modifiedRGB;
//        if (bit == '1') {
//            modifiedRGB = maskedRGB | 0x00000001; // Set the least significant bit to 1
//        } else {
//            modifiedRGB = maskedRGB; // Set the least significant bit to 0
//        }
//        return modifiedRGB;
//    }
//
//    /**
//     * Extracts the least significant bit from a pixel value.
//     *
//     * @param rgb The pixel value.
//     * @return The extracted binary bit.
//     */
//    private static char extractBit(int rgb) {
//        int bit = rgb & 0x00000001; // Extract the least significant bit
//        return (char) (bit + '0');
//    }
//
//    /**
//     * Main method to demonstrate the usage of the ImageEncryption class.
//     *
//     * @param args The command-line arguments.
//     */
//    public static void main(String[] args) {
//        try {
//            String imagePath = "carrier_image.png";
//            String message = "Secret message to be encrypted";
//            String secretKey = "MySecretKey";
//            encryptMessageIntoImage(imagePath, message, secretKey);
//
//            String decryptedMessage = decryptMessageFromImage("encrypted_image.png", secretKey);
//            System.out.println("Decrypted Message: " + decryptedMessage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
