package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SigmoidDriving {

    private Wheels wheels;

    public double cmPerTick;

    private int tolerance = 10; //in ticks

    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 20; //measure in some unit

    //calculate horizontal component of sigmoid function (derivation proves this)
    private double horizontalStretchSigmoid;

    //horizontal shift for sigmoid so that left side lines up with x=0
    //makes point (0, 0.1) on the function
    private double horizontalShiftSigmoid;

    public SigmoidDriving(Wheels wheels) {
        this.wheels = wheels;

        horizontalStretchSigmoid = 4 * maxAcceleration;
        horizontalShiftSigmoid = Math.log(9) / maxAcceleration;

        cmPerTick = (1/wheels.getWheelCircumference()) * wheels.getGearReduction() * wheels.getTicksPerRevolution();
    }

    /**
     * this is not an async function it will steal your thread
     * @param maxPower max power of the wheels
     * @param totalTicks total time for movement in milliseconds
     */
    public void verticalSigmoid(double maxPower, int totalTicks) {
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        //domain of sigmoid function is [-1,1]
        //so total domain is 2 * horizontal stretch
        double sigmoidDomain = 2 * horizontalStretchSigmoid;

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTicks - (2 * sigmoidDomain);

        //acceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            double power = maxPower * sigmoidIntegral((timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
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
            double power = maxPower * sigmoidIntegral(-(timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            vertical(power);
        }

        //stop motion
        stop();
    }

    /**
     *
     * @param maxPower for wheels (not the whole time because of accerlation)
     * @param distance given in cm
     */
    public void verticalDist(double maxPower, double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        wheels.setAllPowers(maxPower);
        wheels.resetEncoders();
        wheels.setTargetPosition(targetTicks);
        wheels.runToPosition();
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

}
