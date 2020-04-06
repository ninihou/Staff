package com.example.staff.member;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.staff.main.MainActivity;
import com.example.staff.task.CommonTask;
import com.example.staff.R;
import com.example.staff.main.Util;
import com.google.gson.JsonObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextView tvMessage;
    private CommonTask isMemberTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvMessage = findViewById(R.id.tvMessage);
        setResult(RESULT_CANCELED);
    }

    protected void onStart(){ //check
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE,
                MODE_PRIVATE);
        boolean login = preferences.getBoolean("login", false);
        if (login) {
            String staff_account = preferences.getString("staff_account", "");
            String staff_password = preferences.getString("staff_password", "");
            if (isMember(staff_account, staff_password)) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    public void onLoginClick(View view) {
        EditText etUser = findViewById(R.id.etUser);
        EditText etPassword = findViewById(R.id.etPassword);
        String staff_account = etUser.getText().toString().trim();
        String staff_password = etPassword.getText().toString().trim();
        if (staff_account.length() <= 0 || staff_password.length() <= 0) {
            showMessage(R.string.msg_InvalidUserOrPassword);
            return;
        }

        if (isMember(staff_account, staff_password)) {
            SharedPreferences preferences = getSharedPreferences(
                    Util.PREF_FILE, MODE_PRIVATE);
            preferences.edit().putBoolean("login", true)
                    .putString("staff_account", staff_account)
                    .putString("staff_password", staff_password).apply();
            setResult(RESULT_OK);
            finish();

        } else {
            showMessage(R.string.msg_InvalidUserOrPassword);
        }
    }

    public void onNewAccountClick(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private boolean isMember(final String staff_account, final String staff_password) {
        boolean isMember = false;
        if (Util.networkConnected(this)) {
            String url = Util.URL + "StaffServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "isMember");
            jsonObject.addProperty("staff_account", staff_account);
            jsonObject.addProperty("staff_password", staff_password);
            String jsonOut = jsonObject.toString();
            isMemberTask = new CommonTask(url, jsonOut);
            try {
                String result = isMemberTask.execute().get();
                isMember = Boolean.valueOf(result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                isMember = false;
            }
        } else {
            Util.showToast(this, R.string.msg_NoNetwork);
        }
        return isMember;
    }

    protected void onStop() {
        super.onStop();
        if (isMemberTask != null) {
            isMemberTask.cancel(true);
        }
    }
}
