package com.example.secretcoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.secretcoder.binary.BinaryDecoder;
import com.example.secretcoder.binary.BinaryEncoder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText main_ETXT_message;
    private TextInputEditText main_ETXT_secret;
    private TextInputEditText main_ETXT_key;
    private MaterialButton main_BTN_encode;
    private MaterialButton main_BTN_decode;
    private SwitchMaterial main_SWT_decode;

    private BinaryEncoder binaryEncoder = new BinaryEncoder();
    private BinaryDecoder binaryDecoder = new BinaryDecoder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

    }

    private void findViews() {
        main_ETXT_message = findViewById(R.id.main_ETXT_message);
        main_ETXT_secret = findViewById(R.id.main_ETXT_secret);
        main_ETXT_key = findViewById(R.id.main_ETXT_key);
        main_BTN_encode = findViewById(R.id.main_BTN_encode);
        main_BTN_decode = findViewById(R.id.main_BTN_decode);
        main_SWT_decode = findViewById(R.id.main_SWT_decode);
    }

    private void initViews() {
        main_BTN_encode.setOnClickListener(v -> clickedEncode());
        main_BTN_decode.setOnClickListener(v -> clickedDecode());
        main_SWT_decode.setOnClickListener(v -> codeStateChange());
    }

    private void codeStateChange() {
        if (main_SWT_decode.isChecked()) {
            main_BTN_encode.setVisibility(View.GONE);
            main_BTN_decode.setVisibility(View.VISIBLE);
        } else {
            main_BTN_encode.setVisibility(View.VISIBLE);
            main_BTN_decode.setVisibility(View.GONE);
        }
    }

    private void clickedEncode() {
        String message, secret, key, encryptionAlgorithm, result;
        if (TextUtils.isEmpty(main_ETXT_message.getText())){

        }
        if (TextUtils.isEmpty(main_ETXT_secret.getText())){

        }
        if (TextUtils.isEmpty(main_ETXT_key.getText())){

        }
        message = main_ETXT_message.getText().toString();
        secret = main_ETXT_secret.getText().toString();
        key = main_ETXT_key.getText().toString();
        binaryEncoder.encodeText(message,  secret,  key,  encryptionAlgorithm);
    }

    private void clickedDecode() {

    }
}