package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Wheels {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //gear boxes on wheels (should be same for all wheels)
    public final int gearReduction = 5*4;
    public final double ticksPerRevolution = 28;
    public final double wheelCircumference = 7.5 * Math.PI;

    public Wheels (DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb = lb;
    }

    public void stop () {
        rf.setPower(0);
        rb.setPower(0);
        lf.setPower(0);
        lb.setPower(0);
    }

    public void setEachPower (double rfp, double rbp, double lfp, double lbp) {
        rf.setPower(rfp);
        rb.setPower(rbp);
        lf.setPower(lfp);
        lb.setPower(lbp);
    }

    public void setAllPowers(double power) {
        rf.setPower(power);
        rb.setPower(power);
        lf.setPower(power);
        lb.setPower(power);
    }

    public void horizontal (double power) { //right positive
        setEachPower(-power, power, power, -power); // -rf, +rb, lf, -lb)
    }

    public void vertical (double power) { //forward positive
        setEachPower(power,  power, power, power); //one side negative -rf, -rb
    }

    public void turn (double t) { setEachPower(t, t, -t, -t); }

    public void runToPosition() {
        rf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        lf.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void setAllTargetPosition(int targetPosition) {
        rf.setTargetPosition(targetPosition);
        rb.setTargetPosition(targetPosition);
        lf.setTargetPosition(targetPosition);
        lb.setTargetPosition(targetPosition);
    }

    public void setEachTargetPosition (int rfTicks, int rbTicks, int lfTicks, int lbTicks) {
        rf.setTargetPosition(rfTicks);
        rb.setTargetPosition(rbTicks);
        lf.setTargetPosition(lfTicks);
        lb.setTargetPosition(lbTicks);
    }

    public void setTargetPositionTolerance(int tolerance) {
        rf.setTargetPositionTolerance(tolerance);
        rb.setTargetPositionTolerance(tolerance);
        lf.setTargetPositionTolerance(tolerance);
        lb.setTargetPositionTolerance(tolerance);
    }

    public void resetEncoders() {
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
