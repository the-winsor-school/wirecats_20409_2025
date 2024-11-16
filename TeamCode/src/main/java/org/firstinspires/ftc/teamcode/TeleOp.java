package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
            //             MAIN CONTROLLER
            //_______________________________________________
            
            float x = gamepad1.right_stick_x;
            float y = -gamepad1.right_stick_y; //inputs from joystick are opposite
            float t = gamepad1.left_stick_x;

            robot.driving.joystickDrive(x, y, t);


            //_______________________________________________
            //             MECH CONTROLLER
            //_______________________________________________


            //ADD MECH CODE HERE FOR GAMEPAD2

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

            //lift manual controls
            if (gamepad2.dpad_up)
                robot.lift.motor.setMotorState(MotorState.FORWARD);
            if(gamepad2.dpad_down)
                robot.lift.motor.setMotorState(MotorState.REVERSE);
            //lift braking
            if (!gamepad2.dpad_down && !gamepad2.dpad_up)
                robot.lift.motor.setMotorState(MotorState.STOP); //test for float stop or full stop

            //lift values
            if (gamepad1.x)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.RESET);
            if (gamepad1.a)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.HIGHBASKET);
            if (gamepad1.b)
                robot.lift.moveLiftToPosition(FullArmLift.LIFT_POSITION.LOWBASKET);

            telemetry.update();
        }
    }
}