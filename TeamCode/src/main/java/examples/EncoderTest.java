package examples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.Locale;

@TeleOp(name = "Encoder Test")
public class EncoderTest extends OpMode {
    private DcMotor lf, lb, rf, rb;
    private boolean previousBumpers = false;

    @Override
    public void init() {
        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
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
    }

    private void operateMotor(final DcMotor motor, final boolean state) {
        motor.setPower(state ? gamepad1.left_stick_y : 0.0);
    }

    @Override
    public void loop() {
        operateMotor(lf, gamepad1.x);
        operateMotor(lb, gamepad1.y);
        operateMotor(rf, gamepad1.a);
        operateMotor(rb, gamepad1.b);

        final boolean bumpers = gamepad1.left_bumper || gamepad1.right_bumper;
        if (bumpers && !previousBumpers) {
            lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        previousBumpers = bumpers;

        telemetry.addData("commands 1", "x: lf, y: lb, a: rf, b: rb");
        telemetry.addData("commands 2", "g1.left_stick_y: speed, bumpers: reset");
        telemetry.addData("gamepad1", String.format(Locale.US, "lx %.2f ly %.2f", gamepad1.left_stick_x, gamepad1.left_stick_y));
        telemetry.addData("encoders", String.format(Locale.US, "lf %d lb %d rf %d rb %d",
                lf.getCurrentPosition(), lb.getCurrentPosition(), rf.getCurrentPosition(), rb.getCurrentPosition()));
        telemetry.update();
    }
}
