package org.firstinspires.ftc.teamcode.Driving.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;


@TeleOp(name = "Max Accel Test", group="test")
public class MaxAccelTest extends LinearOpMode {
    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int testTime = 1000; //in milliseconds
        double maxPower = 1;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y)
                robot.autoDriving.verticalSigmoidTime(1, testTime);
            if (gamepad1.a)
                robot.autoDriving.verticalSigmoidTime(-1, testTime);

            if(gamepad1.dpad_up)
                robot.autoDriving.adjustMaxAcceleration(0.05);
            if(gamepad1.dpad_down)
                robot.autoDriving.adjustMaxAcceleration(-0.05);


            telemetry.addLine("_____Testing for Maximum Acceleration for Sigmoid Driving____");
            telemetry.getItemSeparator();
            telemetry.addData("Max Acceleration:", robot.autoDriving.getMaxAcceleration());
            telemetry.getItemSeparator();
            telemetry.addData("Test Time", testTime);
            telemetry.addData("Max Power", maxPower);
            telemetry.update();
        }
    }
}