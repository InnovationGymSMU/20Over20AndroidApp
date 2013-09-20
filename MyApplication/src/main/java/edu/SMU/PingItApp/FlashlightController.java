package edu.SMU.PingItApp;

import android.hardware.Camera;
import android.view.MenuItem;

/**
 * Created by charlie on 9/20/13.
 */
public class FlashlightController {
    static boolean flashlightOn = false;
    static Camera camera;

    public static void toggleFlashlight(MenuItem flashlightButton){

    if(flashlightOn == false)
    {
        flashlightButton.setIcon(R.drawable.flashlight_black);
        camera = camera.open();
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
        flashlightOn = true;
    }
    else if(flashlightOn == true)
    {
        flashlightButton.setIcon(R.drawable.flashlight_white);
        camera.stopPreview();
        camera.release();
        flashlightOn = false;
    }
    }
}
