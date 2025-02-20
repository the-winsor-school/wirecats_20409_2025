package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutoControls;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@Autonomous(name = "Net Zone Park: Either Right", group= "park")
public class Net_Zone_Park extends LinearOpMode {

    AutoControls robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        waitForStart();

        if (opModeIsActive()) {
            while(!robot.rightColorObject.anyTape() && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
                telemetry.addLine("Moving to tape to park");
                telemetry.update();
            }

            robot.autoDriving.stop();
        }
    }
}
