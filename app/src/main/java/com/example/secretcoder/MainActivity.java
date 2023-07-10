package com.example.secretcoder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private final BinaryEncoder binaryEncoder = new BinaryEncoder();
    private final BinaryDecoder binaryDecoder = new BinaryDecoder();
    private TextInputEditText main_ETXT_message, main_ETXT_secret, main_ETXT_key;
    private MaterialTextView main_ETXT_output;
    private MaterialButton main_BTN_encode, main_BTN_decode;
    private SwitchMaterial main_SWT_decode, main_SWT_ECB, main_SWT_CBC;
    private Toast toaster;

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
        main_ETXT_output = findViewById(R.id.main_ETXT_output);
        main_BTN_encode = findViewById(R.id.main_BTN_encode);
        main_BTN_decode = findViewById(R.id.main_BTN_decode);
        main_SWT_decode = findViewById(R.id.main_SWT_decode);
        main_SWT_ECB = findViewById(R.id.main_SWT_ECB);
        main_SWT_CBC = findViewById(R.id.main_SWT_CBC);
    }

    private void initViews() {
        main_BTN_encode.setOnClickListener(v -> clickedEncode());
        main_BTN_decode.setOnClickListener(v -> clickedDecode());
        main_SWT_decode.setOnClickListener(v -> codeStateChange());
        main_SWT_ECB.setOnClickListener(v -> toggleCBCEncryption());
        main_SWT_CBC.setOnClickListener(v -> toggleECBEncryption());
    }



    private void clickedEncode() {
        main_ETXT_output.setText("");
        String message, secret, key, encryptionAlgorithm, result;
        if (TextUtils.isEmpty(main_ETXT_message.getText())) {
            showToast(Constants.ENTER_MESSAGE);
            return;
        }
        if (TextUtils.isEmpty(main_ETXT_secret.getText())) {
            showToast(Constants.ENTER_SECRET);
            return;
        }
        if (TextUtils.isEmpty(main_ETXT_key.getText())) {
            showToast(Constants.ENTER_KEY);
            return;
        }
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
        if (TextUtils.isEmpty(main_ETXT_message.getText())) {
            showToast(Constants.ENTER_MESSAGE);
            return;
        }
        if (TextUtils.isEmpty(main_ETXT_key.getText())) {
            showToast(Constants.ENTER_KEY);
            return;
        }
        message = main_ETXT_message.getText().toString();
        key = main_ETXT_key.getText().toString();

        // choose encryption algorithm by seeing which switch is checked
        encryptionAlgorithm = getEncryptionAlgorithm();

        result = binaryDecoder.decodeText(message, key, encryptionAlgorithm);
        main_ETXT_output.setText(result);
    }

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
        } else {
            main_BTN_encode.setVisibility(View.VISIBLE);
            main_BTN_decode.setVisibility(View.GONE);
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