package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class StrafeDrive {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    private double speed = 0.5;

    //encoder ticks (given from getCurrentPosition) conversion to revolutions of motor
    //value if hardware and can be looked up on REV website
    private double ticksPerRevolution = 28;

    //gear boxes on wheels (should be same for all wheels)
    private int gearReduction = 5*4;

    //circumference of wheels
    private double wheelCircumference = 7.5 * Math.PI;

    //calculated by values above
    //cm/ticks
    public double cmPerTick = (1/wheelCircumference) * gearReduction * ticksPerRevolution;

    //in ticks
    private int tolerance = 10;

    
    public StrafeDrive(DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb = lb;

        setTargetPositionTolerance(tolerance);
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

    private void setAllPowers(double power) {
        rf.setPower(power);
        rb.setPower(power);
        lf.setPower(power);
        lb.setPower(power);
    }

    public void adjustSpeed(double x) {
        speed = speed + x;
    }

    public double getSpeed() { return speed; }

    /**
     *
     * @param maxPower for wheels (not the whole time because of accerlation)
     * @param distance given in cm
     */
    public void verticalDist(double maxPower, double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));

        setAllPowers(.5);
        resetEncoders();
        setTargetPosition(targetTicks);
        runToPosition();
    }

    //Functions to combine wheels
    private void runToPosition() {
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
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
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean wheelsMoving() {
        if (rf.isBusy() || rb.isBusy() || lf.isBusy() || lb.isBusy())
            return true;
        return false;
    }

}
