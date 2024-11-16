package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LiftMotor {
    private DcMotor motor;
    private double powerUsed;

    LiftMotor (DcMotor motor, double powerUsed){
        this.motor = motor;
        this.powerUsed = powerUsed;
    }

    public void setMotorState(MotorState state) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (state == MotorState.FORWARD)
            motor.setPower(powerUsed);
        if (state == MotorState.REVERSE)
            motor.setPower(-powerUsed);
        if (state == MotorState.STOP)
            motor.setPower(0);
    }
    public int getCurrentPosition () {
        return motor.getCurrentPosition();
    }

    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }
}
