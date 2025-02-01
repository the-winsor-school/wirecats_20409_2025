package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.MotorState;

import java.util.concurrent.atomic.AtomicLong;

public class WristMotorObject extends SimpleMotorObject {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to eadily impelement PID coeffients if we wanted to get really fancy
    private DcMotorEx motor;
    private double powerUsed;
    private AnalogInput wristAngle;

    private double tolerance; //for potentimoeter


    public WristMotorObject (DcMotorEx wristMotor, double powerUsed, double tolerance, AnalogInput wristAngle) {
        super(wristMotor, powerUsed);

        this.tolerance = tolerance;
        this.wristAngle = wristAngle;
    }

    public void moveToPosition(double targetAngle) {
        //code
    }

    public double getCurrentAngle() { return wristAngle.getVoltage(); }

    public double getTolerance() { return tolerance; }
}

class WristThread extends Thread {
    private WristMotorObject wrist;
    private double targetAngle;

    public WristThread(WristMotorObject wrist, double targetAngle) {
        this.wrist = wrist;
        this.targetAngle = targetAngle;

    }
    public void run()
    {
        while (tooHigh(targetAngle) && tooLow(targetAngle)) {
            targetAngle = targetAngle;
            if (tooHigh(targetAngle)) {
                wrist.setMotorPower(1); //will be multiplied by power used
            } else if (tooLow(targetAngle)) {
                wrist.setMotorPower(-1);
            }
        }
    }

    private boolean tooHigh(double targetAngle) {
        return ((wrist.getCurrentAngle() - wrist.getTolerance()) > targetAngle);
    }

    private boolean tooLow(double targetAngle) {
        return ((wrist.getCurrentAngle() - wrist.getTolerance()) < targetAngle);
    }
}
