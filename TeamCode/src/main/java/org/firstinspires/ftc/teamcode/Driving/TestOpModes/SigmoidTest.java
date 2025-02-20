package org.firstinspires.ftc.teamcode.Driving.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutoControls;

@Disabled
@Autonomous(name = "Auto Test", group="test")
public class SigmoidTest extends LinearOpMode {

    AutoControls robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        int targetDist = 100;

        waitForStart();

        while (opModeIsActive()) {
            //should be dist function

            while (opModeIsActive()) {
                telemetry.addLine("Current");
                telemetry.addData("Cm Per Ticks", robot.autoDriving.cmPerTick);
                robot.printWheelCurrentPosition();
                telemetry.addLine("Target");
                robot.printWheelTargetPosition();
                telemetry.update();
            }
        }
    }
}
