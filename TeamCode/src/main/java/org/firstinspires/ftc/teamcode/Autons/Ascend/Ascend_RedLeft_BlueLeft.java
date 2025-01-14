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
            robot.driving.horizontalDist(0.5, -60);

            //move forward to ascend zone
            robot.driving.verticalDist(0.75, 120);

            //move right into ascent zone
            robot.driving.horizontalDist(.5, 30);

            robot.driving.stop();
        }
    }
}
