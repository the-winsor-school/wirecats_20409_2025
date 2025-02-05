package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;
import org.firstinspires.ftc.teamcode.Robot;

import java.lang.annotation.Target;

@TeleOp(name = "Check Arm Locations", group = "arm_test")
public class CheckArmValues extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        LiftPosition currentPosition;

        waitForStart();

        while (opModeIsActive()) {

            currentPosition = LiftPosition.RESET;

            if (gamepad1.y) {
                currentPosition = LiftPosition.HIGH_RUNG;
                robot.autoLift.moveScissorToPosition(LiftPosition.HIGH_RUNG);
                robot.autoLift.moveWristToPositionSync(LiftPosition.HIGH_RUNG);
            }
            if (gamepad1.b) {
                currentPosition = LiftPosition.LOW_BASKET;
                robot.autoLift.moveLiftToPosition(LiftPosition.LOW_BASKET);
            }
            if (gamepad1.a) {
                currentPosition = LiftPosition.RESET;
                robot.autoLift.moveLiftToPosition(LiftPosition.RESET);
            }

            telemetry.addData("Current Position:", currentPosition);
            telemetry.addLine();
            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.addData("Lift Target Position:", robot.autoLift.lift.getTargetPosition());

            telemetry.update();

        }

    }
}
