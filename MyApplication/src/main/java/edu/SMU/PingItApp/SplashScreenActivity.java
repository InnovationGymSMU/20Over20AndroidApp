package edu.SMU.PingItApp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {

    private int loopCounter = 1;
    private static int SPLASH_TIME_OUT = 2000;
    ImageView appLogo;
    Drawable title1;
    Drawable title2;
    Drawable title3;
    Drawable title4;
    AppLoadImageCycleTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


    }

    @Override
    public void onStart() {
        super.onStart();

        appLogo = (ImageView) findViewById(R.id.splash_screen_image_view);
        title1 = getResources().getDrawable(R.drawable.title_1);
        title2 = getResources().getDrawable(R.drawable.title_2);
        title3 = getResources().getDrawable(R.drawable.title_3);
        title4 = getResources().getDrawable(R.drawable.title_4);

        task = new AppLoadImageCycleTask(appLogo, this);
        task.setAllDrawables(title1, title2, title3, title4);
        task.execute(null);
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
