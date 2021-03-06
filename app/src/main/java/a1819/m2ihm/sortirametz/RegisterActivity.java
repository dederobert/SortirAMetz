package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.helpers.Logger;
import a1819.m2ihm.sortirametz.models.Gender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.img_register_user_avatar) ImageView img_register_user_avatar;
    @BindView(R.id.swt_register_gender) Switch swt_register_gender;
    @BindView(R.id.edt_register_username) EditText edt_username;
    @BindView(R.id.edt_register_email) EditText edt_email;
    @BindView(R.id.edt_register_password) EditText edt_password;
    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.link_register_login) TextView link_login;

    @OnClick(R.id.btn_register) void register() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_register.setEnabled(false);

        String username = edt_username.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        Gender gender = (swt_register_gender.isChecked())?Gender.MAN:Gender.WOMAN;


        if (Logger.INSTANCE.register(this, username, email, password, gender))
            onSignupSuccess();
        else
            onSignupFailed();

    }

    @OnClick(R.id.link_register_login) void done() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    private void onSignupSuccess() {
        btn_register.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.error_register), Toast.LENGTH_LONG).show();
        btn_register.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String username = edt_username.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            edt_username.setError(getResources().getString(R.string.error_username));
            valid = false;
        }else{
            edt_username.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError(getResources().getString(R.string.error_email));
            valid = false;
        }else{
            edt_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            edt_password.setError(getResources().getString(R.string.error_password));
            valid = false;
        }else{
            edt_password.setError(null);
        }

        return valid;
    }
}
