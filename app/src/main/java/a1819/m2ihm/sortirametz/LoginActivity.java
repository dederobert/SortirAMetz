package a1819.m2ihm.sortirametz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.edt_login_username) EditText username;
    @BindView(R.id.edt_login_password) EditText password;

    @OnClick(R.id.btn_login_login) void login() {

    }

    @OnClick(R.id.link_login_register) void register() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
}
