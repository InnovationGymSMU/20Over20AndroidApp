package edu.SMU.PingItApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class AboutPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        ImageView gymImageView = (ImageView) findViewById(R.id.innovation_gym_image_view);
        gymImageView.setOnClickListener(new ImageURLListener("http://smugym.com"));

        ImageView hackathonImageView = (ImageView) findViewById(R.id.hackathon_image_view);
        hackathonImageView.setOnClickListener(new ImageURLListener("http://the20over20.com"));

        ImageView smuLyleImageView = (ImageView) findViewById(R.id.smu_lyle_image_view);
        smuLyleImageView.setOnClickListener(new ImageURLListener("http://lyle.smu.edu"));

        ActionBarHelper.setLeftActionBar(this, R.drawable.navigation_back);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    private class ImageURLListener implements View.OnClickListener {
        private String targetURL;

        ImageURLListener(String targetURL) {
            this.targetURL = targetURL;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(targetURL));
            startActivity(intent);
        }
    }
}
