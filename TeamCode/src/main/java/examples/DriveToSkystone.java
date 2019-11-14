package examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import java.io.IOException;
import java.util.Locale;

@TeleOp(name = "Drive to Skystone")
public class DriveToSkystone extends LinearOpMode {
    private final HolonomicDrivetrain dt = new HolonomicDrivetrain();
    private final WebcamVuforia webcam = new WebcamVuforia();

    @Override
    public void runOpMode() throws InterruptedException {
        dt.init(hardwareMap);
        webcam.init(hardwareMap);

        final VuforiaTrackable stone = webcam.getSkystone();

        waitForStart();

        while(opModeIsActive() && ! isStopRequested()) {
            final boolean visible = ((VuforiaTrackableDefaultListener)stone.getListener()).isVisible();
            telemetry.addData("Can see skystone?", visible ? "Y" : "N");

            if (visible) {
                final OpenGLMatrix stoneLoc = stone.getLocation();
                final VectorF trans = stoneLoc.getTranslation();
                final Orientation rot = Orientation.getOrientation(stoneLoc, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                telemetry.addData("translation",
                        String.format(Locale.US, "x: %.2f, y: %.2f, z: %.2f",
                                trans.get(0), trans.get(1), trans.get(2)));
                telemetry.addData("rotation", rot.thirdAngle);
            }

            telemetry.update();
        }

        try {
            webcam.close();
        } catch (IOException ioe) {
            // pass
        }
    }
}
