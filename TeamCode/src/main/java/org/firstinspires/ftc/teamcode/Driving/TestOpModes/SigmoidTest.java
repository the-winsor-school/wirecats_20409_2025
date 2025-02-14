package org.firstinspires.ftc.teamcode.Driving.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeleOpRobot;
@Disabled
@Autonomous(name = "Auto Test", group="test")
public class SigmoidTest extends LinearOpMode {

    TeleOpRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new TeleOpRobot(this);

        int targetDist = 100;

        waitForStart();

        while (opModeIsActive()) {
            //should be dist function

            while (opModeIsActive()) {
                telemetry.addLine("Current");
                //telemetry.addData("Cm Per Ticks", robot.autoDriving.cmPerTick);
                robot.printWheelCurrentPosition();
                telemetry.addLine("Target");
                robot.printWheelTargetPosition();
                telemetry.update();
            }


        }

    }
}
