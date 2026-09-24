package org.firstinspires.ftc.teamcode.subsystems.turret

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo

class turretConfig(
    val turretPower: Double,
    val spinSpeed: Double
)

class Turret(
    private val config: turretConfig,
    private val servo: Servo
) {
    private val control = TurretControl(servo)
    fun aimAt(angle: Double) {
        control.aimAt(angle)
    }
    fun track(delta: Double) {
        control.track(delta)
    }
}