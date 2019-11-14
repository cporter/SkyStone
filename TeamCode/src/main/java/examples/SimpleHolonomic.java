package examples;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import tearsforgears.ControllerState;

@TeleOp(name = "Simple Holonomic")
public class SimpleHolonomic extends OpMode {
    private DcMotor lf, rf, lb, rb;
    private BNO055IMU imu;
    private boolean previousX = false;
    private double forwardAngle;

    private void initIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    @Override
    public void init() {
        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);

        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        initIMU();
    }

    private static double scaleRotation(final double rotation, final double speed) {
        if (0.1 > Math.abs(speed)) {
            return rotation;
        } else {
            return rotation / 2.0;
        }
    }

    @Override
    public void start() {
        super.start();
        final Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        forwardAngle = angles.firstAngle;
    }

    private void drive() {
        final Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

        if (gamepad1.x && ! previousX) {
            forwardAngle = angles.firstAngle;
        }
        previousX = gamepad1.x;


        final double x = gamepad1.left_stick_x;
        final double y = gamepad1.left_stick_y;

        final double direction = Math.atan2(y, x) - (forwardAngle - angles.firstAngle);
        final double speed = Math.sqrt(x * x + y * y);

        final double r = scaleRotation(gamepad1.right_stick_x, speed);

        final double sin_theta = Math.sin(direction - Math.PI / 4.0);
        final double cos_theta = Math.cos(direction - Math.PI / 4.0);

        final double lfp = speed * sin_theta + r;
        final double lbp = speed * cos_theta + r;
        final double rfp = speed * cos_theta - r;
        final double rbp = speed * sin_theta - r;

        lf.setPower(lfp);
        lb.setPower(lbp);
        rf.setPower(rfp);
        rb.setPower(rbp);

        telemetry.addData("Orientation", Math.toDegrees(angles.firstAngle));
        telemetry.addData("Forward Angle", Math.toDegrees(forwardAngle));
        telemetry.addData("Direction", Math.toDegrees(direction));
    }

    @Override
    public void loop() {
        drive();
        telemetry.update();
    }
}
