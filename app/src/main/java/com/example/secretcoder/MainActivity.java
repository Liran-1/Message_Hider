package com.example.secretcoder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.secretcoder.binary.BinaryDecoder;
import com.example.secretcoder.binary.BinaryEncoder;
import com.example.secretcoder.constants.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final BinaryEncoder binaryEncoder = new BinaryEncoder();
    private final BinaryDecoder binaryDecoder = new BinaryDecoder();
    private TextInputEditText main_ETXT_message, main_ETXT_secret, main_ETXT_key;
    private MaterialTextView main_ETXT_output;
    private MaterialButton main_BTN_genKey, main_BTN_encode, main_BTN_decode;
    private SwitchMaterial main_SWT_decode, main_SWT_ECB, main_SWT_CBC;
    private TextView main_LBL_encodeInstructions, main_LBL_decodeInstructions;
    private Toast toaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
    }

    private void findViews() {
        main_LBL_encodeInstructions = findViewById(R.id.main_LBL_encodeInstructions);
        main_LBL_decodeInstructions = findViewById(R.id.main_LBL_decodeInstructions);
        main_ETXT_message = findViewById(R.id.main_ETXT_message);
        main_ETXT_secret = findViewById(R.id.main_ETXT_secret);
        main_ETXT_key = findViewById(R.id.main_ETXT_key);
        main_BTN_genKey = findViewById(R.id.main_BTN_genKey);
        main_ETXT_output = findViewById(R.id.main_ETXT_output);
        main_BTN_encode = findViewById(R.id.main_BTN_encode);
        main_BTN_decode = findViewById(R.id.main_BTN_decode);
        main_SWT_decode = findViewById(R.id.main_SWT_decode);
        main_SWT_ECB = findViewById(R.id.main_SWT_ECB);
        main_SWT_CBC = findViewById(R.id.main_SWT_CBC);
    }

    private void initViews() {
        main_BTN_genKey.setOnClickListener(v -> clickedGenerateKey());
        main_BTN_encode.setOnClickListener(v -> clickedEncode());
        main_BTN_decode.setOnClickListener(v -> clickedDecode());
        main_SWT_decode.setOnClickListener(v -> codeStateChange());
        main_SWT_ECB.setOnClickListener(v -> toggleCBCEncryption());
        main_SWT_CBC.setOnClickListener(v -> toggleECBEncryption());
    }

    private void clickedGenerateKey() {
        StringBuilder generatedKey = new StringBuilder();
        int size = Constants.KEY_LENGTH16;
        for (int i = 0; i < size; i++) {
            Random rand = new Random();
            int randChar = rand.
                    nextInt(Constants.HIGH_END_RANDOM - Constants.LOW_END_RANDOM + 1) + Constants.LOW_END_RANDOM;
            generatedKey.append((char) randChar);
        }
        main_ETXT_key.setText(generatedKey.toString());
    }


    /////////////////////ACTIONS//////////////////////////
    private void clickedEncode() {
        main_ETXT_output.setText("");
        String message, secret, key, encryptionAlgorithm, result;
        if (isEncodeInputsInvalid()) return;
        message = main_ETXT_message.getText().toString();
        secret = main_ETXT_secret.getText().toString();
        key = main_ETXT_key.getText().toString();

        // choose encryption algorithm by seeing which switch is checked
        encryptionAlgorithm = getEncryptionAlgorithm();

        result = binaryEncoder.encodeText(message, secret, key, encryptionAlgorithm);
        main_ETXT_secret.setText("");
        main_ETXT_output.setText(result);
        showToast(Constants.COPY_RESULT);
    }

    private void clickedDecode() {
        main_ETXT_output.setText("");
        String message, key, encryptionAlgorithm, result;
        if (isDecodeInputsInvalid()) return;
        message = main_ETXT_message.getText().toString();
        key = main_ETXT_key.getText().toString();

        // choose encryption algorithm by seeing which switch is checked
        encryptionAlgorithm = getEncryptionAlgorithm();

        result = binaryDecoder.decodeText(message, key, encryptionAlgorithm);
        main_ETXT_output.setText(result);
    }

    /////////////////////CHECKS//////////////////////////
    private boolean isEncodeInputsInvalid() {
        if (TextUtils.isEmpty(main_ETXT_message.getText())) {
            showToast(Constants.ENTER_MESSAGE);
            return true;
        }
        if (TextUtils.isEmpty(main_ETXT_secret.getText())) {
            showToast(Constants.ENTER_SECRET);
            return true;
        }
        if (TextUtils.isEmpty(main_ETXT_key.getText())) {
            showToast(Constants.ENTER_KEY);
            return true;
        }
        if (isKeyInputInvalid(main_ETXT_key)) {
            showToast("Key length must be: " +
                    Constants.KEY_LENGTH16 + "\\" +
                    Constants.KEY_LENGTH24 + "\\" +
                    Constants.KEY_LENGTH32);
        }
        String input = main_ETXT_message.getText().toString();
        if (input.length() < 2)
            return true;
        return false;
    }

    private boolean isKeyInputInvalid(TextInputEditText textInputEditText) {
        String key = textInputEditText.getText().toString();
        int keyLength = key.length();
        if (keyLength != Constants.KEY_LENGTH16 &&
                keyLength != Constants.KEY_LENGTH24 &&
                keyLength != Constants.KEY_LENGTH32) {

            return true;
        }
        return false;
    }

    private boolean isDecodeInputsInvalid() {
        if (TextUtils.isEmpty(main_ETXT_message.getText())) {
            showToast(Constants.ENTER_MESSAGE);
            return true;
        }
        if (TextUtils.isEmpty(main_ETXT_key.getText())) {
            showToast(Constants.ENTER_KEY);
            return true;
        }
        return false;
    }

    ////////////////////UI UPDATES///////////////////////////

    private void toggleCBCEncryption() {
        main_SWT_CBC.setChecked(!main_SWT_CBC.isChecked());
    }

    private void toggleECBEncryption() {
        main_SWT_ECB.setChecked(!main_SWT_ECB.isChecked());
    }

    private void codeStateChange() {
        if (main_SWT_decode.isChecked()) {
            main_BTN_encode.setVisibility(View.GONE);
            main_BTN_decode.setVisibility(View.VISIBLE);
            main_ETXT_secret.setVisibility(View.GONE);
            main_BTN_genKey.setVisibility(View.GONE);
            main_LBL_encodeInstructions.setVisibility(View.GONE);
            main_LBL_decodeInstructions.setVisibility(View.VISIBLE);
        } else {
            main_BTN_encode.setVisibility(View.VISIBLE);
            main_BTN_decode.setVisibility(View.GONE);
            main_ETXT_secret.setVisibility(View.VISIBLE);
            main_BTN_genKey.setVisibility(View.VISIBLE);
            main_LBL_encodeInstructions.setVisibility(View.VISIBLE);
            main_LBL_decodeInstructions.setVisibility(View.GONE);
            main_ETXT_output.setText("");
        }
    }

    @NonNull
    private String getEncryptionAlgorithm() {
        if (main_SWT_CBC.isChecked()) {
            return Constants.AES_CBS_PKCS5_ALGORITHM;
        } else if (main_SWT_CBC.isChecked())
            return Constants.AES_ECB_PKCS5_ALGORITHM;
        else
            return Constants.AES_CBS_PKCS5_ALGORITHM;
    }

    private void showToast(String message) {
        if (toaster != null)
            toaster.cancel();
        toaster = Toast
                .makeText(this, message, Toast.LENGTH_SHORT);
        toaster.show();
    }
}