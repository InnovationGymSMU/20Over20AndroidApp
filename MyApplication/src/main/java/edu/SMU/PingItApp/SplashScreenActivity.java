package edu.SMU.PingItApp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {

    int loopCounter = 1;
    ImageView appLogo;
    Drawable title1;
    Drawable title2;
    Drawable title3;
    Drawable title4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appLogo = (ImageView) findViewById(R.id.image_display);
        title1 = getResources().getDrawable(R.drawable.title_1);
        title2 = getResources().getDrawable(R.drawable.title_2);
        title3 = getResources().getDrawable(R.drawable.title_3);
        title4 = getResources().getDrawable(R.drawable.title_4);

        while(loopCounter <= 4)
        {
            switch (loopCounter)
            {
                case 1:
                    appLogo.setImageDrawable(title1);
                    break;
                case 2:
                    appLogo.setImageDrawable(title2);
                    break;
                case 3:
                    appLogo.setImageDrawable(title3);
                    break;
                case 4:
                    appLogo.setImageDrawable(title4);
                    break;
                default:
                    appLogo.setImageDrawable(title4);
                    break;
            }
            loopCounter++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        goToMainScreen();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    public void goToMainScreen() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);

        finish();
    }
}
