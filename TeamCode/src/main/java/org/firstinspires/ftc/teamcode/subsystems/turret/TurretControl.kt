package org.firstinspires.ftc.teamcode.subsystems.turret

import androidx.core.graphics.component1
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo


private fun getEncoderAngle(motor: DcMotorEx): Double {
    val ticksPerMotorRev = motor.motorType.ticksPerRev
    val turretGearRatio = 10.0

    return motor.currentPosition * 360.0 / (ticksPerMotorRev * turretGearRatio)
}



class TurretControl(
    private val servo: Servo
) {
    var targetAngle = 0.0
        private set

    val minAngle = -90.0
    val maxAngle = 90.0

    fun aimAt(angle: Double) {
        targetAngle = clamp(angle, minAngle, maxAngle)
        servo.position = angleToServoPosition(targetAngle)
    }

    fun track(error: Double) {
        val correction = error * 0.01

        targetAngle += correction

        if (targetAngle > maxAngle) {
            targetAngle = maxAngle
        }
        if (targetAngle < minAngle) {
            targetAngle = minAngle
        }

        servo.position = angleToServoPosition(targetAngle)
    }

    private fun angleToServoPosition(angle: Double): Double {
        return (angle - minAngle) / (maxAngle - minAngle)
    }

    private fun clamp(value: Double, min: Double, max: Double): Double {
        return if (value < min) {
            min
        } else if (value > max) {
            max
        } else {
            value
        }
    }
}