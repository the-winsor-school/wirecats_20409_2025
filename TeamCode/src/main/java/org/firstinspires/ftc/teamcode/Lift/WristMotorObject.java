package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class WristMotorObject {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to easily implement PID coefficients if we wanted to get really fancy

    private final DcMotorEx motor;
    public final double powerUsed;

    private final AnalogInput wristAngle;

    public final double tolerance;

    private double targetAngle;

    public WristMotorObject (DcMotorEx wristMotor, double powerUsed, double tolerance, AnalogInput wristAngle) {

        this.motor = wristMotor;
        this.powerUsed = powerUsed;
        this.tolerance = tolerance;
        this.wristAngle = wristAngle;

        targetAngle = getCurrentAngle();
    }

    /**
     * @return true if arm is to far ABOVE target position
     */
    public boolean tooHigh() { //needs to move down
        return getCurrentAngle() - targetAngle > tolerance;
    }
    /**
     *
     * @return true if arm is to far BELOW target position
     */
    public boolean tooLow() { //needs to move up
        return targetAngle - getCurrentAngle() > tolerance;
    }

    /**
     * power is multiplied by powerUsed within this function
     */
    public void setMotorPower(double power) {
        motor.setPower(power * powerUsed);
    }

    /**
     * @return current potentiometer output
     */
    public double getCurrentAngle() { return wristAngle.getVoltage(); }

    /**
     * moves wrist closer to set target position
     * should be called in a while loop in auto
     * should be put in teleOp lop for teleOp
     */
    public void moveCloserToPosition() {
        double power = powerUsed;
        if (Math.abs(getCurrentAngle() - targetAngle) < 0.5) {
            power = 2*powerUsed/3;
        }
        if (tooHigh()) {
            motor.setPower(-power); //will be multiplied by power used
        } else if (tooLow()) {
            motor.setPower(power);
        }
    }

    /**
     * @return current power of the motor
     */
    public double getPower() { return motor.getPower(); }

    /**
     * sets targetAngle
     * does NOT move motor to that target position
     * call .moveCloserToTargetPosition() to make progress towards target angle
     * @param targetAngle potentiometer output value
     */
    public void setTargetAngle(double targetAngle) { this.targetAngle = targetAngle; }

    /**
     * @return current target angle within the wrist, in potentiometer output
     */

    public double getTargetAngle() { return targetAngle; }

    /**
     * @return true if motor is still trying to move to the current position
     * false if motor's currentPosition is within tolerance of the target position
     */
    public boolean movingToTarget() { return (tooLow() || tooHigh()); }

}
class WristThread extends Thread {
    private final WristMotorObject wrist;
    private final double targetAngle;
    public boolean threadRan = false;

    public WristThread(WristMotorObject wrist, double targetAngle) {
        this.wrist = wrist;
        this.targetAngle = targetAngle;
    }
    public void start()
    {
        threadRan = true;
        wrist.setTargetAngle(targetAngle);
        while (!wrist.movingToTarget()) {
            wrist.moveCloserToPosition();
        }
    }

}
