package org.firstinspires.ftc.teamcode.Autons.Ascend;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Ascend: BlueRight or RedRight", group= "ascend")
public class Ascend_EitherRight extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        if (opModeIsActive()) {
            //moves left to align with zone
            //TODO test this time value
            robot.autoDriving.horizontalSigmoidTime(-1, 2000);

            //move forward to ascend zone
            robot.autoDriving.verticalSigmoidTime(1, 1000);

            //move right into ascent zone
            while(!robot.rightColorObject.whiteTape()) {
                robot.autoDriving.horizontal(0.5);
                sleep(20);
            }


            robot.autoDriving.stop();
        }
    }
}
