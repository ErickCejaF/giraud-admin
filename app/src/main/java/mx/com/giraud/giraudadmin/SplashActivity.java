package mx.com.giraud.giraudadmin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import mx.com.giraud.giraudadmin.Activities.BaseActivity;
import mx.com.giraud.giraudadmin.Activities.LoginActivity;
import mx.com.giraud.giraudadmin.Activities.MainActivity;

import static mx.com.giraud.giraudadmin.Utils.Utils.getSavedUser;

public class SplashActivity extends BaseActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSavedUser() != null) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
