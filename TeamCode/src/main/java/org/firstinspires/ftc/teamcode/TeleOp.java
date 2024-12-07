package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ArmLift.ClawPosition;
import org.firstinspires.ftc.teamcode.ArmLift.FullArmLift;
import org.firstinspires.ftc.teamcode.ArmLift.MotorState;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    Robot robot;

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

            robot.driving.joystickDrive(x, y, t);



            //_______________________________________________
            //             MECH CONTROLLER (gamepad2)
            //_______________________________________________

            //claw code
            if (gamepad2.y) {
                robot.lift.claw.moveClaw(ClawPosition.CLOSE);
            }
            if (gamepad2.a) {
                robot.lift.claw.moveClaw(ClawPosition.OPEN);
            }
            if (!gamepad2.y && !gamepad2.a) {
                robot.lift.claw.moveClaw(ClawPosition.STOP);
            }


            //control lift with right stick y value on mech controller
            robot.lift.joystickControlLift(gamepad2.right_stick_y);

            robot.lift.joystickControlWrist(gamepad2.left_stick_y);

            //lift values
            if (gamepad2.x)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.RESET);
            //if (gamepad2.a)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.HIGHBASKET);
            if (gamepad2.b)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.LOWBASKET);

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

            telemetry.addData("current position:", robot.lift.liftMotor.getCurrentPosition());
            telemetry.addData("target position:", robot.lift.liftMotor.getTargetPosition());
            telemetry.addData("direction:", robot.lift.liftMotor.getMotorState());

            telemetry.update();
        }
    }
}