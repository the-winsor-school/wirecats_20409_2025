package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

/*
@TeleOp(name = "Finding Arm Values", group = "arm_test")
public class FindArmValues extends LinearOpMode {

    Robot robot;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_up)
                robot.autoLift.lift.setTargetPosition(robot.autoLift.lift.getCurrentPosition() + 300);
            else if (gamepad1.dpad_down)
                robot.autoLift.lift.setTargetPosition(robot.autoLift.lift.getCurrentPosition() - 300);

            robot.teleOpLift.joystickControlWrist(-gamepad1.left_stick_y);

            telemetry.addData("Wrist Current Angle:", robot.autoLift.wrist.getCurrentAngle());
            telemetry.addData("Lift Current Position:", robot.autoLift.lift.getCurrentPosition());
            telemetry.addData("Lift Target Position:", robot.autoLift.lift.getTargetPosition());

            telemetry.update();
        }

    }
}
*/