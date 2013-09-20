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

    public ImageSwitcherTask(Context context) {
        this.context = context;

        image = (ImageView)((Activity) context).findViewById(R.id.item_color);
    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            Thread.sleep(1000);
            publishProgress(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Object... progress) {
        //Do the image transition
    }

    @Override
    protected void onPostExecute(Object result) {
        //Set the image view back to the original view
    }

}
