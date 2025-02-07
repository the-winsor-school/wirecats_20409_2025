package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Enums.ClawPosition;
import org.firstinspires.ftc.teamcode.Lift.TeleOpLift;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    Robot robot;
    TeleOpLift lift;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        while (opModeIsActive()){
            
            //_______________________________________________
            //             MAIN CONTROLLER (gamepad1)
            //_______________________________________________
            
            float x = gamepad1.right_stick_x;
            float y = -gamepad1.right_stick_y; //inputs from joystick are opposite
            float t = gamepad1.left_stick_x;

            robot.teleOpDriving.joystickDrive(x, y, t);
            

            //adjusting speed
            if (gamepad1.right_bumper)
                robot.teleOpDriving.setSpeed(robot.teleOpDriving.getSpeed() + 0.5);
            if (gamepad1.left_bumper)
                robot.teleOpDriving.setSpeed(robot.teleOpDriving.getSpeed() - 0.5);

            //_______________________________________________
            //             MECH CONTROLLER (gamepad2)
            //_______________________________________________

            //claw code
            if (gamepad2.right_bumper)
                robot.claw.moveClaw(ClawPosition.CLOSE);
            else if (gamepad2.left_bumper)
                robot.claw.moveClaw(ClawPosition.OPEN);
            else
                robot.claw.moveClaw(ClawPosition.STOP);

            //control lift with right stick y value on mech controller
            lift.joystickControlLift(gamepad2.right_stick_y);
            lift.joystickControlWrist(gamepad2.left_stick_y);

            //_______________________________________________
            //             PRINT STATEMENTS
            //_______________________________________________

            telemetry.addLine("----------------WHEELS-------------------------");

            //joystick inputs
            telemetry.addData("x: ", x);
            telemetry.addData("y: ", y);
            telemetry.addData("t: ", t);

            //wheels powers
            robot.printWheelPowers();

            telemetry.addLine("----------------LIFT-------------------------");
            telemetry.addData("scissor direction:", lift.scissor.getMotorState());
            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());

            telemetry.addLine("----------------CLAW-------------------------");
            telemetry.addData("claw position: ", robot.claw.getCurrentPosition());

            telemetry.update();
        }
    }
}