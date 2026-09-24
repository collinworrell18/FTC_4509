package org.firstinspires.ftc.teamcode.subsystems.shooter

import com.qualcomm.robotcore.hardware.DcMotorEx

class Index(
    private val flywheelMotor: DcMotorEx
) {
    fun shoot(power: Double) {
        flywheelMotor.power = power
    }
    fun stop() {
        flywheelMotor.power = 0.0
    }

    //shot detection
    private fun getRPM(): Double {
        val ticksPerRev = flywheelMotor.motorType.ticksPerRev

        return flywheelMotor.velocity * 60.0 / ticksPerRev
    }

    private var wasDropping = false
    var shotDetected = false
        private set

    fun update() {
        val rpm = getRPM()

        shotDetected = false

        val targetRPM = 1160
        if (rpm < targetRPM-500) {
            wasDropping = true
        }

        if (wasDropping && rpm > targetRPM-150) {
            wasDropping = false
            shotDetected = true
        }
    }
}