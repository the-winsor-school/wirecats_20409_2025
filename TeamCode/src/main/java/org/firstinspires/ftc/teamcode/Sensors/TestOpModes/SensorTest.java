package org.firstinspires.ftc.teamcode.Sensors.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Sensor Test", group="test")
public class SensorTest extends LinearOpMode {
    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int testTime = 1000; //in milliseconds
        double maxPower = 1;

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addLine("_____Testing for All Sensors____");
            telemetry.getItemSeparator();
            telemetry.addData("right distance:", robot.rightDistObject.getDistance());
            telemetry.addData("left distance:", robot.leftDistObject.getDistance());
            telemetry.getItemSeparator();
            telemetry.addData("right color:", robot.rightColorObject.getColors());
            telemetry.addData("left color:", robot.leftColorObject.getColors());
            telemetry.update();
        }
    }
}