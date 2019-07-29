package tearsforgears

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Main Teleop")
class Teleop : OpMode() {
    lateinit var robot : Robot
    lateinit var controllerState: ControllerState

    val TRIGGER_PRESS_THRESHOLD = 0.25f

    override fun init() {
        robot = Robot(hardwareMap, telemetry)
        controllerState = ControllerState(gamepad1)
    }

    override fun loop() {
        controllerState.update()
        robot.drive(gamepad1.left_stick_y, gamepad1.right_stick_y)
        robot.dump(TRIGGER_PRESS_THRESHOLD < gamepad1.left_trigger)
        if (controllerState.leftBumperOnce()) {
            robot.liftUp()
        } else if (controllerState.rightBumperOnce()) {
            robot.liftDown()
        }
    }
}