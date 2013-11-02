package edu.SMU.PingItApp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.AudioManager;

public class FindTagActivity extends Activity {

    private boolean isPinging;
    private Button pingButton;
    private MediaPlayer mediaPlayer;
    private ImageView pingImageView;
    private ImageSwitcherTask task;
    private String personalizedButtonText;
    private MenuItem flashlightButton;
    private AudioManager audio;
    private int maxVolume;
    private int currentVolume;
    private int ringerMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tag);

        //Override user's phone silent mode
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        ringerMode = audio.getRingerMode();

        ActionBarHelper.setLeftActionBar(this, R.drawable.navigation_back);

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.find_tag, menu);
        flashlightButton = menu.findItem(R.id.toggle_flashlight);
        return super.onCreateOptionsMenu(menu);
    }

    public void playMusic(View view) {
        if (isPinging == false) {
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, AudioManager.FLAG_PLAY_SOUND);

            isPinging = true;
            pingButton.setText(R.string.pinging_label_text);

            pingButton.setText(R.string.stop_pinging_button_text);

            task = new ImageSwitcherTask(pingImageView);
            task.execute(null);
            try {
                AssetFileDescriptor descriptor = getAssets().openFd("2khz_10sec_.mp3");
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                        descriptor.getLength());
                descriptor.close();
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isPinging == true) {
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_PLAY_SOUND);
            audio.setRingerMode(ringerMode);

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

    public void toggleFlashlight(MenuItem item) {
        FlashlightController.toggleFlashlight(flashlightButton, this);
    }
}
