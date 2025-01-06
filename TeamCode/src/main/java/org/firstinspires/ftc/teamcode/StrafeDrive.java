package org.firstinspires.ftc.teamcode;

import android.sax.StartElementListener;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StrafeDrive {

    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lf;
    private DcMotor lb;

    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 20; //measure in some unit

    //calculate horizontal component of sigmoid function (derivation proves this)
    private double horizontalStretchSigmoid;

    //horizontal shift for sigmoid so that left side lines up with x=0
    //makes point (0, 0.1) on the function
    private double horizontalShiftSigmoid;

    //shift horizontal for the

    private double speed = 0.5;

    public StrafeDrive(DcMotor rf, DcMotor rb, DcMotor lf, DcMotor lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb =lb;

        //calculations for sigmoid function
        //derivation done on paper for both
        horizontalStretchSigmoid = 4 * maxAcceleration;
        horizontalShiftSigmoid = Math.log(9) / maxAcceleration;
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

        //domain of sigmoid function is [-1,1]
        //so total domain is 2 * horizontal stretch
        double sigmoidDomain = 2 * horizontalStretchSigmoid;

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTime - (2 * sigmoidDomain);

        //acceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            double power = maxPower * sigmoid((timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            vertical(power);
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
            double power = maxPower * sigmoid(-(timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            vertical(power);
        }

        //stop motion
        stop();
    }

    @Deprecated
    private double sigmoid(double time, double horizontalStretchComponent) {
        return (1/(1+Math.exp(-time) * horizontalStretchComponent));
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
}
