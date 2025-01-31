package org.firstinspires.ftc.teamcode.Autons.Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Ascend: BlueLeft", group= "ascend")
public class Ascend_BlueLeft extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves left to align with zone
            robot.sigmoidDriving.horizontalSigmoidTime(-1, 1000);

            //move forward to ascend zone
            robot.sigmoidDriving.verticalSigmoidTime(1, 1000);

            //move right into ascent zone
            robot.sigmoidDriving.horizontalSigmoidTime(1, 500);

            robot.sigmoidDriving.stop();
        }
    }
}
