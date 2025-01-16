package org.firstinspires.ftc.teamcode.Autons.Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Ascend: RedLeft, BlueLeft", group= "ascend")
public class Ascend_RedLeft_BlueLeft extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves left to align with zone
            robot.sigmoidDriving.horizontalDist(0.5, -60);

            //move forward to ascend zone
            robot.sigmoidDriving.verticalSigmoidTime(1, 5000);

            //move right into ascent zone
            //TODO add color sensor code
            robot.sigmoidDriving.horizontalDist(.5, 30);

            robot.sigmoidDriving.stop();
        }
    }
}
