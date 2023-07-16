package com.example.secretcoder.constants;

public class Constants {

    public static final int BYTE_SIZE = 8;
    public static final char ZERO_CHAR = '0';
    public static final char ONE_CHAR = '1';
    public static final char SPACE_CHAR = ' ';
    public static final char ZWNJ_CHAR_AS_ZERO = '\u200C';      // Zero Width Non-Joiner used as '0'
    public static final char ZWJ_CHAR_AS_ONE = '\u200D';        // Zero Width Joiner used as '1'
    public static final char ZERO_WIDTH_SPACE_CHAR = '\u200B';  // Zero Width Space Used as ' '
    public static final String AES_CBS_PKCS5_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES_ECB_PKCS5_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String IV = "SECRET1234567890";
    public static final String ENTER_MESSAGE = "Please enter a message";
    public static final String ENTER_SECRET = "Please enter a secret";
    public static final String ENTER_KEY = "Please enter a key";
    public static final String COPY_RESULT = "Please copy the result";
    public static final String AES = "AES";
    public static final int KEY_LENGTH16 = 16;
    public static final int KEY_LENGTH24 = 24;
    public static final int KEY_LENGTH32 = 32;
    public static final int HIGH_END_RANDOM = 122;
    public static final int LOW_END_RANDOM = 33;
}
