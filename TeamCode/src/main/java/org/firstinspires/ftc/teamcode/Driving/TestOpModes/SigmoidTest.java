package org.firstinspires.ftc.teamcode.Driving.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Auto Test", group="test")
public class SigmoidTest extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int targetDist = 100;

        waitForStart();

        while (opModeIsActive()) {
            robot.autoDriving.verticalDist(.5, 60);

            while (opModeIsActive()) {
                telemetry.addLine("Current");
                telemetry.addData("Cm Per Ticks", robot.autoDriving.getCmPerTick());
                robot.printWheelCurrentPosition();
                telemetry.addLine("Target");
                robot.printWheelTargetPosition();
                telemetry.update();
            }


        }

    }
}
