package edu.SMU.PingItApp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by charlie on 9/21/13.
 */
public class AppLoadImageCycleTask extends AsyncTask <Object, Object, Object> {

    private int loopCounter;
    private ImageView image;
    private List<Drawable> images;
    private SplashScreenActivity activity;

    public AppLoadImageCycleTask(ImageView view, SplashScreenActivity activity){
        this.image = view;
        this.activity = activity;
        images = null;
        loopCounter = 0;
    }

    public void setAllDrawables(List<Drawable> allDrawables)
    {
        images = allDrawables;
    }
    @Override
    protected Object doInBackground(Object... params) {

        try {
            while(loopCounter <= images.size()) {
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

        if (loopCounter < images.size()) {
            image.setImageDrawable(images.get(loopCounter));
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
