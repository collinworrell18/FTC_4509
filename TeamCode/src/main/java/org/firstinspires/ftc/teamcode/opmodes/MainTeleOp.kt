package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake
import org.firstinspires.ftc.teamcode.subsystems.shooter.Index
import org.firstinspires.ftc.teamcode.subsystems.turret.Turret
import org.firstinspires.ftc.teamcode.subsystems.turret.turretConfig

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.teamcode.vision.limelightClass

@TeleOp(name="Main TeleOp")
class MainTeleOp : LinearOpMode() {
    private lateinit var flywheelMotor: DcMotorEx
    private lateinit var intakeMotor: DcMotorEx

    private lateinit var turret: Turret
    private lateinit var intake: Intake

    private lateinit var frontLeft: DcMotorEx
    private lateinit var frontRight: DcMotorEx
    private lateinit var backLeft: DcMotorEx
    private lateinit var backRight: DcMotorEx

    private lateinit var turretServo: Servo

    private lateinit var limelight: Limelight3A
    private lateinit var limelightFile: limelightClass

    private fun initializeHardware() {
        flywheelMotor = hardwareMap.get("flywheel") as DcMotorEx
        intakeMotor = hardwareMap.get("intake") as DcMotorEx

        frontLeft = hardwareMap.get("frontLeft") as DcMotorEx
        frontRight = hardwareMap.get("frontRight") as DcMotorEx
        backLeft = hardwareMap.get("backLeft") as DcMotorEx
        backRight = hardwareMap.get("backRight") as DcMotorEx

        limelight = hardwareMap.get("limelight") as Limelight3A
    }

    private fun initializeSubsystems() {
        turret = Turret(config = turretConfig(1.0, 1.0), turretServo)
        intake = Intake(intakeMotor)
        limelightFile = limelightClass(limelight)
    }

    private fun updateSubsystems() {
        limelightFile.update()

        if (limelightFile.targetVisible) {
            turret.track(limelightFile.targetX)
        }
    }

    override fun runOpMode() {
        initializeHardware()
        initializeSubsystems()

        waitForStart()

        limelight.setPollRateHz(100)
        limelight.start()

        while (opModeIsActive()) {
            updateSubsystems()

            val y = -gamepad1.left_stick_y.toDouble()
            val x = gamepad1.left_stick_x.toDouble()
            val rotation = gamepad1.right_stick_x.toDouble()

            val denominator = Math.max(
                Math.abs(y) + Math.abs(x) + Math.abs(rotation),
                1.0
            )

            val frontLeftPower = (y+x+rotation) / denominator
            val backLeftPower = (y-x+rotation) / denominator
            val frontRightPower = (y-x-rotation) / denominator
            val backRightPower = (y+x-rotation) / denominator

            frontLeft.power = frontLeftPower
            frontRight.power = frontRightPower
            backLeft.power = backLeftPower
            backRight.power= backRightPower

        }

    }

}