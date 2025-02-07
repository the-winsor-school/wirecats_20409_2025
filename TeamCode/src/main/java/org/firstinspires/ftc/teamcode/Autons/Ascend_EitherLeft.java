package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Driving.AutoDriving;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.MotorState;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Ascend: Either Left", group= "ascend")
public class Ascend_EitherLeft extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves left to move past submersible
            while (robot.leftDistObject.isDistanceGreater(60) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, -0.5);
            }

            //move forward to ascend zone
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 1000);

            //move right into ascent zone
            while(!robot.rightColorObject.whiteTape() && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
                sleep(20);
            }

            robot.autoDriving.stop();
        }
    }
}
