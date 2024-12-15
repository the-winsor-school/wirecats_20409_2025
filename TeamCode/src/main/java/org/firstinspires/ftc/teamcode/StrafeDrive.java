package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class StrafeDrive {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //encoder ticks (given from getCurrentPosition) conversion to revolutions of motor
    //value if hardware and can be looked up on REV website
    private double ticksPerRevolution = 0;

    //gear boxes on wheels (should be same for all wheels)
    private int gearReduction = 0;

    //circumference of mecanum wheels
    private double wheelCircumference = 0;

    //calculated by values above
    private double cmPerTick;

    //in ticks
    private int tolerance = 10;

    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 5; //measure in some unit

    //smallest sigmoid curve that does not go over max acceleration (at x=0)
    private double horizontalStretchSigmoid;

    private double speed = 0.5;

    public StrafeDrive(DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb = lb;

        setTargetPositionTolerance(tolerance);
        cmPerTick = calculateCmPerTicks(ticksPerRevolution, gearReduction, wheelCircumference);

        horizontalStretchSigmoid = calculateSigmoidHorizontalStretch(maxAcceleration);
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

    //Sigmoid driving

    /**
     * this is not an async function it will steal your thread
     * @param maxPower max power of the wheels
     * @param totalTime total time for movement
     */
    private void verticalSigmoid(double maxPower, int totalTime) {
        ElapsedTime timer = new ElapsedTime();

        //domain of sigmoid function for acceleration is [-1,1] * maxPower
        //so total domain is 2*maxPower
        //the domain is the time used for acceleration
        double sigmoidDomain = 2 * maxPower;

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTime - (2*sigmoidDomain);

        //acceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            vertical(maxPower * (sigmoid(timer.time())));
        }

        //regular straight motion
        timer.reset();
        while(timer.time() < maxPowerTime) {
            vertical(maxPower);
        }

        //deceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            //reflects sigmoid over y axis by negatizing x values
            vertical(maxPower * sigmoid(-timer.time()));
        }

        //stop motion
        stop();
    }

    private double sigmoid(double time) {
        double stretchedX = horizontalStretchSigmoid * time;
        return (Math.exp(stretchedX)/(1+Math.exp(stretchedX)));
    }

    private double calculateSigmoidHorizontalStretch(double maxAcceleration) {
        //TODO find the derivative of a sigmoid function as a function of its horizontal stretch component
        return 5;
    }

}
