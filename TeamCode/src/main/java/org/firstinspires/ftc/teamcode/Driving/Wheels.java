package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Wheels {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //gear boxes on wheels (should be same for all wheels)
    private int gearReduction = 5*4;
    private double ticksPerRevolution = 28;
    private double wheelCircumference = 7.5 * Math.PI;

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

    private void setAllPowers(double power) {
        rf.setPower(power);
        rb.setPower(power);
        lf.setPower(power);
        lb.setPower(power);
    }

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

    public int getGearReduction() { return gearReduction; }
    public double getTicksPerRevolution() { return ticksPerRevolution; }
    public double getWheelCircumference() { return wheelCircumference; }
}
