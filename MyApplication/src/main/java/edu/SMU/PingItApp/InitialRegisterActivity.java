package edu.SMU.PingItApp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class InitialRegisterActivity extends Activity {
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello",
            "bar@example.com:world"
    };

    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";


    // Values for email and password at the time of the login attempt.
    private String username;
    private String email;
    private boolean usernameFilled;
    private boolean emailFilled;

    // UI references.
    private EditText usernameView;
    private EditText emailView;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_initial_register);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.white_logo);

        usernameFilled = false;
        emailFilled = false;

        registerButton = (Button) findViewById(R.id.sign_in_button);
        registerButton.setEnabled(false);
        registerButton.setBackgroundResource(R.drawable.custom_inactive_button_design);

        usernameView = (EditText) findViewById(R.id.username);
        usernameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String temp = usernameView.getText().toString();
                if (temp.matches("")) {
                    usernameFilled = false;
                    registerButton.setEnabled(false);
                    registerButton.setBackgroundResource(R.drawable.custom_inactive_button_design);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(usernameFilled == true && emailFilled == true)
                {
                    registerButton.setEnabled(true);
                    registerButton.setBackgroundResource(R.drawable.custom_button_design);
                }
                usernameFilled = true;
            }
        });

        emailView = (EditText) findViewById(R.id.email);
        emailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String temp = emailView.getText().toString();
                if (temp.matches("")) {
                    emailFilled = false;
                    registerButton.setEnabled(false);
                    registerButton.setBackgroundResource(R.drawable.custom_inactive_button_design);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(usernameFilled == true && emailFilled == true)
                {
                    registerButton.setEnabled(true);
                    registerButton.setBackgroundResource(R.drawable.custom_button_design);
                }
                emailFilled = true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.initial_register, menu);
        return true;
    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

}
