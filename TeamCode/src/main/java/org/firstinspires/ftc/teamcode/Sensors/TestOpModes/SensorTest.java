package org.firstinspires.ftc.teamcode.Sensors.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AutoControls;
import org.firstinspires.ftc.teamcode.TeleOpControls;
@TeleOp(name = "Sensor Test", group="test")
public class SensorTest extends LinearOpMode {
    AutoControls robot;
    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addLine("_____Testing for All Sensors____");
            telemetry.getItemSeparator();
            telemetry.addData("right distance:", robot.rightDistObject.getDistance());
            telemetry.addData("left distance:", robot.leftDistObject.getDistance());
            telemetry.addData("front distance:", robot.frontDistObject.getDistance());
            telemetry.getItemSeparator();
            telemetry.addLine("Right Color Sensor:");
            telemetry.addLine(robot.rightColorObject.getColors());
            telemetry.getItemSeparator();
            telemetry.addLine("Left Color Sensor:");
            telemetry.addLine(robot.leftColorObject.getColors());
            telemetry.update();
        }
    }
}