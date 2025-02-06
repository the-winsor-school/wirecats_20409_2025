package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Enums.MotorState;

//This class only uses setPower and NO ENCODERS (used for teleOp)
public class SimpleMotorObject {
    private DcMotorEx motor;

    //coefficient for setting power
    private double powerUsed;

    SimpleMotorObject(DcMotorEx motor, double powerUsed){
        this.motor = motor;
        this.powerUsed = powerUsed;
    }

    //mostly used for joystick controls
    public void setMotorPower(float power) {
        //multiplying by powerUsed to reduce power if needed
        motor.setPower(power * powerUsed);
    }

    public MotorState getMotorState() {
        if (motor.getPower() > 0) {
            return MotorState.FORWARD;
        }
        else if (motor.getPower() > 0) {
            return MotorState.BACKWARDS;
        }
        return MotorState.STOP;
    }

    public double getPower() { return motor.getPower(); }
}
