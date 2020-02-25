package mx.com.giraud.giraudadmin.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mx.com.giraud.giraudadmin.Callbacks.LoginCallback;
import mx.com.giraud.giraudadmin.R;
import mx.com.giraud.giraudadmin.Responses.LoginResponse;
import mx.com.giraud.giraudadmin.SplashActivity;
import mx.com.giraud.giraudadmin.Utils.WebServices;

import static mx.com.giraud.giraudadmin.Utils.Utils.saveUser;

public class LoginActivity extends BaseActivity {

    private TextView tvLogin;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLogin = findViewById(R.id.tv_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        tvLogin.setOnClickListener(v -> WebServices.wsLogin(LoginActivity.this,
                etUsername.getText().toString(),
                etPassword.getText().toString(),
                true,
                new LoginCallback() {
                    @Override
                    public void baseResponse(LoginResponse loginResponse) {
                        saveUser(loginResponse.getData());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                        finish();
                    }

                    @Override
                    public void baseError() {

                    }
                }));


    }
}
