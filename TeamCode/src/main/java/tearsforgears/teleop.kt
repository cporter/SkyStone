package tearsforgears

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Main Teleop")
class Teleop : OpMode() {
    lateinit var robot : Robot
    lateinit var controller : ControllerState

    val SLO_MODE_MULTIPLIER = .2f
    var slow_mode = false

    override fun init() {
        robot = Robot(hardwareMap, telemetry)
        controller = ControllerState(gamepad1)
    }

    fun speed() : Float {
        if (slow_mode) {
            return SLO_MODE_MULTIPLIER
        } else {
            return 1.0f
        }
    }

    override fun loop() {
        controller.update()
        if (controller.aOnce()) {
            slow_mode = ! slow_mode
        }

        val x = speed()
        if (gamepad1.dpad_up) {
            robot.drive(SLO_MODE_MULTIPLIER, SLO_MODE_MULTIPLIER)
        } else if(gamepad1.dpad_down) {
            robot.drive(- SLO_MODE_MULTIPLIER, - SLO_MODE_MULTIPLIER)
        } else if (gamepad1.dpad_left) {
            robot.drive(- SLO_MODE_MULTIPLIER, SLO_MODE_MULTIPLIER)
        } else if (gamepad1.dpad_right) {
            robot.drive(SLO_MODE_MULTIPLIER, - SLO_MODE_MULTIPLIER)
        } else {
            robot.drive(x * gamepad1.left_stick_y, x * gamepad1.right_stick_y)
        }
        robot.dump(gamepad1.left_bumper || gamepad1.right_bumper)
        robot.lift(gamepad1.left_trigger / 2.0 - gamepad1.right_trigger)
    }
}