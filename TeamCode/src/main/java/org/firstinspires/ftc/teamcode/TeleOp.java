package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.ClawPosition;
import org.firstinspires.ftc.teamcode.Lift.AutoLift;
import org.firstinspires.ftc.teamcode.Lift.TeleOpLift;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    Robot robot;
    //I declare this lift varaible just so i dont have to say robot.lift everytime
    TeleOpLift lift;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        lift = robot.teleOpLift;

        waitForStart();

        while (opModeIsActive()){
            
            //_______________________________________________
            //             MAIN CONTROLLER (gamepad1)
            //_______________________________________________
            
            float x = gamepad1.right_stick_x;
            float y = -gamepad1.right_stick_y; //inputs from joystick are opposite
            float t = gamepad1.left_stick_x;

            robot.teleOpDriving.joystickDrive(x, y, t);

            //_______________________________________________
            //             MECH CONTROLLER (gamepad2)
            //_______________________________________________

            //claw code
            if (gamepad2.right_bumper)
                robot.claw.moveClaw(ClawPosition.CLOSE);
            if (gamepad2.left_bumper)
                robot.claw.moveClaw(ClawPosition.OPEN);


            //control lift with right stick y value on mech controller
            //y inputs negative bc thats how the controllers are (up is -1)
            lift.joystickControlLift(-gamepad2.right_stick_y);
            lift.joystickControlWrist(-gamepad2.left_stick_y);

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
            telemetry.addData("wrist direction:", lift.wrist.getMotorState());

            telemetry.addLine("----------------CLAW-------------------------");
            telemetry.addData("claw position: ", robot.claw.getCurrentPosition());

            telemetry.update();
        }
    }
}