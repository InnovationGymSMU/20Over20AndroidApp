package edu.SMU.PingItApp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Chris on 11/3/13.
 */
public class GETRequestTask extends AsyncTask<String, Object, String> {
    private static final String tag = "GETRequestTask";
    private String targetURL;
    private GETRequestResponseHandler handler;

    public GETRequestTask(GETRequestResponseHandler handler) {
        this.handler = handler;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public void setRequestParams(Map<String, String> params) {
        targetURL += "?";
        for (Map.Entry<String, String> pair : params.entrySet()) {
            targetURL += (pair.getKey() + "=" + pair.getValue() + "&");
        }
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(new HttpGet(targetURL));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                Log.d(tag, responseString);

                return responseString;
            }
            else {
                response.getEntity().getContent().close();
                Log.e(tag, "Response was: " + statusLine.getStatusCode());
            }
        } catch (IOException e ) {
            Log.e(tag, "Could not make the request", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        handler.handleResponse(result);
    }

    public interface GETRequestResponseHandler {
        public void handleResponse(String response);
    }
}
