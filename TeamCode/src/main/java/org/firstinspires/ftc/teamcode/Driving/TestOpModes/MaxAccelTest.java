package org.firstinspires.ftc.teamcode.Driving.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AutonRobot;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@TeleOp(name = "Max Accel Test", group="test")
public class MaxAccelTest extends LinearOpMode {
    AutonRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutonRobot(this);

        int testTime = 1000; //in milliseconds
        double maxPower = 1;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y)
                robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, testTime);
            if (gamepad1.a)
                robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.BACKWARDS, testTime);

            if (gamepad1.dpad_up)
                robot.autoDriving.adjustMaxAcceleration(0.05);
            if (gamepad1.dpad_down)
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