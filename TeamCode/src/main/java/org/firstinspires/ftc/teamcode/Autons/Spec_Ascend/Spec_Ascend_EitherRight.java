package org.firstinspires.ftc.teamcode.Autons.Spec_Ascend;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonRobot;
import org.firstinspires.ftc.teamcode.Enums.ClawPosition;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@Autonomous(name = "Spec + Ascend: Either Right", group= "spec_ascend")
public class Spec_Ascend_EitherRight extends LinearOpMode {

    AutonRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutonRobot(this);

        robot.claw.moveClaw(ClawPosition.CLOSE);

        waitForStart();

        if (opModeIsActive()) {

            //move forward out of the way of the alliance robot to the left
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 400);

            //extend scissor lift and set position of wrist joint
            //robot.autoLift.moveScissorToPosition(LiftPosition.HIGH_RUNG);

            //moves wrist to the correct position
            while((Math.abs(robot.autoLift.lift.getCurrentPosition() - robot.autoLift.lift.getTargetPosition()) > 100) && opModeIsActive()) {
                telemetry.addLine("Lift Moving");
                telemetry.addData("Lift Position", robot.autoLift.lift.getCurrentPosition());
                telemetry.addData("Lift Target", robot.autoLift.lift.getTargetPosition());
                telemetry.update();
            }

            //move forward until certain distance to align arm with chamber bars
            while(robot.frontDistObject.isDistanceLess(28) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);

                telemetry.addLine("Moving backwards to align");
                telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
                telemetry.update();
            }
            robot.autoDriving.stop();

            robot.autoLift.setWristHighRungPlaceAngle();

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
            while(robot.frontDistObject.isDistanceLess(60) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);
                telemetry.addLine("Moving backwards to align");
                telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
                telemetry.update();
            }

            //move left quickly to net zone
            robot.autoDriving.sigmoidTime(DrivingOrientation.HORIZONTAL, MotorState.FORWARD, 1300);

            //check for tape and park
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.BACKWARDS, 100);

            robot.autoDriving.stop();

        }
    }
}
