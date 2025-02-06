package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Check Arm Locations", group = "arm_test")
public class CheckArmValues extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        LiftPosition currentPosition;

        currentPosition = LiftPosition.RESET;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y) {
                currentPosition = LiftPosition.HIGH_RUNG;
                robot.autoLift.moveLiftToPosition(LiftPosition.HIGH_RUNG);
            }

            robot.autoLift.wrist.moveCloserToPosition();

            telemetry.addData("Current Position:", currentPosition);
            telemetry.addLine();
            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());
            telemetry.addData("Wrist Target Angle:", robot.autoLift.wrist.getTargetAngle());
            telemetry.addData("power", robot.autoLift.wrist.getPower());

            telemetry.addLine();
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.addData("Lift Target Position:", robot.autoLift.lift.getTargetPosition());

            telemetry.update();

        }

    }
}
