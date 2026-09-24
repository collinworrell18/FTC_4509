package org.firstinspires.ftc.teamcode.vision

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.LLResultTypes
import com.qualcomm.hardware.limelightvision.LLStatus
import com.qualcomm.hardware.limelightvision.Limelight3A

class limelightClass(
    private val limelight: Limelight3A
) {
    var targetVisible = false
        private set

    var targetId = -1
        private set

    var targetX = 0.0
        private set

    fun update() {
        val result = limelight.latestResult

        targetVisible = false
        targetId = -1

        if (result == null || !result.isValid) {
            return
        }

        val fiducials = result.fiducialResults

        if (fiducials.isEmpty()) {
            return
        }

        val tag = fiducials[0]

        targetVisible = true
        targetId = tag.fiducialId
        targetX = tag.targetXDegrees
    }
}