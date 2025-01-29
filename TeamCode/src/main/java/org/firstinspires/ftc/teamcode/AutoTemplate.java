package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Close Park (red or blue)")
public class AutoTemplate extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            robot.liftMotor.setTargetPosition(1000);
            robot.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(robot.fullLift.lift.getCurrentPosition() != robot.fullLift.lift.getTargetPosition()){
                telemetry.addData("target", robot.fullLift.lift.getTargetPosition());
                telemetry.addData("current", robot.fullLift.lift.getCurrentPosition());
                telemetry.update();
            }
        }

    }
}
