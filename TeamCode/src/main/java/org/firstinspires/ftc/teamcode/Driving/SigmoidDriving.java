package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SigmoidDriving {

    private Wheels wheels;

    private double cmPerTick;

    public final int tolerance = 10; //in ticks

    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 20; //measure in some unit

    private double horizontalStretchSigmoid;

    //horizontal shift for sigmoid so that left side lines up with x=0
    //makes point (0, 0.1) on the function
    private double horizontalShiftSigmoid;

    private double sigmoidDomain;

    public SigmoidDriving(Wheels wheels) {
        this.wheels = wheels;

        horizontalStretchSigmoid = 4 * maxAcceleration;
        horizontalShiftSigmoid = Math.log(9) / maxAcceleration;

        //domain of sigmoid function is [-1,1]
        //so total domain is 2 * horizontal stretch
        double sigmoidDomain = 2 / horizontalStretchSigmoid;

        cmPerTick = (1/wheels.wheelCircumference) * wheels.gearReduction * wheels.ticksPerRevolution;
    }

    public void verticalSigmoidDist(int totalDist) {

        int totalTicks = (int) Math.round(totalDist * (1/cmPerTick));

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        timer.reset();
        wheels.resetEncoders();
        while(timer.time() < sigmoidDomain) {
            wheels.vertical(sigmoid((timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid));
        }

        double sigmoidTickDistance = wheels.getAverageCurrentPosition();

        double maxPowerTicks = totalTicks - sigmoidTickDistance;


        while (wheels.getAverageCurrentPosition() < (maxPowerTicks + sigmoidTickDistance)) {
            wheels.vertical(1);
        }

        while (wheels.getAverageCurrentPosition() < (maxPowerTicks + (2*sigmoidTickDistance))) {
            wheels.vertical(sigmoid(-(timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid));
        }

        wheels.stop();

        //TODO throw exception for error
        //try error here and catch in teleOp
        //make class that extends throwable
    }

    /**
     * this is not an async function it will steal your thread
     * @param totalTime total time for movement in milliseconds
     */
    public void verticalSigmoidTime(int totalTime) {

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTime - (2 * sigmoidDomain);

        //acceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            double power = sigmoid((timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            wheels.vertical(power);
        }

        //regular straight motion
        timer.reset();
        while(timer.time() < maxPowerTime) {
            wheels.vertical(1);
        }

        //deceleration period
        timer.reset();
        while(timer.time() < sigmoidDomain) {
            //reflects sigmoid over y axis by negatizing x values
            double power = sigmoid(-(timer.time() + horizontalShiftSigmoid), horizontalStretchSigmoid);
            wheels.vertical(power);
        }

        //stop motion
        wheels.stop();
    }

    //DISTANCE FUNCTIONS

    /**
     * @param maxPower for wheels (not the whole time because of accerlation)
     * @param distance given in cm
     */
    public void verticalDist(double maxPower, double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        wheels.setAllPowers(maxPower);
        wheels.resetEncoders();
        wheels.setAllTargetPosition(targetTicks);
        wheels.runToPosition();
    }

    public void horizontalDist(double maxPower, double distance) {
        int targetTicks = (int) Math.round(distance * (1/cmPerTick));
        wheels.setAllPowers(maxPower);
        wheels.resetEncoders();
        wheels.setEachTargetPosition(-targetTicks,
                targetTicks,
                targetTicks,
                -targetTicks);
        wheels.runToPosition();
    }

    //MATH FUNCTIONS
    private double sigmoid(double time, double horizontalStretchComponent) {
        return (1/(1+Math.exp(-time) * horizontalStretchComponent)); }

    private double sigmoidIntegral(double ticks, double horizontalStretchSigIntegral) {
        return (Math.log(1+Math.exp(ticks*horizontalStretchSigIntegral))/horizontalStretchSigIntegral); }


    //MAX ACCEL CHANGES
    public double getMaxAcceleration() { return maxAcceleration; }
    /**
     * only use for teleOp
     */
    public void adjustMaxAcceleration(double maxAccelerationAdjustment) {
        this.maxAcceleration += maxAccelerationAdjustment;
    }

    public void stop() {
        wheels.stop();
    }

}
