package org.firstinspires.ftc.teamcode.subsystems.intake

import com.qualcomm.robotcore.hardware.DcMotorEx

class Intake(
    private val motor: DcMotorEx
) {
    fun activate(power: Double) {
        motor.power = power
    }
    fun reverse(power: Double) {
        motor.power = -power
    }
    fun stop() {
        motor.power = 0.0
    }
}