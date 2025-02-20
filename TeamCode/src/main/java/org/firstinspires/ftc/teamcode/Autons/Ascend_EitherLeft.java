package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutoControls;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@Autonomous(name = "Ascend: Either Left", group= "park")
public class Ascend_EitherLeft extends LinearOpMode {

    AutoControls robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves left to move past submersible
            while (robot.leftDistObject.isDistanceGreater(60) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, -0.5);
            }

            //move forward to ascend zone
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 1000);

            //move right into ascent zone

            while(robot.rightDistObject.isDistanceLess(100) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
            }
           /* while(!robot.rightColorObject.whiteTape() && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
                sleep(20);
            }*/

            robot.autoDriving.stop();
        }
    }
}
