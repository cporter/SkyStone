package examples;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class WebcamVuforia implements Closeable {
    public final static String VUFORIA_KEY = "AawXN4b/////AAABmTPVx6yN3ENeulVubP+TtbVt+AVfzrHOmyNOU/KwtcD9wQI+80L44cGM1t8YOB3GSNB8TfZuE3I7KwfSCHpNAubBMpBbn5LJTnbzy1wnC1M951HXG7wb0644ZZg7gX3cFZzoJgx2I0WOwrEfM5/udNz+3pHnwWk9cmhYCHg0J+4kd7W+nryVTPj8A8m+I4e5fZWRvxkmBirucgXjl69MPPKP6W/QwTRiOCcccGBpRd3ACGlvg7HVjxj/lwU6MEfYtHfiwF0lNepKfwTcQf0dFg9QbTUJKtWfNR1cbffaI1962i2Te/1llhwxYgr1VBw9snJMpNqtzQ8ZbWKtA77ya1sDmVC1BR+lfe8Ruk8yDDGq";

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    private WebcamName webcamName = null;
    private VuforiaLocalizer vuforia = null;

    private VuforiaTrackables targetsSkyStone;
    VuforiaTrackable stoneTarget;

    public void init(final HardwareMap hardwareMap) {
        /*
         * Retrieve the camera we are to use.
         */
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");

        targetsSkyStone.activate();
    }

    public VuforiaTrackable getSkystone() {
        return stoneTarget;
    }

    @Override
    public void close() throws IOException {
        targetsSkyStone.deactivate();
    }
}
