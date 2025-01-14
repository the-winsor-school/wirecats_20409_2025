package org.firstinspires.ftc.teamcode.Autons.Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Ascend: RedRight, BlueRight", group= "ascend")
public class Ascend_RedRight_BlueRight extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves right to align with zone
            robot.driving.horizontalDist(0.5, 60);

            //move forward to ascend zone
            robot.driving.verticalDist(0.75, 120);

            //move left into ascent zone
            robot.driving.horizontalDist(.5, -30);

            robot.driving.stop();
        }
    }
}
