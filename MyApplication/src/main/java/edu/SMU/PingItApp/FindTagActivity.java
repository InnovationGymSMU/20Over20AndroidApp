package edu.SMU.PingItApp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FindTagActivity extends Activity {

    boolean isPinging;
    Button pingButton;
    MediaPlayer mediaPlayer;
    ImageView pingImageView;
    ImageSwitcherTask task;
    String personalizedButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tag);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        isPinging = false;
        pingButton = (Button) findViewById(R.id.pingButton);
        mediaPlayer = new MediaPlayer();
        pingImageView = (ImageView) findViewById(R.id.pingImageView);

        personalizedButtonText = getString(R.string.start_pinging_button_text);
        Intent intent = getIntent();
        if (intent != null) {
            personalizedButtonText += (" for " + intent.getStringExtra("TagName"));
        }
        pingButton.setText(personalizedButtonText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.find_tag, menu);
        return true;
    }

    public void playMusic(View view)
    {
        if(isPinging == false)
        {
            isPinging = true;
            pingButton.setText(R.string.pinging_label_text);

            pingButton.setText(R.string.stop_pinging_button_text);

            task = new ImageSwitcherTask(this, pingImageView);
            task.execute(null);
            try
            {
                AssetFileDescriptor descriptor = getAssets().openFd("sax.mp3");
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                        descriptor.getLength());
                descriptor.close();
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else if(isPinging == true)
        {
            pingButton.setText(personalizedButtonText);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();

            task.cancel(true);
            pingImageView.setImageResource(R.drawable.ping_icon_1);

            isPinging = false;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
