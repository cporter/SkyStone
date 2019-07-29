package tearsforgears

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorController
import com.qualcomm.robotcore.hardware.DcMotorControllerEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry

class Robot (hardwareMap: HardwareMap, telemetry: Telemetry){
    val hmap = hardwareMap
    val tel = telemetry
    val leftMotor = hmap.dcMotor.get("left")
    val rightMotor = hmap.dcMotor.get("right")
    val liftMotor = hmap.dcMotor.get("lift")
    val dumpServo = hmap.servo.get("dump")

    val SERVO_DUMP_POS = 1.0
    val SERVO_NODUMP_POS = 0.0



    // todo: figure out what these values should be
    val LIFT_LEVELS = intArrayOf(0, 100, 200, 300)
    val MAX_LIFT_LEVEL = LIFT_LEVELS.size - 1
    var liftLevel = 0

    init {
        leftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        liftMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        liftMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        dumpServo.position = SERVO_NODUMP_POS
    }

    fun drive(left:Float, right:Float) {
        leftMotor.power = left.toDouble()
        rightMotor.power = right.toDouble()
    }

    fun liftUp() {
        if (liftLevel < MAX_LIFT_LEVEL) {
            liftMotor.targetPosition = LIFT_LEVELS[++liftLevel]
        }
    }

    fun liftDown() {
        if (liftLevel > 0) {
            liftMotor.targetPosition = LIFT_LEVELS[--liftLevel]
        }
    }

    fun setLiftPosition(level : Int) {
        if (0 <= level && level <= MAX_LIFT_LEVEL) {
            liftLevel = level
            liftMotor.targetPosition = LIFT_LEVELS[liftLevel]
        }
    }

    fun dump(dumped : Boolean) {
        if (dumped) {
            if (SERVO_DUMP_POS != dumpServo.position) {
                dumpServo.position = SERVO_DUMP_POS
            }
        } else {
            if (SERVO_NODUMP_POS != dumpServo.position) {
                dumpServo.position = SERVO_NODUMP_POS
            }
        }
    }

    fun setDriveRunmodes(mode : DcMotor.RunMode) {
        leftMotor.mode = mode
        rightMotor.mode = mode
    }

    fun setTargetPositions(leftPos : Int, rightPos : Int) {
        leftMotor.targetPosition = leftPos
        rightMotor.targetPosition = rightPos
    }

    fun currentDriveEncoderPositions() : Pair<Int, Int> {
        return Pair(leftMotor.currentPosition, rightMotor.currentPosition)
    }
}