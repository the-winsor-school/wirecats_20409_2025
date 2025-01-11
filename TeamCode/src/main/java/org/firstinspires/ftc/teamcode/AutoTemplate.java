package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Auto Test")
public class AutoTemplate extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int targetDist = 100;

        waitForStart();

        while (opModeIsActive()) {
            robot.driving.verticalDist(0.5, targetDist);

            while (opModeIsActive()) {
                telemetry.addData("cm per tick", robot.driving.cmPerTick);
                telemetry.addLine("Current");
                robot.printWheelCurrentPosition();
                telemetry.addLine("Target");
                robot.printWheelTargetPosition();
                telemetry.update();
            }


        }

    }
}
