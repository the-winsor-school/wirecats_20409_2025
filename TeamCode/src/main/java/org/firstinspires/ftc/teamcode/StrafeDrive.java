package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;

public class StrafeDrive {

    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lf;
    private DcMotor lb;

    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 5; //measure in some unit

    private double speed = 0.5;

    public StrafeDrive(DcMotor rf, DcMotor rb, DcMotor lf, DcMotor lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb =lb;
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

    //Sigmoid driving

    /**
     * this is not an async function it will steal your thread
     * @param maxPower max power of the wheels
     * @param totalTime total time for movement in milliseconds
     */
    public void verticalSigmoid(double maxPower, int totalTime) {
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        //domain of sigmoid function for acceleration is [-1,1] * maxPower
        //so total domain is 2*maxPower
        //the domain is the time used for acceleration
        double sigmoidDomain = 2 * maxPower;

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTime - (2*sigmoidDomain);

        //calculate horizontal component of sigmoid function (derivation proves this)
        double horizontalStretchSigmoid = 4 * maxAcceleration;

        //used to align sigmoid x=-1 with t=0
        double horizontalShift = sigmoidDomain/2;

        //acceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {

            vertical(maxPower * sigmoid((timer.time() + horizontalShift), horizontalStretchSigmoid));
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
            vertical(maxPower * sigmoid(-timer.time() + horizontalShift, horizontalStretchSigmoid));
        }

        //stop motion
        stop();
    }


    private double sigmoid(double time, double horizontalStretchSigmoid) {
        return (1/(1+Math.exp(-time*horizontalStretchSigmoid)));
    }

    @Deprecated
    private double calculateSigmoidHorizontalStretch(double maxAcceleration, double sigmoidDomain) {
        //setting derivative at inflection point of sigmoid to maxAcceleration and solving for horizontal stretch component
        double inflectionPointLocation = sigmoidDomain/2;
        double horizontalStretchComponent = (8*maxAcceleration)/(inflectionPointLocation - Math.pow(inflectionPointLocation, 2));

        return horizontalStretchComponent;
    }

    //only use for testing teleOp
    public void adjustMaxAcceleration(double maxAccelerationAdjustment) {
        this.maxAcceleration += maxAcceleration;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }
}
