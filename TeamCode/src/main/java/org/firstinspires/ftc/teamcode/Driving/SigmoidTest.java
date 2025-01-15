package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Auto Test")
public class SigmoidTest extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int targetDist = 100;

        waitForStart();

        while (opModeIsActive()) {
            robot.sigmoidDriving.verticalSigmoidTime(0.5, 10000);

            while (opModeIsActive()) {
                telemetry.addLine("Current");
                robot.printWheelCurrentPosition();
                telemetry.addLine("Target");
                robot.printWheelTargetPosition();
                telemetry.update();
            }


        }

    }
}
