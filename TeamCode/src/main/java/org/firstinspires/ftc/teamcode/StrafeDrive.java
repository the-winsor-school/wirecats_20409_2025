package org.firstinspires.ftc.teamcode;

import android.sax.StartElementListener;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Timer;

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
    public double cmPerTick = (1/wheelCircumference) * (1/gearReduction) * ticksPerRevolution;

    //in ticks
    private int tolerance = 10;
    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 20; //measure in some unit

    //calculate horizontal component of sigmoid function (derivation proves this)
    private double horizontalStretchSigmoid;

    //horizontal shift for sigmoid so that left side lines up with x=0
    //makes point (0, 0.1) on the function
    private double horizontalShiftSigmoid;

    public StrafeDrive(DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb =lb;

        //calculations for sigmoid function
        //derivation done on paper for both
        horizontalStretchSigmoid = 4 * maxAcceleration;
        horizontalShiftSigmoid = Math.log(9) / maxAcceleration;

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

    @Deprecated
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

    //Sigmoid driving

    /**
     * this is not an async function it will steal your thread
     * @param maxPower max power of the wheels
     * @param distance total time for movement in milliseconds
     */
    public void verticalSigmoid(double maxPower, int distance) {

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        Timer() timer = new Timer()

        double totalTicks = distance * (1/cmPerTick);

        //domain of sigmoid function is [-1,1]
        //so total domain is 2 * horizontal stretch
        double sigmoidDomain = 2 * horizontalStretchSigmoid;

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTicks - (2 * sigmoidDomain);

        //acceleration period
        resetEncoders();
        while(getAverageCurrentPosition() < sigmoidDomain) {
            double power = maxPower * sigmoidIntegral((getAverageCurrentPosition() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            vertical(power);
        }

        //regular straight motion
        resetEncoders();
        while(getAverageCurrentPosition() < maxPowerTime) {
            vertical(maxPower);
        }

        //deceleration period
        resetEncoders();
        while(getAverageCurrentPosition() < sigmoidDomain) {
            //reflects sigmoid over y axis by negatizing x values
            double power = maxPower * sigmoidIntegral(-(getAverageCurrentPosition() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            vertical(power);
        }

        //stop motion
        stop();
    }


    private double sigmoid(double time, double horizontalStretchComponent) {
        return (1/(1+Math.exp(-time) * horizontalStretchComponent));
    }

    private double sigmoidIntegral(double ticks, double horizontalStretchSigIntegral) {
        return (Math.log(1+Math.exp(ticks*horizontalStretchSigIntegral))/horizontalStretchSigIntegral);
    }

    /**
     * uses taylor expansion to approximate sigmoid function
     * the approximation gets rid of e^x which is difficult to compute
     * using this approximation makes everything more accurate
     * to add more accuracy to this approximation you need to add another term
     * @param time x input
     * @param horizontalStretchComponent horizontal stretch of x input (horizontalStretchSigmoid)
     *                                  based on the max acceleration
     * @return returns y value (power to give motors)
     */
    private double approxSigmoid(double time, double horizontalStretchComponent) {
        return 1 +
                ((horizontalStretchComponent * time)/8) + //first derivative
                ((Math.pow(horizontalStretchComponent, 3) * Math.pow(time, 2))/24); //second derivative
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    //only use for testing teleOp
    public void adjustMaxAcceleration(double maxAccelerationAdjustment) {
        this.maxAcceleration += maxAccelerationAdjustment;
    }

    /**
     *
      * @param distance given in cm
     */
    public void verticalDist(double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        resetEncoders();
        setTargetPosition(targetTicks);
        runToPosition();
    }

    public void horizontalDist(double maxPower, double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        setAllPowers(maxPower);
        resetEncoders();
        rf.setTargetPosition(-targetTicks);
        rb.setTargetPosition(targetTicks);
        lf.setTargetPosition(targetTicks);
        lb.setTargetPosition(-targetTicks);
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

    public int getAverageCurrentPosition() {
        return (rf.getCurrentPosition() + rb.getCurrentPosition() + lf.getCurrentPosition() + lb.getCurrentPosition()) / 4;
    }

}
