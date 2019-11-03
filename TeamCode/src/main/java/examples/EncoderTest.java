package examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.Locale;

@TeleOp(name = "Encoder Test")
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final DcMotor
                lf = hardwareMap.dcMotor.get("lf"),
                lb = hardwareMap.dcMotor.get("lb"),
                rf = hardwareMap.dcMotor.get("rf"),
                rb = hardwareMap.dcMotor.get("rb");

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {


            if (gamepad1.dpad_down) {
                lf.setPower(- 1.0);
                rf.setPower(- 1.0);
                rb.setPower(- 1.0);
                lb.setPower(- 1.0);
            } else if (gamepad1.dpad_up) {
                lf.setPower(1.0);
                rf.setPower(1.0);
                rb.setPower(1.0);
                lb.setPower(1.0);
            } else if (gamepad1.dpad_left) {
                lf.setPower(-1.0);
                lb.setPower(-1.0);
                rb.setPower(1.0);
                rf.setPower(1.0);
            } else if (gamepad1.dpad_right) {
                lf.setPower(1.0);
                lb.setPower(1.0);
                rb.setPower(- 1.0);
                rf.setPower(- 1.0);
            } else {
                lf.setPower(0.0);
                rf.setPower(0.0);
                rb.setPower(0.0);
                lb.setPower(0.0);
            }

            if (gamepad1.x) {
                lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            telemetry.addData("gamepad1", String.format(Locale.US, "lx %.2f ly %.2f", gamepad1.left_stick_x, gamepad1.left_stick_y));
            telemetry.addData("encoders", String.format(Locale.US, "lf %d lb %d rf %d rb %d",
                    lf.getCurrentPosition(), lb.getCurrentPosition(), rf.getCurrentPosition(), rb.getCurrentPosition()));
            telemetry.update();
        }
    }
}
