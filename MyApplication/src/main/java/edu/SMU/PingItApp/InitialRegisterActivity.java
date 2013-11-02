package edu.SMU.PingItApp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class InitialRegisterActivity extends Activity {


    // Values for email and password at the time of the login attempt.
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                usernameFilled = (editable.length() != 0);
                checkIfFieldsAreFilled();
            }
        });

        emailView = (EditText) findViewById(R.id.email);
        emailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                emailFilled = (editable.length() != 0);
                checkIfFieldsAreFilled();
            }
        });
    }

    private void checkIfFieldsAreFilled() {
        if (usernameFilled && emailFilled) {
            registerButton.setEnabled(true);
            registerButton.setBackgroundResource(R.drawable.custom_button_design);
        } else {
            registerButton.setEnabled(false);
            registerButton.setBackgroundResource(R.drawable.custom_inactive_button_design);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.initial_register, menu);
        return true;
    }

    public void goToMainPage(View view) throws IOException {
        if (view.getId() == R.id.skip_button) {
            Intent intent = new Intent(this, MainScreenActivity.class);
            startActivity(intent);
        } else {
            String username = usernameView.getText().toString();
            String email = emailView.getText().toString();
            username = URLEncoder.encode(username, "UTF-8");
            email = URLEncoder.encode(email, "UTF-8");
            new RegistrationThread(this).execute(username, email);
        }
    }

    private class RegistrationThread extends AsyncTask<String, Object, String[]> {
        private static final String tag = "RegistrationThread";
        private InitialRegisterActivity activity;
        public RegistrationThread(InitialRegisterActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String name = params[0];
            String email = params[1];
            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(new HttpGet("http://pingit.smugym.com/registration.php?action=register&name=" +
                        name + "&email=" + email));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK)
                {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();
                    Log.d(tag, responseString);

                    return responseString.split(" ");
                }
                else {
                    response.getEntity().getContent().close();
                }
            } catch (IOException e ) {
                Log.e(tag, "Could not make the request", e);
            }
            return null;
        }


        protected void onPostExecute(String[] result) {
            if (result != null && result.length == 2) {
                if ("Found".equals(result[1])) {
                    Toast.makeText(activity, "You are already in our system!", Toast.LENGTH_LONG).show();
                }
                SharedPreferences preferences = activity.getSharedPreferences(SplashScreenActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("GlobalUserID", Integer.parseInt(result[0].trim()));
                editor.commit();
                Log.d(tag, "New global user ID: " + preferences.getInt("GlobalUserID", -1));
            }
            Intent intent = new Intent(activity, MainScreenActivity.class);
            startActivity(intent);
        }
    }
}
