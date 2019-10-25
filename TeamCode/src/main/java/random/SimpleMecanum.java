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

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);

        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    static double absmax(double... ds) {
        double max = Double.MIN_VALUE;

        for (double d : ds) {
            final double x = Math.abs(d);
            if (x > max) {
                max = x;
            }
        }

        return max;
    }

    @Override
    public void loop() {
        final double x = gamepad1.left_stick_x;
        final double y = gamepad1.left_stick_y;
        final double r = gamepad1.right_stick_x;

        final double direction = Math.atan2(y, x);
        final double speed = Math.sqrt(x * x + y * y);

        final double sin = Math.sin(direction - Math.PI / 4.0);
        final double cos = Math.cos(direction - Math.PI / 4.0);

        final double lfp = speed * sin + r;
        final double lbp = speed * cos + r;
        final double rfp = speed * cos - r;
        final double rbp = speed * sin - r;

        final double scale = absmax(1.0, lfp, lbp, rfp, rbp);

        lf.setPower(lfp / scale);
        lb.setPower(lbp / scale);
        rf.setPower(rfp / scale);
        rb.setPower(rbp / scale);

    }
}
