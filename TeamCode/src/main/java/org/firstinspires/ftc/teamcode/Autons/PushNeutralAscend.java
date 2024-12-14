package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Push Neutral Ascend")
public class PushNeutralAscend extends LinearOpMode {

    Robot robot;
    LinearOpMode opMode;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            robot.checkColorValues();
            while(!robot.checkEndTape()) {
                telemetry.addData("EndTape","not found");
                telemetry.update();
                robot.driving.horizontal(-0.75f);
                robot.checkColorValues();
                opMode.sleep(20);
            }
            telemetry.addData("tape: ", "found");
            telemetry.update();//go to under basket

            robot.driving.horizontal(0.50f);
            opMode.sleep(1000);//line up with white tape ascend area

            while (!robot.checkWhiteTape()) {
                telemetry.addData("WhiteTape", "not found");
                telemetry.update();
                robot.driving.horizontal(-0.75f);
                opMode.sleep(20);
            }//ascend area to line up to push neutrals

            robot.driving.horizontal(-0.50f);
            opMode.sleep(1000);

            while (!robot.checkEndTape()) {
                telemetry.addData("EndTape","not found");
                telemetry.update();
                robot.driving.vertical(-0.75f);
                robot.checkColorValues();
                opMode.sleep(20);
            }//under basket area

            while (!robot.checkEndTape()) {
                telemetry.addData("EndTape","not found");
                telemetry.update();
                robot.driving.horizontal(0.75f);
                robot.checkColorValues();
                opMode.sleep(20);
            }//regular park area

            robot.driving.vertical(0.75f);
            opMode.sleep(3000);

            while (!robot.checkWhiteTape()) {
                telemetry.addData("WhiteTape", "not found");
                telemetry.update();
                robot.driving.horizontal(-0.75f);
                opMode.sleep(20);
            }//ascend
            robot.driving.stop();


        }
    }
}
