package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Spec + Ascend: EitherRight", group= "spec_ascend")
public class Spec_Ascend extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //move to sumbmersible
            robot.autoDriving.verticalSigmoidTime(1, 800);

            robot.autoDriving.horizontalSigmoidTime(-1, 200);


            robot.autoDriving.turn(-.5);

            sleep(400);

            //TODO arm code

            //move right to get to ascend zone
            //robot.sigmoidDriving.horizontalDist(0.5, 120);

            //move backwards into ascent zone
            //robot.sigmoidDriving.verticalSigmoidTime( 1, 3000);

            robot.autoDriving.stop();
        }
    }
}
