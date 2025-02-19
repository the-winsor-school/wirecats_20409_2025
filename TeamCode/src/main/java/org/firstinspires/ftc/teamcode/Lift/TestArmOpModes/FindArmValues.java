package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AutonRobot;
import org.firstinspires.ftc.teamcode.TeleOpRobot;

@Disabled
@TeleOp(name = "Finding Arm Values", group = "arm_test")
public class FindArmValues extends LinearOpMode {

    AutonRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new AutonRobot(this);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up)
                robot.autoLift.lift.setTargetPosition(robot.autoLift.lift.getCurrentPosition() + 300);
            else if (gamepad1.dpad_down)
                robot.autoLift.lift.setTargetPosition(robot.autoLift.lift.getCurrentPosition() - 300);

            if (gamepad1.dpad_left)
                robot.autoLift.wrist.setTargetAngle(robot.autoLift.wrist.getCurrentAngle() + 0.2);
            else if (gamepad1.dpad_right)
                robot.autoLift.wrist.setTargetAngle(robot.autoLift.wrist.getCurrentAngle() - 0.2);

            robot.autoLift.wrist.moveCloserToPosition();

            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());
            telemetry.addData("Wrist Target Angle:", robot.autoLift.wrist.getTargetAngle());
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.addData("Lift Target Position:", robot.autoLift.lift.getTargetPosition());

            telemetry.update();
        }

    }
}
