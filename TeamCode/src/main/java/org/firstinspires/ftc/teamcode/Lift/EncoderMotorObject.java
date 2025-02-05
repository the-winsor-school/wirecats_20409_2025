package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.MotorState;

public class EncoderMotorObject {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to eadily impelement PID coeffients if we wanted to get really fancy
    private DcMotorEx motor;
    private double powerUsed;

    public final int tolerance;

    //tolerance is used for the encoder loops
    //tolerance is how close the motor should get to the exact target position
    // before it is close enough to stop trying to get closer
    //value is experimtnally determined
    EncoderMotorObject(DcMotorEx motor, double powerUsed, int tolerance){
        this.motor = motor;
        this.powerUsed = powerUsed;
        this.tolerance = tolerance;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPositionTolerance(tolerance);
        resetEncoders();
        setTargetPosition(0);
        motor.setPower(powerUsed);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * can be used during Run to Position loops
     */
    public void setMotorPower(float power) {
        //multiplying by powerUsed to reduce power if needed
        motor.setPower(power * powerUsed);
    }

    public MotorState getMotorState() {
        if (motor.getPower() > 0) {
            return MotorState.FORWARD;
        }
        else if (motor.getPower() > 0) {
            return MotorState.REVERSE;
        }
        return MotorState.STOP;
    }

    public void resetEncoders() { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }
    public int getCurrentPosition () { return motor.getCurrentPosition();}
    public int getTargetPosition() { return motor.getTargetPosition(); }
    public void setTargetPosition(int targetPosition) { motor.setTargetPosition(targetPosition); }

    public boolean motorMoving() {
        if (((getCurrentPosition() - tolerance) < getTargetPosition()) ||
                ((getCurrentPosition() + tolerance) > getTargetPosition())) {
            return true;
        }
        return false;
    }
}
