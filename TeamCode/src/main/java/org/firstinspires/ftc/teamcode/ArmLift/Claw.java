package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.ArmLift.ClawPosition;

public class Claw {

    private CRServo servo;
    private ClawPosition currentPosition;

    public Claw(CRServo clawServo) {
        servo = clawServo;
    }

    public void moveClaw(ClawPosition position) {
        if (position == ClawPosition.CLOSE) {
            servo.setPower(0.5);
            currentPosition = ClawPosition.CLOSE;
        }
        if (position == ClawPosition.OPEN) {
            servo.setPower(-0.5);
            currentPosition = ClawPosition.OPEN;
        }
        if (position == ClawPosition.STOP) {
            servo.setPower(0);
        }
    }

    public ClawPosition getCurrentPosition()
    {
        return currentPosition;
    }

    public double getPower() {
        return servo.getPower();
    }

}
