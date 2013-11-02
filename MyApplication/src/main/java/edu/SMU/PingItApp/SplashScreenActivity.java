package edu.SMU.PingItApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends Activity {

    public static final String PREFS_NAME = "PingItPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView appLogo = (ImageView) findViewById(R.id.splash_screen_image_view);
        Drawable title1 = getResources().getDrawable(R.drawable.title_1);
        Drawable title2 = getResources().getDrawable(R.drawable.title_2);
        Drawable title3 = getResources().getDrawable(R.drawable.title_3);
        Drawable title4 = getResources().getDrawable(R.drawable.title_4);

        AppLoadImageCycleTask task = new AppLoadImageCycleTask(appLogo, this);
        List<Drawable> allImages = Arrays.asList(title1, title2, title3, title4);
        task.setAllDrawables(allImages);
        task.execute(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    public void goToMainScreen() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean firstLaunch = settings.getBoolean("firstLaunch", true);
        if(firstLaunch == true)
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstLaunch", false);
            editor.commit();

            Intent intent = new Intent(this, InitialRegisterActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, MainScreenActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
