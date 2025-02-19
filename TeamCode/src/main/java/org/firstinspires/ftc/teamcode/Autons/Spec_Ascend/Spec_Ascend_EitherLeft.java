package org.firstinspires.ftc.teamcode.Autons.Spec_Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonRobot;
import org.firstinspires.ftc.teamcode.Enums.ClawPosition;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

@Disabled
@Autonomous(name = "Spec + Ascend: Either Left", group= "spec_ascend")
public class Spec_Ascend_EitherLeft extends LinearOpMode {

    AutonRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutonRobot(this);

        robot.claw.moveClaw(ClawPosition.CLOSE);

        waitForStart();

        if (opModeIsActive()) {

            //move forward out of the way of the alliance robot to the left
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 400);

            //extend scissor lift and set position of wrist joint
            robot.autoLift.moveScissorToPosition(LiftPosition.HIGH_RUNG);

            //moves wrist to the correct position
            while(Math.abs(robot.autoLift.lift.getCurrentPosition() - robot.autoLift.lift.getTargetPosition()) > 100) {
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
                telemetry.addData("Wrist Position:", robot.autoLift.wrist.getCurrentAngle());
                telemetry.addData("Wrist Target:", robot.autoLift.wrist.getTargetAngle());
            }

            sleep(3000);

            robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);

            sleep(1000);
            robot.autoDriving.stop();

            //TODO move to ascent zone

            robot.autoLift.moveLiftToPosition(LiftPosition.RESET);


            //back away from bars
            while(robot.frontDistObject.isDistanceLess(50) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, -0.3);
                telemetry.addLine("Moving backwards to align");
                telemetry.addData("Front Distance", robot.frontDistObject.getDistance());
                telemetry.update();
            }

            //moves left to move past submersible
            while (robot.leftDistObject.isDistanceGreater(60) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, -0.5);
            }

            //move forward to ascend zone
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 1000);

            //move right into ascent zone
            while(!robot.rightColorObject.whiteTape() && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, 0.5);
                sleep(20);
            }

            robot.autoDriving.stop();

        }
    }
}
