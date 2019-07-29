package tearsforgears

import com.qualcomm.robotcore.hardware.Gamepad

fun incOrZero(x : Int, cond : Boolean) : Int {
    if (cond) {
        return 1 + x
    } else {
        return 0
    }
}

class ControllerState(_gamepad : Gamepad) {
    val gamepad = _gamepad
    var a = 0
    var b = 0
    var x = 0
    var y = 0
    var leftBumper = 0
    var rightBumper = 0
    var dpadUp = 0
    var dpadDown = 0
    var dpadLeft = 0
    var dpadRight = 0

    fun update() {
        a = incOrZero(a, gamepad.a)
        b = incOrZero(b, gamepad.b)
        x = incOrZero(x, gamepad.x)
        y = incOrZero(y, gamepad.y)
        leftBumper = incOrZero(leftBumper, gamepad.left_bumper)
        rightBumper = incOrZero(rightBumper, gamepad.right_bumper)
        dpadDown = incOrZero(dpadDown, gamepad.dpad_down)
        dpadUp = incOrZero(dpadUp, gamepad.dpad_up)
        dpadLeft = incOrZero(dpadLeft, gamepad.dpad_left)
        dpadRight = incOrZero(dpadRight, gamepad.dpad_right)
    }

    fun aOnce() : Boolean { return 1 == a }
    fun bOnce() : Boolean { return 1 == b }
    fun xOnce() : Boolean { return 1 == x }
    fun yOnce() : Boolean { return 1 == y }
    fun leftBumperOnce() : Boolean { return 1 == leftBumper }
    fun rightBumperOnce() : Boolean { return 1 == rightBumper }
    fun dpadUpOnce() : Boolean { return 1 == dpadUp }
    fun dpadDownOnce() : Boolean { return 1 == dpadDown }
    fun dpadLeftOnce() : Boolean { return 1 == dpadLeft }
    fun dpadRightOnce() : Boolean { return 1 == dpadRight }
}