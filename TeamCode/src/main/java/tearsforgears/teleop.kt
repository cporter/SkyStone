package tearsforgears

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Main Teleop")
class Teleop : OpMode() {
    lateinit var robot : Robot

    override fun init() {
        robot = Robot(hardwareMap, telemetry)
    }

    override fun loop() {
        robot.drive(gamepad1.left_stick_y, gamepad1.right_stick_y)
        robot.dump(gamepad1.left_bumper || gamepad1.right_bumper)
        robot.lift(gamepad1.left_trigger - gamepad1.right_trigger)
    }
}