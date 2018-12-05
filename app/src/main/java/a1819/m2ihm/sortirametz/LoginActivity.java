package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.helpers.Logger;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SINGUP = 0;
    private static final int REQUEST_FINGERPRINT = 1;

    @BindView(R.id.edt_login_username) EditText edt_username;
    @BindView(R.id.edt_login_password) EditText edt_password;
    @BindView(R.id.btn_login_login) Button btn_login;

    @OnEditorAction(R.id.edt_login_password) boolean onEditorAction(int actionId, KeyEvent event) {
        if (actionId != 0 || event.getAction()==KeyEvent.ACTION_DOWN) {
            Log.d("LOGIN ACTIVITY","DONE clicked");
            login(btn_login);
        }
        return false;
    }
    @OnClick(R.id.btn_login_login) void login(Button button) {

        Log.d("LOGIN ACTIVITY","Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        button.setEnabled(false);

        String usernameEmail = edt_username.getText().toString();
        String password = edt_password.getText().toString();


        if (Logger.INSTANCE.login(this, usernameEmail, password)) onLoginSuccess();
        else onLoginFailed();
    }


    @OnClick(R.id.link_login_register) void register() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SINGUP);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (PreferencesHelper.INSTANCE.useFingerprint(this)) {
            Intent intent = new Intent(this, FingerPrintActivity.class);
            startActivityForResult(intent, REQUEST_FINGERPRINT);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SINGUP) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        } else if (requestCode == REQUEST_FINGERPRINT) {
            if (resultCode == RESULT_OK) {
                Logger.INSTANCE.loadUser(this);
                setResult(RESULT_OK);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean validate() {
        boolean valid = true;
        String usernameEmail = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        if (usernameEmail.isEmpty() || usernameEmail.length()  < 3) {
            edt_username.setError(getResources().getString(R.string.error_username));
            valid = false;
        }else {
            edt_username.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            edt_password.setError(getResources().getString(R.string.error_password));
            valid = false;
        }else{
            edt_password.setError(null);
        }

        return valid;
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.error_login), Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
    }

    private void onLoginSuccess() {
        btn_login.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }
}
