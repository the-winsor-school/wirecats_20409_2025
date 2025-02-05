package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.MotorState;

import java.security.Policy;
import java.util.concurrent.atomic.AtomicLong;

public class WristMotorObject {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to eadily impelement PID coeffients if we wanted to get really fancy

    private DcMotorEx motor;
    private double powerUsed;

    private AnalogInput wristAngle;

    public final double tolerance;

    private double targetAngle;


    public WristMotorObject (DcMotorEx wristMotor, double powerUsed, double tolerance, AnalogInput wristAngle) {

        this.motor = wristMotor;
        this.powerUsed = powerUsed;

        this.tolerance = tolerance;
        this.wristAngle = wristAngle;
        targetAngle = getCurrentAngle();
    }

    public boolean tooHigh() { //needs to move down
        return getCurrentAngle() - targetAngle > tolerance;
    }

    public boolean tooLow() { //needs to move up
        return targetAngle - getCurrentAngle() > tolerance;
    }

    public void setMotorPower(double power) {
        motor.setPower(power * powerUsed);
    }

    public double getCurrentAngle() { return wristAngle.getVoltage(); }

    public void moveCloserToPosition() {
        double power = powerUsed;
        if (Math.abs(getCurrentAngle() - targetAngle) < 0.5) {
            power = powerUsed/2;
        }
        if (tooHigh()) {
            motor.setPower(power); //will be multiplied by power used
        } else if (tooLow()) {
            motor.setPower(-power);
        }
    }

    public double getPower() {
        return motor.getPower();
    }

    public void setTargetAngle(double targetAngle) { this.targetAngle = targetAngle; }

    public double getTargetAngle() { return targetAngle; }

    public boolean movingToTarget() { return (tooLow() || tooHigh()); }

}

class WristThread extends Thread {
    private WristMotorObject wrist;
    private double targetAngle;
    public boolean threadRan = false;

    public WristThread(WristMotorObject wrist, double targetAngle) {
        this.wrist = wrist;
        this.targetAngle = targetAngle;
    }
    public void start()
    {
/*        threadRan = true;
        wrist.setTargetAngle(targetAngle);
        while (wrist.closeToTarget()) {
            wrist.moveCloserToPosition();
        }*/
    }

}
