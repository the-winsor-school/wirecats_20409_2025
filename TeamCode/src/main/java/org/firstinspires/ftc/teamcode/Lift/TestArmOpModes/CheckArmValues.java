package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AutoControls;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
@Disabled
@TeleOp(name = "Check Arm Locations", group = "arm_test")
public class CheckArmValues extends LinearOpMode {

    AutoControls robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        LiftPosition currentPosition;

        currentPosition = LiftPosition.RESET;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y) {
                currentPosition = LiftPosition.HIGH_RUNG;
                robot.autoLift.moveScissorToPosition(LiftPosition.HIGH_RUNG);
                //robot.autoLift.moveWristToPosition(LiftPosition.HIGH_RUNG);
            }

            robot.autoLift.wrist.moveCloserToPosition();

            telemetry.addData("Current Position:", currentPosition);
            telemetry.addLine();
            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());
            telemetry.addData("Wrist Target Angle:", robot.autoLift.wrist.getTargetAngle());
            telemetry.addData("Power", robot.autoLift.wrist.getPower());
            telemetry.addData("Too High", robot.autoLift.wrist.tooHigh());
            telemetry.addData("Too Low", robot.autoLift.wrist.tooLow());

            telemetry.addLine();
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.addData("Lift Target Position:", robot.autoLift.lift.getTargetPosition());
            telemetry.addData("Current Lift Power", robot.autoLift.lift.getPower());

            telemetry.update();

        }

    }
}
