package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Close Park (red or blue)")
public class AutoTemplate extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //AUTON CODE HERE
            while(!robot.checkEndTape()) {
                telemetry.addData("EndTape","not found");
                telemetry.update();
                robot.driving.horizontal(0.5f);
                robot.checkColorValues();
                //opMode.sleep(20);
            }
            telemetry.addData("tape: ", "found");
            telemetry.update();
            robot.driving.stop();

            /*while (!robot.checkWhiteTape()) {
                telemetry.addData("WhiteTape", "not found");
                telemetry.update();
                robot.driving.vertical(0.5f);
                //opMode.sleep(20);
            }
           robot.driving.stop();*/
        }
    }
}
