package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.helpers.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SINGUP = 0;

    @BindView(R.id.edt_login_username) EditText edt_username;
    @BindView(R.id.edt_login_password) EditText edt_password;
    @BindView(R.id.btn_login_login) Button btn_login;

    @OnClick(R.id.btn_login_login) void login(Button button) {

        if (!validate()) {
            onLoginFailed();
            return;
        }
        button.setEnabled(false);

        String usernameEmail = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        if (Logger.INSTANCE.login(new DataBase(this), usernameEmail, password)) onLoginSuccess();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SINGUP){
            if (resultCode == RESULT_OK) {
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
