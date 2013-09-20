package edu.SMU.PingItApp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Chris on 9/20/13.
 */
public class ImageSwitcherTask extends AsyncTask<Object, Object, Object> {

    private Context context;
    private ImageView image;
    private int currentImage;

    public ImageSwitcherTask(Context context, ImageView image) {
        this.context = context;
        currentImage = 2;
        this.image = image;
    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            while(true) {
                Thread.sleep(400);
                publishProgress(null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Object... progress) {
        if(currentImage == 6)
            currentImage = 1;

        switch (currentImage)
        {
            case 1:
                image.setImageResource(R.drawable.ping_icon_1);
                break;
            case 2:
                image.setImageResource(R.drawable.ping_icon_2);
                break;
            case 3:
                image.setImageResource(R.drawable.ping_icon_3);
                break;
            case 4:
                image.setImageResource(R.drawable.ping_icon_4);
                break;
            case 5:
                image.setImageResource(R.drawable.ping_icon_5);
                break;
            default:
                image.setImageResource(R.drawable.ping_icon_1);
                break;
        }
        currentImage++;
    }

    @Override
    protected void onPostExecute(Object result) {
        //Set the image view back to the original view
    }

}
