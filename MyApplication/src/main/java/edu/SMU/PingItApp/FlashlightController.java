package edu.SMU.PingItApp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by charlie on 9/20/13.
 */
public class FlashlightController {
    static boolean flashlightOn = false;
    static Camera camera;

    public static void toggleFlashlight(MenuItem flashlightButton, Context context) {

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) == true) {
            if (flashlightOn == false) {
                flashlightButton.setIcon(R.drawable.flashlight_black);
                camera = camera.open();
                Camera.Parameters p = camera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
                camera.startPreview();
                flashlightOn = true;
            } else if (flashlightOn == true) {
                flashlightButton.setIcon(R.drawable.flashlight_white);
                camera.stopPreview();
                camera.release();
                flashlightOn = false;
            }
        } else {
            Toast.makeText(context, "Device doesn't support flashlight.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
