package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoundTagActivity extends Activity {

    private Button submitButton;
    private EditText serialSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_tag);

        ActionBarHelper.setLeftActionBar(this, R.drawable.navigation_back);

        submitButton = (Button) findViewById(R.id.submit_lost_tag);
        submitButton.setEnabled(false);
        submitButton.setBackgroundResource(R.drawable.custom_inactive_button_design);

        serialSubmit = (EditText) findViewById(R.id.serial_number_edit);
        serialSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (serialSubmit.getText().toString().equals("")) {
                    submitButton.setEnabled(false);
                    submitButton.setBackgroundResource(R.drawable.custom_inactive_button_design);
                } else {
                    submitButton.setEnabled(true);
                    submitButton.setBackgroundResource(R.drawable.custom_button_design);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.found_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    public void goBackToMainPage(View view) {
        Toast.makeText(this, "Thank you for reporting. You kind soul!", Toast.LENGTH_LONG).show();
        finish();
    }
}
