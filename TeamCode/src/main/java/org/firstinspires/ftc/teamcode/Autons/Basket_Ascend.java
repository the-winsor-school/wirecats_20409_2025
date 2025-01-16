package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Basket + Ascend", group= "basket_ascend")
public class Basket_Ascend extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves forward towards basket
            //check for tape to see net zone so it works for all positions

            //TODO arm code

            //move right to get to ascend zone
            robot.sigmoidDriving.horizontalDist(0.5, 120);

            //move backwards into ascent zone
            robot.sigmoidDriving.verticalSigmoidTime( 1, 3000);

            robot.sigmoidDriving.stop();
        }
    }
}
