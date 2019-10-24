package random;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Simple Mecanum")
public class SimpleMecanum extends OpMode {
    private DcMotor lf, rf, lb, rb;

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
    }

    @Override
    public void loop() {
        final double x = gamepad1.left_stick_x;
        final double y = gamepad1.left_stick_y;
        final double r = gamepad1.right_stick_x;

        final double direction = Math.atan2(y, x);
        final double speed = Math.sqrt(x * x + y * y);

        final double sin = Math.sin(direction + Math.PI / 4.0);
        final double cos = Math.cos(direction + Math.PI / 4.0);

        lf.setPower(speed * sin + r);
        lb.setPower(speed * cos + r);
        rf.setPower(speed * cos - r);
        rb.setPower(speed * sin - r);

    }
}
