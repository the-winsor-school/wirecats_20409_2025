package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Max Accel Test", group="test")
public class MaxAccelTest extends LinearOpMode {
    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        int testTime = 100; //in milliseconds
        double maxPower = 0.5;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y)
                robot.driving.verticalSigmoid(maxPower, testTime);
            if (gamepad1.a)
                robot.driving.verticalSigmoid(-maxPower, testTime);

            if(gamepad1.dpad_up)
                robot.driving.adjustMaxAcceleration(0.05);
            if(gamepad1.dpad_down)
                robot.driving.adjustMaxAcceleration(-0.05);


            telemetry.addLine("_____Testing for Maximum Acceleration for Sigmoid Driving____");
            telemetry.getItemSeparator();
            telemetry.addData("Max Acceleration:", robot.driving.getMaxAcceleration());
            telemetry.getItemSeparator();
            telemetry.addData("Test Time", testTime);
            telemetry.addData("Max Power", maxPower);
            telemetry.update();
        }
    }
}