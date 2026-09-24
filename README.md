# FTC 4509 Robot Code
Welcome to team 4509's code. We are using the latest fork of Pedro Pathing.
The quickstart we used can be found [here](https://github.com/Pedro-Pathing/Quickstart).

## Primary Code
Our team primarily uses Kotlin in our own files. 

Our code is separated into three separate folders:
- opmodes: Holds any primary opmodes our team intends to use for testing or competition purposes.
- subsystems: Holds any subsystem folders and Kotlin files that we use from opmode files.
- vision: Sets up limelight usage to track AprilTags.

## Subsystems

**Flower Subsystem**
The flower subsystem controls any potential motors or servos on the exterior of our robot to collect from flowers for shooting purposes. It is currently blank, as we have not defined any motors or servos for flowers.

**Indexer**
The indexer subsystem allows us to intake pollen (and potentially nectar) into our robot and send it to our turret. This file is currently blank as we brainstorm motors vs servos for this job.

**Intake**
This systems sets up the functions for intaking game pieces to our robot. It has three main functions.
`activate(power: Double)` when supplied with a Double sets the power of the motor. It will almost always be 1.0.
`reverse(power: Double)` when supplied with power will outtake any game pieces currently stuck in the intake.
`stop()` will set the intake's power to 0.

**Shooter**
The shooter system specifically controls the motor for our flywheel system. It has three public functions.
`shoot(power: Double)` will set the flywheel motor to the supplied power. It will almost always be 1.0.
`stop()` allows the motor to come to a stop.
`update()` is the function that is called each time the primary op-file cycles. In this case, it watches the motor RPM to help determine if a game piece was fired.

**Turret**
The turret has two primary files. The first one sets up the turret while the second ones controls it.

`Index.kt` allows the setup of the turret and establishes the servo that will be used. It then defines (and calls) functions from the second file.
`TurretControl.kt` several values must be setup in this file. Most notably, the minimum and maximum angle of the servo. There are two public functions in this file.
`aimAt(angle: Double)` controls where the servo's position is set. Before setting the angle, the angle is clamped to ensure it's within the acceptable range.
`track(error: Double)` this is the primary function, which takes an error and then forces a correction. I accidentally added a secondary clamp inside of this function, which is repetitive. Finally, the servo's position is set to a servo position.

**Vision**
We are using a Limelight 3A on our robot for easier AprilTag vision. Our primary file only has one public function, which is updated during each cycle of the op-file.

`update()` takes the latest limelight result and then checks for any fiducials and/or IDs. If it finds any, it will return them to the primary file to direct the turret.
