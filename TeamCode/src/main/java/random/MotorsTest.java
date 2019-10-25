package random;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import tearsforgears.ControllerState;

@TeleOp(name = "Motors Test")
public class MotorsTest extends OpMode {
    private ControllerState cs;
    private DcMotor lf, lb, rf, rb;

    private DcMotor active;

    @Override
    public void init() {
        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);

        cs = new ControllerState(gamepad1);

        active = lf;
    }

    @Override
    public void loop() {
        cs.update();

        if (cs.xOnce()) {
            active = lf;
        } else if (cs.yOnce()) {
            active = lb;
        } else if (cs.aOnce()) {
            active = rf;
        } else if (cs.bOnce()) {
            active = rb;
        }

        telemetry.addData("Switch", "x = lf, y = lb, a = rf, b = rb");
        telemetry.addData("motor", active.getDeviceName());
        telemetry.update();

        active.setPower(gamepad1.left_stick_y);
    }
}
