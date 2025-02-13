package org.firstinspires.ftc.teamcode.Autons.Spec_Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Enums.ClawPosition;
import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;
import org.firstinspires.ftc.teamcode.Enums.MotorState;
import org.firstinspires.ftc.teamcode.Robot;
@Autonomous(name = "Spec + Ascend: EitherRight", group= "spec_ascend")
public class Spec_Ascend_EitherRight extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //close claw to grab spec
            robot.claw.moveClaw(ClawPosition.CLOSE);

            //move forward out of the way of the alliance robot to the left
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.FORWARD, 400);

            //extend scissor lift and set position of wrist joint
            robot.autoLift.moveLiftToPosition(LiftPosition.HIGH_RUNG);

            //moves wrist to the correct position
            while(robot.autoLift.liftStillMoving()) {
                sleep(5);
            }
/*

            //moves to the left to align with chamber bars
            while(robot.leftDistObject.isDistanceGreater(130) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.HORIZONTAL, -0.5);
            }

            //move forward until certain distance to align arm with chamber bars
            while(robot.frontDistObject.isDistanceLess(34) && opModeIsActive()) {
                robot.autoDriving.simpleDrive(DrivingOrientation.VERTICAL, 0.3);
            }

            //set new position for wrist that lowers clip onto the bar
            robot.autoLift.setWristHighRungPlaceAngle();

            //actually moves wrist to that position to place spec
            while(robot.autoLift.liftStillMoving()) {
                sleep(5);
            }

            //open claw to drop spec
            robot.claw.moveClaw(ClawPosition.OPEN);
            sleep(100);
            robot.claw.moveClaw(ClawPosition.STOP);

            //backs away from chamber bars
            robot.autoDriving.sigmoidTime(DrivingOrientation.VERTICAL, MotorState.BACKWARDS, 500);

            //then reset lift and move to park

            //should park in ascent zone
*/

            robot.autoDriving.stop();
        }
    }
}
