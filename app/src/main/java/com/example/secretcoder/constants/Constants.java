package com.example.secretcoder.constants;

public class Constants {

    public static final int BYTE_SIZE = 8;
    public static final char ZWNJ_CHAR_AS_ZERO = '\u200C';      // Zero Width Non-Joiner used as '0'
    public static final char ZWJ_CHAR_AS_ONE = '\u200D';        // Zero Width Joiner used as '1'
    public static final char ZERO_WIDTH_SPACE_CHAR = '\u200D';  // Zero Width Space Used as ' '
    public static final String AES_CBS_PKCS5_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES_ECB_PKCS5_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String IV = "SECRETIV1234";


    public static final String AES = "AES";
}
