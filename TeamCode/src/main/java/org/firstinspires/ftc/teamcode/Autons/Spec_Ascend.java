package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.ClawPosition;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;
import org.firstinspires.ftc.teamcode.Robot;
@Disabled
@Autonomous(name = "Spec + Ascend: EitherRight", group= "spec_ascend")
public class Spec_Ascend extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            robot.claw.moveClaw(ClawPosition.CLOSE);

            robot.autoDriving.verticalSigmoidTime(1, 400);

            robot.autoLift.moveLiftToPosition(LiftPosition.HIGH_RUNG);

            while(robot.autoLift.wrist.movingToTarget()) {
                robot.autoLift.wrist.moveCloserToPosition();
                sleep(10);
            }

            robot.autoDriving.horizontalSigmoidTime(-1, 1000);

            while(robot.frontDistObject.isDistanceLess(34)) {
                robot.autoDriving.vertical(0.3);
                sleep(10);
            }

            robot.autoLift.setWristHighRungPlaceAngle();

            while(robot.autoLift.wrist.movingToTarget()) {
                robot.autoLift.wrist.moveCloserToPosition();
            }

            robot.claw.moveClaw(ClawPosition.OPEN);
            sleep(100);
            robot.claw.moveClaw(ClawPosition.STOP);

            robot.autoDriving.stop();
        }
    }
}
