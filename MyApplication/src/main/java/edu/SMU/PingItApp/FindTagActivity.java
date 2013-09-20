package edu.SMU.PingItApp;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class FindTagActivity extends Activity {

    boolean isPinging;
    Button pingButton;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tag);

        isPinging = false;
        pingButton = (Button) findViewById(R.id.pingButton);
        mediaPlayer = new MediaPlayer();
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
            pingButton.setText("Stop Pinging");

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
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();

            isPinging = false;
            pingButton.setText("Start Pinging");
        }
    }
    
}
