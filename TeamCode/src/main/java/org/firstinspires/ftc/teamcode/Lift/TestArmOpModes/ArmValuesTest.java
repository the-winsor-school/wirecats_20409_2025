package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Arm Values Test")
public class ArmValuesTest extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        while (opModeIsActive()) {

            gamepad1.

            telemetry.addData("Wrist Current Position:", robot.autoLift.wrist.getCurrentPosition());
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.update();
        }

    }
}
