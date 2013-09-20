package edu.SMU.PingItApp;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tag);

        isPinging = false;
        pingButton = (Button) findViewById(R.id.pingButton);
        mediaPlayer = new MediaPlayer();
        pingImageView = (ImageView) findViewById(R.id.pingImageView);
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

            ((TextView)findViewById(R.id.find_item_title_view)).setText(R.string.pinging_label_text);
            ((Button)findViewById(R.id.pingButton)).setText(R.string.stop_pinging_button_text);

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

            ((TextView)findViewById(R.id.find_item_title_view)).setText(R.string.not_pinging_label_text);
            ((Button)findViewById(R.id.pingButton)).setText(R.string.start_pinging_button_text);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();

            task.cancel(true);
            pingImageView.setImageResource(R.drawable.ping_icon_1);

            isPinging = false;
            pingButton.setText("Start Pinging");
        }


    }
    
}
