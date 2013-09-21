package edu.SMU.PingItApp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by charlie on 9/21/13.
 */
public class AppLoadImageCycleTask extends AsyncTask <Object, Object, Object> {

    private int loopCounter;
    ImageView image;
    Drawable title1;
    Drawable title2;
    Drawable title3;
    Drawable title4;
    SplashScreenActivity activity;

    public AppLoadImageCycleTask(ImageView view, SplashScreenActivity activity){
        this.image = view;
        this.activity = activity;
        loopCounter = 1;
    }

    public void setAllDrawables(Drawable title1, Drawable title2, Drawable title3, Drawable title4)
    {
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.title4 = title4;
    }
    @Override
    protected Object doInBackground(Object... params) {

        try {
            while(loopCounter <= 4) {
                Thread.sleep(500);
                publishProgress(null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Object... progress) {

        switch (loopCounter)
        {
            case 1:
                image.setImageDrawable(title1);
                break;
            case 2:
                image.setImageDrawable(title2);
                break;
            case 3:
                image.setImageDrawable(title3);
                break;
            case 4:
                image.setImageDrawable(title4);
                break;
            default:
                image.setImageDrawable(title4);
                break;
        }
        loopCounter++;
    }

    @Override
    protected void onPostExecute(Object result) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.goToMainScreen();
    }

}
