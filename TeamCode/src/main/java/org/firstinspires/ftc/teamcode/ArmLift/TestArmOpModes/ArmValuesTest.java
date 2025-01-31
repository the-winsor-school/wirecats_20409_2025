package org.firstinspires.ftc.teamcode.ArmLift.TestArmOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Arm Values Test")
public class ArmValuesTest extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        while (opModeIsActive()) {

            gamepad1.

            telemetry.addData("Wrist Current Position:", robot.fullLift.wrist.getCurrentPosition());
            telemetry.addData("Lift Current Position:", robot.fullLift.lift.getCurrentPosition());
            telemetry.update();
        }

    }
}
