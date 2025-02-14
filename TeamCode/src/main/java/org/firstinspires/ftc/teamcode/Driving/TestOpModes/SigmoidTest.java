package org.firstinspires.ftc.teamcode.Driving.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
@Disabled
@Autonomous(name = "Auto Test", group="test")
public class SigmoidTest extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

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
