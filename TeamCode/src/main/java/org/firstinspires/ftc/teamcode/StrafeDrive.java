package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class StrafeDrive {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //encoder ticks (given from getCurrentPosition) conversion to revolutions of motor
    //value if hardware and can be looked up on REV website
    private double ticksPerRevolution = 28;

    //gear boxes on wheels (should be same for all wheels)
    private int gearReduction = 0;

    //circumference of wheels
    private double wheelCircumference = 750 * Math.PI;

    //calculated by values above
    private double cmPerTick;

    //in ticks
    private int tolerance = 10;

    private double speed = 0.5;

    public StrafeDrive(DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb = lb;

        setTargetPositionTolerance(tolerance);
        cmPerTick = calculateCmPerTicks(ticksPerRevolution, gearReduction, wheelCircumference);
    }


    public void joystickDrive (float X, float Y, float T) {
        //threshold for values (bc our controllers are old and bad)
        //these are condensed if statements
        float x = (Math.abs(X) < 0.1f) ? 0 : X;
        float y = (Math.abs(Y) < 0.1f) ? 0 : Y;
        float t = (Math.abs(T) < 0.1f) ? 0 : T;

        //explanation in drive and slack
        rf.setPower((y - x - t) * speed);
        rb.setPower((y + x - t) * speed);
        lf.setPower((y + x + t) * speed);
        lb.setPower((y - x + t) * speed);
    }

    public void turn (double t) {
        setEachPower(t, t, -t, -t);
    }

    public void horizontal (double power) { //right positive
        setEachPower(-power, power, power, -power); // -rf, +rb, lf, -lb)
    }

    public void vertical (double power) { //forward positive
        setEachPower(power,  power, power, power); //one side negative -rf, -rb
    }

    public void stop () {
        rf.setPower(0);
        rb.setPower(0);
        lf.setPower(0);
        lb.setPower(0);
    }

    private void setEachPower (double rfp, double rbp, double lfp, double lbp) {
        rf.setPower(rfp * speed);
        rb.setPower(rbp * speed);
        lf.setPower(lfp * speed);
        lb.setPower(lbp * speed);
    }

    public void adjustSpeed(double x) {
        speed = speed + x;
    }

    public double getSpeed() { return speed; }

    //Distance driving
    private double calculateCmPerTicks(double ticksPerRevolution, double gearBoxes, double wheelCircumference) {
        //this is dimensional analysis
        //going from ticks to cm
        return (1/ticksPerRevolution) * gearBoxes * wheelCircumference;
    }

    /**
     *
     * @param maxPower for wheels (not the whole time because of accerlation)
     * @param distance given in cm
     */
    private void vertical(double maxPower, double distance) {
        resetEncoders();
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        setTargetPosition(targetTicks);
        runToPosition();
    }

    //Functions to combine wheels
    private void runToPosition() {
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    private void setTargetPosition(int targetPosition) {
        rf.setTargetPosition(targetPosition);
        rb.setTargetPosition(targetPosition);
        lf.setTargetPosition(targetPosition);
        lb.setTargetPosition(targetPosition);
    }

    private void setTargetPositionTolerance(int tolerance) {
        rf.setTargetPositionTolerance(tolerance);
        rb.setTargetPositionTolerance(tolerance);
        lf.setTargetPositionTolerance(tolerance);
        lb.setTargetPositionTolerance(tolerance);
    }

    private void resetEncoders() {
        rf.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

}
