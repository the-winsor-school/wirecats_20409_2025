package org.firstinspires.ftc.teamcode.Autons.Spec_Ascend;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutoControls;
import org.firstinspires.ftc.teamcode.Enums.ClawPosition;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@Autonomous(name = "Spec + Net Zone: Either Right", group= "spec_ascend")
public class Spec_Ascend_EitherRight extends LinearOpMode {

    AutoControls robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutoControls(this);

        robot.claw.moveClaw(ClawPosition.CLOSE);

        waitForStart();

        if (opModeIsActive()) {

            //move forward out of the way of the alliance robot to the left
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 400);

            //extend scissor lift and set position of wrist joint
            robot.autoLift.moveScissorToPosition(LiftPosition.HIGH_RUNG);

            //moves wrist to the correct position
            while((Math.abs(robot.autoLift.lift.getCurrentPosition() - robot.autoLift.lift.getTargetPosition()) > 100)
                    && opModeIsActive()) {
                telemetry.addLine("Lift Moving");
                telemetry.addData("Lift Position", robot.autoLift.lift.getCurrentPosition());
                telemetry.addData("Lift Target", robot.autoLift.lift.getTargetPosition());
                telemetry.update();
            }

            telemetry.addLine("lift went up, moving backwards now");
            telemetry.addLine("Moving backwards to align");
            telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
            telemetry.update();
            sleep(3000);

            //move forward until certain distance to align arm with chamber bars
            while(robot.frontDistObject.isDistanceLess(27) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);
                telemetry.addLine("Moving backwards to align");
                telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
                telemetry.update();
            }
            robot.autoDriving.stop();
            sleep(3000);

            telemetry.addLine("moving wrist now, should be in correct distance");
            telemetry.update();

            robot.autoLift.moveWristToPosition(LiftPosition.HIGH_RUNG);

            while(robot.autoLift.wrist.movingToTarget()) {
                robot.autoLift.wrist.moveCloserToPosition();
                telemetry.addLine("Wrist Moving");
                telemetry.addData("Wrist Position", robot.autoLift.wrist.getCurrentAngle());
                telemetry.addData("Wrist Target", robot.autoLift.wrist.getTargetAngle());
                telemetry.update();
            }

            sleep(3000);

            robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);

            sleep(1000);
            robot.autoDriving.stop();

            robot.autoLift.moveLiftToPosition(LiftPosition.RESET);

            //back away from bars
            while(robot.frontDistObject.isDistanceLess(58) && opModeIsActive()) {
                robot.autoLift.wrist.moveCloserToPosition();
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);
                telemetry.addLine("Moving backwards to align");
                telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
                telemetry.update();
            }

            //move left quickly to net zone
            while(!robot.rightColorObject.anyTape() && opModeIsActive()) {
                robot.autoLift.wrist.moveCloserToPosition();
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
                telemetry.addLine("Moving to tape to park");
                telemetry.update();
            }
            robot.autoDriving.stop();

            while((robot.autoLift.wrist.movingToTarget() || robot.autoLift.liftStillMoving()) && opModeIsActive()) {
                robot.autoLift.wrist.moveCloserToPosition();
            }
        }
    }
}
