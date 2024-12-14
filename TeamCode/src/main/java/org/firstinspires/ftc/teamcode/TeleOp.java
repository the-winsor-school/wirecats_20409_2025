package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ArmLift.ClawPosition;
import org.firstinspires.ftc.teamcode.ArmLift.FullArmLift;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    Robot robot;
    FullArmLift fullLift;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        fullLift = robot.fullLift;

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
            if (gamepad2.right_bumper)
                fullLift.claw.moveClaw(ClawPosition.CLOSE);
            if (gamepad2.left_bumper)
                fullLift.claw.moveClaw(ClawPosition.OPEN);


            //control lift with right stick y value on mech controller
            fullLift.joystickControlLift(gamepad2.right_stick_y);
            robot.fullLift.joystickControlWrist(gamepad2.left_stick_y);

            //lift values
            //TO BE TESTED
            if (gamepad2.x)
                fullLift.moveLiftToPosition(FullArmLift.LIFT_POSITION.RESET);
            if (gamepad2.a)
                fullLift.moveLiftToPosition(FullArmLift.LIFT_POSITION.HIGH_BASKET);
            if (gamepad2.b)
                fullLift.moveLiftToPosition(FullArmLift.LIFT_POSITION.LOW_BASKET);

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

            telemetry.addData("current position:", fullLift.lift.getCurrentPosition());
            telemetry.addData("target position:", fullLift.wrist.getTargetPosition());
            telemetry.addData("direction:", fullLift.lift.getMotorState());

            telemetry.addLine("----------------CLAW-------------------------");

            telemetry.addData("claw position: ", fullLift.claw.getCurrentPosition());


            telemetry.update();
        }
    }
}