package tearsforgears

import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.Telemetry

class Robot (hardwareMap: HardwareMap, telemetry: Telemetry){
    val hmap = hardwareMap
    val tel = telemetry
    val leftMotor = hmap.dcMotor.get("left")
    val rightMotor = hmap.dcMotor.get("right")
    val liftMotor = hmap.dcMotor.get("lift")
    val dumpServo = hmap.servo.get("dump")

    val LIFT_MAX_SPEED = 0.1
    val SERVO_DUMP_POS = 0.25
    val SERVO_NODUMP_POS = 0.0

    init {
        leftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightMotor.direction = DcMotorSimple.Direction.REVERSE
        liftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        dumpServo.position = SERVO_NODUMP_POS
    }

    fun drive(left:Float, right:Float) {
        leftMotor.power = left.toDouble()
        rightMotor.power = right.toDouble()
    }

    fun lift(power:Number) {
        liftMotor.power = LIFT_MAX_SPEED * Math.pow(power.toDouble(), 3.0)
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
}