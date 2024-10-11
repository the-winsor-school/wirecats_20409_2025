package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Close Park (red or blue)")
public class AutoTemplate extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        opMode = this;
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //AUTON CODE HERE
            robot.checkColorValues();
            while(!robot.checkEndTape()) {
                telemetry.addData("EndTape","not found");
                telemetry.update();
                robot.driving.horizontal(0.50f);
                robot.checkColorValues();
                opMode.sleep(20);
            }
            telemetry.addData("tape: ", "found");
            telemetry.update();

            robot.driving.vertical(0.75f);
            opMode.sleep(3000);

            while (!robot.checkWhiteTape()) {
                telemetry.addData("WhiteTape", "not found");
                telemetry.update();
                robot.driving.horizontal(-0.75f);
                opMode.sleep(20);
            }
           robot.driving.stop();


        }
    }
}
