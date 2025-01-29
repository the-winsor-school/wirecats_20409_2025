package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class GenericMotor {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to eadily impelement PID coeffients if we wanted to get really fancy
    private DcMotorEx motor;
    private double powerUsed;

    //tolerance is used for the encoder loops
    //tolerance is how close the motor should get to the exact target position
    // before it is close enough to stop trying to get closer
    //value is experimtnally determined
    private int tolerance;

    GenericMotor(DcMotorEx motor, double powerUsed, int tolerance){
        this.motor = motor;
        this.powerUsed = powerUsed;
        this.tolerance = tolerance;
        motor.setTargetPositionTolerance(tolerance);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetEncoders() {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //used to control direction and movement of the motor
    public void setMotorState(MotorState state) {
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        if (state == MotorState.FORWARD)
            motor.setPower(powerUsed);
        if (state == MotorState.REVERSE)
            motor.setPower(-powerUsed);
        if (state == MotorState.STOP)
            motor.setPower(0);
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
            return MotorState.REVERSE;
        }
        return MotorState.STOP;
    }

    //returns current ticks of the encoder
    public int getCurrentPosition () {
        return motor.getCurrentPosition();
    }

    //returns current target position (used mostly for telemetry)
    public int getTargetPosition() { return motor.getTargetPosition(); }
    public void setTargetPosition(int targetPosition) { motor.setTargetPosition(targetPosition); }

    //parameter sets target position of motor
    //runs motor async to that position
    public void runToPosition() {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
